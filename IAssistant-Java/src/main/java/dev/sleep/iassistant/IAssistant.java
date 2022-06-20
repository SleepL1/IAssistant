package dev.sleep.iassistant;

import org.vosk.LibVosk;
import org.vosk.LogLevel;

import dev.sleep.iassistant.callback.AudioCaptureCallback;
import dev.sleep.iassistant.client.audio.Audio;
import dev.sleep.iassistant.client.resource.Resource;
import dev.sleep.iassistant.client.speech.Vosk;
import dev.sleep.iassistant.client.state.State;

public class IAssistant {

	private Resource resource;
	private Audio audio;
	private Vosk vosk;

	public State currentState;

	public void init() {
		LibVosk.setLogLevel(LogLevel.WARNINGS);
		
		resource = new Resource();
		vosk = new Vosk();
		audio = new Audio();
 
		resource.initResources();
		vosk.initModelAndRecognizer();
		audio.initAudio();		
	}

	public void listen() {
		audio.captureAudio(this);
	}
	
	public void listen(AudioCaptureCallback audioCaptureCallback) {
		audio.captureAudio(this, audioCaptureCallback);
	}

	public void perfomRequest() {		
		System.out.println(vosk.getRecognizer().getFinalResult());
		listen();
	}
	
	public Resource getResourceInstance() {
		return resource;
	}

	public Vosk getVoskInstance() {
		return vosk;
	}

	public Audio getAudioInstance() {
		return audio;
	}
}
