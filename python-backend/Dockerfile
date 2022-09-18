FROM python:3.10

COPY requirements.txt requirements.txt

RUN pip install -r requirements.txt

COPY action_keywords.txt action_keywords.txt
COPY delete_keywords.txt delete_keywords.txt
COPY mombot.py mombot.py
COPY nlp_model.py nlp_model.py
RUN python -m pip install -U pip
RUN python -m pip install torch torchvision torchaudio

RUN python nlp_model.py

EXPOSE 80
ENTRYPOINT [ "python" ]
CMD [ "mombot.py" ]