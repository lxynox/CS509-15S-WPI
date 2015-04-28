/**
 * 
 */
package com.flight_ticket_search.Service;

import java.util.List;

import com.flight_ticket_search.Entity.TicketInfo;
import com.flight_ticket_search.Entity.User;

/**
 * @author lxybi_000
 *
 */
public interface ReservationService {
	
	/**
	 * Return whether the purchase of the client is done
	 * The purchaseList must contain valid information about the flight legs
	 * in the database and each sublist should contain two elements, one is
	 * the flight number, the other is the seat type
	 * 
	 * @param string list of all flight leg information of the whole trip
	 * @return whether the flight-legs are successfully removed from the database
	 */
	public boolean purchase(List<List<String>> purchaseList);
	
	/**
	 * Return if tickets the client want are available  
	 * The purchaseList must contain valid information about the flight legs
	 * in the database and each sublist should contain two elements, one is
	 * the flight number, the other is the seat type. This method is called 
	 * before the final purchase to ensure the client could get the ticket for 
	 * the trip
	 * 
	 * @param purchaseList string list of all flight leg information of the whole trip
	 * @return whether  the flight-legs contained in the ticket are still available
	 */
	public boolean checkFlight(List<List<String>> purchaseList);
	
	/**
	 * Return whether the user is successfully added to the database
	 * @param user
	 * @return
	 */
	public boolean addUser (User user);
	
	/**
	 * Return whether the ticketInfo is successfully added into the database
	 * @param ticketInfo
	 * @return
	 */
	public boolean addTicket (TicketInfo ticketInfo);
	
	/**
	 * Return whether the order is successfully added into the database
	 * @param userID
	 * @param number
	 * @return
	 */
	public boolean addOrder (String userID, String number);
}
