package com.flight_ticket_search.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flight_ticket_search.Entity.Ticket;

public class SortRoundFlightServiceImpl extends HttpServlet 
								implements SortRoundFlightService{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		
		// storing global value for filtering 
		List<List<Ticket>> tickListList = (List<List<Ticket>>) request.getSession().
								getAttribute("roundTickets");
		
		// retrieve current request scope value for sorting
		List<List<Ticket>> filteredListList = (List<List<Ticket>>) request.getSession().
								getAttribute("filteredListList");
		
		String sortType = request.getParameter("sort_type");
		
		
		/* section for filtering */
		List<List<Ticket>> filterListList = new ArrayList<List<Ticket>>();
		if (request.getParameter("filter_button") != null) {
			
			// First the seating filter
			if (request.getParameter("coach") != null) {
				if (request.getParameter("coach").equals("on")) {
					
					for (List<Ticket> tickList: tickListList) {
						if (tickList.get(0).getSeatType().equals("COACH")
								&& tickList.get(1).getSeatType().equals("COACH")) {
							
							filterListList.add(tickList);
						}
					}
					
				}
			}
			
			if (request.getParameter("firstclass") != null) {
				if (request.getParameter("firstclass").equals("on")) {
					
					for (List<Ticket> tickList: tickListList) {
						if (tickList.get(0).getSeatType().equals("FIRSTCLASS")
								&& tickList.get(1).getSeatType().equals("FIRSTCLASS")) {
							
							filterListList.add(tickList);
						}
					}
				}
			}
			
			if (request.getParameter("mixed") != null) {
				if (request.getParameter("mixed").equals("on")) {
					
					for (List<Ticket> tickList: tickListList) {
						if (tickList.get(0).getSeatType().equals("MIXED")
								|| tickList.get(1).getSeatType().equals("MIXED")) {
							
							filterListList.add(tickList);
						}
					}
					
				}
			}
			
			
			// next the stop filter
			List<List<Ticket>> filterListList2 = new ArrayList<List<Ticket>>();
			
			if (request.getParameter("nonestop") != null) {
				if (request.getParameter("nonestop").equals("on")) {
					
					for (List<Ticket> tickList: filterListList) {
						if (tickList.get(0).getStopType().equals("none stop")
								&& tickList.get(1).getStopType().equals("none stop")) {
							
							filterListList2.add(tickList);
						}
					}
					
				}
			}
			
			if (request.getParameter("onestop") != null) {
				if (request.getParameter("onestop").equals("on")) {
					
					for (List<Ticket> tickList: filterListList) {
						if (tickList.get(0).getStopType().equals("one stop")
								|| tickList.get(1).getStopType().equals("one stop")) {
							
							filterListList2.add(tickList);
						}
					}
					
				}
			}
	
			/* section for window filtering, takeOff/landing, duration */
			
			List<List<Ticket>> filterListList3 = new ArrayList<List<Ticket>>();
			if (filterListList2.size() > 0) {
				int takeOffMin = filterListList2.size() *
						Integer.parseInt(request.getParameter("departTakeOffMin"))/100;
				int takeOffMax = filterListList2.size() *
						Integer.parseInt(request.getParameter("departTakeOffMax"))/100;
				filterListList3 = filterByDepartureTakeOff(filterListList2, takeOffMin, takeOffMax);
			}
			
			List<List<Ticket>> filterListList4 = new ArrayList<List<Ticket>>();
			if (filterListList3.size() > 0) {
				int landingMin = filterListList3.size() *
						Integer.parseInt(request.getParameter("departLandingMin"))/100;
				int landingMax = filterListList3.size() *
						Integer.parseInt(request.getParameter("departLandingMax"))/100;
				filterListList4 = filterByDepartureLanding(filterListList3, landingMin, landingMax);
			}
			
			List<List<Ticket>> filterListList5 = new ArrayList<List<Ticket>>();
			if (filterListList4.size() > 0) {
				int takeOffMin = filterListList4.size() *
						Integer.parseInt(request.getParameter("returnTakeOffMin"))/100;
				int takeOffMax = filterListList4.size() *
						Integer.parseInt(request.getParameter("returnTakeOffMax"))/100;
				filterListList5 = filterByReturnTakeOff(filterListList4, takeOffMin, takeOffMax);
				
			}
			
			List<List<Ticket>> filterListList6 = new ArrayList<List<Ticket>>();
			if (filterListList5.size() > 0) {
				int landingMin = filterListList5.size() *
						Integer.parseInt(request.getParameter("returnLandingMin"))/100;
				int landingMax = filterListList5.size() *
						Integer.parseInt(request.getParameter("returnLandingMax"))/100;
				filterListList6 = filterByReturnLanding(filterListList5, landingMin, landingMax);
			}
			
			request.setAttribute("sortedListList", filterListList6);

		}
		
		
		/* section for sorting */
		if (sortType != null) {
			
			switch (sortType) {
			
				case "price":
					
					sortByPrice(filteredListList);
					break;
					
				case "duration":
					
					sortByDuration(filteredListList);
					break;
					
				case "depart_takeoff":
					
					sortByDepartureTakeOff(filteredListList);
					break;
					
				case "return_takeoff":
					
					sortByReturnTakeOff(filteredListList);
					break;
					
				case "depart_landing":
					
					sortByDepartureLanding(filteredListList);
					break;
					
				case "return_landing":
					
					sortByReturnLanding(filteredListList);
					break;
					
				case "layover":
					
					sortByLayover(filteredListList);
					break;
					
				default:
					
					break;
			}
			
			request.setAttribute("sortedListList", filteredListList);
		
		}
		
		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("round_flight.jsp");
		rd.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortRoundFlightService#sortByPrice(java.util.List)
	 */
	@Override
	public void sortByPrice(List<List<Ticket>> ticketListList) {
		// TODO Auto-generated method stub
		Collections.sort(ticketListList, new PriceComparator());
		
	}

	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortRoundFlightService#sortByDuration(java.util.List)
	 */
	@Override
	public void sortByDuration(List<List<Ticket>> ticketListList) {
		// TODO Auto-generated method stub
		Collections.sort(ticketListList, new DurationComparator());
		
	}

	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortRoundFlightService#sortByDepartureTakeOff(java.util.List)
	 */
	@Override
	public void sortByDepartureTakeOff(List<List<Ticket>> ticketListList) {
		// TODO Auto-generated method stub
		Collections.sort(ticketListList, new DepartureTakeOffComparator());
	}

	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortRoundFlightService#sortByDepartureLanding(java.util.List)
	 */
	@Override
	public void sortByDepartureLanding(List<List<Ticket>> ticketListList) {
		// TODO Auto-generated method stub
		Collections.sort(ticketListList, new DepartureLandingComparator());
	}

	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortRoundFlightService#sortByReturnTakeOff(java.util.List)
	 */
	@Override
	public void sortByReturnTakeOff(List<List<Ticket>> ticketListList) {
		// TODO Auto-generated method stub
		Collections.sort(ticketListList, new ReturnTakeOffComparator());
	}

	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortRoundFlightService#sortByReturnLanding(java.util.List)
	 */
	@Override
	public void sortByReturnLanding(List<List<Ticket>> ticketListList) {
		// TODO Auto-generated method stub
		Collections.sort(ticketListList, new ReturnLandingComparator());
	}
	

	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortRoundFlightService#sortByLayover(java.util.List)
	 */
	@Override
	public void sortByLayover(List<List<Ticket>> ticketListList) {
		// TODO Auto-generated method stub
		Collections.sort(ticketListList, new LayoverComparator());
	}
	
	public class PriceComparator implements Comparator<List<Ticket>> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(List<Ticket> o1, List<Ticket> o2) {
			// TODO Auto-generated method stub
			return Double.compare(o1.get(0).getTotalPrice() + o1.get(1).getTotalPrice(),
						 o2.get(0).getTotalPrice() + o2.get(1).getTotalPrice());
		}
		
	}

	public class DurationComparator implements Comparator<List<Ticket>> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(List<Ticket> o1, List<Ticket> o2) {
			// TODO Auto-generated method stub
			return o1.get(0).getDuration().getHour()*60 + o1.get(0).getDuration().getMinute() +
					o1.get(1).getDuration().getHour()*60 + o1.get(1).getDuration().getMinute()
 						- (o2.get(0).getDuration().getHour()*60 + o2.get(0).getDuration().getMinute() +
 						o2.get(1).getDuration().getHour()*60 + o2.get(1).getDuration().getMinute());
		}
		
	}
	
	public class DepartureTakeOffComparator implements Comparator<List<Ticket>> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(List<Ticket> o1, List<Ticket> o2) {
			// TODO Auto-generated method stub
			if (o1.get(0).getTakeOffTime().getDate().getDay() == 
					o2.get(0).getTakeOffTime().getDate().getDay()) {
			
				if (o1.get(0).getTakeOffTime().getHour() == 
						o2.get(0).getTakeOffTime().getHour()) {
					
					return o1.get(0).getTakeOffTime().getMinute() - 
							o2.get(0).getTakeOffTime().getMinute();
					
				} else {
					
					return o1.get(0).getTakeOffTime().getHour() -
							o2.get(0).getTakeOffTime().getHour();
				}
				
			} else {
				
				return o1.get(0).getTakeOffTime().getDate().getDay() -
						o2.get(0).getTakeOffTime().getDate().getDay();
			}
	
		}
		
	}
	
	public class DepartureLandingComparator implements Comparator<List<Ticket>> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(List<Ticket> o1, List<Ticket> o2) {
			// TODO Auto-generated method stub
			if (o1.get(0).getLandingTime().getDate().getDay() == 
					o2.get(0).getLandingTime().getDate().getDay()) {
			
				if (o1.get(0).getLandingTime().getHour() == 
						o2.get(0).getLandingTime().getHour()) {
					
					return o1.get(0).getLandingTime().getMinute() - 
							o2.get(0).getLandingTime().getMinute();
					
				} else {
					
					return o1.get(0).getLandingTime().getHour() -
							o2.get(0).getLandingTime().getHour();
				}
				
			} else {
				
				return o1.get(0).getLandingTime().getDate().getDay() -
						o2.get(0).getLandingTime().getDate().getDay();
			}
	
		}
		
	}
	
	public class ReturnTakeOffComparator implements Comparator<List<Ticket>> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(List<Ticket> o1, List<Ticket> o2) {
			// TODO Auto-generated method stub
			if (o1.get(1).getTakeOffTime().getDate().getDay() == 
					o2.get(1).getTakeOffTime().getDate().getDay()) {
			
				if (o1.get(1).getTakeOffTime().getHour() == 
						o2.get(1).getTakeOffTime().getHour()) {
					
					return o1.get(1).getTakeOffTime().getMinute() - 
							o2.get(1).getTakeOffTime().getMinute();
					
				} else {
					
					return o1.get(1).getTakeOffTime().getHour() -
							o2.get(1).getTakeOffTime().getHour();
				}
				
			} else {
				
				return o1.get(1).getTakeOffTime().getDate().getDay() -
						o2.get(1).getTakeOffTime().getDate().getDay();
			}
	
		}
		
	}
	
	public class ReturnLandingComparator implements Comparator<List<Ticket>> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(List<Ticket> o1, List<Ticket> o2) {
			// TODO Auto-generated method stub
			if (o1.get(1).getLandingTime().getDate().getDay() == 
					o2.get(1).getLandingTime().getDate().getDay()) {
			
				if (o1.get(1).getLandingTime().getHour() == 
						o2.get(1).getLandingTime().getHour()) {
					
					return o1.get(1).getLandingTime().getMinute() - 
							o2.get(1).getLandingTime().getMinute();
					
				} else {
					
					return o1.get(1).getLandingTime().getHour() -
							o2.get(1).getLandingTime().getHour();
				}
				
			} else {
				
				return o1.get(1).getLandingTime().getDate().getDay() -
						o2.get(1).getLandingTime().getDate().getDay();
			}
		}
		
	}
	

	public class LayoverComparator implements Comparator<List<Ticket>> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(List<Ticket> o1, List<Ticket> o2) {
			// TODO Auto-generated method stub
			return o1.get(0).getLayover().getHour()*60 + o1.get(0).getLayover().getMinute() +
					o1.get(1).getLayover().getHour()*60 + o1.get(1).getLayover().getMinute() -
					  (o2.get(0).getLayover().getHour()*60 + o2.get(0).getLayover().getMinute() +
						o2.get(1).getLayover().getHour()*60 + o2.get(1).getLayover().getMinute());
		}
		
	}
	
	

	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortRoundFlightService#filterByDepartureTakeOff(java.util.List, int, int)
	 */
	@Override
	public List<List<Ticket>> filterByDepartureTakeOff(List<List<Ticket>> ticketListList,
			int minIndex, int maxIndex) {
		// TODO Auto-generated method stub
		sortByDepartureTakeOff(ticketListList);
		return new ArrayList<List<Ticket>>(ticketListList.subList(minIndex, maxIndex));
		
	}

	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortRoundFlightService#filterByReturnTakeOff(java.util.List, int, int)
	 */
	@Override
	public List<List<Ticket>> filterByReturnTakeOff(List<List<Ticket>> ticketListList,
			int minIndex, int maxIndex) {
		// TODO Auto-generated method stub
		sortByReturnTakeOff(ticketListList);
		return new ArrayList<List<Ticket>>(ticketListList.subList(minIndex, maxIndex));
	}

	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortRoundFlightService#filterByDepartureLanding(java.util.List, int, int)
	 */
	@Override
	public List<List<Ticket>> filterByDepartureLanding(List<List<Ticket>> ticketListList,
			int minIndex, int maxIndex) {
		// TODO Auto-generated method stub
		sortByDepartureLanding(ticketListList);
		return new ArrayList<List<Ticket>>(ticketListList.subList(minIndex, maxIndex));
	
	}

	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Service.SortRoundFlightService#filterByReturnLanding(java.util.List, int, int)
	 */
	@Override
	public List<List<Ticket>> filterByReturnLanding(List<List<Ticket>> ticketListList,
			int minIndex, int maxIndex) {
		// TODO Auto-generated method stub
		sortByReturnLanding(ticketListList);
		return new ArrayList<List<Ticket>>(ticketListList.subList(minIndex, maxIndex));
	
	}
	
}
