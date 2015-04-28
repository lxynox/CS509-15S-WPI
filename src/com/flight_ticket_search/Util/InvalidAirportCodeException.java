/**
 * 
 */
package com.flight_ticket_search.Util;

/**
 * @author lxynox
 *
 */
public class InvalidAirportCodeException extends Exception {
	
	/**
	 * constructor without parameter
	 */
	public InvalidAirportCodeException () {
		
	}
	
	/**
	 * Constructor with message string
	 * @param message could be retrieved later by the throwable instance
	 */
	public InvalidAirportCodeException (String message) {
		super (message);
	}
}
