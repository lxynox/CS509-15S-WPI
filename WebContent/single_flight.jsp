<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import= "java.io.*,java.util.*,
    java.math.*,java.text.DecimalFormat,
    com.flight_ticket_search.Entity.*,
    com.flight_ticket_search.Iterator.*"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title> Single Trip Page </title>
 <link href= "jquery-ui.min.js" rel= "stylesheet">
<link href="jquery-ui.css" rel="stylesheet">
  <link rel="shortcut icon" href="http://designm.ag/favicon.ico">
  <link rel="icon" href="http://designm.ag/favicon.ico">
  <link href= "display_flight.css" rel= "stylesheet">
  <link rel="stylesheet" type="text/css" media="all" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/base/jquery-ui.css">
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
  <style>
	body{
		font: 100% "Trebuchet MS", sans-serif;
		line-height: 120%;
	}
	.demoHeaders {
		margin-top: 2em;
	}
	#dialog-link {
		padding: .4em 1em .4em 20px;
		text-decoration: none;
		position: relative;
	}
	#dialog-link span.ui-icon {
		margin: 0 5px 0 0;
		position: absolute;
		left: .2em;
		top: 50%;
		margin-top: -8px;
	}
	#icons {
		margin: 0;
		padding: 0;
	}
	#icons li {
		margin: 2px;
		position: relative;
		padding: 4px 0;
		cursor: pointer;
		float: left;
		list-style: none;
	}
	#icons span.ui-icon {
		float: left;
		margin: 0 4px;
	}
	.fakewindowcontain .ui-widget-overlay {
		position: absolute;
	}
	select {
		width: 200px;
	}
	</style>
     <script type="text/javascript"> 
$(function() { 
$(window).scroll(function() { 
var top = $(window).scrollTop(); 
var left= $(window).scrollLeft(); 
$("#left").css({ left:left + "px", top: top + "px" }); 
}); 
}); 
</script>
</head>
<body>

<jsp:useBean id="timeAdapter" class="com.flight_ticket_search.Util.TimeAdapter" scope="session"/>   
<jsp:useBean id="sorter" class="com.flight_ticket_search.Service.SortFlightServiceImpl" scope="session"/>  

<div id= "main">
	
	<div id= "left"style="float:left;position:absolute;font-size:14px;line-height: 5px;">
		<p style="font-size:18px;"><strong>Ticket Filter</strong></p>

		<form method= "get" action= "sortServlet">
	
			<div class= "filter_window">
				<h3>Stops</h3>
				<div id= "stop" class= "filter_content">
					<input type= "checkbox" name= "nonestop" checked= "checked">none stop
					<input type= "checkbox" name= "onestop" checked= "checked">one stop<br>
					<input type= "checkbox" name= "multistop" checked= "checked">multi stops<br>
					<br>
				</div>
			</div>

<%		
		Map<Integer, List<List<String>>> ticketMap = new HashMap<Integer, List<List<String>>>();
		TicketCollectionImpl ticketCollection;
		TicketIterator tickItr1;
		
		if (request.getAttribute("sortedCollection") != null) {
			// from sortServlet || from reservation servlet: locking failure case
			ticketCollection = (TicketCollectionImpl) request.getAttribute("sortedCollection");
			session.setAttribute("filteredCollection", ticketCollection);
			tickItr1 = ticketCollection.iterator(TicketTypeEnum.ALL);

			// extending case1: some other client is holding the lock currently 
			if (request.getAttribute("lockFailure") != null) {
%>
<script>
			alert("Sorry, system is queuing up, would you please wait for 2 minutes ......");
</script>
<%
			}
			
			
			// extending case2: condition: when the ticket is sold out 
			if (request.getAttribute("ticketSoldout") != null) {
%>
<script>
			alert("Sorry, your ticket was already sold out. Would you please change one ?");
</script>
<%	
			} 
			
		} else {
			// from requestFlightServlet 
			ticketCollection = (TicketCollectionImpl) session.getAttribute("ticketCollection");
			session.setAttribute("filteredCollection", ticketCollection);
			tickItr1 = (TicketIterator) request.getAttribute("ticketIterator");
			
%>

<%			
		}
		
		
		List<Ticket> ticketList = new ArrayList<Ticket>();
		for (Ticket ticket: ticketCollection.getList()) {
			ticketList.add(ticket);
		}
		
		Time takeOffLow = null, takeOffHigh = null, landingLow = null, landingHigh = null;
		Time minLayover = null, maxLayover = null, minLeg = null, maxLeg = null;
		int takeOffOffset = 0, landingOffset = 0;
		if (ticketList.size()>0) {
			// sort by take-off time
			sorter.sortByTakeoffTime(ticketList);
			takeOffLow = ticketList.get(0).getTakeOffTime();
			takeOffHigh = ticketList.get(ticketList.size()-1).getTakeOffTime();
			takeOffOffset = ticketList.get(0).getFlightList().get(0).getDepartureAirport().getOffset();
			
			// sort by landing time
			sorter.sortByLandingTime(ticketList);
		    landingLow = ticketList.get(0).getLandingTime();
			landingHigh = ticketList.get(ticketList.size()-1).getLandingTime();
			landingOffset = ticketList.get(0).getFlightList().get(ticketList.get(0).getFlightList().size()-1).getArrivalAirport().getOffset();
			
			// sort by layover time 
			minLayover = ticketList.get(0).getLayover();
			maxLayover = minLayover;
			minLeg = ticketList.get(0).getTotalLeg();
			maxLeg = minLeg;
			for (Ticket ticket: ticketList) {
				if (minLayover.getHour() >= ticket.getLayover().getHour()) 
					minLayover = ticket.getLayover();
				if (maxLayover.getHour() <= ticket.getLayover().getHour()) 
					maxLayover = ticket.getLayover();
				if (minLeg.getHour() >= ticket.getTotalLeg().getHour())
					minLeg = ticket.getTotalLeg();
				if (maxLeg.getHour() <= ticket.getTotalLeg().getHour()) 
					maxLeg = ticket.getTotalLeg();
			}
			// sort by leg
			
		}
%>
			<div class= "filter_window">
				<h3>Times</h3>
				<div class= "filter_content">
					<p>Take-off time</p>
					<p>
				<%= (ticketList.size() > 0)? 
						("May," + timeAdapter.getLocalTime(takeOffLow, takeOffOffset).getDate().getDay() + " " +
								timeAdapter.getLocalTime(takeOffLow, takeOffOffset).getHour() 
								+ "h ~ " +
						 "May," + timeAdapter.getLocalTime(takeOffHigh, takeOffOffset).getDate().getDay() + " " +
							timeAdapter.getLocalTime(takeOffHigh, takeOffOffset).getHour() + "h") 
							: 	"No matching result"
				%>
					</p>
			        <span id="take_off_rangeval" class= "slider_range">range: 0% - 100%</span>
					<div id="take_off_slider" class= "slider"></div>
					<input type= "hidden" id= "take_off_min" name= "takeOffMin" value= "0">
					<input type= "hidden" id= "take_off_max" name= "takeOffMax" value= "100">
					
					<p>Landing time</p>
					<p>
				<%= (ticketList.size() > 0)? 
						("May," + timeAdapter.getLocalTime(landingLow, landingOffset).getDate().getDay() + " " +
								timeAdapter.getLocalTime(landingLow, landingOffset).getHour() 
								+ "h ~  " +
						"May," + timeAdapter.getLocalTime(landingHigh, landingOffset).getDate().getDay() + " " +		
							timeAdapter.getLocalTime(landingHigh, landingOffset).getHour() + "h") 
							: "No matching result"
				%>
					</p>
					<span id="landing_rangeval" class= "slider_range">range: 0% - 100%</span>
					<div id="landing_slider" class= "slider"></div>
					<input type= "hidden" id= "landing_min" name= "landingMin" value= "0">
					<input type= "hidden" id= "landing_max" name= "landingMax" value= "100">
					
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
			</div>
			
			
			<div class= "filter_window">
				<h3>Durations</h3>
				<div class= "filter_content">
					<p>Layover</p>
					<p>
					<%= (ticketList.size() > 0?
					(minLayover.getHour() + "h ~ " + maxLayover.getHour() + "h") :
					"No matching result")
					%>
					</p>
					<span id="layover_rangeval" class= "slider_range">range: 0% - 100%</span>
					<div id="layover_slider" class= "slider"></div>
					<input type= "hidden" id= "layover_min" name="layoverMin" value= "0">
					<input type= "hidden" id= "layover_max" name= "layoverMax" value= "100">
					
					<p>Flight Leg</p>
					<p>
					<%= (ticketList.size() > 0?
					(minLeg.getHour() + "h ~ " + maxLeg.getHour() + "h") :
					"No matching result") 
					%>
					</p>
					<span id="leg_rangeval" class= "slider_range">range: 0% - 100%</span>
					<div id="leg_slider" class= "slider"></div>
					<input type= "hidden" id= "leg_min" name= "legMin" value= "0">
					<input type= "hidden" id= "leg_max" name= "legMax" value= "100">				
				</div>
				
				<!--  filtering button here to start filtering -->
				<input type= "submit" style= "margin: 50px 50% 10px; width: 45%; height: 20px" 
					name= "filter_button" value= "start filtering">
			</div>

		</form>
	</div>
	
	<div id= "right">
		Sorting Options  
		<form style= "margin-left: 22%" method= "get" action= "sortServlet">
			<select name= "sort_type" style= "margin-left: 10px">
				  <option value="price">sort by price</option>
				  <option value="duration">sort by duration</option>
				  <option value="take-off">sort by take-off time</option>
				  <option value="landing">sort by landing time</option>
				  <option value="layover">sort by layover time</option>
			</select>
			<input type= "submit" name= "sort_type" value= "start sorting">
		</form>
<%
		if (ticketList.size() == 0) {
%>
		<!-- <p class= "no_match_hint">
			: ( <i>Sorry, no matching result has been found, change filtering ?</i> 
		</p> 	 -->
			<div class="ui-widget">
<div class="ui-state-error ui-corner-all" style="margin-top: 120px; padding: 0 .7em;text-align:center;">
<p><span class=" ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
<strong>Sorry,</strong> no matching result has been found, change filtering ?</p>
</div>
</div>
<%			
		}
		//Iterating through ALL the ticket type in the collection
		int ticketId = 0;
		Ticket ticket = null;
		for (TicketIterator itr2 = tickItr1; itr2.hasNext();) {
			
			ticket = itr2.next();
			List<List<String>> ticketInfo = null;
			
			if (ticketId / 20 < 1) { 
%>		
		<div id= "page_seperator<%= ticketId/20 %>">
			
<% 				while (true) {  
%>
			<div class= "ticket_info" id= "ticket_info<%= ticketId%>" style= "height: 220px">
				<div style= "background-color: white; float: left; width: 20%; height: 100%">
					
					<p style= "text-align: center; margin-top: 20px">
					Price:<%= " " + ((ticket.getTotalPrice() > 1000)?
							new DecimalFormat("$#,###.##").format(ticket.getTotalPrice()):
						new DecimalFormat("$###.##").format(ticket.getTotalPrice())) %>
					</p>
					
					<p style= "text-align: center; margin-top: 5px">
					Duration:<%= " " + ticket.getDuration().getHour() + "h, " + ticket.getDuration().getMinute() + "m"  %>
					</p>
					
					<form action= "buyServlet" method= "get">
					<input type= "hidden" name= "ticketId" value= <%=ticketId %>>
					<input type= "submit" name= "selectSingleTicket" 
					value= "Select" style= "width: 60%; margin: 40px 30px">
					</form>
					
				</div>
				<div  class= "departing">
				<%= "Layover: " + ticket.getLayover().getHour() + "h, " + ticket.getLayover().getMinute() + "m"  %>
					<table>
						<tr>
							<td><%= "May," + timeAdapter.getLocalTime(ticket.getTakeOffTime(), takeOffOffset).getDate().getDay() + " " +
									timeAdapter.getLocalTime(ticket.getTakeOffTime(), takeOffOffset).getHour()  
										+ ":" + 
									(timeAdapter.getLocalTime(ticket.getTakeOffTime(), takeOffOffset).getMinute()<10? "0":"") 
										+ timeAdapter.getLocalTime(ticket.getTakeOffTime(), takeOffOffset).getMinute() %>
							</td>
							<td><%= ticket.getDepartureCity() %></td>
							<td>-></td>
							<td><%= "May," + timeAdapter.getLocalTime(ticket.getLandingTime(), landingOffset).getDate().getDay() + " " +
									timeAdapter.getLocalTime(ticket.getLandingTime(), landingOffset).getHour()  
									+ ":" + 
								(timeAdapter.getLocalTime(ticket.getLandingTime(), landingOffset).getMinute()<10? "0":"") 
									+ timeAdapter.getLocalTime(ticket.getLandingTime(), landingOffset).getMinute() %>
							</td>
							<td><%= ticket.getArrivalCity() %></td>
							<td><%= ticket.getDuration().getHour() + "h" + " " + ticket.getDuration().getMinute() + "min" %></td>
							<td><%= ticket.getStopType() %></td>
						</tr>
					</table>
				</div>
				
				<ul>
					<li style= "display: inline-block; padding: 8px"><%= ticket.getSeatType()%></li>
				</ul>
				<p id= "detail_hinter<%= ticketId %>" class= "detail_hinter">Show details</p>
				
			</div>
			<div hidden id= "detail_flight_info<%= ticketId %>"+ class= "detail_flight_info">
			<p style= "padding: 10px; font-size: 1.2em">Departure</p>
			<div style= "margin: 20px 15px">
				<table>

<% 
				    ticketInfo = new ArrayList<List<String>>();
					for (Flight flight: ticket.getFlightList()) {
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
								+ timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getMinute()  %>
						</td>
						<td><%= flight.getArrivalAirport().getCode()%></td>
						<td><%= flight.getDuration() + "min" %></td>
						<td><%= flight.getSeatType() %></td>
						<td><%= "$" + flight.getPrice() %></td>
					</tr>
<% 
					} 
%>
				</table>
				
			</div>
		</div>
<% 				 	if ((ticketId+1) % 20 == 0) {
%>
			<div class= "page_button">
				<button style= "float: left" class = "prev" onclick= "prev()">Previous</button>
  				<button class = "next" onclick= "next()">Next</button>
			</div>
<%						break;	
					}
					
					if (itr2.hasNext()) {
						
						ticket = itr2.next();
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

<% 				while (true) {  
%>
		<div class= "ticket_info" id= "ticket_info<%= ticketId%>" style= "height: 220px">
			<div style= "background-color: white; float: left; width: 20%; height: 100%">
				
				<p style= "text-align: center; margin-top: 20px">
				Price:<%= " " + ((ticket.getTotalPrice() > 1000)?
						new DecimalFormat("$#,###.##").format(ticket.getTotalPrice()):
					new DecimalFormat("$###.##").format(ticket.getTotalPrice())) %>
				</p>
				
				<p style= "text-align: center; margin-top: 5px">
				Duration:<%= " " + ticket.getDuration().getHour() + "h, " + ticket.getDuration().getMinute() + "m"  %>
				</p>
				
				<form action= "buyServlet" method= "get">
				<input type= "hidden" name= "ticketId" value= <%=ticketId %>>
				<input type= "submit" name= "selectSingleTicket" 
				value= "Select" style= "width: 60%; margin: 40px 30px">
				</form>
				
			</div>
			<div  class= "departing">
			<%= "Layover: " + ticket.getLayover().getHour() + "h, " + ticket.getLayover().getMinute() + "m" %>
				<table>
					<tr>
						<td><%= "May," + timeAdapter.getLocalTime(ticket.getTakeOffTime(), takeOffOffset).getDate().getDay() + " " +
								timeAdapter.getLocalTime(ticket.getTakeOffTime(), takeOffOffset).getHour()  
									+ ":" + 
								(timeAdapter.getLocalTime(ticket.getTakeOffTime(), takeOffOffset).getMinute()<10? "0":"") 
									+ timeAdapter.getLocalTime(ticket.getTakeOffTime(), takeOffOffset).getMinute() %>
						</td>
						<td><%= ticket.getDepartureCity() %></td>
						<td>-></td>
						<td><%= "May," + timeAdapter.getLocalTime(ticket.getLandingTime(), landingOffset).getDate().getDay() + " " +
								timeAdapter.getLocalTime(ticket.getLandingTime(), landingOffset).getHour()  
								+ ":" + 
							(timeAdapter.getLocalTime(ticket.getLandingTime(), landingOffset).getMinute()<10? "0":"") 
								+ timeAdapter.getLocalTime(ticket.getLandingTime(), landingOffset).getMinute() %>
						</td>
						<td><%= ticket.getArrivalCity() %></td>
						<td><%= ticket.getDuration().getHour() + "h" + " " + ticket.getDuration().getMinute() + "min" %></td>
						<td><%= ticket.getStopType() %></td>
					</tr>
				</table>
			</div>
			
			<ul>
				<li style= "display: inline-block; padding: 8px"><%= ticket.getSeatType()%></li>
			</ul>
			<p id= "detail_hinter<%= ticketId %>" class= "detail_hinter">Show details</p>
			
		</div>
		<div hidden id= "detail_flight_info<%= ticketId %>"+ class= "detail_flight_info">
		<p style= "padding: 10px; font-size: 1.2em">Departure</p>
		<div style= "margin: 20px 15px">
			<table>
<% 
			    ticketInfo = new ArrayList<List<String>>();
				for (Flight flight: ticket.getFlightList()) {
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
							+ timeAdapter.getLocalTime(flight.getArrivalTime(), a_offset).getMinute()  %>
					</td>
					<td><%= flight.getArrivalAirport().getCode()%></td>
					<td><%= flight.getDuration() + "min" %></td>
					<td><%= flight.getSeatType() %></td>
					<td><%= "$" + flight.getPrice() %></td>
				</tr>
<% 				} 
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
				
				if (itr2.hasNext()) {
				
					ticket = itr2.next();
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
			// store the values in map for retrieving in the purchase servlet
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
	
	$("#ticket_info<%= i%>").hover(function() {
		$(this).css("border", "solid orange 5px");
	},
	function() {
		$(this).css("border", "solid #cc9900 3px");
	});
	
	<%
		}		
	%>
	  
	
});
</script>
<script src= "display_flight.js">
</script>
<script src="external/jquery/jquery.js"></script>
<script src="jquery-ui.js"></script>

</html>