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
	/**
	 * 
	 * @param user
	 * @return
	 */
	public boolean addUser(User user);
	
	/**
	 * 
	 * @param ticketInfo
	 * @return
	 */
	public boolean addTicket(TicketInfo ticketInfo);
	
	/**
	 * 
	 * @param userID
	 * @return
	 */
	public User selectUser(String userID);
	
	/**
	 * 
	 * @param userID
	 * @param number
	 * @return
	 */
	public boolean addOrder(String userID, String number);
	
	/**
	 * 
	 * @param userID
	 * @return
	 */
	public List<TicketInfo> selectOrder(String userID);
	
	/**
	 * 
	 * @param number
	 * @return
	 */
	public boolean deleteOrder(String number);
}
