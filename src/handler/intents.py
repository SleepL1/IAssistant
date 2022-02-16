import sys


def stop_assistant(generic_assistant, speech, message):
    speech.say(generic_assistant.request_response(message))
    sys.exit(0)
