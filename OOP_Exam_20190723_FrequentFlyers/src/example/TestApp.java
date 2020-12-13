package example;

import static org.junit.Assert.*;
import org.junit.Test;

import frequentFlyers.FFException;
import frequentFlyers.FrequentFlyers;
import java.util.*;

public class TestApp {

@Test
public void test() throws FFException {
	FrequentFlyers f = new FrequentFlyers();

	//R1
	f.addAlliance("a2", "beta", "gamma", "delta");
	f.addAlliance("a1", "omega", "alfa");
	f.addAlliance("a3", "eta", "kappa");
	try {  f.addAlliance("a1", "epsilon");   
	       fail("Expected exception due to alliance duplication");
	}catch(FFException ex) {}
	try {  f.addAlliance("a10", "lambda", "beta"); 
	       fail("Expected exception due to double membership");
	}catch(FFException ex) {}
	assertEquals("a1", f.getAllianceName("alfa"));
	f.setMultiplier(5, "a2", "a3");
	assertEquals(5, f.getMultiplier("a2"));
	assertEquals(10, f.getMultiplier("a1"));
	
	//R2
		f.addFlight("fl1", "beta", 80, 500);
		f.addFlight("fl2", "beta", 120, 700);
		f.addFlight("fl3", "gamma", 200, 1100);
		f.addFlight("fl4", "omega", 200, 1500);
		f.addFlight("fl5", "alfa", 300, 2000);
	try {f.addFlight("fl100", "alfabeta", 100, 800); fail("Expected exception");} //alfabeta undef
	catch(FFException ex) {}
	assertEquals(3, f.getNofFlights("a2"));
	assertEquals(767, f.getAverageNofMilesPerFlight("a2"));
	
	
	//R3
		f.addFrequentFlyer("ff1", "a2", "a1");
		f.addFrequentFlyer("ff2", "a2", "a3");
	try {f.addFrequentFlyer("ff100", "a2", "a100"); fail("Expected exception");} //a100 undef
	catch(FFException ex) {}
	assertEquals(2, f.getNOfFF("a2"));
	
	//R4
	int journeyCode = 0;
		journeyCode = f.addJourney("ff1", "fl1", "fl4", "fl5");
	assertEquals(1, journeyCode);
	assertEquals(580, f.getJourneyPrice(journeyCode));
	assertEquals(4000, f.getJourneyPoints(journeyCode));
	assertEquals(4000, f.getPoints("ff1"));
	
	try {f.addJourney("ff2", "fl1", "fl5"); fail("Expected exception");}  //fl5 operated by alfa (alliance a1)
	catch(FFException ex) {}
	
   	int points = f.pointsForFreeFlight("fl1"); //fl1 operated by beta in alliance a2 (multiplier 5)
	assertEquals(2500, points);
	
		journeyCode = f.addJourneyWithPoints("ff1", 2500, "fl1", "fl4"); // 2500 point for fl1 
	assertEquals(200, f.getJourneyPrice(journeyCode)); //fl4 price
	assertEquals(1500, f.getJourneyPoints(journeyCode)); //fl4 points
	assertEquals(3000, f.getPoints("ff1")); //4000 - 2500 + 1500 = 3000

	try {
		journeyCode = f.addJourneyWithPoints("ff1", 4000, "fl4"); // ff1 has only 3000 points
		fail("Expected exception");
	} catch(Exception ex) {}
	
	try {
		journeyCode = f.addJourneyWithPoints("ff1", 2000, "fl4"); // 15000 points needed, no match
		fail("Expected exception");
	} catch(Exception ex) {}
	
	//R5
	SortedMap<Integer, List<String>> map1 = f.listOfFlightIdsPerPrice();
	String map1String = "{300=[fl5], 200=[fl3, fl4], 120=[fl2], 80=[fl1]}";
	assertEquals(map1String, map1.toString());
	
	SortedMap<Integer, Long> map2 = f.nOfJourneysPerNofFlights();
	String map2String = "{2=1, 3=1}";
	assertEquals(map2String, map2.toString());
}

}
