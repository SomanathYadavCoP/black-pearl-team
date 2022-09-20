## Team Name : Black Pearl Team ("Nigh Uncatchable")

# MoM BoT (Minutes of Meeting Bot)

* Web conferencing and online meetings have become the new work culture nowadays. But somewhere along the lines, we often keep missing the crux of the discussions in  these online meetings, and here comes our solution - MoM BoT. 

* MoM (minutes of meetings) MeetingBot is an automatic meeting transcription system with real-time recognition, summarization to generate important points and actionables from any meeting, transcripts.

* It is your easy to use online meeting assistant that backs you up with automated meeting minutes in every conversation. It will help you save time on board meetings, team management, and customer support such that you just focus on the conversation and never miss what’s important.

* It could be used along with all video conferencing tools - Skype, Google meet, Zoom, Microsoft Teams, and more. 
* What’s more special is - using our Natural Language Processing algorithms, it will extract essential Action Items, Decisions, and crucial Insights, and transcript and turn them into a comprehensive, collaborative meeting summary.

* # Project Structure :
   * Frontend : Python 3.10.5 , Agent installed in Skype, Google meet, Zoom, Microsoft Team 
                  which capable of invoking REST API hosted on Microsoft Azure with vital meeting information and meeting transcript in json format.

    * Backend : SpringBoot Java 11 , REST API 
    
    * Testing platform : POSTMAN

    * Other Technologies : Machine Learning , Natural Language Processing algorithms Library
    
    * Email Generator : Apache FreeMarker Template Engine
    
    * Version Control: Git and GitHub
    
    * Deployment Platform : Docker Build , Microsoft Azure
    
    * Code Editor and tools: IntelliJ IDEA, PyCharm.
    

* # Features:
    * Remove the Distraction of Note-Taking
    * Reduce Ineffective Meetings
    * Help People Do Their Best Work
    * Automatically creates a meeting Summary Points and Action Items after each meeting
    * Analysing the ambience of a meeting by performing sentiment analysis on it.
    * Share, edit, search and collaborate on the MOMs
    * Dashboard to organize the MoMs
    * Send mail to the user Participants when the MoM is generated.

* # Run the Application using Docker at your local system:
Two Docker images are pushed in docker hub at this URL -  https://hub.docker.com/r/somanathyadav
### Prerequisite - 
  1. Docker should be installed on your machine.
  2. Postman application should be installed and logged in.
  3. 
### Run instructions - 
  1. For backend Python NLP processor API, get the image using command in command prompt- 
        docker pull somanathyadav/hackathonpy
  2. Run the Python API docker image using below docker run command in same command prompt -
        docker run --rm -p 80:80 somanathyadav/hackathonpy:latest
  3.  Open new command prompt and get the REST API - Springboot docker image using below command - 
        docker pull somanathyadav/blackpearl     
  4. In same command prompt, get machine's IPv4 Address using "ipconfig" command - 
     For now, lets take it as  <192.168.50.50>  (It will be used in input parameter of next docker run command)
  5. Run the REST API docker image using docker run -
     (Please change the ipconfig got in previous step)
        docker run --rm --env=spring.mail.username=somanath.yadav@gmail.com --env spring.mail.password=xmnxomrbftsofcgkpy --env python.summary.api.url=http://192.168.50.50/process -p 8088:8088 somanathyadav/blackpearl:latest
  6. Using Postman, create a request to post the JSON to below API -
      a. Enter the API in postman -   http://localhost:8088/api/submit-transcript-api
      b. Input the JSON file commited in GIT path - black-pearl-team/java-springboot-api/src/test/api/Meeting_1_Request.json
      
     
