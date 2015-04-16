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

<title>Order detail</title>
</head>
<%-- 
<jsp:useBean id = "ticket" class = "com.flight_ticket_search.Entity.TicketInfo" scope = "session"/>
<jsp:useBean id = "user" class = "com.flight_ticket_search.Entity.User" scope = "session"/>
 --%>
<body>
<%	List<TicketInfo> orderList = null; 
	User client = new User("", "dear", "client"); 
	Map<Integer, String> cancelMap = new HashMap<Integer, String>();
	
	if (request.getAttribute("orderList") != null) {
		orderList = (List<TicketInfo>) request.getAttribute("orderList");		
	} 
	if (request.getAttribute("userInfo") != null) {
		client = (User) request.getAttribute("userInfo");
	}
%>
	<h1>Welcome, <%= " " + client.getFirstName() + " " + client.getLastName() %></h1>
	<p style= "margin-left: 10%">Check your orders below</p>
	<div id= "main" style= "margin-left: 10%; width: 80%">
		<fieldset>
			<legend><i>order information</i></legend>
		  
		  <form action= "orderServlet" method= "post">
			<table style= "width: 100%; style: text-align: center">
				<tr>
					<td>Seat</td>
					<td>Number</td>
					<td>Departure Airport</td>
					<td>Arrival Airport</td>
					<td>Departure Time (local)</td>
					<td>Arrival Time (local)</td>
					<td>Cancel Option</td>
				</tr>
			<% if (orderList != null) {
				int i = 0;
				for (TicketInfo order: orderList) { 
			%>
				<tr>
					<td><%= order.getSeat()  %></td>
					<td><%= order.getNumber()  %></td>
					<td><%= order.getDepartureCity()  %></td>
					<td><%= order.getArrivalCity()  %></td>
					<td><%= order.getDepartureTime() %></td>
					<td><%= order.getArrivalTime()  %></td>
					<td><input type= "submit" name= "cancel_button<%=i %>" value= "cancel"></td>
				</tr>
				<% 
					cancelMap.put(i, order.getNumber());
					i++;
				 }
				
				 session.setAttribute("cancelMap", cancelMap); 
			   }
				%>
			</table>
			<input type= "hidden" name= "cancel_order" value= "true">
		</form>	
			
			<p style= "font-weight: normal; font-size: .8em"></p>
			
			<form action= "homepage.html">
				<input type= "submit" name= "confirm" value= "Confirm">
			</form>
			<!-- <form action= "orderServlet" method= "post">
				<input type= "submit" name= "cancelOrder" value= "Cancel">
			</form> -->
			
		</fieldset>
	</div>

</body>
</html>