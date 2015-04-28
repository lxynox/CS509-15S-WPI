<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import= "java.util.*,
    		com.flight_ticket_search.Entity.*,
    		com.flight_ticket_search.Util.*,
    		com.flight_ticket_search.Service.RequestFlightServiceImpl"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CS509_Team07 - online flight ticket service</title>
<link href= "homepage.css" rel= "stylesheet">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js">
</script> 	
</head>
<body>


<div id= "header">
<h1>Online Flight Reservation System</h1>
</div>

<div id= "navigator">
	<h3 style= "float: left; color: white; padding: 10px"><i>CS509 Team07</i></h3>
</div>

<br><br><br>

<!-- <h2 style= "color: white; font-size: 2em; padding-left: 10px"><bold>Flight<bold></h2>  -->

<ul class= "trip">    
	<li><a id= "oneWay" onclick= "ticket.oneWay()">One-way</a></li>
	<li><a id= "roundTrip" onclick= "ticket.roundTrip()">Round-trip</a></li>
	<li><a id= "multiDest" onclick= "ticket.multiDest()">Check order</a></li>				
</ul>

<div id= "single_ticket">
<br><br>
    <form class= "form" action="requestFlightServlet" method="get">
    <input type = "hidden" name= "trip_type" value = "single">
	  
	 <fieldset id= "field_frame">
	    <legend>Flight Ticket</legend>
		<div class= "single_div">
			<p><b>FROM</b></p>
            <input class= "single_ticket_input" type= "text" placeholder= "BOS for Boston" name= "departureCity">
		</div>
		<div class= "single_div">
			<p><b>TO</b></p>
	        <input class= "single_ticket_input" type= "text" placeholder= "LAX for Los Angeles" name= "arrivalCity">
        </div>
		<div class= "single_div">
			<p><b>Departing</b></p>
			<input class= "single_ticket_input" type= "date" placeholder= "format: YYYY-MM-dd" name= "departureDate">
		</div>	
		<div class= "single_div">
			<p><b>Seating</b></p>
			<select class= "single_ticket_input" name= "seat">
				<option value= "coach">Coach</option>
				<option value= "firstclass">FirstClass</option>
				<option value= "mixed">Mixed</option>
			</select>
		</div>	
		<div style= "clear: both">
			<input type="submit" class= "search_button" value= "search">
		</div>
	 </fieldset>
	</form><br><br>
	
</div>

<div hidden id= "round_ticket">
<br><br>
    <form class= "form"  action="requestFlightServlet" method="get">
	<input type= "hidden" name= "trip_type" value= "round"> 
	  <fieldset id= "field_frame">
	    <legend>Flight Ticket</legend>
		<div class= "round_div">
		    <p><b>FROM</b></p>
            <input class= "round_ticket_input" type= "text" placeholder= "LAX for Los Angeles" name = "departureCity">
		</div>
		<div class= "round_div">
		    <p><b>TO</b></p>
	        <input class= "round_ticket_input" type= "text"  placeholder= "BOS for Boston" name = "arrivalCity">
		</div>
		<div class= "round_div">
			<p><b>Seating</b></p>
			<select class= "round_ticket_input" name= "seat">
				<option value= "coach">Coach</option>
				<option value= "firstclass">FirstClass</option>
				<option value= "mixed">Mixed</option>
			</select>
		</div>
		<div class= "round_div" style= "clear: left">
		    <p><b>Departing</b></p>
            <input class= "round_ticket_input" placeholder= "format: YYYY-MM-dd" type= "date" name= "departureDate">
		</div>
		<div class= "round_div">
		    <p><b>Returning</b></p>
		    <input class= "round_ticket_input" placeholder= "format: YYYY-MM-dd" type= "date" name= "returnDate">
		</div>
			
		<div style= "clear: both">
		<br><br>
			<Input type= "submit" class= "search_button" value= "search">
		</div>
	  </fieldset>
	</form><br><br>
</div>

<div hidden id= "multi_ticket">
<br><br>
    <form class= "form" method= "post" action= "orderServlet">
    <input type= "hidden" name= "trip_type" value= "multi_trip">
    
	  <fieldset id= "field_frame">
	    <legend>Search Order</legend>
		<div class= "multi_div">
	        <p><b>User ID</b></p>
            <input class= "multi_ticket_input" placeholder= "for eg. 'tom123'" type= "text" name= "user_id">
			<button type= "submit" name= "get_order" class= "search_button">My Order</button>
		</div>
		
	  </fieldset>
	</form><br><br>
</div>

<div id= "footer">
</div>

<%
	String nullInputErrorMessage = (String) request.getAttribute("nullInput");
	String invalidInputErrorMessage = (String) request.getAttribute("invalidInput");
	if (nullInputErrorMessage != null) {
%>
<script> alert ("Something is missing, please see bottom of the current page"); </script>	
<p class = "error_notification"><%= " * " + nullInputErrorMessage %></p>
<% 		
	}

	if (invalidInputErrorMessage != null) {
%>
<script> alert ("Invalid input, please see bottom of the current page"); </script>
<p class = "error_notification"><%= " * " + invalidInputErrorMessage %></p>
<%		
	}
%>

<script src= "homepage.js">
</script>

</body>
</html>