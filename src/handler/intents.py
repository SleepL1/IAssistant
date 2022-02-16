import sys


def stop_assistant(generic_assistant, message, speech):
    speech.say(generic_assistant.request_response(message))
    sys.exit(0)
