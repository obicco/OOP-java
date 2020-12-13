package it.polito.oop.stocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

public class StockExchange {
	private int userNumber = 0;
	private Map<String, Stock> allStocks = new HashMap<>();
	private Map<Integer, User> users = new HashMap<>();
	private ArrayList<Bid> asks = new ArrayList<>();

	public int defineStocks(String... stocks) {
		int cnt=0;
		for(String s:stocks){
			if(s.contains(": ")){
				String sp[]=s.split(": ");
				if(allStocks.putIfAbsent(sp[0],new Stock(sp[0],sp[1]))==null)cnt++;}}
	    return cnt;
	}

	public String getStock(String stockCode) throws StockException {
		if (stockCode == null || !allStocks.containsKey(stockCode))
			throw new StockException();
        return allStocks.get(stockCode).toString();
	}

	public int registerUser(String name) {
		User u = new User(name);
		
		userNumber++;
		u.setUserNumber(userNumber);
		users.put(userNumber, u);
		
        return userNumber;
	}

	public String getUser(int userId) throws UserException {
		User u = users.get(userId);
		if (u == null)
			throw new UserException();
        return u.toString();
	}

	public void addToBalance(int userId, double amount) throws UserException {
		User u = users.get(userId);
		if (u == null)
			throw new UserException();
		u.addBalance(amount);
	}

	public double getBalance(int userId) throws UserException {
		User u = users.get(userId);
		if (u == null)
			throw new UserException();
        return u.getBalance();
	}

	public void addToWallet(int userId, String stockCode, int quantity) throws UserException, StockException {
		User u = users.get(userId);
		if (u == null)
			throw new UserException();
		if (stockCode == null || !allStocks.containsKey(stockCode))
			throw new StockException();
		u.toWallet(allStocks.get(stockCode), quantity);
	}

	public Collection<String> getWallet(int userId) throws UserException {
		User u = users.get(userId);
		if (u == null)
			throw new UserException();
        return u.getWallet();
	}

	public void addAsk(int userId, String stockCode, int quantity, double minPrice)
			throws UserException, StockException, WalletException {
		if(!users.containsKey(userId)) throw new UserException();
		if(!allStocks.containsKey(stockCode)) throw new StockException();
		if(!users.get(userId).hasStocks(allStocks.get(stockCode))) throw new WalletException();
		if(quantity>users.get(userId).getWalletValue(allStocks.get(stockCode))) throw new WalletException();
		asks.add(new Bid(users.get(userId),allStocks.get(stockCode),quantity,minPrice));
		users.get(userId).setQuantity(allStocks.get(stockCode), -quantity);
	}

	public Collection<String> getBook(String stockCode) throws StockException {
		if(!allStocks.containsKey(stockCode)) throw new StockException();
		return asks.stream().filter(x->x.getStock()==(allStocks.get(stockCode))).sorted(comparing(Bid::getMinPrice).thenComparing((a,b)->b.getQuantity()-a.getQuantity())).map(x->x.getUser().getUserNumber()+": "+x.getQuantity()+"@"+x.getMinPrice()).collect(toList());	
	}

	public boolean executeBid(int userId, String stockCode, int quantity, double maxPrice)
			throws UserException, StockException, BalanceException {
		if(!users.containsKey(userId)) throw new UserException(); 
		if(!allStocks.containsKey(stockCode)) throw new StockException();
		if(users.get(userId).getBalance()<(quantity*maxPrice)) throw new BalanceException();
		Collection<Bid> list = asks.stream().filter(x->x.getStock()==allStocks.get(stockCode)).filter(x->maxPrice>x.getMinPrice()).sorted(comparing(Bid::getMinPrice) .thenComparing((a,b)->b.getQuantity()-a.getQuantity())).distinct().collect(toList()); 
		int n=0,q=0; 
		for(Bid b:list) {n+=b.getQuantity();} 
			if(n>quantity) { 
				for(Bid b:list) { int flag=0; 
					asks.remove(b); 
					if(quantity-q>=b.getQuantity()) q=q+b.getQuantity(); 
					else { q=quantity-q; 
						b.setQuantity(-q); 
						asks.add(b); 
						flag=1; }
					b.getUser().addBalance(q*b.getMinPrice()); 
					users.get(userId).checkStock(allStocks.get(stockCode)); 
					users.get(userId).setQuantity(allStocks.get(stockCode),q); 
					users.get(userId).addBalance(-q*b.getMinPrice()); 
					if(flag==1) return true;
			   } return true; 
		 } else return false; 
	}
	
	public long countActiveWallets() {
        return users.values().stream().filter(x->x.hasStocks()).count();
	}
	
	public long countAskedStocks() {
        return asks.stream().map(x->x.getStock()).distinct().count();
	}
	
	public Map<Integer, Long> stocksPerUsers() {
	        return users.values().stream().collect(Collectors.toMap(User::getUserNumber, User::numberStocks));
	}
}
