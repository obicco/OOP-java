package transactions;

import java.util.ArrayList;
import java.util.List;

public class Region {
	
	private String name;
	private List<Place> places = new ArrayList<>();
	private List<Carrier> carriers = new ArrayList<>();
	
	public Region(String regionName) {
		this.name = regionName;
	}
	
	public void addPlace (Place place) {
		places.add(place);
	}
	
	public String getName() {
		return name;
	}
	
	public void addCarrier(Carrier carrier) {
        carriers.add(carrier);
    }
	
	public List<Carrier> getCarriers() {
        return carriers;
    }

}
