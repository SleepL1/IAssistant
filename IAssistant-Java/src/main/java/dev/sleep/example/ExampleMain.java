package dev.sleep.example;

import dev.sleep.iassistant.MainIAssistant;

public class ExampleMain {

	public static void main(String[] args) {
		MainIAssistant genericAssistant = new MainIAssistant();

		genericAssistant.init();
		genericAssistant.listen();
	}
}
