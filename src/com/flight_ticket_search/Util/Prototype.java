/**
 * 
 */
package com.flight_ticket_search.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flight_ticket_search.Dao.FlightDaoImpl;
import com.flight_ticket_search.Entity.Flight;


/**
 * @author lxybi_000
 *
 */
public class Prototype {
	FlightDaoImpl flightDao = new FlightDaoImpl();
	
	/**
	 * @param args
	 */

	public static void main(String[] args) {
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		Prototype tester = new Prototype();
		
		// TODO Auto-generated method stub
		System.out.println("This is the prototype test of team07");
		
		for (int i=100; i>0; i--)	System.out.print("*");
		
		String dCode;
		String aCode;
		String dDate;
		
		try {
			while (true) {
				System.out.print("\nPlease enter your departing airport code(format: BOS/LAX/PIT):");
				String departingCode = bReader.readLine();
				dCode = departingCode;
				
				System.out.print("Next, please enter the returning airport code(format: BOS/LAX/PIT):");
				String arrivingCode = bReader.readLine();
				aCode = arrivingCode;
				
				System.out.println("Finally, specify the date below");
				System.out.print("Year(YYYY):");
				String year = bReader.readLine();
				System.out.print("Month(MM):");
				String month = bReader.readLine();
				System.out.print("Day(DD):");
				String day = bReader.readLine();
				String date = String.valueOf(year + "_" + month + "_" + day);
				dDate = date;
				
				System.out.println("Start searching ... " + "\nAll none stop flights are as follows:");
				
				System.out.println();
		
				List<Flight> flightList = tester.flightDao.selectFlights(departingCode, date, true);
				//System.out.println(flightList);
				for (int i=100; i>0; i--) System.out.print((i > 1)? "-":"\n");
				int j = 0;
				for (Flight flight: flightList) {
					if (flight.getArrivalAirport().getCode().equals(arrivingCode)) {
						System.out.println(flight);
						int firstClassRemain = flight.getAirplane().getFirstClassSeats() - flight.getSeat().getFirstClassSeats();
						int coachRemain = flight.getAirplane().getCoachSeats() - flight.getSeat().getCoachSeats();
					
						List<Integer> list = new ArrayList<Integer>();
						list.add(firstClassRemain);
						list.add(coachRemain);
						map.put(flight.getNumber(), list);
						System.out.println("number of firstClass remains: " + (firstClassRemain));
						System.out.println("number of coach remains: " + (coachRemain));
						for (int i=100; i>0; i--) System.out.print((i > 1)? "-":"\n");
						j++;
					}
				}
				
				if (j != 0) break;
				else System.out.println("No corresponding flight found, change your select condition, please");
			}
			
			for (int i=100; i>0; i--) 	System.out.print("*");


			System.out.println("\nThen he may want to reserve it, right?");
			System.out.println("\nFirst, system has to lock the database.");

			 tester.flightDao.unlockDB();
			tester.flightDao.lockDB();
			System.out.println("\nSecond, once the database is locked, we have to purchase the ticket.");
			
			System.out.println("purchase started!");
			System.out.println();
			System.out.print("Choose the flight number from above(format: 3/4 digits): ");
			String number = bReader.readLine();
			System.out.print("Choose the seat type(format: FirstClass/Coach): ");
			String seating = bReader.readLine();

			
			while (true) {
				System.out.print("Specify how many tickets you want to buy: ");
				int ticks = Integer.parseInt(bReader.readLine());
				
				List<String> flightString = new ArrayList<String>();
				
				/* assume the flight number is 2009, seating is Coach */
				int seatRemain;
				switch (seating) {
					case "FirstClass": seatRemain = map.get(number).get(0); break;
					case "Coach": seatRemain = map.get(number).get(1); break; 
					default: seatRemain = 0; break;
				}
				
				List<List<String>> flightStringList = null;
				if (ticks <= seatRemain) { 
					flightStringList = new ArrayList<List<String>>();
					for (; ticks > 0; ticks--) {
						flightString.add(number);
						flightString.add(seating);
						flightStringList.add(flightString);
					}
					
					tester.flightDao.removeFlights(flightStringList);
					break;
				
				} else {
					System.out.println("Change the number of your purchase, tickets not enough!");
				}
			}
			
			for (int i=100; i>0; i--)	System.out.print((i > 1)? "*": "\n");
			
			System.out.println("Lastly, let us check the available seats again!");
			List<Flight> flightList2 = tester.flightDao.selectFlights(dCode, dDate, true);
			for (Flight flight: flightList2) {
				if (flight.getArrivalAirport().getCode().equals(aCode)) {
					
					System.out.println(flight);
					int firstClassRemain = flight.getAirplane().getFirstClassSeats() - flight.getSeat().getFirstClassSeats();
					int coachRemain = flight.getAirplane().getCoachSeats() - flight.getSeat().getCoachSeats();
					System.out.println("number of firstClass remains: " + (firstClassRemain));
					System.out.println("number of coach remains: " + (coachRemain));
					for (int i=100; i>0; i--) System.out.print((i > 1)? "-":"\n");
				
				}
			}

			for (int i=100; i>0; i--)	System.out.print("*");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		

	}

}
