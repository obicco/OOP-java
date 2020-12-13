package transactions;

public class Place {
	
	private String name;
	private Region region;
	
	public Place (String name, Region region) {
		this.name = name;
		this.region = region;
		region.addPlace(this);
	}

	public String getName() {
		return name;
	}

	public Region getRegion() {
		return region;
	}
}
