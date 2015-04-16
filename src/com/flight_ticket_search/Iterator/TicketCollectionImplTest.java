/**
 * 
 */
package com.flight_ticket_search.Iterator;

import java.util.List;

import com.flight_ticket_search.Entity.Flight;
import com.flight_ticket_search.Service.RequestFlightServiceImpl;

import junit.framework.TestCase;

/**
 * @author lxybi_000
 *
 */
public class TicketCollectionImplTest extends TestCase {
	
	/**
	 * Test method for {@link com.flight_ticket_search.Iterator.TicketCollectionImpl#add(java.util.List)}.
	 */
	public void testAdd() {
		
		
	}

	/**
	 * Test method for {@link com.flight_ticket_search.Iterator.TicketCollectionImpl#remove(java.util.List)}.
	 */
	public void testRemove() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.flight_ticket_search.Iterator.TicketCollectionImpl#iterator(com.flight_ticket_search.Iterator.TicketTypeEnum)}.
	 */
	public void testIterator() {

		TicketCollection ticketCollection = new RequestFlightServiceImpl().
				getSingleTrip("BOS", "PIT", "2015_05_10");
		
		TicketIterator iterator = ticketCollection.iterator(TicketTypeEnum.MIXED);

		while (iterator.hasNext()) {
			List<Flight> flightList = (List<Flight>) iterator.next();
			
			System.out.println(flightList);
			
			for (Flight flight: flightList)
				System.out.println(flight.getSeatType());
			for (int i=2; i>0; i--) System.out.println();
		}
	}
	
	public static void main(String[] args) {
		new TicketCollectionImplTest().testAdd();
		
	}

}
