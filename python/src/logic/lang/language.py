from translate import Translator
import src.logic.data.data_preferences as data_preference

intents_lang = 'en'
speech_rec_lang = 'en-EN'
translate_lang = 'en'

if data_preference.get_language() == 'es':
    intents_lang = 'es'
    speech_rec_lang = 'es-ES'
    translate_lang = 'es'

translator = Translator(to_lang=translate_lang)

