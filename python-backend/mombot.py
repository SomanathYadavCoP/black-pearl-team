from transformers import pipeline
from flask import Flask, jsonify, request
from flask_cors import CORS
import os

base_path = os.getcwd()

NO_OF_SENTENCES_READ = 3

app = Flask(__name__)
app.secret_key = 'asdf'
app._static_folder = base_path
app._static_files_root_folder_path = base_path

CORS(app)

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

        if action_flag == 1 and '?' not in sentence:
            while "[" in sentence and "]" in sentence:
                rem = ""
                start = sentence.find("[")
                end = sentence.find("]")
                if start != -1 and end != -1:
                    rem = "[" + sentence[start + 1:end] + "]"
                sentence = sentence.replace(rem, '')

            file = open('delete_keywords.txt', "r")
            delete_keywords_lines = file.readlines()

            replace_string = sentence.split(':')[1].split(',')[0]
            sentence = sentence.replace(replace_string + ',', '')
            action = sentence.replace(':', ' - ')
            action_points.append(action)

    print(action_points)

    total_summary = []
    summarizer = pipeline("summarization", model="knkarthick/MEETING_SUMMARY")
    for i in range(0, no_of_sentences, NO_OF_SENTENCES_READ):
        data = '. '.join(transcript[i:i + NO_OF_SENTENCES_READ])
        if len(data) < 200:
            continue
        summary = summarizer(data[:1024])
        total_summary.append(summary[0]['summary_text'])

    print(total_summary)

    return jsonify({
        "summary": total_summary,
        "actionItems": action_points
    })


if __name__ == "__main__":
    print(' * Starting app with base path:', base_path)
    app.debug = True
    app.run(host='0.0.0.0', threaded=True, port=80)
