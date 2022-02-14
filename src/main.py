import os
from src.logic import speech

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'

while True:
    speech.listen()



