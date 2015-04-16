/**
 * @author lxybi_000
 * Date: 29, march, 2015
 */

package com.flight_ticket_search.Service;

import java.util.List;

import com.flight_ticket_search.Entity.Ticket;
import com.flight_ticket_search.Iterator.TicketCollection;

public interface RequestFlightService {
	
	/**
	 * Return all the ticketCollection of the single trip ticket
	 * 
	 * Get the request parameter from the UI, including the departing city,
	 * arriving city, and departure time, for one single trip and return
	 * all the tickets in collection
	 * 
	 * @param dCity the departing airport city chosen by user, for eg. "BOS"
	 * @param aCity the arriving airport city chosen by user, for eg. "LAX"
	 * @param dTime the departing date chosen by user, for eg. "2015-05-15"
	 * @return List of ticket collections [departing tickets]
	 */
	public TicketCollection getSingleTrip(String dCity, String aCity, String dTime);

	/**
	 * Return List of list of tickets of the request round trip ticket
	 *  
	 * Get the request parameter from the UI for round trip, including departing city,
	 * arriving city, departure time, arrival time, and return all the tickets in 
	 * List for iteration
	 * 
	 * @param dCityOne departing city airport code - departing trip
	 * @param aCityOne arriving city airport code - arriving trip
	 * @param dTimeOne departing time (Format: YYYY-MM-DD) - departing trip
	 * @param aTime 	arriving time (Format: YYYY-MM-DD) - arriving trip
	 * @return List of list of tickets [ [departing tickets] [returning tickets] ]
	 */
	public List<List<Ticket>> getRoundTrip(String dCityOne, String aCityOne, 
			String dTimeOne, String aTime);

}
