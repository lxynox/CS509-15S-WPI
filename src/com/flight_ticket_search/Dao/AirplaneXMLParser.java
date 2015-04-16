/** cs509 team: team07
 * Date: March 13. 2015
 * Description: Creates a AirplaneDOM that parses an Airplane XML file
 * 				The class stores the information in a list of airplane objects
 * 				and builds a model-airplane map
 */

package com.flight_ticket_search.Dao;

import java.io.IOException;
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

import com.flight_ticket_search.Entity.Airplane;

/* package protection */
class AirplaneXMLParser {

	/**
	 * Singleton Design Pattern
	 */
	private static AirplaneXMLParser instance = null;
	private List<Airplane> airplaneList;
	private Map<String, Airplane> airplaneMap;

	/* protected constructor */
	protected AirplaneXMLParser() {
		airplaneList = new ArrayList<Airplane>();
		airplaneMap = new HashMap<String, Airplane>();
		parseXML();
	}

	public static AirplaneXMLParser getInstance() {
		if (instance == null) {
			instance = new AirplaneXMLParser();
		}
		return instance;
	}

	/* Interface: API: visible to same package only */
	public Airplane getAirplane(String model) {
		return airplaneMap.get(model);
	}

	public List<Airplane> getAirplanes() {
		return airplaneList;
	}

	/* Private and protected methods */
	private void parseXML() {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		Document document = null;
		try {
			// DOM parser instance
			DocumentBuilder builder = builderFactory.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(XMLConnector.getInstance()
					.getAirplanes()));
			// parse an XML file into a DOM tree, in this case, change the
			// filepath to xml String
			// document = builder.parse(new File(filePath));
			document = builder.parse(is);
			// get root element, which is <Airports>
			Element rootElement = document.getDocumentElement();

			// traverse child elements. retrieve all the <Airport>
			NodeList nodes = rootElement.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					// process child element
					String model = element.getAttribute("Model");
					String manufacturer = element.getAttribute("Manufacturer");

					// child node of airport
					NodeList airplaneChildren = element.getChildNodes();

					// value of longitude & latitude
					String firstClassSeats = airplaneChildren.item(0)
							.getTextContent();
					String coachSeats = airplaneChildren.item(1)
							.getTextContent();

					Airplane airplane = new Airplane(model, manufacturer,
							Integer.parseInt(firstClassSeats),
							Integer.parseInt(coachSeats));
					airplaneMap.put(model, airplane);
					airplaneList.add(airplane);
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
