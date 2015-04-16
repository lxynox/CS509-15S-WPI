/**
 * 
 */
package com.flight_ticket_search.Dao;

import java.util.List;

import com.flight_ticket_search.Entity.Airplane;

import junit.framework.TestCase;

/**
 * @author Kun Huang Date: 22, march, 2015
 */
public class AirplaneXMLParserTest extends TestCase {

	/**
	 * Test method for
	 * {@link com.flight_ticket_search.Dao.AirplaneXMLParser#getAirplane(java.lang.String)}
	 * .
	 */
	public void testGetAirplane() {
		// Expected result: one airplane corresponding to its model is printed
		// out
		String model1 = "A320";
		System.out.println("A320 airplane test ... ");
		System.out.println(AirplaneXMLParser.getInstance().getAirplane(model1));

		System.out.println();

		String model2 = "767";
		System.out.println("767 airplane test .... ");
		System.out.println(AirplaneXMLParser.getInstance().getAirplane(model2));

		System.out.println();

		String model3 = "A340";
		System.out.println("A340 airplane test .... ");
		System.out.println(AirplaneXMLParser.getInstance().getAirplane(model3));

	}

	/**
	 * Test method for
	 * {@link com.flight_ticket_search.Dao.AirplaneXMLParser#getAirplanes()}.
	 */
	public void testGetAirplanes() {
		// Expected result: whole list of airplanes are printed out
		List<Airplane> airplanes = AirplaneXMLParser.getInstance()
				.getAirplanes();
		System.out.println("retrieve all the airplanes info below ... ");
		System.out.println(airplanes);
	}

	/*
	 * public static void main(String[] args) { AirplaneXMLParserTest tester =
	 * new AirplaneXMLParserTest();
	 * 
	 * tester.testGetAirplane();
	 * 
	 * tester.testGetAirplanes(); }
	 */
}
