import pyttsx3 as tts
import speech_recognition as spr
import src.logic.lang.language as language
import src.logic.data.data_preferences as data_preferences

recognizer = spr.Recognizer()
speaker = tts.init()

speaker.setProperty('rate', 150)
voices = speaker.getProperty('voices')
speaker.setProperty('voice', voices[0])

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

            message = recognizer.recognize_google(audio, language=language.speech_rec_lang)
            validate_message(message, generic_assistant)
    except spr.UnknownValueError:
        recognizer = spr.Recognizer()
        waiting_order = False


def validate_message(message, generic_assistant):
    global recognizer
    global waiting_order

    message = message.lower()
    message = normalize_message(message)

    print(message)
    if waiting_order:
        if data_preferences.get_assistant_name() in message:
            message = message.replace(data_preferences.get_assistant_name(), '')
            generic_assistant.request(message)
            return

        generic_assistant.request(message)
        waiting_order = False

    if message == data_preferences.get_assistant_name():
        print("Waiting for order")
        waiting_order = True
        recognizer = spr.Recognizer()
        return

    if data_preferences.get_assistant_name() in message:
        message = message.replace(data_preferences.get_assistant_name(), '')
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
