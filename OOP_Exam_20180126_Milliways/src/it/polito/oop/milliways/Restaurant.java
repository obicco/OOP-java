package it.polito.oop.milliways;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Restaurant {
	
	private Map<String, Race> races = new HashMap<>();
	private Map<Integer, Hall> hallsMap = new HashMap<>();
	private List<Party> parties;
	private List<Hall> hallsList = new ArrayList<>();

    public Restaurant() {
	}
	
	public Race defineRace(String name) throws MilliwaysException{
		if (races.containsKey(name)) {
			throw new MilliwaysException();
		}
		Race r = new Race(name);
		races.put(name, r);
	    return r;
	}
	
	public Party createParty() {
	    return new Party();
	}
	
	public Hall defineHall(int id) throws MilliwaysException{
		if (hallsMap.containsKey(id)) {
			throw new MilliwaysException();
		}
		Hall h = new Hall(id);
		hallsMap.put(id, h);
		hallsList.add(h);
	    return h;
	}

	public List<Hall> getHallList() {
		return hallsList;
	}

	public Hall seat(Party party, Hall hall) throws MilliwaysException {
		if (!hall.isSuitable(party))
			throw new MilliwaysException();
		
		parties.add(party);
		return hall;
	}

	public Hall seat(Party party) throws MilliwaysException {
		for (Hall hall : hallsList)
			if (hall.isSuitable(party))
				return seat(party, hall);
		
		throw new MilliwaysException();
	}

	public Map<Race, Integer> statComposition() {
        return null;
	}

	public List<String> statFacility() {
        return null;
	}
	
	public Map<Integer,List<Integer>> statHalls() {
        return null;
	}

}
