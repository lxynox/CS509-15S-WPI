/**
 * @author lxybi_000
 * Description: Entity to store/pass Airplane information
 */

package com.flight_ticket_search.Entity;

public class Airplane {
	
	private int firstClassSeats, coachSeats;	
	private String model, manufacturer;

	public Airplane(String model, String manufacturer, int firstClassSeats, int coachSeats) {
		this.firstClassSeats = firstClassSeats;
		this.coachSeats = coachSeats;
		this.model = model;
		this.manufacturer = manufacturer;
	}
	
	
	 /* getter && setter */
	/**
	 * set the model of the airplane
	 * @param model
	 */
	public void setModel(String model) {
		this.model = model;
	}
	
	/**
	 * get the model of the airplane
	 * @return the model of airplane
	 */
	public String getModel() {
		return model;
	}
	
	/**
	 * set the manufacturer of the airplane
	 * @param manufacturer
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	/**
	 * get the manufacturer of the airplane
	 * @return the manufacturer of the airplane
	 */
	public String getManufacturer() {
		return manufacturer; 
	}
	
	/**
	 * set the number of first class seats of the airplane
	 * @param seat number of first class seats
	 */
	public void setFirstClassSeats(int seat) {
		firstClassSeats = seat;
	}
	
	/**
	 * get the number of first class seats of the airplane
	 * @return number of first class seats
	 */
	public int getFirstClassSeats() {
		return firstClassSeats;
	}
	
	/**
	 * set the number of coach seats of the airplane
	 * @param seat number of coach seats
	 */
	public void setCoachSeats(int seat) {
		coachSeats = seat;
	}
	
	/**
	 * get the number of coach seats of the airplane
	 * @return number of coach seats
	 */
	public int getCoachSeats() {
		return coachSeats;
	}
	
	@Override
	public String toString() {
		return "Airplane\tmodel: "+ model
				+ ", manufacturer: "+ manufacturer
				+ ", firstClassSeats: "+ firstClassSeats
				+ ", coachSeats: "+ coachSeats;
	}
}
