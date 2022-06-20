package dev.sleep.iassistant.client.resource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import dev.sleep.iassistant.client.data.AssistantData;

public class Resource {

	private AssistantData assistantData;
	private Gson gson;

	private final String DATA_DIRECTORY_PATH = System.getenv("APPDATA") + "\\IAssistant";

	public void initResources() {
		gson = new GsonBuilder().setPrettyPrinting().create();

		createAssistantDataDirectory();
		createAssistantDataFile();
	}

	public void createAssistantDataDirectory() {
		File dataDirectoryFile = new File(DATA_DIRECTORY_PATH);

		if (dataDirectoryFile.exists()) {
			return;
		}

		dataDirectoryFile.mkdir();
	}

	public void createAssistantDataFile() {
		File dataFile = new File(DATA_DIRECTORY_PATH + "\\assistantData.json");

		if (dataFile.exists()) {
			populateDataFromJson(dataFile);
			return;
		}

		try {
			dataFile.createNewFile();
			populateJsonFromData(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void populateDataFromJson(File dataFile) {
		try {
			assistantData = gson.fromJson(Files.asCharSource(dataFile, Charsets.UTF_8).read(), AssistantData.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void populateJsonFromData(File dataFile) {
		try(FileWriter writer = new FileWriter(dataFile)) {
			assistantData = new AssistantData();
			gson.toJson(assistantData, writer);
			
			writer.flush();
			writer.close();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public AssistantData getAssistantData() {
		return assistantData;
	}
}
