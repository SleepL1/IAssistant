package dev.sleep.iassistant;

import org.vosk.LibVosk;
import org.vosk.LogLevel;

import dev.sleep.iassistant.client.audio.Audio;
import dev.sleep.iassistant.client.speech.Vosk;

public class IAssistant {

	private Audio audio;
	private Vosk vosk;

	public boolean listening;

	public void init() {
		LibVosk.setLogLevel(LogLevel.DEBUG);

		vosk = new Vosk();
		audio = new Audio();
 
		vosk.initModelAndRecognizer();
		audio.initAudio();		
	}

	public void listen() {
		listening = true;
		audio.captureAudio(this);
	}

	public void perfomRequest() {
		listening = false;
		
		System.out.println(vosk.getRecognizer().getFinalResult());
		listen();
	}

	public Vosk getVoskInstance() {
		return vosk;
	}

	public Audio getAudioInstance() {
		return audio;
	}
}
