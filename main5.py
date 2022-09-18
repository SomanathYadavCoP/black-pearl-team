from transformers import pipeline
import os
from flask import Flask, jsonify
import requests
NO_OF_LINES_READ = 10
port = 9999

def read_transcript(file_name):
    file = open(file_name, "r")
    filedata = file.readlines()
    return filedata

app = Flask(__name__)

@app.route('/summarize', methods=['POST'])
def generate_summary(file_path='transcript.txt'):
    # Read and make string of entire file
    filedata = read_transcript(file_path)
    filelines = len(filedata)
    total_summary = []
    summarizer = pipeline("summarization", model="knkarthick/MEETING_SUMMARY")
    for i in range(0, filelines, NO_OF_LINES_READ):
        data = '. '.join(filedata[i:i + NO_OF_LINES_READ])
        summary = summarizer(data[:1024])
        print(summary)
        total_summary.append(summary['summary_text'])
        final_summary = '. '.join(total_summary)

    print(final_summary)
    return jsonify({
        "summary": final_summary})

if __name__ == "__main__":
    app.run()
    #print('Running as testing server.')
    #app.debug = True
    #app.run(host='0.0.0.0', threaded=True, port=port)
