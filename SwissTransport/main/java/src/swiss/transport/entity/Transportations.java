package swiss.transport.entity;

public enum Transportations {
	ICE_TGV_RJ, EC_IC, IR, RE_D, SHIP, S_SN_R, BUS, CABLEWAY, ARZ_EXT, TRAMWAY_UNDERGROUND;

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
