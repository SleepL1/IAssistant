package dev.sleep.iassistant.client.voice.binding;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import autovalue.shaded.org.apache.commons.lang.ArrayUtils;
import autovalue.shaded.org.apache.commons.lang.StringUtils;
import dev.sleep.iassistant.callback.AssistantSpeakCallback;
import dev.sleep.iassistant.client.voice.component.TokenComponent;

public class GttsBinding {

	private TokenComponent tokenComponent;

	private final String GOOGLE_TTS_URL = "https://translate.google.com/translate_tts";

	public void initGttsBinding(TokenComponent tokenComponent) {
		this.tokenComponent = tokenComponent;
	}

	public void speak(String textToSay, AssistantSpeakCallback assistantSpeakCallback) {
		execute(splitText(textToSay), assistantSpeakCallback);
	}

	private void execute(List<String> splitedText, AssistantSpeakCallback assistantSpeakCallbck) {
		String threadName = "IAssistant-TTS";
		
		new Thread(new Runnable() {
			@Override
			public void run() {	
				CloseableHttpClient closeableHttpClient = null;
				CloseableHttpResponse closeableHttpResponse = null;

				try {
					for (int i = 0; i < splitedText.size(); i++) {
						String textSegment = splitedText.get(i);
						System.out.println(textSegment);
						String url = GOOGLE_TTS_URL + "?ie=UTF-8&q=" + textSegment + "&tl=es&ttsspeed=1&total="
								+ splitedText.size() + "&idx=" + i + "&client=tw-ob&textlen=" + textSegment.length() + "&tk="
								+ tokenComponent.calculateToken(textSegment, null);
						HttpGet get = new HttpGet(GOOGLE_TTS_URL);

						try {
							URI uri = new URI(url);

							get.setURI(uri);
							get.setHeader("Referer", "http://translate.google.com/");
							get.setHeader("User-Agent",
									"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");

							closeableHttpClient = HttpClients.createDefault();
							closeableHttpResponse = closeableHttpClient.execute(get);

							HttpEntity httpEntity = closeableHttpResponse.getEntity();
							System.out.println(closeableHttpResponse.toString());

							if (httpEntity != null) {
								InputStream inputStream = httpEntity.getContent();
								assistantSpeakCallbck.onSpeakFinished();
							}
							
						} catch (URISyntaxException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, threadName).start();
	}

	private List<String> splitText(String textToSay) {
		List<String> splitedText = new ArrayList<String>();

		if (textToSay.length() <= 100) {
			splitedText.add(textToSay);
			return cleanSplitedText(textToSay, splitedText);
		}

		splitedText = tokenizeText(textToSay, 100);
		return cleanSplitedText(textToSay, splitedText);
	}

	private List<String> cleanSplitedText(String textToSay, List<String> splitedText) {
		List<String> cleanedSplitedText = new ArrayList<String>();

		for (int i = 0; i < splitedText.size(); i++) {
			String tempText = strip(splitedText.get(i));

			if (tempText != null) {
				if (!tempText.isEmpty()) {
					cleanedSplitedText.add(tempText);
				}
			}
		}

		return cleanedSplitedText;
	}

	private List<String> tokenizeText(String text, int max_size) {
		String punc = "¡!()[]¿?.,،;:—。、：？！\n";
		char[] puncList = punc.toCharArray();
		String pattern = StringUtils.join(ArrayUtils.toObject(puncList), '|');
		String[] parts = text.split(pattern);

		List<String> minParts = new ArrayList<String>();

		for (String p : parts) {
			minParts.addAll(minimize(p, " ", max_size));
		}

		return minParts;
	}

	private List<String> minimize(String thestring, String delim, int max_size) {
		int beginInx = 0;
		List<String> outStrings = new ArrayList<String>();
		if ((thestring.length()) > max_size) {
			int idx = thestring.lastIndexOf(delim, max_size);
			if (idx > 0) {
				outStrings.add(thestring.substring(beginInx, idx));

				List<String> temp = minimize(thestring.substring(idx), delim, max_size);
				for (String s : temp)
					outStrings.add(s);
			}
		} else {
			outStrings.add(thestring);
		}

		return outStrings;
	}

	private String strip(String textToStrip) {
		return textToStrip.replace("\n", "").trim();
	}
}
