package dev.sleep.example;

import dev.sleep.iassistant.IAssistant;

public class ExampleMain {

	public static void main(String[] args) {
		IAssistant genericAssistant = new IAssistant();

		genericAssistant.init();
		genericAssistant.listen();
	}
}
