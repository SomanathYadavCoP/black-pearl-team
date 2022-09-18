from transformers import pipeline
from flask import Flask, jsonify, request
import re

NO_OF_SENTENCES_READ = 3

app = Flask(__name__)


@app.route('/process', methods=['POST'])
def generate_summary():
    if not request.is_json:
        print("Request is not a JSON object :(")
        return ""
    content = request.get_json()
    transcript = content['text']
    no_of_sentences = len(transcript)
    print(transcript)

    file = open('action_keywords.txt', "r")
    action_keywords_lines = file.readlines()

    action_points = []

    for sentence in transcript:
        action_flag = 0
        for keywords in action_keywords_lines:
            counter = 0
            action_counter = 0
            for keyword in keywords.split(','):
                counter += 1
                if keyword.strip() in sentence.lower():
                    action_counter += 1
                else:
                    action_counter = 0
            if counter == action_counter:
                action_flag = 1
                break

        if action_flag == 1:
            sentence = re.sub('[0-9][0-9]:[0-9][0-9]', '', sentence).replace('[]','')
            #print(sentence)
            #if ']' in sentence:
            #    sentence = sentence.split(']')[1]
            action = sentence.replace(':', ' said that, ')
            action_points.append(action)

    print(action_points)

    total_summary = []
    summarizer = pipeline("summarization", model="knkarthick/MEETING_SUMMARY")
    for i in range(0, no_of_sentences, NO_OF_SENTENCES_READ):
        data = '. '.join(transcript[i:i + NO_OF_SENTENCES_READ])
        summary = summarizer(data[:1024])
        total_summary.append(summary[0]['summary_text'])

    print(total_summary)

    return jsonify({
        "summary": total_summary,
        "action_points": action_points
    })


if __name__ == "__main__":
    app.run()
