from math import ceil
import sys
import os
import math

# Pegasus

from transformers import PegasusForConditionalGeneration, PegasusTokenizer


def read_transcript(file_name):
    file = open(file_name, "r")
    filedata = file.read()
    print("Transcript\n")
    #print(filedata)
    print("Transcript END\n")

    return filedata

def generate_summary(file_path, file_name):
    # Read and make string of entire file
    filedata = read_transcript(file_path)

    # Use Pegasus-xsum
    tokenizer = PegasusTokenizer.from_pretrained("google/pegasus-xsum")
    model = PegasusForConditionalGeneration.from_pretrained("google/pegasus-xsum")


    tokens = tokenizer(filedata, truncation=True, padding="longest", return_tensors="pt")
    print(tokens)
    summarize_text = model.generate(**tokens)

    summary = tokenizer.decode(summarize_text[0])

    print("Summary\n")
    print(summary)
    print("Summary END\n")


def main(file_name):
    generate_summary(f"{os.path.dirname(os.path.realpath(__file__))}/{file_name}", file_name)


if __name__ == "__main__":
    main('transcript.txt')
