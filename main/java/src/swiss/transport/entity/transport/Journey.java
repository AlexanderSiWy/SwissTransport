package swiss.transport.entity.transport;

import java.util.List;

public class Journey {
	private String name;
	private String category;
	private String subcategory;
	private String categoryCode;
	private String number;
	private String operator;
	private String to;
	private List<Stop> passList;
	private String capacity1st;
	private String capacity2nd;

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public String getNumber() {
		return number;
	}

	public String getOperator() {
		return operator;
	}

	public String getTo() {
		return to;
	}

	public List<Stop> getPassList() {
		return passList;
	}

	public String getCapacity1st() {
		return capacity1st;
	}

	public String getCapacity2nd() {
		return capacity2nd;
	}
}
