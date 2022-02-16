import pyttsx3 as tts
import speech_recognition as spr

recognizer = spr.Recognizer()
speaker = tts.init()

waiting_order = False


def say(message):
    speaker.say(message)
    speaker.runAndWait()


def listen(generic_assistant):
    print("Listening")
    global recognizer
    global waiting_order

    try:
        with spr.Microphone() as mic:
            recognizer.adjust_for_ambient_noise(mic, duration=0.2)
            audio = recognizer.listen(mic)

            message = recognizer.recognize_google(audio, language="es-ES")
            validate_message(message, generic_assistant)
    except spr.UnknownValueError:
        recognizer = spr.Recognizer()
        waiting_order = False


def validate_message(message, generic_assistant):
    global recognizer
    global waiting_order

    message = message.lower()
    message = normalize_message(message)

    if waiting_order:
        if generic_assistant.get_assistant_name() in message:
            message = message.replace(generic_assistant.get_assistant_name(), '')
            generic_assistant.request(message)
            return

        generic_assistant.request(message)
        waiting_order = False

    if message == generic_assistant.get_assistant_name():
        print("Waiting for order")
        waiting_order = True
        recognizer = spr.Recognizer()
        return

    if generic_assistant.get_assistant_name() in message:
        message = message.replace(generic_assistant.get_assistant_name(), '')
        generic_assistant.request(message)
        return

    recognizer = spr.Recognizer()


def normalize_message(message):
    replacements = (
        ("á", "a"),
        ("é", "e"),
        ("í", "i"),
        ("ó", "o"),
        ("ú", "u"),
    )
    for a, b in replacements:
        message = message.replace(a, b).replace(a.upper(), b.upper())

    return message
