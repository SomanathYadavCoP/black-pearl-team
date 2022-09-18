FROM python:3.10

COPY requirements.txt requirements.txt

RUN pip install -r requirements.txt

COPY mombot.py mombot.py
COPY nlp_model.py nlp_model.py
RUN python -m pip install -U pip
RUN python -m pip install torch torchvision torchaudio

RUN python nlp_model.py

EXPOSE 80
ENTRYPOINT [ "python" ]
CMD [ "mombot.py" ]