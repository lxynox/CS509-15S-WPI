/**
 * @author: lxynox
 * @date: march, 20, 2015
 * Description: created to throw exception when the user input date is 
 * 				not within the range of May,08,2015 ~ May,17,2015
 */
package com.flight_ticket_search.Util;

public class DateOutOfRangeException extends Exception {

	public DateOutOfRangeException() {
		// TODO Auto-generated constructor stub
	}
	
	public DateOutOfRangeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
