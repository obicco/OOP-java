package transactions;

public class Product {

	private String id;
	private Place place;
	private String productId;
	private Transaction transaction;
	
	public Product(String id, Place place, String productid) {
		this.id = id; 
		this.place = place; 
		this.productId = productid;
	}
	
	public String getId () {
		return id;
	}

	public Place getPlace() {
		return place;
	}

	public String getProductId() {
		return productId;
	}
	
	public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
