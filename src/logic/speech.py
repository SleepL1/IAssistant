import pyttsx3 as tts
import speech_recognition as spr
from src.ia.neural import GenericNeural as genericNeural

recognizer = spr.Recognizer()
speaker = tts.init()


def say(message):
    speaker.say(message)
    speaker.runAndWait()


def listen():
    print("Listening")
    global recognizer

    try:
        with spr.Microphone() as mic:
            recognizer.adjust_for_ambient_noise(mic, duration=0.2)
            audio = recognizer.listen(mic)

            message = recognizer.recognize_google(audio, language="es-ES")
            validate_message(message)
    except spr.UnknownValueError:
        recognizer = spr.Recognizer()


def validate_message(message):
    global recognizer

    message = message.lower()
    message = normalize_message(message)

    # Hardcoded name for now
    if 'assistantname' in message:
        message = message.replace('assistantname', '')
        genericNeural.request(message)
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
