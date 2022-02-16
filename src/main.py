import os

from src.ia.assistant import genericAssistant

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'

while True:
    genericAssistant.run()



