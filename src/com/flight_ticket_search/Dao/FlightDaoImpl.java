/**
 * @author lxybi_000
 * Date: March, 20, 2015
 * Description: encapsulates all back-end data and interact 
 * 				with the service layer (Control-Layer)
 *
 */
package com.flight_ticket_search.Dao;

import java.util.List;

import com.flight_ticket_search.Entity.Airplane;
import com.flight_ticket_search.Entity.Airport;
import com.flight_ticket_search.Entity.Flight;

public class FlightDaoImpl implements FlightDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flight_ticket_search.Model.FlightDao#selectFlights(java.lang.String,
	 * java.lang.String, boolean)
	 */
	@Override
	public List<Flight> selectFlights(String airportCode, String date,
			boolean isDeparting) {
		// TODO Auto-generated method stub
		return new FlightGetter().getFlights(airportCode, date, isDeparting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flight_ticket_search.Model.FlightDao#removeFlight(java.lang.String)
	 */
	@Override
	public boolean removeFlights(List<List<String>> reservedFlights) {
		// TODO Auto-generated method stub
		// retrieve the target xmlFlight first
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<Flights>");
		for (List<String> flight : reservedFlights) {
			sBuilder.append("<Flight number=\"" + flight.get(0)
					+ "\" seating=\"" + flight.get(1) + "\" />");
		}
		sBuilder.append("</Flights>");
		String xmlFlight = sBuilder.toString();
		// System.out.println(xmlFlight);

		// pass xmlFlight to XMLLoader as param to remove flight
		return XMLConnector.getInstance().reserveFlight(xmlFlight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flight_ticket_search.Dao.FlightDao#getAirport(java.lang.String)
	 */
	@Override
	public Airport getAirport(String airportCode) {
		// TODO Auto-generated method stub
		return AirportXMLParser.getInstance().getAirport(airportCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flight_ticket_search.Dao.FlightDao#getAirplane(java.lang.String)
	 */
	@Override
	public Airplane getAirplane(String model) {
		// TODO Auto-generated method stub
		return AirplaneXMLParser.getInstance().getAirplane(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flight_ticket_search.Dao.FlightDao#lockDB()
	 */
	@Override
	public boolean lockDB() {
		// TODO Auto-generated method stub
		return XMLConnector.getInstance().lock();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flight_ticket_search.Dao.FlightDao#unlockDB()
	 */
	@Override
	public boolean unlockDB() {
		// TODO Auto-generated method stub
		return XMLConnector.getInstance().unlock();
	}

	/* (non-Javadoc)
	 * @see com.flight_ticket_search.Dao.FlightDao#selectFlight(java.lang.String)
	 */
	@Override
	public Flight selectFlight(String number) {
		// TODO Auto-generated method stub
		return FlightGetter.getFlight(number);
	}

}
