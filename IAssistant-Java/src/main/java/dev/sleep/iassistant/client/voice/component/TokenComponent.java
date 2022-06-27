package dev.sleep.iassistant.client.voice.component;

import java.util.ArrayList;
import java.util.List;

public class TokenComponent {

	/**
	 * Recursively split a string in the largest chunks possible from the highest
	 * position of a delimiter all the way to a maximum size
	 * 
	 * Args: text (string): The string to split. delim (string): The delimiter to
	 * split on. max_size (int): The maximum size of a chunk.
	 * 
	 * Returns: list: the minimized string in tokens
	 * 
	 * @return
	 **/
	public List<String> minimizeToken(String text, String delimitation, int maxSize) {
		List<String> outStrings = new ArrayList<>();

		if (text.length() > maxSize) {
			int lastIndex = text.lastIndexOf(delimitation, maxSize);

			if (lastIndex > 0) {
				outStrings.add(text.substring(0, lastIndex));
				List<String> tempTextList = minimizeToken(text.substring(lastIndex), delimitation, maxSize);

				for (String tempText : tempTextList) {
					outStrings.add(tempText);
				}
			}
		} else {
			outStrings.add(text);
		}

		return outStrings;
	}

	/**
	 * Generates a Google Translate URL
	 * 
	 * Args: tld (string): Top-level domain for the Google Translate host, i.e
	 * ``https://translate.google.<tld>``. path: (string): A
	 * 
	 * path to append to the Google Translate host, i.e
	 * ``https://translate.google.com/<path>``.
	 * 
	 * Returns: string: A Google Translate URL `https://translate.google.<tld>/path`
	 **/
	public String translateUrl(String tld, String path) {
		return "https://translate.google." + tld + "/" + path;
	}
}
