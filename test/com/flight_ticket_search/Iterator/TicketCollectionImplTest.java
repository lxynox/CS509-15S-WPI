/**
 * 
 */
package com.flight_ticket_search.Iterator;

import java.util.List;

import com.flight_ticket_search.Entity.Flight;
import com.flight_ticket_search.Entity.Ticket;
import com.flight_ticket_search.Service.RequestFlightServiceImpl;

import junit.framework.TestCase;

/**
 * @author kunHuang
 * @Date: 2015, April, 6
 *
 */
public class TicketCollectionImplTest extends TestCase {
	TicketCollectionImpl ticketCollection = new TicketCollectionImpl();
	Ticket ticket;
	/**
	 * Test method for {@link com.flight_ticket_search.Iterator.TicketCollectionImpl#add(java.util.List)}.
	 */
	public void testAdd() {
		ticket = new Ticket();
		ticketCollection.add (ticket);
		assertTrue ("ticket collection should be able to add tickets", ticketCollection.add(ticket));
	}

	/**
	 * precondition: TicketCollectionImpl.add is already called before the execution of this method
	 * Test method for {@link com.flight_ticket_search.Iterator.TicketCollectionImpl#remove(java.util.List)}.
	 */
	public void testRemove() {
		ticket = new Ticket();
		ticketCollection.add(ticket);
		assertTrue ("ticket collection should be able to remove added tickets", ticketCollection.remove(ticket));
	}

	/**
	 * Test method for {@link com.flight_ticket_search.Iterator.TicketCollectionImpl#iterator(com.flight_ticket_search.Iterator.TicketTypeEnum)}.
	 */
	public void testIterator() {
		TicketCollection ticketCollection = new RequestFlightServiceImpl().
				getSingleTrip("BOS", "PIT", "2015_05_10");
		TicketIterator iteratorMixed = ticketCollection.iterator(TicketTypeEnum.MIXED);
		while (iteratorMixed.hasNext()) {
			Ticket ticket = (Ticket) iteratorMixed.next();
			assertEquals("all ticket type should be mixed", "MIXED", ticket.getSeatType());
			System.out.println(ticket);
		}
		
		TicketIterator iteratorFirstClass = ticketCollection.iterator (TicketTypeEnum.FIRSTCLASS);
		while (iteratorFirstClass.hasNext()) {
			assertEquals("all ticket type should be mixed", "FIRSTCLASS", ticket.getSeatType());
			System.out.println(ticket);
		}
		
		TicketIterator iteratorCoach = ticketCollection.iterator(TicketTypeEnum.COACH);
		while (iteratorCoach.hasNext()) {
			Ticket ticket = (Ticket) iteratorCoach.next();
			assertEquals("all ticket type should be mixed", "COACH", ticket.getSeatType());
			System.out.println(ticket);
		}
	}

}
