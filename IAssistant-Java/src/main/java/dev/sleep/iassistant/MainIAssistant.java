package dev.sleep.iassistant;

import org.vosk.LibVosk;
import org.vosk.LogLevel;

import dev.sleep.iassistant.callback.AssistantListenCallback;
import dev.sleep.iassistant.callback.AssistantSpeakCallback;
import dev.sleep.iassistant.client.State;
import dev.sleep.iassistant.client.resource.Resource;
import dev.sleep.iassistant.client.voice.SpeechToText;
import dev.sleep.iassistant.client.voice.TextToSpeech;

public class MainIAssistant {

	private Resource resource;
	private SpeechToText speechToText;
	private TextToSpeech textToSpeech;

	public State currentState;

	public boolean xd = false;

	public void init() {
		LibVosk.setLogLevel(LogLevel.WARNINGS);
		currentState = State.STARTING;

		resource = new Resource();
		speechToText = new SpeechToText();
		textToSpeech = new TextToSpeech();

		resource.initResources();
		speechToText.initSpeechToText();
		textToSpeech.initTextToSpeech();
	}

	public void listen() {
		speechToText.listen(this);
	}

	public void listen(AssistantListenCallback audioCaptureCallback) {
		speechToText.listen(this, audioCaptureCallback);
	}

	public void speak(String textToSay, AssistantSpeakCallback assistantSpeakCallback) {
		textToSpeech.speak(textToSay, assistantSpeakCallback);
	}

	public void perfomRequest() {
		speak("Test", new AssistantSpeakCallback() {
			@Override
			public void onSpeakFinished() {
				listen();
			}
		});
	}

	public Resource getResourceInstance() {
		return resource;
	}

	public SpeechToText getSpeechToText() {
		return speechToText;
	}

	public TextToSpeech getTextToSpeech() {
		return textToSpeech;
	}
}
