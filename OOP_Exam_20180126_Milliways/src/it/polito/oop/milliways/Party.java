package it.polito.oop.milliways;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Party {
	
	private Map<Race, Integer> composition = new HashMap<>();

    public void addCompanions(Race race, int num) {
    	composition.put(race, num);
	}

	public int getNum() {
        return composition.values().stream()
        					.mapToInt(i -> i)
        					.sum();
	}

	public int getNum(Race race) {
		if (composition.containsKey(race)) {
			return composition.get(race);
		}
		else return 0;
	}

	public List<String> getRequirements() {
		Set<String> req = new TreeSet<>();
		for (Race r : composition.keySet()) {
			req.addAll(r.getRequirements());
		}
        return new ArrayList<String>(req);
	}

    public Map<String,Integer> getDescription(){
        return composition.entrySet().stream()
        		.collect(Collectors.toMap(e -> e.getKey().getName(), e -> e.getValue()));
    }

}
