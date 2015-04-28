/**
 * 
 */
package com.flight_ticket_search.Iterator;

import com.flight_ticket_search.Entity.Ticket;

/**
 * @author lxybi_000
 * Date: 29, March, 2015
 * Description: Interface to put List(Flight)(one trip tickets) into List of collection
 * 				using iterator to specify sequence and approach of iteration
 */
public interface TicketCollection  {
	
	/**
	 * Return whether the ticket is successfully added to the collection
	 * Add the tickets of one whole trip to the TicketCollection 
	 * 
	 * @param ticket ticket that contains the global information of the flight-legs
	 * @return whether the tickets of one trip is successfully added to the 
	 * 			tickets collection
	 */
	public boolean add(Ticket ticket);
	


	/**
	 * Return whether the ticket is successfully removed from the collection
	 * Remove the specific tickets (for eg. [flight-leg1, flight-leg2])
	 *  of one trip from the tickets collection
	 * 
	 * @param ticket ticket that contains the global information of the flight-legs
	 * @return whether the ticket of one trip is successfully removed from 
	 * 			the ticket collection
	 */
	public boolean remove(Ticket ticket);

	
	/**
	 * Return the iterator of the ticketCollection 
	 * Specify the ticket type and get the corresponding iterator from the collection
	 * 
	 * @param type specify the type of iterator for use of iterating over an 
	 * 			given type, for eg. FIRSTCLASS , COACH , MIXED, NONESTOP, ONESTOP
	 * 			MULTISTOP
	 * 
	 * @return	the iterator of the specified type
	 */
	public TicketIterator iterator(TicketTypeEnum type);
}
