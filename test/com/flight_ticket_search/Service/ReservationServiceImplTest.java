/**
 * 
 */
package com.flight_ticket_search.Service;

import java.util.ArrayList;
import java.util.List;

import com.flight_ticket_search.Dao.FlightDaoImpl;

import junit.framework.TestCase;

/**
 * @author lxynox
 *
 */
public class ReservationServiceImplTest extends TestCase {
	ReservationService reservationServicer = new ReservationServiceImpl();

	/**
	 * precondition: back end data has been fetched and loaded which ensures that the flightMap not null
	 * Test method for {@link com.flight_ticket_search.Service.ReservationServiceImpl#checkFlight(java.util.List)}.
	 */
	public final void testCheckFlight() {
		// TODO
		new FlightDaoImpl().selectFlights ("BOS", "2015_05_15", true);
//		first case: successful case: flight with empty seat to reserve
		List<List<String>> purchaseList = new ArrayList<List<String>>();
		List<String> flightStr = new ArrayList<String>();
		flightStr.add("2011");
		flightStr.add("FirstClass");
		purchaseList.add(flightStr);
		assertTrue ("flight 2011 has first class seats, system should enter to the step of purchase flight",
				reservationServicer.checkFlight (purchaseList));
		
//		second case: failure case: flight without empty seat to reserve
		List<List<String>> purchaseList2 = new ArrayList<List<String>>();
		List<String> flightStr2 = new ArrayList<String>();
		flightStr2.add("2010");
		flightStr2.add("FirstClass");
		purchaseList2.add(flightStr2);
		assertTrue ("flight 2010 has one first class seat left, system should enter to the step of purchase flight",
				reservationServicer.checkFlight (purchaseList2));
		System.out.println("test1");
	}
	
	/**
	 * precondition: 1. our team is holding the lock of database
	 * 				 2. checkFlight() returns true
	 * Test method for {@link com.flight_ticket_search.Service.ReservationServiceImpl#purchase(java.util.List)}.
	 */
	public final void testPurchase() {
		// TODO
		testCheckFlight();
		System.out.println("purchase right now");
		new FlightDaoImpl().lockDB();
		List<List<String>> purchaseList = new ArrayList<List<String>>();
		List<String> flightStr = new ArrayList<String>();
		flightStr.add("2010");
		flightStr.add("FirstClass");
		purchaseList.add(flightStr);
		reservationServicer.purchase(purchaseList);
				
		List<List<String>> purchaseList3 = new ArrayList<List<String>>();
		List<String> flightStr3 = new ArrayList<String>();
		flightStr3.add("2010");
		flightStr3.add("FirstClass");
		purchaseList3.add(flightStr3);
		assertFalse ("flight 2010 does not have first class seats, system should not enter to the step of purchase flight",
				reservationServicer.checkFlight (purchaseList3));
		
		System.out.println("test2");
	}
	
}
