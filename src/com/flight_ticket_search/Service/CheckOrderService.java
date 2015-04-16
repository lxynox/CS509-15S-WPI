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
public interface CheckOrderService {
	
	/**Return list of TicketInfo on check of users' orders
	 * Return list of TickeInfo, which contains the seating, number information
	 * of the user's order only user's ID(which they used to buy the ticket)
	 * has to be specified
	 *  
	 * @param userID user's input string of user_id
	 * @return list of TicketInfo
	 */
	public List<TicketInfo> getOrder (String userID);
	
	/**Return the User of the userID which is specified by the user at UI
	 * Use the given userID String to retrieve the right user stored in the 
	 * database user table.
	 * 
	 * @param userID user's input string of user_id
	 * @return The User corresponds to the userID
	 */
	public User getUser (String userID);
	
	/**Return whether the order is successfully removed from the database
	 * Use the flight number to delete the ticketInfo(one whole row in database) 
	 * stored in the database, return whether the operation is successfully 
	 * done at the back end.
	 * 
	 * @param number flight number 
	 * @return whether the order is successfully removed from database
	 */
	public boolean deleteOrder (String number);
}
