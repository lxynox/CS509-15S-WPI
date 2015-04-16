/** cs509 team: team07
 * Date: March 13. 2015
 * Description: Creates a AirportDOM that parses an Airport XML file
 * 				The class stores the information in a list of Airport objects
 * 				and builds a code-Airport map
 * 
 * Significant Change:
 * Date: March, 20, 2015 
 * Description: Change AirportXMLParser && AirplaneXMLParser to 
 * 				Singleton Design Design Pattern 
 */

package com.flight_ticket_search.Dao;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.flight_ticket_search.Entity.Airport;
import com.flight_ticket_search.Util.TimeAdapter;

import java.io.IOException;

/* package protection */
class AirportXMLParser {

	/**
	 * Singleton Design Pattern
	 */
	private static AirportXMLParser instance = null;
	private Map<String, Airport> airportMap;
	private List<Airport> airportList;

	/* protected constructor */
	protected AirportXMLParser() {
		airportMap = new HashMap<String, Airport>();
		airportList = new ArrayList<Airport>();
		parseXML();
	}

	public static AirportXMLParser getInstance() {
		if (instance == null) {
			instance = new AirportXMLParser();
		}
		return instance;
	}

	/**
	 * XMLParser converts xmlString -> DOM object -> Airport -> airportList.
	 */
	/* Interface: API: visible to same package only */
	public Airport getAirport(String code) {
		return airportMap.get(code);
	}

	public List<Airport> getAirports() {
		return airportList;
	}

	/* private and protected methods */
	private void parseXML() {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		Document document = null;
		try {
			// DOM parser instance
			DocumentBuilder builder = builderFactory.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(XMLConnector.getInstance()
					.getAirports()));

			document = builder.parse(is);
			// get root element, which is <Airports>
			Element rootElement = document.getDocumentElement();

			// traverse child elements. retrieve all the <Airport>
			NodeList nodes = rootElement.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element airplane = (Element) node;

					// process child element
					String code = airplane.getAttribute("Code");
					String name = airplane.getAttribute("Name");

					// child node of airport
					NodeList airplaneChildren = airplane.getChildNodes();

					// value of longitude & latitude
					String longitude = airplaneChildren.item(1)
							.getTextContent();
					double longt = Double.parseDouble(longitude);
					String latitude = airplaneChildren.item(0).getTextContent();
					double lat = Double.parseDouble(latitude);
					
					Airport airport = new Airport(code, name, longt, lat, 
							new TimeAdapter().getTimezoneOffset(lat, longt)); 
					airportMap.put(code, airport);
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
