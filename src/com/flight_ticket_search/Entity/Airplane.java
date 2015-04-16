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
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String getManufacturer() {
		return manufacturer; 
	}
	
	public void setFirstClassSeats(int seat) {
		firstClassSeats = seat;
	}
	
	public int getFirstClassSeats() {
		return firstClassSeats;
	}
	
	public void setCoachSeats(int seat) {
		coachSeats = seat;
	}
	
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
