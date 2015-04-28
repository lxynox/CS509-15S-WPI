/**
 * @author: lxybi_000
 * Date: 20, March, 2015
 * Description: Interface used to provide access for communication
 * 				with outer packages and encapsulates all inner info 
 */
package com.flight_ticket_search.Dao;

import java.util.List;

import com.flight_ticket_search.Entity.Airplane;
import com.flight_ticket_search.Entity.Airport;
import com.flight_ticket_search.Entity.Flight;

public interface FlightDao {


	/**
	 * Return list of Flights retrieved from database
	 * 
	 * Retrieve list of Flights according to the airport code, departure/arrival
	 * date, and type of departure/arrival flight
	 * 
	 * @param airportCode code used to specify departure airport
	 * @param arrivalDate time used to specify departure/arrival date
	 * @param isDeparting specify the flight type departing or arriving
	 * @return a list of flights
	 */
	public List<Flight> selectFlights(String airportCode, String date,
			boolean isDeparting);

	/**
	 * Return specific Flight from the database
	 * Retrieve specific flight from the database to check the seat condition
	 * for the reservation service when the client determines to reserve a 
	 * ticket. precondition: User select specific type of tickets
	 * postcondition: All flight contained in the tickets are retrieved
	 * 
	 * @param number flight number of the flight also recognized as the key of the flight
	 * @return Flight corresponding to the given flight number
	 */
	public Flight selectFlight(String number);
	
	/**
	 * Return whether the Flights are successfully removed from database
	 * Remove flights from database using the reservedFlights, which is a 
	 * list of string representation of flightNumber and seating information.
	 * Return true indicates the purchase is succeed, false indicates failure. 
	 * 
	 * @param reservedFlights list of string list of [number, seating]
	 * @return whether the flight is successfully removed from DB
	 */
	public boolean removeFlights(List<List<String>> reservedFlights);

	/**
	 * Return Airport according to its code
	 * Using the airport code to retrieve the right airport 
	 * which could be used for the time conversion in the front-end
	 * 
	 * @param airportCode 3 digits standard airport code of the US airports 
	 * @return The airport that matches the airportCode
	 */
	public Airport getAirport(String airportCode);

	/**
	 * Return Airplane according to its model
	 * 
	 * @param model model name of the airplane, like "Boeing, Airbus" etc
	 * @return The airplane that matches the model
	 */
	public Airplane getAirplane(String model);

	/**
	 * Return whether the database is successfully locked
	 * Lock the database to avoid concurrency access. Precondition:
	 * No other teams are holding the lock of the database
	 * 
	 * @return whether DB is successfully locked
	 */
	public boolean lockDB();

	/**
	 * Unlock the database to continue system business
	 * precondition: system is holding the lock and purchase is 
	 * successfully done
	 * 
	 * @return whether DB is successfully unlocked
	 */
	public boolean unlockDB();
}
