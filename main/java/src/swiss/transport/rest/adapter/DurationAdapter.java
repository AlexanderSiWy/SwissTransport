package swiss.transport.rest.adapter;

import java.time.Duration;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DurationAdapter extends XmlAdapter<String, Duration> {
	@Override
	public Duration unmarshal(String s) throws Exception {
		String[] split = s.split("[:d]");
		return Duration.ofDays(Integer.parseInt(split[0])).plusHours(Integer.parseInt(split[1]))
				.plusMinutes(Integer.parseInt(split[2])).plusSeconds(Integer.parseInt(split[3]));
	}

	@Override
	public String marshal(Duration duration) throws Exception {
		return duration.toString();
	}

}
