package dev.sleep.iassistant.client.voice.component;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioComponent {

	private AudioFormat audioFormat;
	private DataLine.Info audioDataInfo;
	private TargetDataLine microphone;

	public void initAudio() {
		audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 60000, 16, 2, 4, 44100, false);
		audioDataInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
	}

	public void setAudioFormat(AudioFormat audioFormat) {
		this.audioFormat = audioFormat;
	}

	public void setAudioDataInfo(DataLine.Info audioDataInfo) {
		this.audioDataInfo = audioDataInfo;
	}

	public void setMicrophone(TargetDataLine microphone) {
		this.microphone = microphone;
	}

	public AudioFormat getAudioFormat() {
		return audioFormat;
	}

	public DataLine.Info getAudioDataInfo() {
		return audioDataInfo;
	}

	public TargetDataLine getMicrophone() {
		return microphone;
	}
}
