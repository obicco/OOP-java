package transactions;

public class Transaction {
	  private String transactionId;
	    private Carrier carrier;
	    private Product request;
	    private Product offer;
	    private int score = 0;

	   public Transaction(String transactionId, Carrier carrier, Product request, Product offer) {
	        this.transactionId = transactionId;
	        this.carrier = carrier;
	        this.request = request;
	        this.offer = offer;
	    }

	   public String getDeliveryPlace() {
	        return request.getPlace().getName();
	    }

	   public String getPickupPlace() {
	        return offer.getPlace().getName();
	    }

	    public String getDeliveryRegion() {
	        return request.getPlace().getRegion().getName();
	    }

	    public int getScore() {
	        return score;
	    }

	    public String getCarrierName() {
	        return carrier.getName();
	    }

	    public String getProductId() {
	        return request.getProductId();
	    }

	    public void setScore(int score) {
	        this.score = score;
	    }

	    @Override
	    public String toString() {
	        return transactionId + " " + carrier.getName() + " " + score;
	    }
}
