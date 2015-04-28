/** cs509 team: team07
 * Date: March 13. 2015
 * Description: Creates a FlightDOM that parses an Flight XML file
 * 				The class stores the information in a list of Flight object
 * 
 * 
 * Update: March, 29, 2015
 * 			Change the flightList
 * 			add constraints to the flightList:
 * 					ONLY flights with available seats could be added to the flightList
 */

package com.flight_ticket_search.Dao;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;





//import org.apache.tomcat.jni.Time;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.flight_ticket_search.Entity.Airplane;
import com.flight_ticket_search.Entity.Airport;
import com.flight_ticket_search.Entity.Date;
import com.flight_ticket_search.Entity.Flight;
import com.flight_ticket_search.Entity.Seat;
import com.flight_ticket_search.Entity.Time;

/* package protection */
class FlightGetter {

	private List<Flight> flightList;
	private static Map<String, Flight> flightMap = new HashMap<String, Flight>();

	public FlightGetter() {
		flightList = new ArrayList<Flight>();
	}

	/* Interface: API: visible to same package only */
	public List<Flight> getFlights(String code, String date, boolean isDeparting) {
		parseXML(code, date, isDeparting);
		return flightList;
	}
	
	public Flight getFlight(String number) {
		Flight flight = flightMap.get(number);
		String departureCode = flight.getDepartureAirport().getCode();
		int day = flight.getDepartureTime().getDate().getDay();
		String date = "2015_05_" + (day < 10? "0":"") + day;
		parseXML(departureCode, date, true);
		return flightMap.get(number);
	}
	/* XML String parsing */
	/**
	 * FlightXMLString Parser: DOM -> Flight -> List<Flight>
	 * 
	 * @param code
	 * @param date
	 * @param isDeparting
	 *            specify the flight type as departing or arriving
	 */
	private void parseXML(String code, String date, boolean isDeparting) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		Document document = null;
		try {
			// DOM parser instance
			DocumentBuilder builder = builderFactory.newDocumentBuilder();

			InputSource is = new InputSource();
			if (isDeparting) {
				is.setCharacterStream(new StringReader(XMLConnector
						.getInstance().getDepartingFlights(code, date)));
			} else {
				is.setCharacterStream(new StringReader(XMLConnector
						.getInstance().getArrivingFlights(code, date)));
			}
			document = builder.parse(is);
			// get root element, which is <Flights>
			Element rootElement = document.getDocumentElement();

			// traverse child elements. retrieve all the <Flight>
			NodeList nodes = rootElement.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element flight = (Element) node;

					/* retrieve and get flightID and duration of Flight */
					String flightTime = flight.getAttribute("FlightTime");
					String number = flight.getAttribute("Number");

					/* retrieve and get Airplane info */
					String airplaneModel = flight.getAttribute("Airplane");
					Airplane airplane = AirplaneXMLParser.getInstance()
							.getAirplane(airplaneModel);// gaidong

					// child node of airport, <Departure> <Arrival> <Seat>
					NodeList flightChildren = flight.getChildNodes();

					/* --- data about Departure and Arrival and Seats --- */

					/* retrieve Departure and Arrival Airport and Time */
					String dcode = flightChildren.item(0).getChildNodes()
							.item(0).getTextContent();
					String acode = flightChildren.item(1).getChildNodes()
							.item(0).getTextContent();
					String departingTime = flightChildren.item(0)
							.getChildNodes().item(1).getTextContent(); // format of:2015 May 15 10:22
					String arrivingTime = flightChildren.item(1)
							.getChildNodes().item(1).getTextContent();
					
					/* put info to Airport and Time entity */
					Airport departing = AirportXMLParser.getInstance()
							.getAirport(dcode);
					Airport arriving = AirportXMLParser.getInstance()
							.getAirport(acode); // gaidong

					/**
					 * Next put the time String from database to new Time() and new Date()
					 */
					int year = 2015;
					int month = 5;
					
					int d_day = (Character.toString(departingTime.substring(9, 11).charAt(0)).equals("0"))? 
								Integer.parseInt(Character.toString(departingTime.substring(9, 11).charAt(1))): 
									Integer.parseInt(departingTime.substring(9, 11));
		
					int d_hour = (Character.toString(departingTime.substring(12, 14).charAt(0)).equals("0"))? 
							Integer.parseInt(Character.toString(departingTime.substring(12, 14).charAt(1))): 
								Integer.parseInt(departingTime.substring(12, 14));
							
					int d_minute = (Character.toString(departingTime.substring(15, 17).charAt(0)).equals("0"))? 
							Integer.parseInt(Character.toString(departingTime.substring(15, 17).charAt(1))): 
								Integer.parseInt(departingTime.substring(15, 17));
							
					int a_day = (Character.toString(arrivingTime.substring(9, 11).charAt(0)).equals("0"))? 
							Integer.parseInt(Character.toString(arrivingTime.substring(9, 11).charAt(1))): 
								Integer.parseInt(arrivingTime.substring(9, 11));
		
					int a_hour = (Character.toString(arrivingTime.substring(12, 14).charAt(0)).equals("0"))? 
							Integer.parseInt(Character.toString(arrivingTime.substring(12, 14).charAt(1))): 
								Integer.parseInt(arrivingTime.substring(12, 14));
							
					int a_minute = (Character.toString(arrivingTime.substring(15, 17).charAt(0)).equals("0"))? 
							Integer.parseInt(Character.toString(arrivingTime.substring(15, 17).charAt(1))): 
								Integer.parseInt(arrivingTime.substring(15, 17));
					
					Date d_date = new Date(year, month, d_day);
					Date a_date = new Date(year, month, a_day);
					
 					Time dTime = new Time(d_hour, d_minute, d_date);
					Time aTime = new Time(a_hour, a_minute, a_date);
					
					

					/* retrieve Seats Info */
					String hPrice = ((Element) flightChildren.item(2)
							.getChildNodes().item(0)).getAttribute("Price");
					String lPrice = ((Element) flightChildren.item(2)
							.getChildNodes().item(1)).getAttribute("Price");
					String firstClassSeats = flightChildren.item(2)
							.getChildNodes().item(0).getTextContent();
					String coachSeats = flightChildren.item(2).getChildNodes()
							.item(1).getTextContent();
					/*
					 * to convert the String format of currency to double
					 * "$1,200.00" -> "1200.00" first replace the "$" symbol and
					 * then replace "," with "" to validate the parse for more
					 * accuracy requirement change Double to BigDecimal
					 */
					String h = hPrice.substring(1).replace(",", "");
					String l = lPrice.substring(1).replace(",", "");
					Seat seat = new Seat(Integer.parseInt(firstClassSeats),
							Integer.parseInt(coachSeats),
							Double.parseDouble(h), Double.parseDouble(l));

					
					// ONLY IF: seats are AVAILABLE then put them into list
					if (airplane.getCoachSeats() - seat.getCoachSeats() > 0) {
//							coach ticket
						flightList.add(new Flight(number, Integer
								.parseInt(flightTime), airplane, departing,
								arriving, dTime, aTime, seat, "Coach", Double.parseDouble(l)));
						
					} 
					
					if (airplane.getFirstClassSeats() - seat.getFirstClassSeats() > 0) {
						//   firstclass ticket
						flightList.add(new Flight(number, Integer
								.parseInt(flightTime), airplane, departing,
								arriving, dTime, aTime, seat, "FirstClass", Double.parseDouble(h)));
						
					}
					
					// map number to flight for later's recheck database when purchasing
					flightMap.put(number, new Flight(number, Integer
							.parseInt(flightTime), airplane, departing,
							arriving, dTime, aTime, seat, null, 0d));
					
				}
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}



