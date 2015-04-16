package com.flight_ticket_search.Dao;

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
		System.out
				.println(testdaoImpl.selectFlights("BOS", "2015_05_09", true));
	}

	/**
	 * Test method for
	 * {@link com.flight_ticket_search.Dao.FlightDaoImpl#removeFlight(java.util.List)}
	 * .
	 */
	public void testRemoveFlights() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.flight_ticket_search.Dao.FlightDaoImpl#getAirport(java.lang.String)}
	 * .
	 */
	public void testGetAirport() {
		String airportCode = "LAX";
		System.out.println(testdaoImpl.getAirport(airportCode));
	}

	/**
	 * Test method for
	 * {@link com.flight_ticket_search.Dao.FlightDaoImpl#getAirplane(java.lang.String)}
	 * .
	 */
	public void testGetAirplane() {
		String airplane = "767";
		System.out.println(testdaoImpl.getAirplane(airplane));
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

	/*
	 * public static void main(String[] args) { FlightDaoImplTest tester = new
	 * FlightDaoImplTest();
	 * 
	 * tester.testSelectFlights();
	 * 
	 * tester.testRemoveFlights();
	 * 
	 * tester.testGetAirport();
	 * 
	 * tester.testGetAirplane();
	 * 
	 * tester.testLockDB();
	 * 
	 * tester.testUnlockDB();
	 * 
	 * }
	 */
}
