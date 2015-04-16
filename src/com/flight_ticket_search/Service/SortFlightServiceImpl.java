/**
 * 
 */
package com.flight_ticket_search.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flight_ticket_search.Entity.Flight;
import com.flight_ticket_search.Entity.Ticket;
import com.flight_ticket_search.Entity.Time;
import com.flight_ticket_search.Iterator.TicketCollection;
import com.flight_ticket_search.Iterator.TicketCollectionImpl;
import com.flight_ticket_search.Iterator.TicketIterator;
import com.flight_ticket_search.Iterator.TicketTypeEnum;

/**
 * @author lxybi_000
 * Date: 27, march, 2015
 * Description: Flight sorter class used to interact with UI to make
 * 				the right sort according to the request type, price, duration
 * 				take-off time, landing time.
 *
 */
public class SortFlightServiceImpl extends HttpServlet implements SortFlightService {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		
 		
		// 	Collection of All tickets combination, used for filtering (Collection.iterator(type))
 		TicketCollection ticketCollection = (TicketCollection) request.getSession().getAttribute("ticketCollection");
 		
 		TicketCollection filteredCollection = (TicketCollectionImpl) request.getSession().getAttribute("filteredCollection");
//		List of ALL Filtered tickets. Used for sorting as sort(List<Ticket>)
 		List<Ticket> filteredList = (List<Ticket>) ((TicketCollectionImpl) request.getSession().
 				getAttribute("filteredCollection")).getList();
 		
		/* section for seat filtering 
		 * check whether firstclass && coach && mixed is on 
		 * */
		TicketIterator itr;
		TicketCollection filterCollection = new TicketCollectionImpl();
		if (request.getParameter("filter_button") != null) {
			
			if (request.getParameter("firstclass") != null) {
			
				if (request.getParameter("firstclass").equals("on")) {
					
					itr = ticketCollection.iterator(TicketTypeEnum.FIRSTCLASS);
					
					while(itr.hasNext()) {
						filterCollection.add(itr.next());
					}
					
				}
			}
			
			if (request.getParameter("coach") != null) {
				
				if (request.getParameter("coach").equals("on")) {
				
					itr = ticketCollection.iterator(TicketTypeEnum.COACH);
				
					while(itr.hasNext()) {
						filterCollection.add(itr.next());
				
					}
				}
				
			}
			
			if (request.getParameter("mixed") != null) {
				if (request.getParameter("mixed").equals("on")) {
					
					itr = ticketCollection.iterator(TicketTypeEnum.MIXED);
				
					while(itr.hasNext()) {
						filterCollection.add(itr.next());
					}	
			
				}
			}	
			/* section for stop filtering */
			TicketIterator itr2;
			TicketCollectionImpl filterCollection2 = new TicketCollectionImpl();
			if (request.getParameter("nonestop") != null) {
				if (request.getParameter("nonestop").equals("on")) {
		
					itr2 = filterCollection.iterator(TicketTypeEnum.NONESTOP);
					
					while(itr2.hasNext()) {
						filterCollection2.add(itr2.next());
					}
					
				}
			}
			
			if (request.getParameter("onestop") != null) {
				if (request.getParameter("onestop").equals("on")) {
					
					itr2 = filterCollection.iterator(TicketTypeEnum.ONESTOP);
					
					while(itr2.hasNext()) {
						filterCollection2.add(itr2.next());
					}
				
				}			
			}
			
			if (request.getParameter("multistop") != null) {
				if (request.getParameter("multistop").equals("on")) {
				
					itr2 = filterCollection.iterator(TicketTypeEnum.MULTISTOP);
				
					while(itr2.hasNext()) {
						filterCollection2.add(itr2.next());
					}
				
				}
			}
			
			/* section for window filtering, takeOff/landing, duration */
			if (filterCollection2.getList().size() > 0) {
				int takeOffMin = filterCollection2.getList().size() *
						Integer.parseInt(request.getParameter("takeOffMin"))/100;
				int takeOffMax = filterCollection2.getList().size() *
						Integer.parseInt(request.getParameter("takeOffMax"))/100;
				filterByTakeOff(filterCollection2, takeOffMin, takeOffMax);
			}
			
			if (filterCollection2.getList().size() > 0) {
				int landingMin = filterCollection2.getList().size() *
						Integer.parseInt(request.getParameter("landingMin"))/100;
				int landingMax = filterCollection2.getList().size() *
						Integer.parseInt(request.getParameter("landingMax"))/100;
				filterByLanding(filterCollection2, landingMin, landingMax);
			}
			
			if (filterCollection2.getList().size() > 0) {
				int layoverMin = filterCollection2.getList().size() *
						Integer.parseInt(request.getParameter("layoverMin"))/100;
				int layoverMax = filterCollection2.getList().size() *
						Integer.parseInt(request.getParameter("layoverMax"))/100;
				filterByLayover(filterCollection2, layoverMin, layoverMax);
				
			}
			
			if (filterCollection2.getList().size() > 0) {
				int legMin = filterCollection2.getList().size() *
						Integer.parseInt(request.getParameter("legMin"))/100;
				int legMax = filterCollection2.getList().size() *
						Integer.parseInt(request.getParameter("legMax"))/100;
				filterByLeg(filterCollection2, legMin, legMax);
			}
			// request parameter setting
			request.setAttribute("sortedCollection", filterCollection2);
		}
		
		
		/* section of sorting */
		String sortType = request.getParameter("sort_type");
		if (sortType != null) {
			switch (sortType) {
				
				case "price":
					
					sortByPrice(filteredList);
					break;
					
				case "duration":
					
					sortByDuration(filteredList);
					break;
					
				case "take-off":
					
					sortByTakeoffTime(filteredList);
					break;
					
				case "landing":
					
					sortByLandingTime(filteredList);
					break;
					
				case "layover":
					
					sortByLayover(filteredList);
					break;
					
				default: 
					break;
			
			}
			
//			request parameter setting
			request.setAttribute("sortedCollection", filteredCollection);
		}
		
		
		//out.println(ticketList);
		
		RequestDispatcher rd = request.getRequestDispatcher("single_flight.jsp");  
		rd.forward(request,response);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * 
	 * @param flightListList
	 * @return the fligthList in sorted order asc/desc by its price
	 */
	public void sortByPrice(List<Ticket> tickets) {
		Collections.sort(tickets, new FlightSortByPrice());
	}
	
	
	/**
	 * 
	 * @param flightListList
	 * @return the flightList in sorted order according to the total length of the flight
	 */
	public void sortByDuration(List<Ticket> tickets) {
		Collections.sort(tickets, new FlightSortByDuration());
	}
	
	/**
	 * 
	 */
	public void sortByTakeoffTime(List<Ticket> tickets) {
		Collections.sort(tickets, new FlightSortByTakeoffTime());
	}
	
	/**
	 * 
	 */
	public void sortByLandingTime(List<Ticket> tickets) {
		Collections.sort(tickets, new FlightSortByLandingTime());
	}
	
	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortFlightService#sortByLayover(java.util.List)
	 */
	@Override
	public void sortByLayover(List<Ticket> tickets) {
		// TODO Auto-generated method stub
		Collections.sort(tickets, new FlightSortByLayover());
	}
	
	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortFlightService#sortByLeg(java.util.List)
	 */
	@Override
	public void sortByLeg(List<Ticket> tickets) {
		// TODO Auto-generated method stub
		Collections.sort(tickets, new FlightSortByLeg());
		
	}
	
	
	public class FlightSortByPrice implements Comparator<Ticket> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Ticket o1, Ticket o2) {
			// TODO Auto-generated method stub
			return Double.compare(o1.getTotalPrice(), o2.getTotalPrice());
		}
		
	}
	
	public class FlightSortByDuration implements Comparator<Ticket> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Ticket o1, Ticket o2) {
			// TODO Auto-generated method stub
			Time t1 = o1.getDuration();
			
			Time t2 = o2.getDuration();
			
			
			if (t1.getHour() != t2.getHour()) {
				
				return t1.getHour() - t2.getHour();
				
			} else {
				
				return t1.getMinute() - t2.getMinute();
			}

		}
		
	}
	
	public class FlightSortByTakeoffTime implements Comparator<Ticket> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Ticket o1, Ticket o2) {
			// TODO Auto-generated method stub
			if (o1.getTakeOffTime().getDate().getDay() == 
					o2.getTakeOffTime().getDate().getDay()) {
			
				if (o1.getTakeOffTime().getHour() == 
						o2.getTakeOffTime().getHour()) {
					
					return o1.getTakeOffTime().getMinute() - 
							o2.getTakeOffTime().getMinute();
					
				} else {
					
					return o1.getTakeOffTime().getHour() -
							o2.getTakeOffTime().getHour();
				}
				
			} else {
				
				return o1.getTakeOffTime().getDate().getDay() -
						o2.getTakeOffTime().getDate().getDay();
			}
		}
		
	}
	
	public class FlightSortByLandingTime implements Comparator<Ticket> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Ticket o1, Ticket o2) {
			// TODO Auto-generated method stub
			if (o1.getLandingTime().getDate().getDay() == 
					o2.getLandingTime().getDate().getDay()) {
			
				if (o1.getLandingTime().getHour() == 
						o2.getLandingTime().getHour()) {
					
					return o1.getLandingTime().getMinute() - 
							o2.getLandingTime().getMinute();
					
				} else {
					
					return o1.getLandingTime().getHour() -
							o2.getLandingTime().getHour();
				}
				
			} else {
				
				return o1.getLandingTime().getDate().getDay() -
						o2.getLandingTime().getDate().getDay();
			}
		}
		
	}
	
	public class FlightSortByLayover implements Comparator<Ticket> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Ticket o1, Ticket o2) {
			// TODO Auto-generated method stub
			return o1.getLayover().getHour() - o2.getLayover().getHour();
		}
		
	}
	
	public class FlightSortByLeg implements Comparator<Ticket> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Ticket o1, Ticket o2) {
			// TODO Auto-generated method stub
			return o1.getTotalLeg().getHour() - o2.getTotalLeg().getHour();
		}
		
		
	}
	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortFlightService#filterByTakeOff(com.flight_ticket_search.Iterator.TicketCollectionImpl)
	 */
	@Override
	public void filterByTakeOff(TicketCollectionImpl ticketCollection, int minIndex, int maxIndex) {
		// TODO Auto-generated method stub
		// retrieve list from ticket collection for use of sorting
		List<Ticket> ticketList = ticketCollection.getList();
		sortByTakeoffTime(ticketList);
		
		ticketCollection.setList(ticketList.subList(minIndex, maxIndex));
	}

	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortFlightService#filterByLanding(com.flight_ticket_search.Iterator.TicketCollectionImpl)
	 */
	@Override
	public void filterByLanding(TicketCollectionImpl ticketCollection, int minIndex, int maxIndex) {
		// TODO Auto-generated method stub
		List<Ticket> ticketList = ticketCollection.getList();
		sortByLandingTime(ticketList);
		
		ticketCollection.setList(ticketList.subList(minIndex, maxIndex));
	}

	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortFlightService#filterByLayover(com.flight_ticket_search.Iterator.TicketCollectionImpl)
	 */
	@Override
	public void filterByLayover(TicketCollectionImpl ticketCollection, int minIndex, int maxIndex) {
		// TODO Auto-generated method stub
		List<Ticket> ticketList = ticketCollection.getList();
		sortByLayover(ticketList);
		
		ticketCollection.setList(ticketList.subList(minIndex, maxIndex));
	}

	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortFlightService#filterByLeg(com.flight_ticket_search.Iterator.TicketCollectionImpl)
	 */
	@Override
	public void filterByLeg(TicketCollectionImpl ticketCollection, int minIndex, int maxIndex) {
		// TODO Auto-generated method stub
		List<Ticket> ticketList = ticketCollection.getList();
		sortByLeg(ticketList);
		
		ticketCollection.setList(ticketList.subList(minIndex, maxIndex));
	}


}
