/**
 * 
 */
package com.flight_ticket_search.Iterator;

import java.util.List;

import com.flight_ticket_search.Entity.Flight;
import com.flight_ticket_search.Entity.Ticket;

/**
 * @author lxybi_000
 *
 */
public interface TicketIterator  {
	
	/**
	 * Return whether next element is reacheable in the collection
	 * Used to check whether the iterator has next element for iteration
	 * 
	 * @return whether this iterator has next element to iterate through
	 */
	public boolean hasNext();
	
	/**
	 * Return the next element in the ticketCollection
	 * Move the cursor of the iterator to the next element and retrieve it
	 * 
	 * @return next element of the specified type of the collection
	 */
	public Ticket next();
	
	/**
	 * Remove the last element returned by the iterator, could only be called
	 * once after the next() call (Optional operation)
	 */
	public void remove();
}
