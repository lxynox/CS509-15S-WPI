/**
 * 
 */
package com.flight_ticket_search.Service;

import junit.framework.TestCase;

/**
 * @author Kunhuang
 * @Date 2015, 04, 20
 *
 */
public class RequestFlightServiceImplTest extends TestCase {
	RequestFlightService requestFlightServicer = new RequestFlightServiceImpl();
	
	/** preconditions: all input arguments are valid and matches the back end inputs
	 *  postconditions: reasonable flight unions are returned 
	 *  (layover time in range 1-8, flight directions are always approaching)
	 * Test method for {@link com.flight_ticket_search.Service.RequestFlightServiceImpl#getRoundTrip(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	public final void testGetRoundTrip() {
//		fail("Not yet implemented"); // TODO
		String departureAirport = "SFO";
		String arrivalAirport = "BOS";
		String departureDate = "2015_05_09";
	    String returnDate = "2015_05_10";
	    
		System.out.println(requestFlightServicer.getRoundTrip(departureAirport, arrivalAirport, departureDate, returnDate));
		assertNotNull ("from sfo to bos on 2015,5,9 to 2015,5,10 round trip cannot be empty set", 
				requestFlightServicer.getRoundTrip(departureAirport, arrivalAirport, departureDate, returnDate));
		
		departureAirport = "IND";
		arrivalAirport = "BOS";
	    departureDate = "2015_05_09";
        returnDate = "2015_05_10";
	    
		System.out.println(requestFlightServicer.getRoundTrip(departureAirport, arrivalAirport, departureDate, returnDate));
		
		departureAirport = "IND";
		arrivalAirport = "PIT";
		departureDate = "2015_05_09";
	    returnDate = "2015_05_10";
	    
		System.out.println(requestFlightServicer.getRoundTrip(departureAirport, arrivalAirport, departureDate, returnDate));
		
		departureAirport = "SFO";
		arrivalAirport = "BOS";
		departureDate = "2015_05_15";
	    returnDate = "2015_05_16";
	    
		System.out.println(requestFlightServicer.getRoundTrip(departureAirport, arrivalAirport, departureDate, returnDate));
		
		departureAirport = "IND";
		arrivalAirport = "PIT";
		departureDate = "2015_05_15";
	    returnDate = "2015_05_16";
	    
		System.out.println(requestFlightServicer.getRoundTrip(departureAirport, arrivalAirport, departureDate, returnDate));
		
		departureAirport = "LAX";
		arrivalAirport = "SFO";
		departureDate = "2015_05_17";
	    returnDate = "2015_05_17";
	    assertSame ("from lax to sfo on 2015,5,17 to 2015,5,17 round trip should be empty set", 0,
				requestFlightServicer.getRoundTrip(departureAirport, arrivalAirport, departureDate, returnDate).size());
		System.out.println(requestFlightServicer.getRoundTrip(departureAirport, arrivalAirport, departureDate, returnDate));
	}

}
