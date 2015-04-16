/**
 * 
 */
package com.flight_ticket_search.Service;

import java.util.List;

import com.flight_ticket_search.Entity.Flight;
import com.flight_ticket_search.Entity.Ticket;
import com.flight_ticket_search.Iterator.TicketCollectionImpl;

/**
 * @author lxybi_000
 *
 */
public interface SortFlightService {
	
	/**
	 * Sort all the tickets by price in ascending order by default
	 * Use java.util.Collections to sort, Ticket must be comparable
	 * or a comparator must be specified for sorting
	 * 
	 * @param ticketList list of flight tickets
	 */
	public void sortByPrice(List<Ticket> ticketList);
	
	
	/**
	 * Sort all the tickets by duration(length of trip) in ascending order by default
	 * Use java.util.Collections to sort, Ticket must be comparable
	 * or a comparator must be specified for sorting
	 * 
	 * @param ticketList list of flight tickets
	 */
	public void sortByDuration(List<Ticket> ticketList);
	
	
	
	/**
	 * Sort all the tickets by take-off time of flight in ascending order by default
	 * Use java.util.Collections to sort, Ticket must be comparable
	 * or a comparator must be specified for sorting
	 * 
	 * @param ticketList list of flight tickets
	 */
	public void sortByTakeoffTime(List<Ticket> ticketList);
	
	
	
	/**
	 * Sort all the tickets by landing time in ascending order by default
	 * Use java.util.Collections to sort, Ticket must be comparable
	 * or a comparator must be specified for sorting
	 * 
	 * @param ticketList list of flight tickets
	 */	
	public void sortByLandingTime(List<Ticket> ticketList);
	
	
	/**
	 * Sort all the tickets by layover time in ascending order by default
	 * Use java.util.Collections to sort, Ticket must be comparable
	 * or a comparator must be specified for sorting
	 * 
	 * @param ticketList list of flight tickets
	 */
	public void sortByLayover(List<Ticket> ticketList);
	
	
	/**
	 * Sort all the tickets by length of total flight leg time in ascending order by default
	 * Use java.util.Collections to sort, Ticket must be comparable
	 * or a comparator must be specified for sorting
	 * 
	 * @param ticketList list of flight tickets
	 */
	public void sortByLeg(List<Ticket> ticketList);
	
	/**
	 * Filter the ticket collection by the specified time window of take-off time
	 * MinIndex and maxIndex are used to specify the range of the ticketCollection
	 * , which should be the sub-collection of the original one. MinIndex could not 
	 * exceed the maxIndex
	 * 
	 * @param ticketCollection all flight tickets of one single trip the user specified
	 * @param minIndex	the lower bound of the ticketCollection
	 * @param maxIndex	the upper bound of the ticketCollection
	 */
	public void filterByTakeOff(TicketCollectionImpl ticketCollection, int minIndex, int maxIndex);
	
	/**
	 * Filter the ticket collection by the specified time window of landing time
	 * MinIndex and maxIndex are used to specify the range of the ticketCollection
	 * , which should be the sub-collection of the original one. MinIndex could not 
	 * exceed the maxIndex
	 * 
	 * @param ticketCollection all flight tickets of one single trip the user specified
	 * @param minIndex	the lower bound of the ticketCollection
	 * @param maxIndex	the upper bound of the ticketCollection
	 */
	public void filterByLanding(TicketCollectionImpl ticketCollection, int minIndex, int maxIndex);
	
	/**
	 * Filter the ticket collection by the specified time window of layover time
	 * MinIndex and maxIndex are used to specify the range of the ticketCollection
	 * , which should be the sub-collection of the original one. MinIndex could not 
	 * exceed the maxIndex
	 * 
	 * @param ticketCollection all flight tickets of one single trip the user specified
	 * @param minIndex	the lower bound of the ticketCollection
	 * @param maxIndex	the upper bound of the ticketCollection
	 */
	public void filterByLayover(TicketCollectionImpl ticketCollection, int minIndex, int maxIndex);
	
	/**
	 * Filter the ticket collection by the specified time window of all flight-legs time
	 * MinIndex and maxIndex are used to specify the range of the ticketCollection
	 * , which should be the sub-collection of the original one. MinIndex could not 
	 * exceed the maxIndex
	 * 
	 * @param ticketCollection all flight tickets of one single trip the user specified
	 * @param minIndex	the lower bound of the ticketCollection
	 * @param maxIndex	the upper bound of the ticketCollection
	 */
	public void filterByLeg(TicketCollectionImpl ticketCollection, int minIndex, int maxIndex);

}
