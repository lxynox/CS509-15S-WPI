/**
 * 
 */
package com.flight_ticket_search.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flight_ticket_search.Dao.UserDaoImpl;
import com.flight_ticket_search.Entity.TicketInfo;
import com.flight_ticket_search.Entity.User;

/**
 * @author lxybi_000
 *
 */
@SuppressWarnings("serial")
public class CheckOrderServiceImpl extends HttpServlet implements
		CheckOrderService {
	
	@Override
	   protected void doGet (HttpServletRequest request, HttpServletResponse response) {
	    
	   }

	   @Override
	   protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	       PrintWriter out = new PrintWriter();
	       RequestDispatcher rd = null;		   
//		   if page directs from check_order homepage.html
		   if (request.getParameter("get_order") != null) {
			   String userID = request.getParameter("user_id");
			   request.getSession().setAttribute("userID", userID);
			   request.setAttribute("orderList", getOrder(userID));
			   request.setAttribute("userInfo", getUser(userID));
		   }
		   
//		   if page directs from confirm/cancel order page (return back to homepage)
		   if (request.getParameter("confirm") != null) {
			   
			   rd = request.getRequestDispatcher("homepage.html");
			   rd.forward(request, response);
		   }
		   
		   if (request.getParameter("cancel_order") != null) {
			   
			   Map<Integer, String> map = (HashMap<Integer, String>) request.getSession().getAttribute("cancelMap");
			   for (int i=0; i < map.size(); i++) {
				   if (request.getParameter("cancel_button" + i) != null) {
					   deleteOrder(map.get(i));
					   String userID = (String) request.getSession().getAttribute("userID");
					   request.setAttribute("orderList", getOrder(userID));
				   }
			   }
		   }
		   
		   rd = request.getRequestDispatcher("check_order.jsp");
	       rd.forward(request, response);
	   }

	   
	   public List<TicketInfo> getOrder (String userID) {
		   return new UserDaoImpl().selectOrder(userID);
	   }
	   
	   public User getUser (String userID) {
		   return new UserDaoImpl().selectUser(userID);
	   }
	   
	   public boolean deleteOrder (String number) {
		   return new UserDaoImpl().deleteOrder(number);
	   }


}
