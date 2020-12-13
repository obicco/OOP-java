package warehouse;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

public class Warehouse {
	
	private Map<String, Product> products = new TreeMap<>();
	private Map<String, Supplier> suppliers = new TreeMap<>();
	private Map<String, Order> orders = new TreeMap<>();
	private int orderCnt = 0;
	
	public Product newProduct(String code, String description){
		Product p = new Product(code, description);
		products.put(code, p);
		return p;
	}
	
	public Collection<Product> products(){
		return products.values();
	}

	public Product findProduct(String code){
		return products.get(code);
	}

	public Supplier newSupplier(String code, String name){
		Supplier s = new Supplier(code, name);
		suppliers.put(code, s);
		return s;
	}
	
	public Supplier findSupplier(String code){
		return suppliers.get(code);
	}

	public Order issueOrder(Product prod, int quantity, Supplier supp)
		throws InvalidSupplier {
		if (!supp.supplies().contains(prod))
			throw new InvalidSupplier();
		String code = "ORD" + ++orderCnt;
		Order o = new Order(code,prod,supp,quantity);
		orders.put(code,o);
		prod.addOrder(o);
		return o;
	}

	public Order findOrder(String code){
		return orders.get(code);
	}
	
	static <T> Predicate<T> not(Predicate<T> t) {
	    return t.negate();
	}
	
	public List<Order> pendingOrders(){
		return orders.values().stream()
				.filter(not(Order :: delivered))
				.sorted(comparing(Order :: getProductCode))
				.collect(toList());
	}

	public Map<String,List<Order>> ordersByProduct(){
	    return  orders.values().stream()
	            .collect(groupingBy(Order :: getProductCode));
	}

	public Map<String,Long> orderNBySupplier(){
        return  orders.values().stream()
                .collect(groupingBy(Order :: getSupplierName, TreeMap::new, counting()));        
	}

	public List<String> countDeliveredByProduct(){
        return  orders.values().stream()
                .filter(Order :: delivered)
                .collect(groupingBy(Order :: getProductCode, counting()))
                .entrySet().stream()
                .sorted(comparing(Map.Entry<String, Long>::getValue).reversed())
                .map(e -> e.getKey() + " - " + e.getValue())
                .collect(toList());
	}
}
