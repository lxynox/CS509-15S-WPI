/**
 *  Date: 22, March, 2015
 *  @author: Kun Huang
 *  Description: This the unit test class for XMLConnector
 *  			 created to test the function of writing data to DB 
 *  			 and reading data from DB 
 */
package com.flight_ticket_search.Dao;

public class XMLConnectorTest {
	/**
	 * 1. 写的时候先测试 public方法，因为他们是package内的接口， 是不同class之间沟通的桥梁 2. 写出public
	 * 方法的测试case， 多用System.out.println()；一般是2-3组输入 对应2-3组输出 3. 测试结果正确 4. 测试结果错误，
	 * 开始测试public method调用的private方法, 试图debug出错误的地方
	 * 
	 */
	/**
	 * test for reading data
	 */
	/* test to get whole list of airports */
	public void getAirportTest() {
		// 不要调用 new XMLConnector() 因为private花了， 是singleton design pattern需要，
		// 用 XMLConnector.getInstance()得到你要的对象， 下面都是这样
		String airportXMLStrings = XMLConnector.getInstance().getAirports();
		System.out.println(airportXMLStrings);
	}

	/* test to get whole list of airplanes */
	public void getAirplaneTest() {
		String airplaneXMLStrings = XMLConnector.getInstance().getAirplanes();
		System.out.println(airplaneXMLStrings);
	}

	/* test to get whole list of departing flights */
	public void getDepartingFlightsTest() {
		String airportCode = "LAX";
		String dDate = "2015_05_15";
		String departureXMLStrings = XMLConnector.getInstance()
				.getDepartingFlights(airportCode, dDate);
		System.out.println(departureXMLStrings);
	}

	/* test to get whole list of arriving flights */
	public void getArrivingFlightsTest() {
		String airportCode = "BOS";
		String aDate = "2015_05_09";
		String arrivalXMLStrings = XMLConnector.getInstance()
				.getArrivingFlights(airportCode, aDate);
		System.out.println(arrivalXMLStrings);
	}

	/**
	 * test for writing data
	 * 
	 */
	/* precondition: no team holds the lock on the database */
	/* postcondition: our team holds the lock for 2 minutes, could not read data */
	public void lockTest() {
		System.out.println("start to lock the database ... ");
		boolean isLocked = XMLConnector.getInstance().lock();
		if (isLocked) {
			System.out
					.println("Database should be locked, continue to access some data ... ");
			getAirportTest(); // check if you could retrieve whole list of
								// airports
			System.out.println("There should not be any xml strings above me!");

		} else {
			System.out.println("Failure: database is not successfully locked!");
		}
	}

	/* precondition: database is locked by your own team */
	/* postcondition: our team no longer holds the lock, could read data again */
	public void unlockTest() {
		System.out.println("start to unlock the database ... ");
		if (XMLConnector.getInstance().lock()) {
			boolean isUnlocked = XMLConnector.getInstance().unlock();
			if (isUnlocked) {
				System.out
						.println("Database should be unlocked, continue to access some data ... ");
				getAirportTest();
				System.out
						.println("There should be a line of xml strings above me!");
			}
		}
	}

	public void reserveFlightTest() {
		/* test for purchasing single ticket (Coach) */
		String xmlString1 = "<Flights>"
				+ "<Flight number=\"2000\" seating=\"Coach\" />" + "</Flights>";
		XMLConnector.getInstance().reserveFlight(xmlString1);
		System.out.println();

		/* test for purchasing single ticket (FirstClass) */
		String xmlString2 = "<Flights>"
				+ "<Flight number=\"2000\" seating=\"FirstClass\" />"
				+ "</Flights>";
		XMLConnector.getInstance().reserveFlight(xmlString2);
		System.out.println();

		/* test for purchasing multiple tickets */
		String xmlString3 = "<Flights>"
				+ "<Flight number=\"2000\" seating=\"FirstClass\" />"
				+ "<Flight number=\"2000\" seating=\"FirstClass\" />"
				+ "<Flight number=\"2000\" seating=\"FirstClass\" />"
				+ "</Flights>";
		XMLConnector.getInstance().reserveFlight(xmlString3);
		System.out.println();

	}

	/**
	 * @param args
	 */
	/*
	 * public static void main(String[] args) { // TODO Auto-generated method
	 * stub XMLConnectorTest tester = new XMLConnectorTest();
	 * 
	 * // tester.getAirportTest();
	 * 
	 * // tester.getAirplaneTest();
	 * 
	 * // tester.getDepartingFlightsTest();
	 * 
	 * // tester.getArrivingFlightsTest();
	 * 
	 * // tester.lockTest();
	 * 
	 * // tester.unlockTest();
	 * 
	 * // tester.reserveFlightTest(); }
	 */
}
