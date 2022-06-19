import json
import src.logic.lang.language as language

assistant_intents_file = open(f'../resources/intents/assistant-intents-{language.intents_lang}.json', "r",
                              encoding='utf8')
assistant_intents = json.load(assistant_intents_file)
