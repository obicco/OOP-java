package frequentFlyers;

import java.util.ArrayList;
import java.util.List;

public class Alliance {
	
	private String allianceName;
	private List<Airline> airlines = new ArrayList<>();
	private int multiplier = 10;
	
	public Alliance (String allianceName) {
		this.allianceName = allianceName;
	}
	
	public int getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier; 
	}

	public String getAllianceName() {
		return allianceName;
	}

	public List<Airline> getAirlines() {
		return airlines;
	}
	
}
