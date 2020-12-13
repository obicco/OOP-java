package frequentFlyers;

import java.util.ArrayList;
import java.util.List;

public class Airline {
	
	private String airlineName;
	private Alliance alliance;
	private List<String> flights = new ArrayList<>();
	
	public Airline (String airlineName, Alliance alliance) {
		this.airlineName = airlineName;
		this.alliance = alliance;
	}

	public String getAirlineName() {
		return airlineName;
	}
	
	public String getAllianceName() {
		return alliance.getAllianceName();
	}
	
	public void addFlight() {
		
	}
	
}
