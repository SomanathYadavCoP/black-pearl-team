from transformers import pipeline
import os
from flask import Flask, jsonify
import smtplib
import requests
from email.mime.text import MIMEText
NO_OF_LINES_READ = 10
MY_EMAIL = "milindbobade@gmail.com"

def read_transcript(file_name):
    file = open(file_name, "r", encoding='utf-8')
    filedata = file.readlines()
    return filedata

app = Flask(__name__)

@app.route('/')
def generate_summary(file_path='transcript.txt'):
    # Read and make string of entire file
    filedata = read_transcript(file_path)
    filelines = len(filedata)
    print(filedata)

    participants = []

    for fileline in filedata:
        if ':' in fileline:
            participant = fileline.split(':')[0]
            if participant not in participants:
                participants.append(participant)

    print(participants)

    total_summary = []
    summarizer = pipeline("summarization", model="knkarthick/MEETING_SUMMARY")
    for i in range(0, filelines, NO_OF_LINES_READ):
        data = '. '.join(filedata[i:i + NO_OF_LINES_READ])
        summary = summarizer(data[:1024])
        print(summary)
        total_summary.append(summary[0]['summary_text'])
        final_summary = ' '.join(total_summary)

    print(final_summary)

    with open("mail_template.txt") as template:
        message = template.read().replace("[Participants]", ','.join(participants)).replace("[Summary]", final_summary)
    with smtplib.SMTP("smtp.mailtrap.io", 2525) as server:
        server.starttls()
        server.login("7f8de7d4b9ba45", "228e0264aedbda")
        server.sendmail(from_addr=MY_EMAIL,
                        to_addrs="milindbobade@gmail.com",
                        msg=f"Subject: Meeting MOM\n\n{message}")

    #return jsonify({
    #    "summary": final_summary,
    #    "participants": ','.join(participants)
    #   })

if __name__ == "__main__":
    #app.run()
    #print('Running as testing server.')
    #app.debug = True
    #app.run(host='0.0.0.0', threaded=True, port=port)
    generate_summary()
