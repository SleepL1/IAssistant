import src.logic.data.data_preferences as data_preference

intents_lang = 'en'
speech_rec_lang = 'en-EN'

if data_preference.get_language() == 'es':
    intents_lang = 'es'
    speech_rec_lang = 'es-ES'
