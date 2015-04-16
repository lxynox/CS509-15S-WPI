/**
 * 
 */
package com.flight_ticket_search.Dao;

import junit.framework.TestCase;

/**
 * @author Kun Huang Date: 22, march, 2015
 */
public class FlightGetterTest extends TestCase {

	/**
	 * Test method for
	 * {@link com.flight_ticket_search.Dao.FlightXMLParser#getFlights(java.lang.String, java.lang.String, boolean)}
	 * .
	 */

	/**
	 * precondition: date range from : 2015/5/8 ~ 2015/5/17 code must be three
	 * valid airport code
	 * 
	 * postcondition: flights matches the searching criteria is returned
	 */
	public void testGetFlights() {
		// Expected output: list of all departing/arriving flights information
		// on a specified day and airport code
		/* departure flights test */
		String code1 = "BOS";
		String date1 = "2015_05_09";
		boolean departing1 = true;
		System.out.println(new FlightGetter().getFlights(code1, date1,
				departing1));

		System.out.println();

		/* arrival flights test */
		String code2 = "BOS";
		String date2 = "2015_05_15";
		boolean departing2 = false;
		System.out.println(new FlightGetter().getFlights(code2, date2,
				departing2));

		System.out.println();

		/* starting date test */
		String code3 = "BOS";
		String date3 = "2015_05_08";
		boolean departing3 = true;
		System.out.println(new FlightGetter().getFlights(code3, date3,
				departing3));

		System.out.println();

		/* ending date test */
		String code4 = "BOS";
		String date4 = "2015_05_17";
		boolean departing4 = false;
		System.out.println(new FlightGetter().getFlights(code4, date4,
				departing4));

	}

	public static void main(String[] args) {
		FlightGetterTest tester = new FlightGetterTest();

		tester.testGetFlights();
	}

}
