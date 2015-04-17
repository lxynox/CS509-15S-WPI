/**
 * 
 */
package com.flight_ticket_search.Dao;

import java.util.List;

import com.flight_ticket_search.Entity.TicketInfo;
import com.flight_ticket_search.Entity.User;

/**
 * @author lxybi_000
 *
 */

public interface UserDao {
	/**Return whether the user's information is added to database
	 * Given the user information encapsulated in user entity, add them to 
	 * database and return whether the operation is successful or not
	 * 
	 * @param user contains user id, firstName, lastName information specified by the user
	 * @return whether the user information is stored in the database
	 */
	public boolean addUser(User user);
	
	/**Return whether the ticket information is added to database
	 * Given the tickets information encapsulated in ticketInfo entity, add them to 
	 * database and return whether the operation is successful or not
	 * 
	 * @param ticketInfo the tickets and its information the user wants to buy 
	 * @return whether the ticketInfo is stored into the database or not
	 */
	public boolean addTicket(TicketInfo ticketInfo);
	
	/**Return whether the order information is added to database
	 * Given the userID and flight ticket number, add them to the  
	 * database and return whether the operation is successful or not
	 * 
	 * @param userID the userID specified by the user from UI 
	 * @param number the flight tickets number
	 * @return whether the ticketInfo is stored into the database or not
	 */
	public boolean addOrder(String userID, String number);
	
	/**Return the user specified by the userID
	 * Use the userID to retrieve the right user 
	 * 
	 * @param userID the symbol used to retrieve the user
	 * @return User the right user corresponds to the userID
	 */
	public User selectUser(String userID);

	/**Return the order owned by the user whose id is userID
	 * Use the userID to specify the user and to retrieve his/her 
	 * order 
	 * 
	 * @param userID  the owner of the orders (TicketInfo)
	 * @return List<TicketInfo> the order (TicketInfo) in list 
	 */
	public List<TicketInfo> selectOrder(String userID);
	
	/**Return whether the ticket is successfully removed from the database
	 * Given the flight ticket number the user wants to remove from their order
	 * , return whether the ticket is successfully removed from the database
	 * 
	 * @param number flight ticket number 
	 * @return whether the ticket is successfully removed from the database
	 */
	public boolean deleteOrder(String number);
}
