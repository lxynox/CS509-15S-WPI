/** cs509: team07
 *  Description: This class is created to generate the query parameters 
 *    like list=airports, list=airplanes etc, used to append to mBaseURL.
 *    Each call of query returns its unique
 *    URL parameter.
 */
package com.flight_ticket_search.Util;

import com.flight_ticket_search.Entity.TicketInfo;
import com.flight_ticket_search.Entity.User;
 
 
public class QueryFactory {
        /**
         * Return query parameter to retrieve airport from database
         * 
         * @param team ticket agent name used as parameter to query database
         * @return the query parameter to retrieve airports
         */
        public static String getAirports(String team) {
            return "team=" + team + "&action=list&list_type=airports";
        }
        
        /**
         * Return query parameter to retrieve airplanes from database
         * 
         * @param team ticket agent name used as parameter to query database
         * @return query parameter to retrieve airplanes
         */
        public static String getAirplanes(String team) {
            return "team=" + team + "&action=list&list_type=airplanes";
        }
        
        /**
         * Return query parameter to lock the database
         * 
         * @param team ticket agent name used as parameter to query database
         * @return the query parameter to lock the database
         */
        public static String lockDB(String team) {
        	return "team=" + team + "&action=lockDB";
        }
 
        /**
         * Return the query parameter to unlock database
         * 
         * @param team ticket agent name used as parameter to query database
         * @return query parameter to unlock the database
         */
        public static String unlockDB(String team) {
        	return "team=" + team + "&action=unlockDB";
        }
        
        /**
         * Return the query parameter to reserve flight from database
         * 
         * @param team ticket agent name used as parameter to query database
         * @return query parameter to reserve flight
         */
        public static String reserve(String team, String xmlFlights) {
        	return "team=" + team + "&action=buyTickets" + "&flightData=" + xmlFlights;
        }
        
        // method used for test of flight reservation and DB locking
        /**
         * Return the query parameter to reset the database for test
         * 
         * @param team ticket agent name used as parameter to query database
         * @return query parameter to resetDB
         */
        public static String resetDB(String team) {
        	return "team="+ team + "&action=resetDB";
        }
        
        /** 
         * Return the query parameter to retrieve departing flight
         *
         * @param code   key of airport
         * @param day    departure date
         * @return query parameter to retrieve departing flight
         */
        public static String getDepartingFlights(String team, String code, String day) {
            String d = day.toString();
            return "team=" + team + "&action=list&list_type=departing&airport="+ code+ "&day="+ d;
        }
        
        /** 
         * Return the query parameter to retrieve arriving flight
         *
         * @param code   key of airport
         * @param day    departure date
         * @return query parameter to retrieve arriving flight
         * @see see also getDepartingFlights(String team, String code, String day)
         */
        public static String getArrivingFlights(String team, String code, String day) {
            String d = day.toString();
            return "team=" + team + "&action=list&list_type=arriving&airport="+ code+ "&day="+ d;
        }
 
        /** Below are additional sub system of checking order for the user */       
        /* User and Ticket Information */
    	public static String addUser(User user) {
    		return "insert into user values('" + 
    					user.getUserID() + "','" +
    					user.getFirstName() + "','" +
    					user.getLastName() + "')";
    	}

    	public static String addTicket(TicketInfo ticketInfo) {
    		return "insert into ticket values('" +
    					ticketInfo.getSeat() + "','" +
    					ticketInfo.getNumber() + "','" +
    					ticketInfo.getDepartureCity() + "','" +
    					ticketInfo.getArrivalCity() + "','" + 
    					ticketInfo.getDepartureTime() + "','" +
    					ticketInfo.getArrivalTime() + "')";
    	}
    	
    	/* Below are all Order information */
    	public static String addOrder (String userID, String number) {
    		return "insert into orders values('" + 
    				userID + "','" +
    				number + "')";
    	}
    	
    	public static String getOrder (String userID) {
    		return "select * from ticket where number "
    				+ "in (select number from orders where user_id = '" + userID + "')";
    	}
    	
    	public static String deleteOrder (String number) {
    		return "delete from orders where number = " + number;
    	}
    	
    	public static String getUser (String userID) {
    		return "select id, first_name, last_name from user where id = '" + userID + "'";
    	}
 }