
package com.flight_ticket_search.Dao;

import java.util.List;

import com.flight_ticket_search.Entity.TicketInfo;
import com.flight_ticket_search.Entity.User;

/**
 * @author lxybi_000
 * Date: April, 10, 2015
 * Description: UserDaoImpl implements the interface of UserDao 
 * for interaction with the logic/business layer
 */
public class UserDaoImpl implements UserDao {

	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		SQLConnector.getInstance().addUser(user);
		return false;
	}

	@Override
	public boolean addTicket(TicketInfo ticketInfo) {
		// TODO Auto-generated method stub
		return SQLConnector.getInstance().addTicket(ticketInfo);
	}

	@Override
	public User selectUser(String userID) {
		// TODO Auto-generated method stub
		return SQLConnector.getInstance().getUser(userID);
	}

	@Override
	public boolean addOrder(String userID, String number) {
		// TODO Auto-generated method stub
		return SQLConnector.getInstance().addOrder(userID, number);
	}

	@Override
	public List<TicketInfo> selectOrder(String userID) {
		// TODO Auto-generated method stub
		return SQLConnector.getInstance().getOrder(userID);
	}

	@Override
	public boolean deleteOrder(String number) {
		// TODO Auto-generated method stub
		return SQLConnector.getInstance().cancelOrder(number);
	}

}
