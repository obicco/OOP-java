package warehouse;

import java.util.LinkedList;
import java.util.List;

public class Supplier {
	
	private String codice;
	private String name;
	private List<Product> products = new LinkedList<>();
	
	public Supplier(String codice, String name) {
		this.codice = codice;
		this.name = name;
	}

	public String getCodice(){
		return codice;
	}

	public String getNome(){
		return name;
	}
	
	public void newSupply(Product product){
		products.add(product);
		product.addSupplier(this);
	}
	
	public List<Product> supplies(){
		return products;
	}
}
