package transport;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import rest.JsonList;

public class StationBoard {

	private Stop stop;
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

	public Stop getStop() {
		return stop;
	}

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

	public static class StationBoardList implements JsonList<StationBoard> {
		@JsonProperty(value = "stationboard")
		private List<StationBoard> list;

		private Location station;

		@Override
		public List<StationBoard> getList() {
			return list;
		}

		public Location getStation() {
			return station;
		}
	}
}
