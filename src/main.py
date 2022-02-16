from src.assistant import GenericAssistant

import src.handler.intents as intents
import os

mappings = {'exit-program': intents.stop_assistant}

assistant = GenericAssistant(intent_methods=mappings)
assistant.train_model()

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'

while True:
    assistant.tick()
