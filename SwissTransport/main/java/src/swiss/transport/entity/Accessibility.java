package swiss.transport.entity;

public enum Accessibility {
	INDEPENDENT_BOARDING, ASSISTED_BOARDING, ADVANCED_NOTICE;

	public String value() {
		return toString().toLowerCase();
	}

	public static String[] toStringArray(Transportations[] transportations) {
		String[] params = new String[transportations.length];
		for (int i = 0; i < transportations.length; i++) {
			params[i] = transportations[i].value();
		}
		return params;
	}
}
