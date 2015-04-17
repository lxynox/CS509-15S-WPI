/**
 * @author lxybi_000
 * Date: 22, march, 2015
 * Description: Service called when user purchase a flight
 */

package com.flight_ticket_search.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flight_ticket_search.Dao.FlightDaoImpl;
import com.flight_ticket_search.Dao.UserDaoImpl;
import com.flight_ticket_search.Entity.Flight;
import com.flight_ticket_search.Entity.TicketInfo;
import com.flight_ticket_search.Entity.User;

public class ReservationServiceImpl extends HttpServlet implements ReservationService {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		RequestDispatcher rd = null; 
		
		Map<Integer, List<List<String>>> ticketMap = (Map<Integer, List<List<String>>>) 
				request.getSession().getAttribute("ticketMap");
		
		String ticketId = null;
		if (request.getParameter("ticketId") != null) { 
		// from single/round flight jsp pages
			ticketId = request.getParameter("ticketId");
			request.setAttribute("ticketId", ticketId);
		
		} else {
		//  redirecting from confirm_ticket_info.jsp
			ticketId = (String) request.getSession().getAttribute("id");
		}
		
		List<List<String>> purchaseList = ticketMap.get(Integer.parseInt(ticketId));
		request.getSession().setAttribute("purchaseList", purchaseList);
		
		if (request.getParameter("selectSingleTicket") != null
				|| request.getParameter("selectRoundTicket") != null) {
			
			boolean isLocked = new FlightDaoImpl().lockDB();
			
			//	check whether some clients is holding the lock of database
			if (isLocked) {
				// no other client is holding the lock: check if flights are available
				if (checkFlight(purchaseList)) {
					//case: continue to confirm page
					request.setAttribute("ticketId", ticketId);
					List<Flight> flightList = new ArrayList<Flight>();
					for (List<String> sList: purchaseList) { 
						flightList.add(new FlightDaoImpl().selectFlight(sList.get(0)));
					}
					
					request.setAttribute("flightList", flightList);
					rd = request.getRequestDispatcher("ticket_confirm_info.jsp");
					
				} else {
					
					new FlightDaoImpl().unlockDB();
					//case:  ticket is already sold out 
					request.setAttribute("ticketSoldout", true);
					
					if (request.getParameter("selectRoundTicket") != null) {
						
						request.setAttribute("sortedListList", request.getSession().
								getAttribute("filteredListList"));
						rd = request.getRequestDispatcher("round_flight.jsp");
						
					} else if (request.getParameter("selectSingleTicket") != null) {
						
						request.setAttribute("sortedCollection", request.getSession().
								getAttribute("filteredCollection"));
						rd = request.getRequestDispatcher("single_flight.jsp");
					}
					
				}
			
			} else {
				
				// extending case:  remind the user to line up for 2 minutes (lock failure)
				request.setAttribute("lockFailure", true);
				
				if (request.getParameter("selectRoundTicket") != null) {
					
					request.setAttribute("sortedListList", request.getSession().
							getAttribute("filteredListList"));
					rd = request.getRequestDispatcher("round_flight.jsp");
					
				} else if (request.getParameter("selectSingleTicket") != null) {
					
					request.setAttribute("sortedCollection", request.getSession().
							getAttribute("filteredCollection"));
					rd = request.getRequestDispatcher("single_flight.jsp");
				}
				
				rd.forward(request, response);
				
			}
				
		
		}
		
//		redirecting from ticket_confirm_info.jsp
		if (request.getParameter("buyTicket") != null) {
			
			String userID = request.getParameter("user_id");
		    String firstName = request.getParameter("first_name");
		    String lastName = request.getParameter("last_name");
		
//		    set the values into entities: User, TicketInfo, Order 
		    User user = new User (userID, firstName, lastName);
		    addUser (user);
		       
		    List<TicketInfo> orderList = (List<TicketInfo>) request.getSession().getAttribute("orderList");
		    for (TicketInfo ticketInfo: orderList) {
		    	 addTicket (ticketInfo);
		         addOrder (userID, ticketInfo.getNumber());
		    }
			
//		    start purchasing the ticket
			if (purchase(purchaseList)) {
				new FlightDaoImpl().unlockDB();
			}
			
			rd = request.getRequestDispatcher("thankyou_note.html");
		}
		
		
		rd.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
		
	}
	
	public boolean purchase(List<List<String>> purchaseList) {
		return new FlightDaoImpl().removeFlights(purchaseList);
	}
	
	public boolean checkFlight(List<List<String>> ticketInfo) {
		for (List<String> sticket: ticketInfo) {
			Flight  flight = 
					new FlightDaoImpl().selectFlight(sticket.get(0));
			
			switch (sticket.get(1)) {
				
				case "FirstClass":
					if (flight.getAirplane().getFirstClassSeats() == 
							flight.getSeat().getFirstClassSeats()) {
						return false;
					}
					break;
					
				case "Coach":
					if (flight.getAirplane().getCoachSeats() == 
							flight.getSeat().getCoachSeats()) {
						return false;
					}
					break;
					
				default: 
					break;
			}
			
		}
		
		return true;
	}
	
	 public boolean addUser(User user) {
     	  return new UserDaoImpl().addUser(user);
	 }
	   
	 public boolean addTicket(TicketInfo ticketInfo) {
		  return  new UserDaoImpl().addTicket(ticketInfo);
	 }
	   
	 public boolean addOrder(String userID, String number) {
		  return new UserDaoImpl().addOrder(userID, number);
	 }
}
