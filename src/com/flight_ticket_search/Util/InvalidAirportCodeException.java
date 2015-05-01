/**
 * @author: lxynox
 * @date: march, 20, 2015
 * Description: created to throw exception when the airport code 
 * 				from user input is not valid three digit us airport code
 */
package com.flight_ticket_search.Util;

public class InvalidAirportCodeException extends Exception {

	public InvalidAirportCodeException () {
		
	}
	
	public InvalidAirportCodeException (String message) {
		super (message);
	}
}
