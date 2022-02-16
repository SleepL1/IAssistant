import json

assistant_info_file = open('../resources/data/assistant-preferences.json', "r", encoding='utf8')
assistant_info = json.load(assistant_info_file)


def get_language():
    return assistant_info['language'].lower()


def get_assistant_name():
    return assistant_info['name'].lower()
