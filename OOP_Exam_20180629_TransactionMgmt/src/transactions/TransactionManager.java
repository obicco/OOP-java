package transactions;
import java.util.*;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

public class TransactionManager {
	
	private Map<String, Region> regions = new HashMap<>();
	private Map<String, Place> places = new HashMap<>();
	private Map<String, Carrier> carriers = new HashMap<>();
	private SortedMap<String, Product> requests = new TreeMap<>();
	private SortedMap<String, Product> offers = new TreeMap<>();
	private SortedMap<String, Transaction> transactions = new TreeMap<>();
	
//R1
	public List<String> addRegion(String regionName, String... placeNames) { 
		Region r = new Region(regionName);
		regions.put(regionName, r);
		
		ArrayList<String> names = new ArrayList<>();
		for (String p : placeNames) {
			if (!places.containsKey(p)) {
				Place pl = new Place(p, r);
				places.put(p, pl);
				names.add(p);
			}
		}
		names.sort(Comparator.naturalOrder());
		return names;
	}
	
	public List<String> addCarrier(String carrierName, String... regionNames) { 
		Carrier c =new Carrier(carrierName);
		carriers.put(carrierName, c);
		
		TreeSet<String> names = new TreeSet<>();
		for (String r: regionNames) {
			if (regions.containsKey(r) && !names.contains(r)) {
				names.add(r); 
				Region region = regions.get(r);
				c.addRegion(region); 
				region.addCarrier(c);
			}
		}
		return new ArrayList<String>(names);
	}
	
	public List<String> getCarriersForRegion(String regionName) { 
		Region region = regions.get(regionName);
		
		TreeSet<String> names = new TreeSet<>();
		for (Carrier carrier: region.getCarriers()) {
		    names.add(carrier.getName());
		}
		return new ArrayList<String>(names);
	}
	
//R2
	public void addRequest(String requestId, String placeName, String productId) 
			throws TMException {
		Product request = requests.get(requestId);
		if (request != null) throw new TMException();
		Place place = places.get(placeName);
		if(place == null) throw new TMException();
		
		requests.put(requestId, new Product(requestId, place, productId));
	}
	
	public void addOffer(String offerId, String placeName, String productId) 
			throws TMException {
		Product offer = offers.get(offerId);
		if (offer != null) throw new TMException();
		Place place = places.get(placeName);
		if(place == null) throw new TMException();
		
		offers.put(offerId, new Product(offerId, place, productId));
	}
	

//R3
	public void addTransaction(String transactionId, String carrierName, String requestId, String offerId) 
			throws TMException {
		Product request = requests.get(requestId); Product offer = offers.get(offerId);
		if(!request.getProductId().equals(offer.getProductId())) throw new TMException();
		if(request.getTransaction()!= null) throw new TMException();
		if(offer.getTransaction()!= null) throw new TMException();
		
		Region pickupRegion = offer.getPlace().getRegion();
		Region deliveryRegion = request.getPlace().getRegion();
		Carrier carrier = carriers.get(carrierName);
		if (  !carrier.containsRegion(deliveryRegion) || !carrier.containsRegion(pickupRegion))
			throw new TMException();
		
		Transaction t = new Transaction(transactionId, carrier, request, offer);
		transactions.put(transactionId, t);
		request.setTransaction(t); offer.setTransaction(t); 
	}
	
	public boolean evaluateTransaction(String transactionId, int score) {
		if(score < 1 || score > 10) return false;
		
        Transaction t = transactions.get(transactionId);
		t.setScore(score);
		
		return true;
	}
	
//R4
	public SortedMap<Long, List<String>> deliveryRegionsPerNT() {
		SortedMap<String, Long> map = transactions.values().stream()
				.map(Transaction::getDeliveryRegion)
				.collect(groupingBy(s -> s, TreeMap::new, counting()));
				
				SortedMap<Long, List<String>> map1 = map.entrySet().stream()
				.collect(groupingBy(e -> e.getValue(), () -> new TreeMap<Long, List<String>>(reverseOrder()), 
						mapping(e -> e.getKey(), toList())));
				
		return map1;
	}
	
	public SortedMap<String, Integer> scorePerCarrier(int minimumScore) {
		return transactions.values().stream()
				.filter(t -> t.getScore() >= minimumScore)
				.collect(groupingBy(Transaction::getCarrierName, 
				                    TreeMap::new, 
				                    summingInt(Transaction::getScore)));	
	}
	
	public SortedMap<String, Long> nTPerProduct() {
		return transactions.values().stream()
				.map(Transaction::getProductId)
				.collect(groupingBy(s->s, 
				                    TreeMap::new, 
				                    counting()));
	}
	
	
}

