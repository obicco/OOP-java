import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Test;

import it.polito.oop.stocks.BalanceException;
import it.polito.oop.stocks.StockException;
import it.polito.oop.stocks.StockExchange;
import it.polito.oop.stocks.UserException;
import it.polito.oop.stocks.WalletException;

public class TestApp {

	@Test
	public void test() throws StockException, UserException, BalanceException, WalletException {
		StockExchange se = new StockExchange();

		// R1
		assertEquals(2, se.defineStocks("AAPL: Apple", "MSFT: Microsoft"));
		assertEquals("AAPL: Apple", se.getStock("AAPL"));

		// R2
		int u1 = se.registerUser("Marco");
		int u2 = se.registerUser("Giovanni");
		assertNotEquals(u1, u2);
		assertEquals(String.format("%d: Marco", u1), se.getUser(u1));
		se.addToBalance(u2, 1000.0);
		se.addToBalance(u2, 500.0);
		assertEquals(Double.valueOf(1500.0), Double.valueOf(se.getBalance(u2)));

		// R3
		Collection<String> wallet = se.getWallet(u1);
		assertNotNull(wallet);
		assertEquals(0, wallet.size());
		se.addToWallet(u1, "AAPL", 5);
		se.addToWallet(u1, "AAPL", 10);
		wallet = se.getWallet(u1);
		assertNotNull(wallet);
		assertEquals(1, wallet.size());
		assertEquals("AAPL: 15", new LinkedList<String>(wallet).get(0));

		// R4
		se.addAsk(u1, "AAPL", 4, 194.5);
		se.addAsk(u1, "AAPL", 6, 207.8);
		boolean bid = se.executeBid(u2, "AAPL", 6, 210.0);
		assertTrue(bid);
		assertEquals(Double.valueOf(1193.6), Double.valueOf(se.getBalance(u1)), 0.01);
		assertEquals(Double.valueOf(306.4), Double.valueOf(se.getBalance(u2)), 0.01);
		Collection<String> book = se.getBook("AAPL");
		assertNotNull(book);
		assertEquals(1, book.size());
		assertEquals(u1 + ": 4@207.8", new LinkedList<String>(book).get(0));
		wallet = se.getWallet(u2);
		assertNotNull(wallet);
		assertEquals(1, wallet.size());
		assertEquals("AAPL: 6", new LinkedList<String>(wallet).get(0));

		// R5
		assertEquals(2, se.countActiveWallets());
		assertEquals(1, se.countAskedStocks());
		Map<Integer, Long> users = se.stocksPerUsers();
		assertNotNull(users);
		assertEquals(2, users.size());
		assertEquals(Long.valueOf(1), users.get(u1));
		assertEquals(Long.valueOf(1), users.get(u2));
		se.addAsk(u2, "AAPL", 1, 198.4);
//		Map<String, Double> stocks = se.pricePerStocks();
//		assertNotNull(stocks);
//		assertEquals(1, stocks.size());
//		assertEquals(205.92, stocks.get("AAPL"), 0.01);
	}

}
