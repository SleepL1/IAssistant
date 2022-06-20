package dev.sleep.iassistant.client.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import dev.sleep.iassistant.IAssistant;
import dev.sleep.iassistant.callback.AudioCaptureCallback;
import dev.sleep.iassistant.client.state.State;

public class Audio {

	private AudioFormat audioFormat;
	private DataLine.Info audioDataInfo;
	private TargetDataLine microphone;

	public void initAudio() {
		audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 60000, 16, 2, 4, 44100, false);
		audioDataInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
	}

	public void captureAudio(IAssistant assistant) {
		try {
			assistant.currentState = State.LISTENING;
			
			microphone = (TargetDataLine) AudioSystem.getLine(audioDataInfo);
			microphone.open(audioFormat);
			microphone.start();

			int numBytesRead;
			int CHUNK_SIZE = 1024;
			byte[] newByte = new byte[4096];

			while (assistant.currentState == State.LISTENING) {
				numBytesRead = microphone.read(newByte, 0, CHUNK_SIZE);
				
				if(passWaveForm(assistant, newByte, numBytesRead)){
					clean();
					
					assistant.currentState = State.IDLE;
					assistant.perfomRequest();
				}
			}
		} catch (Exception e) {
			System.out.println("Failed to capture audio: " + e.getLocalizedMessage());
		}
	}
	
	public void captureAudio(IAssistant assistant, AudioCaptureCallback audioCaptureCallback) {
		try {
			assistant.currentState = State.LISTENING;
			
			microphone = (TargetDataLine) AudioSystem.getLine(audioDataInfo);
			microphone.open(audioFormat);
			microphone.start();

			int numBytesRead;
			int CHUNK_SIZE = 1024;
			byte[] newByte = new byte[4096];

			while (assistant.currentState == State.LISTENING) {
				numBytesRead = microphone.read(newByte, 0, CHUNK_SIZE);
				
				if(passWaveForm(assistant, newByte, numBytesRead)){
					clean();
					
					assistant.currentState = State.IDLE;
					audioCaptureCallback.onAudioCaptured();
				}
			}
		} catch (Exception e) {
			System.out.println("Failed to capture audio: " + e.getLocalizedMessage());
		}
	}

	private static boolean passWaveForm(IAssistant assistant, byte[] newByte, int numBytesRead) {
		if (assistant.getVoskInstance().getRecognizer().acceptWaveForm(newByte, numBytesRead)) {
			return true;
		}
		
		return false;
	}

	private void clean() {
		microphone.close();
	}

}
