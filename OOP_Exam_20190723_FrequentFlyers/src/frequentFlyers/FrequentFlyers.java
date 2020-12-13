package frequentFlyers;
import java.util.*;

public class FrequentFlyers {
	
	
	private Map<String, Alliance> alliances = new HashMap<>();
	private Map<String, Airline> airlines = new HashMap<>();
	private List<Flight> flights = new ArrayList<>();
	//R1
	/**
	 * Creates a new alliance with members airlines
	 * 
	 * @param allianceName
	 * @param airlineNames
	 * @throws FFException in case of duplicate alliance or multiple membership of one airline
	 */
	public void addAlliance(String allianceName, String... airlineNames)
			throws FFException { 
		for (String n: airlineNames) {
			if (!alliances.containsKey(n)) {
				Alliance alliance = new Alliance(allianceName);
				Airline airline = new Airline(n,alliance);
				airlines.put(n, airline);
				alliances.put(allianceName, alliance);
			} 
			else throw new FFException();
		}
		
	}
	
	public String getAllianceName(String airlineName) {
		Airline airline = airlines.get(airlineName);
	    return airline.getAllianceName(); 
	}
	
	public void setMultiplier(int multiplier, String... allianceNames) {
		for (String a: allianceNames) {
			alliances.get(a).setMultiplier(multiplier);
		}
	}
	
	public int getMultiplier(String allianceName) {
		Alliance alliance = alliances.get(allianceName);
	    return alliance.getMultiplier();
	}
	
	
	//R2
	public void addFlight(String flightId, String airlineName, int price, int nOfMiles) 
			throws FFException { 
		Flight flight = new Flight(flightId, airlineName, price, nOfMiles);
		flights.add(flight);
	}
	
	
	public int getNofFlights(String allianceName) {
		Alliance alliance = alliances.get(allianceName);
        return -1;
	}
	
	public int getAverageNofMilesPerFlight(String allianceName) {
        return -1;
	}
	
	//R3
	public void addFrequentFlyer(String ffId, String... allianceNames)
			throws FFException { 
	}
	
	public int getNOfFF(String allianceName) {
        return -1;
	}
	
	//R4
	public int addJourney(String ffId, String... flightIds) throws FFException {
        return -1;
	}
	
	public int getJourneyPrice (int journeyCode) {
        return -1;
	}
	
	public int getJourneyPoints (int journeyCode) {
        return -1;
	}
	
	public int getPoints(String ffId) {
        return -1;
	}
	
	public int addJourneyWithPoints(String ffId, int points, String... flightIds) throws FFException { 
        return -1;
	}
	
	public int pointsForFreeFlight(String flightId) {
        return -1;
	}
	
	//R5
	public SortedMap<Integer, List<String>> listOfFlightIdsPerPrice() {
        return null;
	}
	
	public SortedMap<Integer, Long> nOfJourneysPerNofFlights() { 
        return null;
	}
}
