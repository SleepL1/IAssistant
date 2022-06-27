package dev.sleep.iassistant.client.voice.binding;

import java.io.IOException;

import org.vosk.Model;
import org.vosk.Recognizer;

public class VoskBinding {

	private Model languageModel;
	private Recognizer recognizer;
	
	public void initBinding() {
		try {
			languageModel = new Model("src/main/resources/assets/dev/sleep/model/model-es-0.22");
			recognizer = new Recognizer(languageModel, 120000);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public Model getLanguageModel() {
		return languageModel;
	}

	public Recognizer getRecognizer() {
		return recognizer;
	}
}
