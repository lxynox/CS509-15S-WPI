<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import= "java.util.*,  
    	com.flight_ticket_search.Entity.*,
    	com.flight_ticket_search.Util.*"
    	%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href= "ticket_confirm_info.css" rel= "stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src= "http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js">
</script>
<title>Purchased Ticket Information Page</title>
</head>

<body>
<div id= "header">
<h1>Confirm the ticket info below </h1>
</div>


<jsp:useBean id="timeAdapter" class="com.flight_ticket_search.Util.TimeAdapter" scope="session"/>   


<%	List<List<String>> ticketInfo = (List<List<String>>) session.getAttribute("purchaseList"); 
	List<Flight> flightList = (List<Flight>) request.getAttribute("flightList");
	session.setAttribute("id", request.getAttribute("ticketId"));
	
	List<TicketInfo> orderList = new ArrayList<TicketInfo>();
%>

<div id= "main" style= "margin-left: 10%; width: 80%">
<form action= "buyServlet" method= "get">
	<fieldset>
		<legend><i>ticket information</i></legend>
		<table style= "width: 100%; text-align: center">
			
		<!-- 	<colgroup>
				<col span= "1" style= "width: 30%">
			</colgroup> -->
			<tr>
				<td>Ticket Number</td>
				<td>Seat Type</td>
				<td>Departure Airport</td>
				<td>Arrival Airport</td>
				<td>Departure Time (local)</td>
				<td>Arrival Time (local)</td>
			</tr>
			<% 	int index = 0;
				for (List<String> ticket: ticketInfo)  { 
					int d_offset = flightList.get(index).getDepartureAirport().getOffset();
					int a_offset = flightList.get(index).getArrivalAirport().getOffset();
					
					String departureAirport = flightList.get(index).getDepartureAirport().getCode();
					String arrivalAirport = flightList.get(index).getArrivalAirport().getCode();
					
					String departureTime = "May," + timeAdapter.getLocalTime(flightList.get(index).getDepartureTime(), d_offset).getDate().getDay() + " " +
					timeAdapter.getLocalTime(flightList.get(index).getDepartureTime(), d_offset).getHour()  
							+ ":" + 
						(timeAdapter.getLocalTime(flightList.get(index).getDepartureTime(), d_offset).getMinute()<10? "0":"") 
							+ timeAdapter.getLocalTime(flightList.get(index).getDepartureTime(), d_offset).getMinute();
					
					String arrivalTime =  "May," + timeAdapter.getLocalTime(flightList.get(index).getArrivalTime(), a_offset).getDate().getDay() + " " +
					timeAdapter.getLocalTime(flightList.get(index).getArrivalTime(), a_offset).getHour()  
							+ ":" + 
						(timeAdapter.getLocalTime(flightList.get(index).getArrivalTime(), a_offset).getMinute()<10? "0":"") 
							+ timeAdapter.getLocalTime(flightList.get(index).getArrivalTime(), a_offset).getMinute();
			%>
			<tr>
				<td><%= ticket.get(0) %></td>
				<td><%= ticket.get(1) %></td>
				<td><%= departureAirport %></td>
				<td><%= arrivalAirport %></td>
				<td><%= departureTime %></td>
				<td><%= arrivalTime %></td>
			</tr>
			<% 
			//      add order info to orderList
					orderList.add(new TicketInfo(ticket.get(1), ticket.get(0), departureAirport, 
							arrivalAirport, departureTime, arrivalTime));
					index++;
				}
				
				session.setAttribute("orderList", orderList);
			%>
			
		</table>
		
		<div id= "user_info">
			<h3><i>personal information for checking order</i></h3>
			<table>
				<tr style= "background-color: darkgray">
					<td>User ID: <input type= "text" name= "user_id" placeholder= "for eg. team07">(*) </td>
					<td>First Name: <input type= "text" name= "first_name">(optional) </td>
					<td>Last Name: <input type= "text" name= "last_name">(optional) </td>
				</tr>
			</table>
			<input style= "margin-top: 30px; width: 120px; height: 40px; background-color: none"
					 type= "submit" name= "buyTicket" value= "Buy">
		</div>
		
	</fieldset>
</form>
</div>

</body>
<script>
$(document).ready(function() {

	$("ul li").click(function() {
		$("#table_div").slideToggle(2000);
	});
	
	$("input").focus(function() {
		$(this).css("background-color", "white");
	});	
});
</script>
</html>