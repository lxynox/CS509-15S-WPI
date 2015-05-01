/**
 * 
 */
package com.flight_ticket_search.Dao;

import java.util.List;

import com.flight_ticket_search.Entity.Flight;

import junit.framework.TestCase;

/**
 * @author lxynox
 *
 */
public class FlightGetterTest extends TestCase {
	
	FlightGetter flightG = new FlightGetter();
	/**
	 * get multiple flights from the database, from departure airport, to arrival airport on a specific day
	 * Test method for {@link com.flight_ticket_search.Dao.FlightGetter#getFlights(java.lang.String, java.lang.String, boolean)}.
	 */
	public final void testGetFlights() {
		// TODO
//		test of arrving flights
		List<Flight> flightList = flightG.getFlights("BOS", "2015_05_15", false);
		assertNotNull ("arriving flight should not be null", flightList);
		System.out.println(flightList);
//		test of departing flights
		List<Flight> flightList2 = flightG.getFlights("BOS", "2015_05_15", true);
		assertNotNull ("departing flight should not be null", flightList2);
		System.out.println(flightList2);
	}

	/**
	 * get single flight from database used to check if the this flight still exists(still seats available)
	 * Test method for {@link com.flight_ticket_search.Dao.FlightGetter#getFlight(java.lang.String)}.
	 */
	public final void testGetFlight() {
		// TODO
//		test of arrving flights
		Flight flight = flightG.getFlight("2010");
		System.out.println(flight);
		assertNotNull ("arriving flight should not be null, from 'sfo' to 'bos'", flight);
		assertNull("flight number of 2010 has been sold out, returned seat type should be null", flight.getSeatType());
	}

}
