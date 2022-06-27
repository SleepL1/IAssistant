package dev.sleep.iassistant.client.voice;

import dev.sleep.iassistant.callback.AssistantSpeakCallback;
import dev.sleep.iassistant.client.voice.binding.GttsBinding;
import dev.sleep.iassistant.client.voice.component.TokenComponent;

public class TextToSpeech {

	private GttsBinding gttsBinding;
	private TokenComponent tokenComponent;
	
	public void initTextToSpeech() {
		gttsBinding = new GttsBinding();
		tokenComponent = new TokenComponent();
		
		gttsBinding.initGttsBinding(tokenComponent);
	}

	
	public void speak(String textToSay, AssistantSpeakCallback assistantSpeakCallback) {
		gttsBinding.speak(textToSay, assistantSpeakCallback);
	}
}
