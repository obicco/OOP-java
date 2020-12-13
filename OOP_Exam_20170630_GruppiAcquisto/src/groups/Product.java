package groups;

import java.util.TreeSet;

public class Product {
	
	private String productTypeName;
	TreeSet<Supplier> suppliers = new TreeSet<>();

	public Product(String productTypeName, String... suppliers) {
		this.productTypeName = productTypeName;
	}
	
	void addSupplier(Supplier supplier) {
		suppliers.add(supplier);
	}
}
