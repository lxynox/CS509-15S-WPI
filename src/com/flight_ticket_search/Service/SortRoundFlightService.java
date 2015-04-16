/**
 * 
 */
package com.flight_ticket_search.Service;

import java.util.List;

import com.flight_ticket_search.Entity.Ticket;

/**
 * @author lxybi_000
 *
 */
public interface SortRoundFlightService {
	
//	below are all sorting methods
	/**
	 * 
	 * @param ticketListList
	 */
	public void sortByPrice(List<List<Ticket>> ticketListList);
	
	/**
	 * 
	 * @param ticketListList
	 */
	public void sortByDuration(List<List<Ticket>> ticketListList);
	
	/**
	 * 
	 * @param ticketListList
	 */
	public void sortByDepartureTakeOff(List<List<Ticket>> ticketListList);
	
	/**
	 * 
	 * @param ticketListList
	 */
	public void sortByDepartureLanding(List<List<Ticket>> ticketListList);

	/**
	 * 
	 * @param ticketListList
	 */
	public void sortByReturnTakeOff(List<List<Ticket>> ticketListList);
	
	/**
	 * 
	 * @param ticketListList
	 */
	public void sortByReturnLanding(List<List<Ticket>> ticketListList);
	
	/**
	 * 
	 * @param ticketListList
	 */
	public void sortByLayover(List<List<Ticket>> ticketListList);
	
//	below are all filtering methods
	/**
	 * 
	 * @param ticketListList
	 * @param minHour
	 * @param maxHour
	 * @return 
	 */
	public List<List<Ticket>> filterByDepartureTakeOff(List<List<Ticket>> ticketListList, int minHour, int maxHour);
	
	/**
	 * 
	 * @param ticketListList
	 * @param minHour
	 * @param maxHour
	 * @return 
	 */
	public List<List<Ticket>> filterByReturnTakeOff(List<List<Ticket>> ticketListList,  int minHour, int maxHour);
	
	/**
	 * 
	 * @param ticketListList
	 * @param minHour
	 * @param maxHour
	 * @return 
	 */
	public List<List<Ticket>> filterByDepartureLanding(List<List<Ticket>> ticketListList, int minHour, int maxHour); 
	
	/**
	 * 
	 * @param ticketListList
	 * @param minHour
	 * @param maxHour
	 * @return 
	 */
	public List<List<Ticket>> filterByReturnLanding(List<List<Ticket>> ticketListList, int minHour, int maxHour);

}
