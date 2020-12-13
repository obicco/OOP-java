package it.polito.oop.milliways;

import java.util.ArrayList;
import java.util.List;

public class Hall {
	
	private int id;
	private List<String> facilities = new ArrayList<>();
	
	public Hall(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void addFacility(String facility) throws MilliwaysException {
		if (facilities.contains(facility)) {
			throw new MilliwaysException();
		}
		facilities.add(facility);
	}

	public List<String> getFacilities() {
        return facilities;
	}
	
	int getNumFacilities(){
        return -1;
	}

	public boolean isSuitable(Party party) {
		return party.getRequirements().stream()
					.allMatch(r -> facilities.contains(r));
	}

}
