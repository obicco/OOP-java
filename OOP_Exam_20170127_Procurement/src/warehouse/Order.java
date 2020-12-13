package warehouse;

public class Order {
	
	private String code;
	private Supplier supp;
	private Product prod;
	private int quantity;
	private boolean delivered = false;
	
	public Order(String code, Product prod, Supplier supp, int quantity) {
		this.code = code;
		this.prod = prod;
		this.supp = supp;
		this.quantity = quantity;
	}

	public String getCode(){
		return code;
	}
	
	public boolean delivered(){
		return delivered;
	}

	public void setDelivered() throws MultipleDelivery {
		if(delivered){
	        throw new MultipleDelivery();
	    }
		delivered = true;
		prod.setQuantity(prod.getQuantity()+quantity);
	}
	
	public String toString(){
		return "Order " + code + " for " + quantity + " of " + prod.getCode() + 
		        " : " + prod.getDescription() + " from " + supp.getNome();
	}
	
	int getQuantity() {
        return quantity;
    }


    String getProductCode() {
        return prod.getCode();
    }

    String getSupplierName() {
        return supp.getNome();
    }
}
