package it.polito.oop.stocks;

public class Stock {
	private String cod;
	private String name;
	
	public Stock(String cod, String name) {
		this.cod=cod;
		this.name=name;
	}
	
	public String getCod() {
		return cod;
	}
	
	@Override
	public String toString() {
		return cod + ": " + name;
	}
}
