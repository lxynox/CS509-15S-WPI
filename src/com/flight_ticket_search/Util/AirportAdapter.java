/**
 * 
 */
package com.flight_ticket_search.Util;


import com.flight_ticket_search.Dao.FlightDaoImpl;
import com.flight_ticket_search.Entity.Airport;


/**
 * @author lxybi_000
 *
 */
public class AirportAdapter {
	
	
	/**
	 * 
	 * @param city
	 * @return
	 */
	public Airport getAirport(String cityCode) {
		return new FlightDaoImpl().getAirport(cityCode);
	}
	
		
	/**
	 * convert the String city from user input to
	 * String cityCode which is the format used in db
	 * @param city
	 * @return String representation of cityCode
	 */
	public static String getCode(String city) {
		if (city.equals("Boston")) {
			return "BOS";
		}else if (city.equals("Los Angeles")) {
			return "LA";
		}else if (city.equals("New York")) {
			return "NY";
		}else {
			return "Wrong input";
		}
	}
	

	public static void main(String[] args) {
		System.out.println(new AirportAdapter().getAirport("ATL").toString());
//		System.out.println(new AirportAdapter().getTimezoneOffset
//				(new AirportAdapter().getAirport("ATL")));
	}
	
	
}



