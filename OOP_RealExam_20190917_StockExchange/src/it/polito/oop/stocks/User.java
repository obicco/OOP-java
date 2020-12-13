package it.polito.oop.stocks;

import static java.util.stream.Collectors.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
	private String name;
	private int userNumber;
	private double balance = 0;
	private Map<Stock, Integer> wallet = new HashMap<>();
	
	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}
	
	@Override
	public String toString() {
		return userNumber + ": " + name;
	}
	
	public void addBalance(double amount) {
		balance += amount;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void toWallet(Stock cod, int amount) {
		if(wallet.containsKey(cod)) {
			int a = wallet.get(cod);
			wallet.replace(cod, a+amount);
		}
		else {
			wallet.put(cod, amount);
		}
	}
	
	public int getWalletValue(Stock cod) {
		return wallet.get(cod);
	}
	
	public Collection<String> getWallet() {
		return wallet.entrySet().stream().map(w -> w.getKey().getCod() + ": " + w.getValue()).collect(toList());
	}
	public void checkStock(Stock stock) { if(!wallet.containsKey(stock)) wallet.put(stock, 0); }
	public void setQuantity(Stock stock,int quantity) {
		int q=this.getWalletValue(stock)+quantity;
		if(q==0) wallet.remove(stock, this.getWalletValue(stock));
		else wallet.put(stock,q); }
	public boolean hasStocks(Stock stock) {
		if(wallet.containsKey(stock)) return true;
		else return false; }
	public boolean hasStocks() {
		if(wallet.size()>0) return true;
		else return false; }
	public long numberStocks() { return wallet.size(); }
}
