package dev.sleep.iassistant.client.voice;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

import dev.sleep.iassistant.MainIAssistant;
import dev.sleep.iassistant.callback.AssistantListenCallback;
import dev.sleep.iassistant.client.State;
import dev.sleep.iassistant.client.voice.binding.VoskBinding;
import dev.sleep.iassistant.client.voice.component.AudioComponent;

public class SpeechToText {
	
	private VoskBinding voskBinding;
	private AudioComponent audioComponent;
	
	public void initSpeechToText() {
		voskBinding = new VoskBinding();
		voskBinding.initBinding();
		
		audioComponent = new AudioComponent();
		audioComponent.initAudio();
	}
	
	public void listen(MainIAssistant assistant) {
		try {
			assistant.currentState = State.LISTENING;
			
			audioComponent.setMicrophone((TargetDataLine) AudioSystem.getLine(audioComponent.getAudioDataInfo()));
			audioComponent.getMicrophone().open(audioComponent.getAudioFormat());
			audioComponent.getMicrophone().start();

			int numBytesRead;
			int CHUNK_SIZE = 1024;
			byte[] newByte = new byte[4096];

			while (assistant.currentState == State.LISTENING) {
				numBytesRead = audioComponent.getMicrophone().read(newByte, 0, CHUNK_SIZE);
				
				if(passWaveForm(assistant, newByte, numBytesRead)) {
					clean();
					
					assistant.currentState = State.WORKING;
					assistant.perfomRequest();
				}
			}
		} catch (Exception e) {
			System.out.println("Failed to capture audio: " + e.getLocalizedMessage());
		}
	}
	
	public void listen(MainIAssistant assistant, AssistantListenCallback audioCaptureCallback) {
		try {
			assistant.currentState = State.LISTENING;
			
			audioComponent.setMicrophone((TargetDataLine) AudioSystem.getLine(audioComponent.getAudioDataInfo()));
			audioComponent.getMicrophone().open(audioComponent.getAudioFormat());
			audioComponent.getMicrophone().start();

			int numBytesRead;
			int CHUNK_SIZE = 1024;
			byte[] newByte = new byte[4096];

			while (assistant.currentState == State.LISTENING) {
				numBytesRead = audioComponent.getMicrophone().read(newByte, 0, CHUNK_SIZE);
				
				if(passWaveForm(assistant, newByte, numBytesRead)){
					clean();
					
					assistant.currentState = State.WORKING;
					audioCaptureCallback.onListenFinished();
				}
			}
		} catch (Exception e) {
			System.out.println("Failed to capture audio: " + e.getLocalizedMessage());
		}
	}

	private boolean passWaveForm(MainIAssistant assistant, byte[] newByte, int numBytesRead) {
		if (voskBinding.getRecognizer().acceptWaveForm(newByte, numBytesRead)) {
			return true;
		}
		
		return false;
	}

	private void clean() {
		audioComponent.getMicrophone().close();
	}
	
	public VoskBinding getVoskBinding() {
		return voskBinding;
	}
}
