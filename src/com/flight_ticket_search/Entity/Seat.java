/**
 * @author lxybi_000
 * Description: Entity to store/pass Seat information
 */

package com.flight_ticket_search.Entity;

public class Seat {
	private int firstClassSeats;
	private int coachSeats;
	private double lPrice;
	private double hPrice;
	
	public Seat(int firstClassSeats, int coachSeats, double hPrice, double lPrice) {
		this.firstClassSeats = firstClassSeats; 
		this.coachSeats = coachSeats; 
		this.hPrice = hPrice; 
		this.lPrice = lPrice; 
	}
	
	
	/* getter && setter */
	public int getFirstClassSeats() {
		return firstClassSeats;
	}
	
	public int getCoachSeats() {
		return coachSeats;
	}
	
	public double getFirstClassPrice() {
		return hPrice;
	}
	
	public double getCoachPrice() {
		return lPrice;
	}
	
	public void setFirstClassSeats(int seats) {
		firstClassSeats = seats;
	}
	
	public void setCoachSeats(int seats) {
		coachSeats = seats;
	}
	
	public void setFirstClassPrice(double price) {
		hPrice = price;
	}
	
	public void setCoachPrice(double price) {
		lPrice = price;
	}
	
	
	@Override
	public String toString() {
		return "first class seats\t purchased: " + firstClassSeats + ", cost: $" + hPrice 
				+ "\ncoach seats\t purchased: " + coachSeats + ", cost: $" + lPrice;
	}
	
}
