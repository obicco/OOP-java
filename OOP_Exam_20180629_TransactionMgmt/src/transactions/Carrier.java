package transactions;

import java.util.ArrayList;
import java.util.List;

public class Carrier {
	
	private String name;
	private List<Region> regions = new ArrayList<>();
	
	public Carrier(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addRegion(Region region) {
		regions.add(region);
	}
	
	public boolean containsRegion(Region region) {
        return regions.contains(region);
	}
}
