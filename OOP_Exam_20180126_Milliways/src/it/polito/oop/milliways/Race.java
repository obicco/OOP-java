package it.polito.oop.milliways;

import java.util.ArrayList;
import java.util.List;

public class Race {
	
	private String name;
	private List<String> requirements = new ArrayList<>();
	
	public Race (String name ) {
		this.name = name;
	}
    
	public void addRequirement(String requirement) throws MilliwaysException {
		if (requirements.contains(requirement)) {
			throw new MilliwaysException();
		}
		else requirements.add(requirement);
	}
	
	protected List<String> getRequirements() {
        return requirements;
	}
	
	public String getName() {
        return name;
	}
}
