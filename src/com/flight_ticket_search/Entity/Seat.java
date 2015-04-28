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
	/**
	 * get the number of first class seats
	 * @return the number of the first class seats
	 */
	public int getFirstClassSeats() {
		return firstClassSeats;
	}
	/**
	 * get the number of coach seats
	 * @return the number of coach seats
	 */
	public int getCoachSeats() {
		return coachSeats;
	}
	/**
	 * get the price of first class seats
	 * @return the price of first class seats
	 */
	public double getFirstClassPrice() {
		return hPrice;
	}
	/**
	 * get the price of the coach class seats
	 * @return the price of coach seats
	 */
	public double getCoachPrice() {
		return lPrice;
	}
	/**
	 * set the number of the first class seats
	 * @param seats the number of first class seats
	 */
	public void setFirstClassSeats(int seats) {
		firstClassSeats = seats;
	}
	/**
	 * set the number of coach seats 
	 * @param seats the number of coach seats
	 */
	public void setCoachSeats(int seats) {
		coachSeats = seats;
	}
	/**
	 * set the price of first class seats
	 * @param price price of first class seats
	 */
	public void setFirstClassPrice(double price) {
		hPrice = price;
	}
	/**
	 * set the price of coach seats
	 * @param price the price of coach seats
	 */
	public void setCoachPrice(double price) {
		lPrice = price;
	}
	
	
	@Override
	public String toString() {
		return "first class seats\t purchased: " + firstClassSeats + ", cost: $" + hPrice 
				+ "\ncoach seats\t purchased: " + coachSeats + ", cost: $" + lPrice;
	}
	
}
