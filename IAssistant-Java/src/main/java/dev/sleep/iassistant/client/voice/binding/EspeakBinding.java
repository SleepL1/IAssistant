package dev.sleep.iassistant.client.voice.binding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dev.sleep.iassistant.callback.AssistantSpeakCallback;
import dev.sleep.iassistant.client.voice.component.VoiceComponent;

public class EspeakBinding {
	
	/** UNUSED AT THE MOMENT **/

	private VoiceComponent voiceComponent;
	
	public void initEspeakBinding(VoiceComponent voiceComponent) {
		this.voiceComponent = voiceComponent;
	}

	public void speak(String textToSay, AssistantSpeakCallback assistantSpeakCallback) {
		execute(assistantSpeakCallback, "espeak-ng", "-v", buildVariant(voiceComponent), "-p", Integer.toString(voiceComponent.getPitch()),
				"-a", Integer.toString(voiceComponent.getAmplitude()), "-s",
				Integer.toString(voiceComponent.getSpeed()), "-g", Integer.toString(voiceComponent.getGap()), textToSay);
	}

	private String buildVariant(VoiceComponent voice) {
		StringBuilder builder = new StringBuilder();
		if (voice.getLanguageIdentifier() != null && !voice.getLanguageIdentifier().isEmpty()) {
			builder.append(voice.getLanguageIdentifier());
		}

		if (voice.getVariant() != null && !voice.getVariant().isEmpty()) {
			builder.append("+");
			builder.append(voice.getVariant());
		}

		return builder.toString();
	}

	private static void execute(AssistantSpeakCallback assistantSpeakCallback, final String... command) {
		String threadName = "iassistant-espeak";

		new Thread(new Runnable() {
			public void run() {
				ProcessBuilder b = new ProcessBuilder(command);
				b.redirectErrorStream(true);
				try {
					Process process = b.start();

					readErrors(process);
					process.waitFor();
					process.destroy();
					
					assistantSpeakCallback.onSpeakFinished();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

			private void readErrors(Process process) throws IOException {
				BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					System.err.println(line);
				}
			}
		}, threadName).start();
	}

}
