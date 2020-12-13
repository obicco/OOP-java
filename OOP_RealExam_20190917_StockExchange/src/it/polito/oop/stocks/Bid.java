package it.polito.oop.stocks;
public class Bid {
	private User user;
	private int quantity;
	private double minPrice;
	private Stock stock;
	public Bid (User user, Stock stock,int quantity, double minPrice) { this.user = user; this.quantity = quantity; this.minPrice = minPrice; this.stock = stock; }
	public User getUser() {	return user; }
	public int getQuantity() { return quantity; }
	public double getMinPrice() { return minPrice; }
	public Stock getStock() { return stock; }
	public void setQuantity(int quantity) {	this.quantity += quantity; }
}
