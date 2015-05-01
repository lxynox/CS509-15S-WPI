package com.flight_ticket_search.Dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author Kun Huang Date: 22, March, 2015
 */
public class FlightDaoImplTest extends TestCase {

	FlightDaoImpl testdaoImpl = new FlightDaoImpl();

	/**
	 * Test method for
	 * {@link com.flight_ticket_search.Dao.FlightDaoImpl#selectFlights(java.lang.String, java.lang.String, boolean)}
	 * .
	 */
	public void testSelectFlights() {
		// testdaoImpl.selectFlights("BOS","2015_05_09",true);
		assertNotNull ("departure flights on date 2015, may, 9 should not be null : ", 
				testdaoImpl.selectFlights ("BOS", "2015_05_09", true));
		System.out
				.println(testdaoImpl.selectFlights("BOS", "2015_05_09", true));
	}

	/**
	 * Test method for
	 * {@link com.flight_ticket_search.Dao.FlightDaoImpl#removeFlight(java.util.List)}
	 * .
	 */
	public void testRemoveFlights() {
		List<List<String>> removeFlightList = new ArrayList<List<String>>();
		int ticketNum = 0;
		while (true) {
			List<String> flightList = new ArrayList<String>();
			flightList.add("2000");
			flightList.add("coach");
			removeFlightList.add(flightList);
			ticketNum++;
			if (ticketNum >= 5) break;
		}
		assertTrue("system could remove 5 (flightNumber:2011, seating: coach) tickets",
				testdaoImpl.removeFlights(removeFlightList));
	}

	/**
	 * Test method for
	 * {@link com.flight_ticket_search.Dao.FlightDaoImpl#getAirport(java.lang.String)}
	 * .
	 */
	public void testGetAirport() {
		String airportCode = "LAX";
		System.out.println(testdaoImpl.getAirport(airportCode));
		assertNotNull ("airport information of LAX should be returned", testdaoImpl.getAirport(airportCode));
	}

	/**
	 * Test method for
	 * {@link com.flight_ticket_search.Dao.FlightDaoImpl#getAirplane(java.lang.String)}
	 * .
	 */
	public void testGetAirplane() {
		String airplane = "767";
		System.out.println(testdaoImpl.getAirplane(airplane));
		assertNotNull ("airplane information of \"767\" should be returned", testdaoImpl.getAirplane(airplane));
	}

	/**
	 * Test method for
	 * {@link com.flight_ticket_search.Dao.FlightDaoImpl#lockDB()}.
	 */
	public void testLockDB() {
		if (testdaoImpl.lockDB()) {
			System.out.println("Successfully locked database");
		} else {
			System.out.println("Fail to lock database");
		}
	}

	/**
	 * Test method for
	 * {@link com.flight_ticket_search.Dao.FlightDaoImpl#unlockDB()}.
	 */
	public void testUnlockDB() {
		if (!testdaoImpl.unlockDB()) {
			System.out.println("Successfully unlocked database");
		} else {
			System.out.println("Fail to unlock database");
		}
	}
}
