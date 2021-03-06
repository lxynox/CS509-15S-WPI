/**
 * 
 * Created to interact with the users' search flight request
 * return noneStop, oneStop, MultiStop flights list as response to
 * their request 
 * 
 * User specify FROM, TO, DATE
 * @author lxybi_000
 * Date: 15, march, 2015
 *
 */
package com.flight_ticket_search.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flight_ticket_search.Dao.FlightDaoImpl;
import com.flight_ticket_search.Entity.Date;
import com.flight_ticket_search.Entity.Flight;
import com.flight_ticket_search.Entity.Ticket;
import com.flight_ticket_search.Entity.Time;
import com.flight_ticket_search.Iterator.TicketCollection;
import com.flight_ticket_search.Iterator.TicketCollectionImpl;
import com.flight_ticket_search.Iterator.TicketIterator;
import com.flight_ticket_search.Iterator.TicketTypeEnum;
import com.flight_ticket_search.Util.AirportAdapter;
import com.flight_ticket_search.Util.DateOutOfRangeException;
import com.flight_ticket_search.Util.InvalidAirportCodeException;
import com.flight_ticket_search.Util.TimeAdapter;

public class RequestFlightServiceImpl extends HttpServlet implements
		RequestFlightService {

	private static final long serialVersionUID = 1L;
	
	public final static String INVALID_INPUT =  "invalidInput";
	public final static String NULL_INPUT = "nullInput";

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		 
		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("homepage.jsp");
		
		/* user input parameters from the UI */
		 String dCityCode = request.getParameter("departureCity");
		 String aCityCode = request.getParameter("arrivalCity");
		 String dDate = request.getParameter("departureDate");
		 
		 /* validated parameter declaration */
		 String validDepartureDate = null, validReturnDate = null, validDepartureAirport = null,
				 validArrivalAirport = null;
		 Date departureDate = null, returnDate = null;

		/* verify user inputs here (input validations); User input FORM input validation block 
		 * 1. first: input airport code must be valid 3 digit airport code (CASE_INSENSITIVE)
		 * 2. second: departure time should be in format of 2015-05-11
		 * 3. third: departure/return time both should be within the range of 2015,May,08 ~ 2015,May,17
		 * 4. fourth: return time must not be earlier than departure time
		 * 5. fifth: no empty input fields
		 * */
		 if (dCityCode != "" && aCityCode != "") {
			 try {
				validDepartureAirport = new AirportAdapter().getAirportCodeFromUI (dCityCode);
				validArrivalAirport = new AirportAdapter().getAirportCodeFromUI (aCityCode);
			 } catch (InvalidAirportCodeException iace) {
				request.setAttribute(INVALID_INPUT, iace.getMessage());
				rd.forward(request, response);
			 }
		 } else {
//			 null input exception
			 request.setAttribute(NULL_INPUT, "input fields cannot be empty!");
			 rd.forward(request, response);
		 }
		 
		 if (dDate != "") {
			 try {
				 departureDate = new TimeAdapter().getDateFromUI(dDate);
				 validDepartureDate = "" + departureDate.getYear() + "_0" +
						 						  departureDate.getMonth() + "_" +
						 						  (departureDate.getDay() < 10? "0":"") +
						 						  departureDate.getDay();
			 } catch (NumberFormatException nfe) {
				 request.setAttribute (INVALID_INPUT, "Valid date format should be: YYYY-MM-DD, for eg. 2015-05-10");
				 rd.forward(request, response);
			 } catch (DateOutOfRangeException dobe) {
				 request.setAttribute (INVALID_INPUT, dobe.getMessage());
				 rd.forward(request, response); 
			 } catch (Exception exp) {
				 request.setAttribute (INVALID_INPUT, "Valid date format should be: YYYY-MM-DD, for eg. 2015-05-10");
				rd.forward(request, response); 
			 }
			 
		 } else {
//			 null input exception
			 request.setAttribute(NULL_INPUT, "input fields cannot be empty!");
			 rd.forward(request,  response);
		 }
		 
		 TicketCollection singleTicket = null;
		 List<List<Ticket>> roundTicket = null; 
		 /* specify single-trip/ round-trip first */
		 if (request.getParameter("trip_type").equals("round")) {
			 String rDate = request.getParameter("returnDate");
			 if (rDate != "") {
				 try {
					 returnDate = new TimeAdapter().getDateFromUI(rDate);
//					 check whether the return date is no earlier than departure date
					 if (returnDate.getDay() < departureDate.getDay()) {
//						 return date earlier than departure date exception
						 request.setAttribute(INVALID_INPUT, "Return date must not be ealier than departure date !");
						 rd.forward(request, response);
					 }
					 validReturnDate = "" + returnDate.getYear() + "_0" +
							 						returnDate.getMonth() + "_" +
							 					    (returnDate.getDay() < 10? "0":"") +
							 					    returnDate.getDay();
				 } catch (NumberFormatException nfe) {
					 request.setAttribute (INVALID_INPUT, "Valid date format should be: YYYY-MM-DD, for eg. 2015-05-10");
					 rd.forward(request, response);
				 } catch (DateOutOfRangeException dobe) {
					 request.setAttribute (INVALID_INPUT, dobe.getMessage());
					 rd.forward(request, response); 
				 } catch (Exception exp) {
					request.setAttribute (INVALID_INPUT, "Valid date format should be: YYYY-MM-DD, for eg. 2015-05-10");
					rd.forward(request, response); 
				 }
			 } else {
//				 null input exception
				 request.setAttribute(NULL_INPUT, "input fields cannot be empty!");
				 rd.forward(request,  response);
			 }
			 
/* Round Trip Case */
			 roundTicket = getRoundTrip(validDepartureAirport, validArrivalAirport, validDepartureDate, validReturnDate);
//			 boundary cases: departing date and return date are on the same day, union of two single trip has to
//			 add some time constraints to validate the layover time 
			 if (validDepartureDate.equals(validReturnDate)) {
				 List<List<Ticket>> invalidList = new ArrayList<List<Ticket>>();
				 for (List<Ticket> ticketList: roundTicket) {
					 if (ticketList.get(1).getTakeOffTime().getHour() -
					 ticketList.get(0).getLandingTime().getHour() < 8
					 || ticketList.get(0).getLandingTime().getDate().getDay() != 
					 	 ticketList.get(1).getTakeOffTime().getDate().getDay()) {
						 invalidList.add(ticketList);
					 }
				 }
				 roundTicket.removeAll(invalidList);
			 }
/* store all Round trip tickets into session */
			 request.getSession().setAttribute("roundTickets", roundTicket);
			 
			 List<List<Ticket>> reqTickListList = new ArrayList<List<Ticket>>();
			 if (request.getParameter("seat").equals("firstclass")) {
				// client's request seat is firstclass
				for (List<Ticket> tickList: roundTicket) {
					if (tickList.get(0).getSeatType().equals("FIRSTCLASS")
							&& tickList.get(1).getSeatType().equals("FIRSTCLASS")) {
						reqTickListList.add(tickList);
					}
				}
			 } else if (request.getParameter("seat").equals("coach")) {
				// client request seat is coach
				for (List<Ticket> tickList: roundTicket) {
					if (tickList.get(0).getSeatType().equals("COACH")
							&& tickList.get(1).getSeatType().equals("COACH")) {				
						reqTickListList.add(tickList);
					}
				}
			 } else {
				// client request seat is mixed
				for (List<Ticket> tickList: roundTicket) {
					if (!tickList.get(0).getSeatType().equals(tickList.get(1).getSeatType()))
						reqTickListList.add(tickList);
				}
			 }
/* put the requested round trip tickets into request */
			 request.setAttribute("roundTicket", reqTickListList);
			 // store the completed result in session
			 rd = request.getRequestDispatcher("round_flight.jsp");

		 } else {
/* Single Trip Case */
			 singleTicket = getSingleTrip(validDepartureAirport, validArrivalAirport, validDepartureDate);
/* store all single tickets result into session */			 
			 request.getSession().setAttribute("ticketCollection", singleTicket);// store the completed result in session
			 
			 TicketIterator tickItr = singleTicket.iterator(TicketTypeEnum.ALL);
				
			 if (request.getParameter("seat").equals("firstclass")) {
		    	tickItr = singleTicket.iterator(TicketTypeEnum.FIRSTCLASS);
			 } else if (request.getParameter("seat").equals("mixed")) {
				tickItr = singleTicket.iterator(TicketTypeEnum.MIXED);
			 } else {
				tickItr = singleTicket.iterator(TicketTypeEnum.COACH);
			 }
/* put the requested single trip ticket into request */			 
			 request.setAttribute("ticketIterator", tickItr);
			 rd = request.getRequestDispatcher("single_flight.jsp");
		 }
		 
		 rd.forward(request,response);
		
	}

	protected void dePost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	
	/**
	 * Retrieve all the flight-Legs union for single trip from dCity to 
	 * aCity on a specific dDate
	 * 
	 * @param dCity 	 
	 * 			The departing airport city chosen by user 
	 * @param aCity
	 * 			The arriving airport city chosen by user
	 * @param dDate 
	 * 			The departing date chosen by user
	 * @return Collection of [ 
	 * 							flight1: [flight-leg11, flight-leg12, flight-leg13]
	 * 						 	flight2: [flight-leg21, flight-leg22]
	 * 								......
	 * 							flightn: [flight-legn1, flight-legn2, flight-legn3]
	 * 						  ] 
	 */
	public TicketCollection getSingleTrip(String dCity, String aCity,
			String dDate) {
		//System.out.println(getAllFlightsForSingleTrip(dCity, aCity, dDate));
		return getTicketsCollectionForSingleTrip(dCity, aCity, dDate);
	}

	
	public List<List<Ticket>> getRoundTrip(String dCity, String aCity,
			String dDate, String returnDate) {
		/* Format of [ Collection1: [departingTrip collections], 
							  Collection2: [returningTrip collections] 
						    ]
		*/
		TicketCollection departingTrip = getSingleTrip(dCity, aCity, dDate);
		TicketCollection returningTrip = getSingleTrip(aCity, dCity, returnDate);
		List<List<Ticket>> totalTrip = new ArrayList<List<Ticket>>();
		
		Ticket ticket = null;
		for (TicketIterator tickItr = departingTrip.iterator(TicketTypeEnum.NONESTOP); 
					tickItr.hasNext();) {
			ticket = tickItr.next();
			for (TicketIterator tickItr2 = returningTrip.iterator(TicketTypeEnum.NONESTOP); 
					tickItr2.hasNext();) {
				List<Ticket> ticketList = new ArrayList<Ticket>();	
				ticketList.add(ticket);
				ticketList.add(tickItr2.next());
				totalTrip.add(ticketList);
			}
		}
		
		for (TicketIterator tickItr = departingTrip.iterator(TicketTypeEnum.NONESTOP);
				tickItr.hasNext();) {
			ticket = tickItr.next();
			for (TicketIterator tickItr2 = returningTrip.iterator(TicketTypeEnum.ONESTOP);
					tickItr2.hasNext();) {
				List<Ticket> ticketList = new ArrayList<Ticket>();	
				ticketList.add(ticket);
				ticketList.add(tickItr2.next());
				totalTrip.add(ticketList);
			} 
		}
		
		for (TicketIterator tickItr = departingTrip.iterator(TicketTypeEnum.ONESTOP); 
				tickItr.hasNext();) {
			ticket = tickItr.next();
			for (TicketIterator tickItr2 = returningTrip.iterator(TicketTypeEnum.NONESTOP); 
					tickItr2.hasNext();) {
				List<Ticket> ticketList = new ArrayList<Ticket>();	
				ticketList.add(ticket);
				ticketList.add(tickItr2.next());
				totalTrip.add(ticketList);
			}
		}
		
		for (TicketIterator tickItr = departingTrip.iterator(TicketTypeEnum.ONESTOP); 
				tickItr.hasNext();) {
			ticket = tickItr.next();
			for (TicketIterator tickItr2 = returningTrip.iterator(TicketTypeEnum.ONESTOP); 
					tickItr2.hasNext();) {
				List<Ticket> ticketList = new ArrayList<Ticket>();	
				ticketList.add(ticket);
				ticketList.add(tickItr2.next());
				totalTrip.add(ticketList);
			}
		}
		return totalTrip;
	}

	
	/**
	 * retrieve all the possible flights for one-single trip divided in to three
	 * different types:
	 * 
	 * @param dCity
	 *            format: "Boston"
	 * @param aCity
	 *            format: "NewYork"
	 * @param dDate
	 *            format: "2015-05-15" noneStopList, oneStopList, multiStopList
	 */
	private TicketCollection getTicketsCollectionForSingleTrip(String dCityCode,
			String aCityCode, String dDate) {

		List<List<Flight>> flightListList = new ArrayList<List<Flight>>();
		TicketCollection tickCollection = new TicketCollectionImpl();
		List<Flight> flightList = new ArrayList<Flight>();

		// create a handler for DFS recursion
		getAllFlightsHandler(dCityCode, aCityCode, dDate, flightList, flightListList, false);
		
		for (List<Flight> flightList2: flightListList) {
			Ticket ticket = new Ticket();
//			set Departing FlightList
			ticket.setFlightList(flightList2);
			
//			set take-off time 
			ticket.setTakeOffTime(flightList2.get(0).getDepartureTime());
//			set landing time 
			ticket.setLandingTime(flightList2.get(flightList2.size()-1).getArrivalTime());
//			set departure airport 
			ticket.setDepartureCity(flightList2.get(0).getDepartureAirport().getCode());
//			set arrival airport
			ticket.setArrivalCity(flightList2.get(flightList2.size()-1).getArrivalAirport().getCode());
			
//			set Duration of the trip
			int aDay = flightList2.get(flightList2.size()-1).getArrivalTime().getDate().getDay();
			int aHour = flightList2.get(flightList2.size()-1).getArrivalTime().getHour();
			int aMinute = flightList2.get(flightList2.size()-1).getArrivalTime().getMinute();
			
			int dDay = flightList2.get(0).getDepartureTime().getDate().getDay();
			int dHour = flightList2.get(0).getDepartureTime().getHour();
			int dMinute = flightList2.get(0).getDepartureTime().getMinute();
			int duration = (dDay == aDay? 0 : 24*60) + 60*(aHour-dHour) + aMinute - dMinute;
			ticket.setDuration(new Time(duration/60, duration%60)); // format of  12h, 05m
			
//			set stop type 
			if (flightList2.size() == 1) {
				ticket.setStopType("none stop");
			} else if (flightList2.size() == 2) {
				ticket.setStopType("one stop");
			} else {
				ticket.setStopType("multi stops");
			}
			
			double sumPrice = 0;
			int flightTime = 0;
			String firstSeat = flightList2.get(0).getSeatType();
			boolean isMixed = false;
			for (Flight flight: flightList2) {
				sumPrice += flight.getPrice();
				
				flightTime += flight.getDuration();
				if (!flight.getSeatType().equals(firstSeat)) {
					ticket.setSeatType("MIXED");
					isMixed = true;
				} 
			}
//			 set ticket total price
			ticket.setTotalPrice(sumPrice);
			// CONDT: seat is not mixed seat
			if (!isMixed) {
				if (firstSeat.equals("FirstClass")) {
					ticket.setSeatType("FIRSTCLASS");
				} else {
					ticket.setSeatType("COACH");
				}
			}
			
//			set layover && totalLeg time of the ticket
			int layover = duration - flightTime;
			int totalLeg = flightTime;
			ticket.setLayover(new Time(layover/60, layover%60));
			ticket.setTotalLeg(new Time(totalLeg/60, totalLeg%60));
//			System.out.println(ticket);
			tickCollection.add(ticket);
		}
		return tickCollection;
	}

	/**
	 * Flight union according to space && time constraints by DFS
	 * @param dCityCode 	departure airport code
	 * @param aCityCode		arrival airport code
	 * @param dDate  		departure date
	 * @param flightList	temporary flight list to hold flight legs
	 * @param flightListList  list of working flight legs, from departure -> arrival
	 * @param isOvernight	flag indicated that the flights is overnight or not
	 */
	@SuppressWarnings("unchecked")
	private void getAllFlightsHandler(String dCityCode, String aCityCode,
					String dDate, List<Flight> flightList, 
			List<List<Flight>> flightListList, boolean isOvernight) {
		List<Flight> dFlightList;
		if (flightList.size() == 0) {
//			initialization: get all flights from the departing airport specified by the user
			dFlightList = new FlightDaoImpl().selectFlights(dCityCode, dDate, true);
		
		} else {
//			get all flights satisfy the layover condition (min: 1hour, max: 8hours )
			dFlightList = getDepartingFlightsAfter(
					dCityCode, dDate, flightList.get(flightList.size()-1).getArrivalTime());
		}

//		 create a temporary list for the iteration of each level of tree
		List<Flight> unchangedList = flightList;
		
//		starting and destination longitude and longitude 
		AirportAdapter adapter = new AirportAdapter();
		double dLongt = adapter.getAirport(dCityCode).getLongitude();
		double dLat = adapter.getAirport(dCityCode).getLatitude();
		double aLongt = adapter.getAirport(aCityCode).getLongitude();
		double aLat = adapter.getAirport(aCityCode).getLatitude(); 
		
		for (Flight flight : dFlightList) {
//			departure && arrival airport longitude and latitude 
			double arrivalLongt = flight.getArrivalAirport().getLongitude();
			double arrivalLat = flight.getArrivalAirport().getLatitude();
			double departLongt = flight.getDepartureAirport().getLongitude();
			double departLat = flight.getDepartureAirport().getLatitude();
			
//			ending condition:  arrived at the arrival airport 
			if (flight.getArrivalAirport().getCode().equals(aCityCode)) {
				flightList = (List<Flight>) ((ArrayList<Flight>) unchangedList).clone();
				flightList.add(flight);
//				add this ticket union  to the tickets collection
				flightListList.add(flightList);
			} else if (Math.abs(dLongt -aLongt) > Math.abs(dLat - aLat)) {
				/* | diff(long) | > | diff(lat) | */
				/* long becomes closer, and lat fluctuates less than 20 */
				if (Math.abs(arrivalLongt - aLongt) < Math.abs(dLongt - aLongt)
						&& Math.abs(arrivalLongt - dLongt) < Math.abs(dLongt - aLongt)
							&& Math.abs(departLongt - dLongt) < Math.abs(arrivalLongt - dLongt)
								&& Math.max(Math.abs(dLat - arrivalLat), Math.abs(Math.abs(aLat - arrivalLat))) < 10) {
					flightList = (List<Flight>) ((ArrayList<Flight>) unchangedList).clone();
					flightList.add(flight); //add to flightlist
//					 whether over night ?
//					if same day: only requirement is - no more than two stops = flightList.size() <= 3
					if (flight.getArrivalTime().getDate().getDay() == 
							flightList.get(0).getDepartureTime().getDate().getDay()) {
						if (flightList.size() < 3) {
							getAllFlightsHandler(flight.getArrivalAirport()
									.getCode(), aCityCode, dDate, flightList, 
									flightListList, isOvernight); 
						}
					} else if (flight.getArrivalTime().getDate().getDay() -
								flightList.get(0).getDepartureTime().getDate().getDay() == 1 && flightList.size() < 3) {
						if (flight.getArrivalTime().getHour() < flightList.get(0).getDepartureTime().getHour()) {
							if (!isOvernight) {
//								 first time overnight, update date and overnight status for next round
								getAllFlightsHandler(flight.getArrivalAirport()
										.getCode(), aCityCode, new TimeAdapter().addOneDay(dDate), flightList, 
										flightListList, !isOvernight);
							} else {
//								 already overnight, remain the same status for next round
								getAllFlightsHandler(flight.getArrivalAirport()
										.getCode(), aCityCode, dDate, flightList, 
										flightListList, isOvernight);
							}
						}
					}
				}
			} else {
				// |latitude| > |longitude|
				if (Math.abs(arrivalLat - aLat) < Math.abs(dLat - aLat)
						&& Math.abs(arrivalLat - dLat) < Math.abs(dLat - aLat)
							&& Math.abs(departLat - dLat) < Math.abs(arrivalLat - dLat)
								&& Math.max(Math.abs(dLongt - arrivalLongt), Math.abs(Math.abs(aLongt - arrivalLongt))) < 10) {
					
					flightList = (List<Flight>) ((ArrayList<Flight>) unchangedList).clone();
					flightList.add(flight);

					if (flight.getArrivalTime().getDate().getDay() == 
							flightList.get(0).getDepartureTime().getDate().getDay()) {
						if (flightList.size() < 3) {
							getAllFlightsHandler(flight.getArrivalAirport()
									.getCode(), aCityCode, dDate, flightList, 
									flightListList, isOvernight); // union
						}
					} else if (flight.getArrivalTime().getDate().getDay() - 
							flightList.get(0).getDepartureTime().getDate().getDay() == 1 && flightList.size() < 3) {
						if (flight.getArrivalTime().getHour() < flightList.get(0).getDepartureTime().getHour()) {
							if (!isOvernight) {
								// first time overnight, update date and overnight status for next round
								getAllFlightsHandler(flight.getArrivalAirport()
										.getCode(), aCityCode, new TimeAdapter().addOneDay(dDate), flightList, 
										flightListList, !isOvernight);
							} else {
								// already overnight, remain the same status for next round
								getAllFlightsHandler(flight.getArrivalAirport()
										.getCode(), aCityCode, dDate, flightList, 
										flightListList, isOvernight);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * layover time should be within the range of
	 * 1 ~ 8 hours
	 */
	private List<Flight> getDepartingFlightsAfter(String dCityCode, String dDate, Time aTime) { 
		
    	List<Flight> resultList = new ArrayList<Flight>();
    	if (aTime.getHour() < 16) {
    		// Departing flights on the same day
    		for (Flight flight:  new FlightDaoImpl().selectFlights(dCityCode, dDate, true)) {
    		// condt aTime.hour < 16
    			if (flight.getDepartureTime().getHour() 
    					- aTime.getHour() > 1 &&
    					flight.getDepartureTime().getHour()
    					- aTime.getHour() < 8) {
    				resultList.add(flight);
    			}
    		}
    		
    	} else {
    		// Departing flights on the same day
    		for (Flight flight:  new FlightDaoImpl().selectFlights(dCityCode, dDate, true)) {
        		// condt aTime.hour > 16
        			if (flight.getDepartureTime().getHour() 
        					- aTime.getHour() >= 1) {
        				resultList.add(flight);
        			}
        	}
    		// Departing flights on the next day
    		for (Flight flight: new FlightDaoImpl().selectFlights(dCityCode, 
    							new TimeAdapter().addOneDay(dDate), true)) {
    		
    			if (flight.getDepartureTime().getHour() + 24
    				- aTime.getHour() < 8) {
    					resultList.add(flight);
    			}
    		}
    	}
    	return resultList;
	}
}


