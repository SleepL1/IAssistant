package dev.sleep.iassistant.client.voice.component;

public class VoiceComponent {

	private String languageIdentifier = "default", variant = "";
	private int pitch = 50, amplitude = 100, speed = 175, gap = 0;

	public String getLanguageIdentifier() {
		return languageIdentifier;
	}

	public void setLanguageIdentifier(String languageIdentifier) {
		this.languageIdentifier = languageIdentifier;
	}

	public int getPitch() {
		return pitch;
	}

	public void setPitch(int pitch) {
		if (pitch < 0 || pitch > 99) {
			throw new IllegalArgumentException("The pitch must be be between 0 and 99");
		}
		this.pitch = pitch;
	}

	public int getAmplitude() {
		return amplitude;
	}

	public void setAmplitude(int amplitude) {
		if (amplitude < 0 || amplitude > 200) {
			throw new IllegalArgumentException("The amplitude must be be between 0 and 200");
		}
		this.amplitude = amplitude;
	}

	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed - Reading speed (recommended range between 80 ~ 500)
	 */
	public void setSpeed(int speed) {
		if (speed < 1) {
			throw new IllegalArgumentException("The speed must be above 0");
		}
		this.speed = speed;
	}

	public int getGap() {
		return gap;
	}

	public void setGap(int gap) {
		if (gap < 0) {
			throw new IllegalArgumentException("The gap between words must be positive number");
		}
		this.gap = gap;
	}

	public String getVariant() {
		return variant;
	}

	/**
	 * Method to set a custom variant
	 * 
	 * @param variant - custom variant
	 */
	public void setVariant(String variant) {
		this.variant = variant;
	}

	/**
	 * @param isMale - if it is a male voice
	 * @param index  - index of voice
	 */
	public void setVariant(boolean isMale, int index) {
		if (isMale && (index < 1 || index > 7)) {
			throw new IllegalArgumentException("The variant index for male must be between 1 and 7");
		}
		if (!isMale && (index < 1 || index > 5)) {
			throw new IllegalArgumentException("The variant index for female must be between 1 and 5");
		}

		StringBuilder builder = new StringBuilder();

		builder.append(isMale ? "m" : "f");
		builder.append(index);

		setVariant(builder.toString());
	}

	public void setCroakVariant() {
		setVariant("+croak");
	}

	public void setWhisperVariant() {
		setVariant("+whisper");
	}

	public void removeVariant() {
		variant = "";
	}
}
