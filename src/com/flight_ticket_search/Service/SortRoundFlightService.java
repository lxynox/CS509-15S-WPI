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
	 * Sort round trip by its total price 
	 * @param ticketListList collection of round trip tickets
	 */
	public void sortByPrice(List<List<Ticket>> ticketListList);
	
	/**
	 * Sort round trip by its total length
	 * @param ticketListList collection of round trip tickets
	 */
	public void sortByDuration(List<List<Ticket>> ticketListList);
	
	/**
	 * Sort by the departure take-off time of the round trip
	 * @param ticketListList collection of round trip tickets
	 */
	public void sortByDepartureTakeOff(List<List<Ticket>> ticketListList);
	
	/**
	 * Sort by the departure landing-time of the round trip
	 * @param ticketListList collection of round trip tickets
	 */
	public void sortByDepartureLanding(List<List<Ticket>> ticketListList);

	/**
	 * Sort by the return take-off time of the round trip
	 * @param ticketListList collection of round trip tickets
	 */
	public void sortByReturnTakeOff(List<List<Ticket>> ticketListList);
	
	/**
	 * Sort by the return landing-time of the round trip
	 * @param ticketListList collection of round trip tickets
	 */
	public void sortByReturnLanding(List<List<Ticket>> ticketListList);
	
	/**
	 * Sort by the layover time of the round trip
	 * @param ticketListList collection of round trip tickets
	 */
	public void sortByLayover(List<List<Ticket>> ticketListList);
	
//	below are all filtering methods
	/**
	 * Filter by the departure take-off time of the round trip
	 * @param ticketListList collection of round trip tickets
	 * @param minHour the lower bound of the take-off time of departing 
	 * @param maxHour the upper bound of the take-off time of departing
	 * @return 
	 */
	public List<List<Ticket>> filterByDepartureTakeOff(List<List<Ticket>> ticketListList, int minHour, int maxHour);
	
	/**
	 * Filter by the return take-off time of the round trip
	 * @param ticketListList collection of round trip tickets 
	 * @param minHour the lower bound of the take-off time of returning
	 * @param maxHour the upper bound of the take-off time of returning
	 * @return 
	 */
	public List<List<Ticket>> filterByReturnTakeOff(List<List<Ticket>> ticketListList,  int minHour, int maxHour);
	
	/**
	 * Filter by the departure landing-time of the round trip
	 * @param ticketListList collection of round trip tickets
	 * @param minHour the lower bound of landing-time of departing
	 * @param maxHour the upper bound of landing-time of departing
	 */
	public List<List<Ticket>> filterByDepartureLanding(List<List<Ticket>> ticketListList, int minHour, int maxHour); 
	
	/**
	 * Filter by the return landing-time of the round trip
	 * @param ticketListList collection of round trip tickets
	 * @param minHour the lower bound of landing-time of returning
	 * @param maxHour the upper bound of landing-time of returning
	 * @return 
	 */
	public List<List<Ticket>> filterByReturnLanding(List<List<Ticket>> ticketListList, int minHour, int maxHour);

}
