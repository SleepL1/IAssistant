from src.ia.neural import GenericNeural
from src.handler import intents

mappings = {'exit-program': intents.stop_assistant}
genericAssistant = GenericNeural(mappings)
genericAssistant.train_model()
