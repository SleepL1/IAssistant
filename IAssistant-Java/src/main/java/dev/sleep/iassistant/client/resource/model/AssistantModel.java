package dev.sleep.iassistant.client.resource.model;

public class AssistantModel {
	
	private String wakeName;
	
	public AssistantModel() {
		wakeName = "No Name";
	}

	public String getWakeName() {
		return wakeName;
	}

	public void setWakeName(String wakeName) {
		this.wakeName = wakeName;
	}
}
