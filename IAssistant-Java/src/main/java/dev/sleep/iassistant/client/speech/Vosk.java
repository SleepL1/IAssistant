package dev.sleep.iassistant.client.speech;

import java.io.IOException;

import org.vosk.Model;
import org.vosk.Recognizer;

public class Vosk {

	private Model languageModel;
	private Recognizer recognizer;

	public void initModelAndRecognizer() {
		try {
			languageModel = new Model("src/main/resources/assets/dev/sleep/model/model-es-0.22");
			recognizer = new Recognizer(languageModel, 120000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Recognizer getRecognizer() {
		return recognizer;
	}

}
