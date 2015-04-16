<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
	import= "java.util.*, java.text.DecimalFormat,
			com.flight_ticket_search.Entity.*, 
			com.flight_ticket_search.Util.*, 
			com.flight_ticket_search.Iterator.*
			 "    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Round Trip Page</title>

  <link href= "display_flight.css" rel= "stylesheet">
  <link rel="shortcut icon" href="http://designm.ag/favicon.ico">
  <link rel="icon" href="http://designm.ag/favicon.ico">
  <link rel="stylesheet" type="text/css" media="all" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/base/jquery-ui.css">
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
</head>

<jsp:useBean id="timeAdapter" class="com.flight_ticket_search.Util.TimeAdapter" scope="session"/>   
<jsp:useBean id="roundSorter" class="com.flight_ticket_search.Service.SortRoundFlightServiceImpl" scope="session"/>  


<body>
<div id= "main">
	
	<form action= "sortRoundServlet" method= "get">
	
		<div id= "left">
			<p>Ticket Filter</p>
			
			<div class= "filter_window">
				<h3>Stops</h3>
				<div id= "stop" class= "filter_content">
					<input type= "checkbox" name= "nonestop" checked= "checked">none stop<br>
					<input type= "checkbox" name= "onestop" checked= "checked">one stop<br>
					<br>
				</div>
			</div>
		
<%
	Map<Integer, List<List<String>>> ticketMap = new HashMap<Integer, List<List<String>>>();
	List<List<Ticket>> reqTickListList = new ArrayList<List<Ticket>>();
	
	if (request.getAttribute("sortedListList") != null) {
		
		// from sortServlet  || from reservation servlet: lock failure case
		List<List<Ticket>> tickListList = (List<List<Ticket>>) request.getAttribute("sortedListList");
		if (request.getAttribute("lockFailure") != null) {
%>
<script>
		alert("Sorry, system is queuing up, would you please wait for 2 minutes ......");
</script>
<% 			
		}
		
		// extra condition: when the ticket is sold out 
		if (request.getAttribute("ticketSoldout") != null) {
%>
<script>
		alert("Sorry, your ticket was already sold out. Would you please change one ?");
</script>		
<%		} 
		
		session.setAttribute("filteredListList", tickListList);
		reqTickListList = tickListList;
		
	} else {
		// from requestFlightServlet
		List<List<Ticket>> tickListList = (List<List<Ticket>>) session.getAttribute("roundTickets");
		session.setAttribute("filteredListList", tickListList);
		reqTickListList = (List<List<Ticket>>) request.getAttribute("roundTicket");	
%>
<script>
		alert("Welcome to the second page  -  selecting flight tickets !");
</script>
<%		
	}
		
		List<List<Ticket>> reqTickListList2 = new ArrayList<List<Ticket>>();
		for (List<Ticket> tickList: reqTickListList) {
			reqTickListList2.add(tickList);
		}
		Time departureTakeOffLow = null, departureLandingLow = null, departureTakeOffHigh = null, departureLandingHigh = null;
		Time returnTakeOffLow = null, returnLandingLow = null, returnTakeOffHigh = null, returnLandingHigh = null;
		Time minLayover = null, maxLayover = null, minLeg = null, maxLeg = null;
		int departureTakeOffOffset = 0, departureLandingOffset = 0;
		if (reqTickListList2.size() > 0) {
			// sort by departure take-off time
			roundSorter.sortByDepartureTakeOff(reqTickListList2);
			departureTakeOffLow = reqTickListList2.get(0).get(0).getTakeOffTime();
			departureTakeOffHigh = reqTickListList2.get(reqTickListList.size()-1).get(0).getTakeOffTime();
			departureTakeOffOffset = reqTickListList2.get(0).get(0).getFlightList().
					get(0).getDepartureAirport().getOffset();
			// sort by departure landing time
			roundSorter.sortByDepartureLanding(reqTickListList2);
		    departureLandingLow = reqTickListList2.get(0).get(0).getLandingTime();
			departureLandingHigh = reqTickListList2.get(reqTickListList.size()-1).get(0).getLandingTime();
			departureLandingOffset = reqTickListList2.get(0).get(0).getFlightList().
					get(reqTickListList2.get(0).get(0).getFlightList().size()-1).getArrivalAirport().getOffset();
			
			// sort by return take-off time
			roundSorter.sortByReturnTakeOff(reqTickListList2);
			returnTakeOffLow = reqTickListList2.get(0).get(1).getTakeOffTime();
			returnTakeOffHigh = reqTickListList2.get(reqTickListList.size()-1).get(1).getTakeOffTime();
			// sort by return landing time
			roundSorter.sortByReturnLanding(reqTickListList2);
		    returnLandingLow = reqTickListList2.get(0).get(1).getLandingTime();
			returnLandingHigh = reqTickListList2.get(reqTickListList.size()-1).get(1).getLandingTime();
			
		}
	
%>
			<div class= "filter_window">
				<h3>Times</h3>
				<div class= "filter_content">
					<div id= "takeoff_filter">
						<p>Departure Take-off time</p>
						<p>
						<%= (reqTickListList.size() > 0)? 
								("May," + timeAdapter.getLocalTime(departureTakeOffLow, departureTakeOffOffset).getDate().getDay() + " " +
										timeAdapter.getLocalTime(departureTakeOffLow, departureTakeOffOffset).getHour() 
										+ "h ~ " +
								 "May," + timeAdapter.getLocalTime(departureTakeOffHigh, departureTakeOffOffset).getDate().getDay() + " " +
									timeAdapter.getLocalTime(departureTakeOffHigh, departureTakeOffOffset).getHour() + "h") 
									: 	"No matching result"
						
						%>
						</p>
						<span id="depart_take_off_rangeval" class= "slider_range">range: 0% - 100%</span>
						<div id="depart_take_off_slider" class= "slider"></div>
						<input type= "hidden" id= "depart_take_off_min" name="departTakeOffMin" value= "0">
						<input type= "hidden" id= "depart_take_off_max" name= "departTakeOffMax" value= "100">
	
						<p>Return Take-off time</p>
						<p>
						<%= (reqTickListList.size() > 0)? 
								("May," + timeAdapter.getLocalTime(returnTakeOffLow, departureLandingOffset).getDate().getDay() + " " +
										timeAdapter.getLocalTime(returnTakeOffLow, departureLandingOffset).getHour() 
										+ "h ~ " +
								 "May," + timeAdapter.getLocalTime(returnTakeOffHigh, departureLandingOffset).getDate().getDay() + " " +
									timeAdapter.getLocalTime(returnTakeOffHigh, departureLandingOffset).getHour() + "h") 
									: 	"No matching result"
						
						%>
						</p>
						<span id="return_take_off_rangeval" class= "slider_range">range: 0% - 100%</span>
						<div id="return_take_off_slider" class= "slider"></div>
						<input type= "hidden" id= "return_take_off_min" name="returnTakeOffMin" value= "0">
						<input type= "hidden" id= "return_take_off_max" name= "returnTakeOffMax" value= "100">
					</div>
					
					<p id= "landing_hinter" style= "padding: 20px">Landing Below</p>
					
					<div id= "landing_filter" style= "display: none">
						<p>Departure Landing time</p>
						<p>
						<%= (reqTickListList.size() > 0)? 
								("May," + timeAdapter.getLocalTime(departureLandingLow, departureLandingOffset).getDate().getDay() + " " +
										timeAdapter.getLocalTime(departureLandingLow, departureLandingOffset).getHour() 
										+ "h ~ " +
								 "May," + timeAdapter.getLocalTime(departureLandingHigh, departureLandingOffset).getDate().getDay() + " " +
									timeAdapter.getLocalTime(departureLandingHigh, departureLandingOffset).getHour() + "h") 
									: 	"No matching result"
						
						%>
						</p>
						<span id="depart_landing_rangeval" class= "slider_range">range: 0% - 100%</span>
						<div id="depart_landing_slider" class= "slider"></div>
						<input type= "hidden" id= "depart_landing_min" name="departLandingMin" value= "0">
						<input type= "hidden" id= "depart_landing_max" name= "departLandingMax" value= "100">
						
						<p>Return Landing time</p>
						<p>
						<%= (reqTickListList.size() > 0)? 
								("May," + timeAdapter.getLocalTime(returnLandingLow, departureTakeOffOffset).getDate().getDay() + " " +
										timeAdapter.getLocalTime(returnLandingLow, departureTakeOffOffset).getHour() 
										+ "h ~ " +
								 "May," + timeAdapter.getLocalTime(returnLandingHigh, departureTakeOffOffset).getDate().getDay() + " " +
									timeAdapter.getLocalTime(returnLandingHigh, departureTakeOffOffset).getHour() + "h") 
									: 	"No matching result"
						
						%>
						</p>
						<span id="return_landing_rangeval" class= "slider_range">range: 0% - 100%</span>
						<div id="return_landing_slider" class= "slider"></div>
						<input type= "hidden" id= "return_landing_min" name="returnLandingMin" value= "0">
						<input type= "hidden" id= "return_landing_max" name= "returnLandingMax" value= "100">				</div>
				</div>
			</div>
		
		
			<div id= "seat" class= "filter_window">
				<h3>Cabin</h3>
				<div class= "filter_content">
					<input type= "checkbox" name= "coach" checked= "checked">Coach<br>
					<input type= "checkbox" name= "firstclass" checked= "checked">First Class<br>
					<input type= "checkbox" name= "mixed" checked= "checked">Mixed<br>
					<br>
				</div>
			
			<!-- Filter button to invoke filtering service -->
			<input type= "submit" style= "margin: 100px 50% 10px; width: 45%; height: 50px" 
					name= "filter_button" value= "start filtering">
			
			</div>
		</div>
		
	</form>
	
	<div id= "right">
		<form method= "get" action= "sortRoundServlet" style= "margin-left: 22%">
			<select name= "sort_type" style= "margin-left: 10px">
				  <option value="price">sort by price</option>
				  <option value="duration">sort by duration</option>
				  <option value="depart_takeoff">sort by departure take-off time</option>
				  <option value="return_takeoff">sort by return take-off time</option>
				  <option value="depart_landing">sort by departure landing time</option>
				  <option value="return_landing">sort by return landing time</option>
				  <option value="layover">sort by layover time</option>
			</select>
			<input type= "submit" name= "sort_button" value= "start sorting">   
		</form>
	
<%
	if (reqTickListList.size() == 0) {
%>
		<p class= "no_match_hint">
			: ( <i>Sorry, no matching result has been found, change filtering ?</i> 
		</p> 	
<%			
	}	

	int ticketId = 0;
	List<Ticket> tickList = null;
	for (int i = 0 ; i < reqTickListList.size(); i++) {

		tickList = reqTickListList.get(i);
	
		int takeOffOffset = tickList.get(0).getFlightList().get(0).getDepartureAirport().getOffset();
		int landingOffset = tickList.get(0).getFlightList().
							get(tickList.get(0).getFlightList().size()-1).getArrivalAirport().getOffset();
	
		// compute the duration in metric of minutes
		List<List<String>> ticketInfo = null;
		if (ticketId / 20 < 1) { 
%>
	<div id= "page_seperator<%= ticketId/20 %>">
			
<% 			while (true) { 
%>
				
		<div class= "ticket_info" id= "ticket_info<%= ticketId%>">
			<div style= "background-color: white; float: left; width: 20%; height: 100%">
				<p class= "departing_left" style= "text-align: center; margin-top: 35px">
					Price:<%= " " + ((tickList.get(0).getTotalPrice() + tickList.get(1).getTotalPrice() > 1000)?
							new DecimalFormat("$#,###.##").format(tickList.get(0).getTotalPrice() + tickList.get(1).getTotalPrice()):
						new DecimalFormat("$###.##").format(tickList.get(0).getTotalPrice() + tickList.get(1).getTotalPrice()))%>
				</p>
				<p style= "text-align: center; margin-top: 5px">
					Duration: <%=  " " +
					((tickList.get(0).getDuration().getMinute() + tickList.get(1).getDuration().getMinute() >= 60)?
						(tickList.get(0).getDuration().getHour()+ tickList.get(1).getDuration().getHour() + 1 + "h, " +
							(tickList.get(0).getDuration().getMinute() + tickList.get(1).getDuration().getMinute() - 60) + "m") 
							: 
						(tickList.get(0).getDuration().getHour()+ tickList.get(1).getDuration().getHour() + "h, " +
							 (tickList.get(0).getDuration().getMinute() + tickList.get(1).getDuration().getMinute()) + "m"))
					%>
				</p>
				<!--  Purchase ticket selection here -->
				<form action= "buyServlet" method= "get">
					<input type= "hidden" name= "ticketId" value= <%=ticketId %>>
					<input type= "submit" name= "selectRoundTicket" 
					value= "Select" style= "width: 60%; margin: 40px 30px">
				</form>
				
			</div>
			<div  class= "departing">
			<%= "Layover: " 
				+ (tickList.get(0).getLayover().getMinute() + tickList.get(1).getLayover().getMinute() > 60 ? 
						((tickList.get(0).getLayover().getHour() + tickList.get(1).getLayover().getHour() + 1) + "h, " +
						+ (tickList.get(0).getLayover().getMinute() + tickList.get(1).getLayover().getMinute() - 60) + "m")
					 : (tickList.get(0).getLayover().getHour() + tickList.get(1).getLayover().getHour() + "h, " +
							+ (tickList.get(0).getLayover().getMinute() + tickList.get(1).getLayover().getMinute()) + "m")) 
			%>
				<table style= "margin-left: 30%">
					<colgroup span= "1">
					</colgroup>
					<tr>
						<td>
						<%= "May," + timeAdapter.getLocalTime(tickList.get(0).getTakeOffTime(), takeOffOffset).getDate().getDay() + " " +
						timeAdapter.getLocalTime(tickList.get(0).getTakeOffTime(), takeOffOffset).getHour()  
								+ ":" + 
							(timeAdapter.getLocalTime(tickList.get(0).getTakeOffTime(), takeOffOffset).getMinute()<10? "0":"") 
								+ timeAdapter.getLocalTime(tickList.get(0).getTakeOffTime(), takeOffOffset).getMinute()
						%>
						</td>
						<td><%= tickList.get(0).getDepartureCity() %></td>
						<td>-></td>
						<td>
						<%= "May," + timeAdapter.getLocalTime(tickList.get(0).getLandingTime(), landingOffset).getDate().getDay() + " " +
						timeAdapter.getLocalTime(tickList.get(0).getLandingTime(), landingOffset).getHour()  
								+ ":" + 
							(timeAdapter.getLocalTime(tickList.get(0).getLandingTime(), landingOffset).getMinute()<10? "0":"") 
								+ timeAdapter.getLocalTime(tickList.get(0).getLandingTime(), landingOffset).getMinute()
						%>
						</td>
						<td><%= tickList.get(0).getArrivalCity() %></td>
						<td><%= 
						tickList.get(0).getDuration().getHour() + "h" + " " + tickList.get(0).getDuration().getMinute() +"min"
						%>
						</td>
						<td><%= tickList.get(0).getSeatType() %></td>
					</tr>
					
					<tr>
						<td>
						<%= "May," + timeAdapter.getLocalTime(tickList.get(1).getTakeOffTime(), landingOffset).getDate().getDay() + " " +
						timeAdapter.getLocalTime(tickList.get(1).getTakeOffTime(), landingOffset).getHour()  
								+ ":" + 
							(timeAdapter.getLocalTime(tickList.get(1).getTakeOffTime(), landingOffset).getMinute()<10? "0":"") 
								+ timeAdapter.getLocalTime(tickList.get(1).getTakeOffTime(), landingOffset).getMinute()
						%>
						</td>
						<td><%= tickList.get(1).getDepartureCity() %></td>
						<td>-></td>
						<td>
						<%= "May," + timeAdapter.getLocalTime(tickList.get(1).getLandingTime(), takeOffOffset).getDate().getDay() + " " + 
						timeAdapter.getLocalTime(tickList.get(1).getLandingTime(), takeOffOffset).getHour()  
								+ ":" + 
							(timeAdapter.getLocalTime(tickList.get(1).getLandingTime(), takeOffOffset).getMinute()<10? "0":"") 
								+ timeAdapter.getLocalTime(tickList.get(1).getLandingTime(), takeOffOffset).getMinute()
						%>
						</td>
						<td><%= tickList.get(1).getArrivalCity() %></td>
						<td><%= tickList.get(1).getDuration().getHour() + "h" + " " + tickList.get(1).getDuration().getMinute() +"min" %></td>
						<td><%= tickList.get(1).getSeatType() %></td>
					</tr>
				</table>
			</div>
			
			
			<ul>
				<li style= "display: inline; padding: 8px"><%= tickList.get(0).getSeatType() %></li> 
			    <li style= "display: inline"><%= tickList.get(1).getSeatType() %></li>
			</ul>
			<p class= "detail_hinter" id= "detail_hinter<%= ticketId%>">Show details</p>
			
		</div>
		
		<div hidden class= "detail_flight_info" id= "detail_flight_info<%= ticketId %>">
			<p style= "padding: 10px; font-size: 1.2em">Depart</p>
			<div style= "margin: 20px 15px">
				<table>

<% 
				ticketInfo = new ArrayList<List<String>>();
				for (Flight flight: tickList.get(0).getFlightList()) {
					int d_offset = flight.getDepartureAirport().getOffset();
				 	int a_offset = flight.getArrivalAirport().getOffset();
					
					List<String> leg = new ArrayList<String>();
					leg.add(flight.getNumber());
					leg.add(flight.getSeatType());
					ticketInfo.add(leg);
					ticketMap.put(ticketId, ticketInfo);
					
%>

					<tr>
						<td><%= flight.getAirplane().getModel() %></td>
						<td><%= flight.getAirplane().getManufacturer() %></td>
						<td>
						<%= "May," + timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getDate().getDay() + " " +
						timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getHour()  
								+ ":" + 
							(timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getMinute()<10? "0":"") 
								+ timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getMinute() %>
						</td>
						<td><%=  flight.getDepartureAirport().getCode()%></td>
						<td>
						->
						</td>
						<td>
						<%= "May," + timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getDate().getDay() + " " +
						timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getHour()  
								+ ":" + 
							(timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getMinute()<10? "0":"") 
								+ timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getMinute() %>
						</td>
						<td><%= flight.getArrivalAirport().getCode() %></td>
						<td><%= flight.getDuration() + "min" %></td>
						<td><%= flight.getSeatType() %></td>
						<td><%= "$" + flight.getPrice() %></td>
					</tr>
<% 				} 
%>
	
				</table>
			</div>
			
			<p style= "padding: 10px; font-size: 1.2em">Return</p>
			<div style= "margin: 20px 15px">
				<table>
				
<%
				for (Flight flight: tickList.get(1).getFlightList()) {
					int d_offset = flight.getDepartureAirport().getOffset();
				 	int a_offset = flight.getArrivalAirport().getOffset();
					
					List<String> leg = new ArrayList<String>();
					leg.add(flight.getNumber());
					leg.add(flight.getSeatType());
					ticketInfo.add(leg);
					ticketMap.put(ticketId, ticketInfo);
%>
					
					<tr>
						<td><%= flight.getAirplane().getModel() %></td>
						<td><%= flight.getAirplane().getManufacturer() %></td>
						<td><%= "May," + timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getDate().getDay() + " " +
						timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getHour()  
								+ ":" + 
							(timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getMinute()<10? "0":"") 
								+ timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getMinute() %></td>
						<td><%=  flight.getDepartureAirport().getCode()%></td>
						<td>-></td>
						<td><%= "May," + timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getDate().getDay() + " " + 
						timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getHour()  
								+ ":" + 
							(timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getMinute()<10? "0":"") 
								+ timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getMinute() %></td>
						<td><%= flight.getArrivalAirport().getCode() %></td>
						<td><%= flight.getDuration() + "min" %></td>
						<td><%= flight.getSeatType() %></td>
						<td><%= "$" + flight.getPrice() %></td>
					</tr>
<%				} 
%>
				</table>
			</div>
		</div>
	
<%  			if ((ticketId+1) % 20 == 0) {
%>
			<div class= "page_button">
				<button style= "float: left" class = "prev" onclick= "prev()">Previous</button>
  				<button class = "next" onclick= "next()">Next</button>
			</div>
			
<%					break;	
				}
						
				if (++i < reqTickListList.size()) {
					
				 	tickList = reqTickListList.get(i);
					ticketId++;
					
				} else {
%>
			<div class= "page_button">
				<button style= "float: left" class = "prev" onclick= "prev()">Previous</button>
				<button class = "next" onclick= "next()">Next</button>
			</div>
<%		
					break;
				}
			}	
%>	

	</div>
			
<%
		} else { 
%>
	
	
	<div id= "page_seperator<%= ticketId/20 %>" style= "display: none">
			
<% 			while (true) { 
%>
				
		<div class= "ticket_info" id= "ticket_info<%= ticketId %>">
			<div style= "background-color: white; float: left; width: 20%; height: 100%">
				<p class= "departing_left" style= "text-align: center; margin-top: 35px">
					Price:<%= " " + ((tickList.get(0).getTotalPrice() + tickList.get(1).getTotalPrice() > 1000)?
							new DecimalFormat("$#,###.##").format(tickList.get(0).getTotalPrice() + tickList.get(1).getTotalPrice()):
						new DecimalFormat("$###.##").format(tickList.get(0).getTotalPrice() + tickList.get(1).getTotalPrice()))%>
				</p>
				<p style= "text-align: center; margin-top: 5px">
					Duration: <%= " " + 
					((tickList.get(0).getDuration().getMinute() + tickList.get(1).getDuration().getMinute() >= 60)?
						(tickList.get(0).getDuration().getHour()+ tickList.get(1).getDuration().getHour() + 1 + "h, " +
							(tickList.get(0).getDuration().getMinute() + tickList.get(1).getDuration().getMinute() - 60) + "m") 
							: 
						(tickList.get(0).getDuration().getHour()+ tickList.get(1).getDuration().getHour() + "h, " +
							 (tickList.get(0).getDuration().getMinute() + tickList.get(1).getDuration().getMinute()) + "m"))
					%>
				</p>
				<!--  Purchase ticket selection here -->
				<form action= "buyServlet" method= "get">
					<input type= "hidden" name= "ticketId" value= <%=ticketId %>>
					<input type= "submit" name= "selectRoundTicket" 
					value= "Select" style= "width: 60%; margin: 40px 30px">
				</form>
				
			</div>
			<div  class= "departing">
			<%= "Layover: " 
			+ (tickList.get(0).getLayover().getMinute() + tickList.get(1).getLayover().getMinute() > 60 ? 
				((tickList.get(0).getLayover().getHour() + tickList.get(1).getLayover().getHour() + 1) + "h, " +
				+ (tickList.get(0).getLayover().getMinute() + tickList.get(1).getLayover().getMinute() - 60) + "m")
			 : (tickList.get(0).getLayover().getHour() + tickList.get(1).getLayover().getHour() + "h, " +
					+ (tickList.get(0).getLayover().getMinute() + tickList.get(1).getLayover().getMinute()) + "m")) 							
			%>
				<table style= "margin-left: 30%">
					<colgroup span= "1">
					</colgroup>
					<tr>
						<td>
						<%= "May," + timeAdapter.getLocalTime(tickList.get(0).getTakeOffTime(), takeOffOffset).getDate().getDay() + " " +
						timeAdapter.getLocalTime(tickList.get(0).getTakeOffTime(), takeOffOffset).getHour()  
								+ ":" + 
							(timeAdapter.getLocalTime(tickList.get(0).getTakeOffTime(), takeOffOffset).getMinute()<10? "0":"") 
								+ timeAdapter.getLocalTime(tickList.get(0).getTakeOffTime(), takeOffOffset).getMinute()
						%>
						</td>
						<td><%= tickList.get(0).getDepartureCity() %></td>
						<td>-></td>
						<td>
						<%= "May," + timeAdapter.getLocalTime(tickList.get(0).getLandingTime(), landingOffset).getDate().getDay() + " " +
						timeAdapter.getLocalTime(tickList.get(0).getLandingTime(), landingOffset).getHour()  
								+ ":" + 
							(timeAdapter.getLocalTime(tickList.get(0).getLandingTime(), landingOffset).getMinute()<10? "0":"") 
								+ timeAdapter.getLocalTime(tickList.get(0).getLandingTime(), landingOffset).getMinute()
						%>
						</td>
						<td><%= tickList.get(0).getArrivalCity() %></td>
						<td><%= 
						tickList.get(0).getDuration().getHour() + "h" + " " + tickList.get(0).getDuration().getMinute() +"min"
						%>
						</td>
						<td><%= tickList.get(0).getSeatType() %></td>
					</tr>
					
					<tr>
						<td>
						<%= "May," + timeAdapter.getLocalTime(tickList.get(1).getTakeOffTime(), landingOffset).getDate().getDay() + " " +
						timeAdapter.getLocalTime(tickList.get(1).getTakeOffTime(), landingOffset).getHour()  
								+ ":" + 
							(timeAdapter.getLocalTime(tickList.get(1).getTakeOffTime(), landingOffset).getMinute()<10? "0":"") 
								+ timeAdapter.getLocalTime(tickList.get(1).getTakeOffTime(), landingOffset).getMinute()
						%>
						</td>
						<td><%= tickList.get(1).getDepartureCity() %></td>
						<td>-></td>
						<td>
						<%= "May," + timeAdapter.getLocalTime(tickList.get(1).getLandingTime(), takeOffOffset).getDate().getDay() + " " + 
						timeAdapter.getLocalTime(tickList.get(1).getLandingTime(), takeOffOffset).getHour()  
								+ ":" + 
							(timeAdapter.getLocalTime(tickList.get(1).getLandingTime(), takeOffOffset).getMinute()<10? "0":"") 
								+ timeAdapter.getLocalTime(tickList.get(1).getLandingTime(), takeOffOffset).getMinute()
						%>
						</td>
						<td><%= tickList.get(1).getArrivalCity() %></td>
						<td><%= tickList.get(1).getDuration().getHour() + "h" + " " + tickList.get(1).getDuration().getMinute() +"min" %></td>
						<td><%= tickList.get(1).getSeatType() %></td>
					</tr>
				</table>
			</div>
			
			
			<ul>
				<li style= "display: inline; padding: 8px"><%= tickList.get(0).getSeatType() %></li> 
			    <li style= "display: inline"><%= tickList.get(1).getSeatType() %></li>
			</ul>
			<p class= "detail_hinter" id= "detail_hinter<%= ticketId%>">Show details</p>
			
		</div>
		<div hidden class= "detail_flight_info" id= "detail_flight_info<%= ticketId %>">
		<p style= "padding: 10px; font-size: 1.2em">Depart</p>
		<div style= "margin: 20px 15px">
			<table>
			
<% 
			ticketInfo = new ArrayList<List<String>>();
			for (Flight flight: tickList.get(0).getFlightList()) {
				int d_offset = flight.getDepartureAirport().getOffset();
			 	int a_offset = flight.getArrivalAirport().getOffset();
				
				List<String> leg = new ArrayList<String>();
				leg.add(flight.getNumber());
				leg.add(flight.getSeatType());
				ticketInfo.add(leg);
				ticketMap.put(ticketId, ticketInfo);
			
%>
				<tr>
					<td><%= flight.getAirplane().getModel() %></td>
					<td><%= flight.getAirplane().getManufacturer() %></td>
					<td>
					<%= "May," + timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getDate().getDay() + " " +
					timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getHour()  
							+ ":" + 
						(timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getMinute()<10? "0":"") 
							+ timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getMinute() %>
					</td>
					<td><%=  flight.getDepartureAirport().getCode()%></td>
					<td>
					->
					</td>
					<td>
					<%= "May," + timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getDate().getDay() + " " +
					timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getHour()  
							+ ":" + 
						(timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getMinute()<10? "0":"") 
							+ timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getMinute() %>
					</td>
					<td>
					<%= flight.getArrivalAirport().getCode()
					%>
					</td>
					<td><%= flight.getDuration() + "min" %></td>
					<td><%= flight.getSeatType() %></td>
					<td><%= "$" + flight.getPrice() %></td>
				</tr>
			<% } %>
	
			</table>
			
		</div>
		
		<p style= "padding: 10px; font-size: 1.2em">Return</p>
		<div style= "margin: 20px 15px">
			<table>

<%
			for (Flight flight: tickList.get(1).getFlightList()) {
				int d_offset = flight.getDepartureAirport().getOffset();
			 	int a_offset = flight.getArrivalAirport().getOffset();
				
				List<String> leg = new ArrayList<String>();
				leg.add(flight.getNumber());
				leg.add(flight.getSeatType());
				ticketInfo.add(leg);
				ticketMap.put(ticketId, ticketInfo);

%>
				<tr>
					<td><%= flight.getAirplane().getModel() %></td>
					<td><%= flight.getAirplane().getManufacturer() %></td>
					<td><%= "May," + timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getDate().getDay() + " " +
					timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getHour()  
							+ ":" + 
						(timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getMinute()<10? "0":"") 
							+ timeAdapter.getLocalTime(flight.getDepartureTime(), d_offset).getMinute() %></td>
					<td><%=  flight.getDepartureAirport().getCode()%></td>
					<td>-></td>
					<td><%= "May," + timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getDate().getDay() + " " + 
					timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getHour()  
							+ ":" + 
						(timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getMinute()<10? "0":"") 
							+ timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getMinute() %></td>
					<td><%= flight.getArrivalAirport().getCode()
					%></td>
					<td><%= flight.getDuration() + "min" %></td>
					<td><%= flight.getSeatType() %></td>
					<td><%= "$" + flight.getPrice() %></td>
				</tr>
			<% } %>
			</table>
		</div>
		</div>
	
		
<% 			// ticket map used to mapping the div id to CORRESPONDING ticketId
			  	if ((ticketId+1) % 20 == 0) {
%>
	<div class= "page_button">
		<button style= "float: left" class = "prev" onclick= "prev()">Previous</button>
			<button class = "next" onclick= "next()">Next</button>
	</div>
<%					break;	
				}
							
				if (++i < reqTickListList.size()) {
					
					tickList = reqTickListList.get(i);
				 	ticketId++;
				 	
				} else {
%>
	<div class= "page_button">
		<button style= "float: left" class = "prev" onclick= "prev()">Previous</button>
		<button class = "next" onclick= "next()">Next</button>
	</div>
<%		
					break;
				}
			}
%>		
		
	</div>
<%
		}
		
		session.setAttribute("ticketMap", ticketMap);
		ticketId++;

	}
%>
	</div>
	
</div>
</body>

<script>

var counter = 0;
function prev() {
	if (counter > 0) {
		counter--;
	}
	

	document.getElementById("page_seperator"+counter).style.display = "block";
	
	document.getElementById("page_seperator"+(counter+1)).style.display = "none";
}

function next() {
	if (counter < <%= ticketId/20 %>) {
		counter++;
	}
	
	document.getElementById("page_seperator"+counter).style.display = "block";

	document.getElementById("page_seperator"+(counter-1)).style.display = "none";
}

$(document).ready(function() {
<%  
	for (int i=0; i <= ticketId; i++) {
%>
	$("#detail_hinter<%= i%>").click(function() {
		$("#detail_flight_info<%= i%>").slideToggle(50);
	});
	
	$("#landing_hinter").hover(function() {
		$("#landing_filter").slideDown(500);
	});
	
	$("#ticket_info<%= i%>").hover(
	function() {
		$(this).css("border", "3px orange solid");
	},
	function() {
		$(this).css("border", "3px black dotted");
	});
<%
	}
%>
	
	
});

</script>
<script src= "display_flight.js">
</script>

</body>
</html>