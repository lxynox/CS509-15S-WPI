/**
 * 
 */
package com.flight_ticket_search.Dao;

import java.util.List;

import com.flight_ticket_search.Entity.Airport;

import junit.framework.TestCase;

/**
 * @author Kun Huang Date: 22, march, 2015
 */
public class AirportXMLParserTest extends TestCase {

	/**
	 * Test method for
	 * {@link com.flight_ticket_search.Dao.AirportXMLParser#getAirport(java.lang.String)}
	 * .
	 */
	public void testGetAirport() {
		// Expected output: get one airport corresponds to its airport code

		String code1 = "BOS";
		System.out.println("Boston airport test ... ");
		System.out.println(AirportXMLParser.getInstance().getAirport(code1));

		System.out.println();

		String code2 = "LAX";
		System.out.println("Los Angeles airport test .... ");
		System.out.println(AirportXMLParser.getInstance().getAirport(code2));

		System.out.println();

		String code3 = "PIT";
		System.out.println("Pittsburgh airport test .... ");
		System.out.println(AirportXMLParser.getInstance().getAirport(code3));

	}

	/**
	 * Test method for
	 * {@link com.flight_ticket_search.Dao.AirportXMLParser#getAirports()}.
	 */
	public void testGetAirports() {
		// Expected result: whole list of airplanes are printed out
		List<Airport> airports = AirportXMLParser.getInstance().getAirports();
		System.out.println("retrieve all the airports info below ... ");
		System.out.println(airports);
	}

	
	 public static void main(String[] args) { 
		 AirportXMLParserTest tester = new AirportXMLParserTest();
	  
  		 tester.testGetAirport();
	  
  		 tester.testGetAirports();
	 }
	

}
