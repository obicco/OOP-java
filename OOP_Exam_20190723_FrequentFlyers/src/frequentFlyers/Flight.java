package frequentFlyers;

import java.util.ArrayList;
import java.util.List;

public class Flight {
	private String flightId;
	private String airlineName;
	private int price;
	private int nOfMiles;
	private Airline airline;
	
	public Flight(String flightId, String airlineName, int price, int nOfMiles) {
		this.flightId = flightId;
		this.airlineName = airlineName;
		this.price = price;
		this.nOfMiles = nOfMiles;
	}
	
	public void setAirline(String airlineName) {
		  
	}

}
