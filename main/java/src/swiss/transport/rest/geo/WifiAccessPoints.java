package swiss.transport.rest.geo;

public class WifiAccessPoints {
	private String macAddress;
	private String signalStrength;
	private String age;
	private String channel;
	private String signalToNoiseRatio;

	public WifiAccessPoints(String macAddress, String signalStrength, String age, String channel,
			String signalToNoiseRatio) {
		this.macAddress = macAddress;
		this.signalStrength = signalStrength;
		this.age = age;
		this.channel = channel;
		this.signalToNoiseRatio = signalToNoiseRatio;
	}

	public WifiAccessPoints(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public String getSignalStrength() {
		return signalStrength;
	}

	public String getAge() {
		return age;
	}

	public String getChannel() {
		return channel;
	}

	public String getSignalToNoiseRatio() {
		return signalToNoiseRatio;
	}

	void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	void setSignalStrength(String signalStrength) {
		this.signalStrength = signalStrength;
	}

	void setAge(String age) {
		this.age = age;
	}

	void setChannel(String channel) {
		this.channel = channel;
	}

	void setSignalToNoiseRatio(String signalToNoiseRatio) {
		this.signalToNoiseRatio = signalToNoiseRatio;
	}
}
