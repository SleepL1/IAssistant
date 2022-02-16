import json

assistant_info_file = open('../resources/data/assistant-info.json', "r", encoding='utf8')
assistant_info = json.load(assistant_info_file)

assistant_intents_file = open('../resources/intents/assistant-intents.json', "r", encoding='utf8')
assistant_intents = json.load(assistant_intents_file)