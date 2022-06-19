import sys


def stop_assistant(generic_assistant, message, speech, translator):
    speech.say(generic_assistant.request_response(message))
    sys.exit(0)
