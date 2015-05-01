/**
 * @author lxybi_000
 * @Date: march, 20, 2015
 * Description: This class is used for converting the airport code to 
 * 				airport entity and to check/validate airport code from UI
 */
package com.flight_ticket_search.Util;

import com.flight_ticket_search.Dao.FlightDaoImpl;
import com.flight_ticket_search.Entity.Airport;

public class AirportAdapter {
	
	/**
	 * Return the airport according to the airport code
	 * @param cityCode US airport code
	 * @return Airport the right airport according to the valid airport code
	 */
	public Airport getAirport(String cityCode) {
		return new FlightDaoImpl().getAirport(cityCode);
	}
	
	/**
	 * Return the valid 3 digits US airport code from UI
	 * Return valid us airport code for flight search, if the input airport 
	 * code does not match the standard criteria, a new Exception is thrown
	 * 
	 * @param uiAirport input airport specified by the user
	 * @return valid 3 digits US airport code
	 * @throws InvalidAirportCodeException
	 */
	public String getAirportCodeFromUI(String uiAirport) throws InvalidAirportCodeException{
		uiAirport = uiAirport.toUpperCase();
		if (getAirport(uiAirport) == null) {
			throw new InvalidAirportCodeException ("Departure/Arrival airport must be 3 digits valid US airports");
		}
		return uiAirport;
	}
}



