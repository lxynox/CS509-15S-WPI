/**
 * @author lxybi_000
 * Description: Entity to store/pass Flight information
 */

package com.flight_ticket_search.Entity;

/**
 * @author lxynox
 *
 */
public class Flight implements Cloneable {
	
	private String number;
	private int duration;
	private Airplane airplane;
	private Airport departing;
	private Airport arriving;
	private Time dTime;
	private Time aTime;
	private Seat seat;

	private String seatType;
	private double price;
	
	public Flight(String number, int duration, Airplane airplane, Airport departing, Airport arriving, 
			Time dTime, Time aTime, Seat seat, String seatType, double price) {
		this.number = number;
		this.duration = duration;
		this.airplane = airplane;
		this.departing = departing;
		this.arriving = arriving;
		this.dTime = dTime;
		this.aTime = aTime;
		this.seat = seat;
		this.seatType = seatType;
		this.price = price;
	}
	
	
	/** getter && setter
	 * 
	 */
	/**
	 * get the flight number 
	 * @return the flight number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * set the flight number
	 * @param number flight number
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * get the airplane of this flight
	 * @return the airplane of this flight
	 */
	public Airplane getAirplane() {
		return airplane;
	}
	/**
	 * set the airplane of this flight
	 * @param airplane the airplane of this flight
	 */
	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}
	/**
	 * get the departure time of this flight 
	 * @return the departure time  of this flight
	 */
	public Time getDepartureTime() {
		return dTime;
	}
	/**
	 * set the departure time of this flight
	 * @param time departure time of this flight
	 */
	public void setDepartureTime(Time time) {
		dTime = time;
	}
	/**
	 * get the arrival time of this flight
	 * @return the arrival time of this flight
	 */
	public Time getArrivalTime() {
		return aTime;
	}
	/**
	 * set the arrival time of this flight
	 * @param time  the arrival time of this flight
	 */
	public void setArrivalTime(Time time) {
		aTime = time;
	}
	/**
	 * get the departure airport of this flight
	 * @return the departure airport of this flight
	 */
	public Airport getDepartureAirport() {
		return departing;
	}
	/**
	 * set the departure airport of this flight
	 * @param airport the departure airport
	 */
	public void setDepartureAirport(Airport airport) {
		departing = airport;
	}
	/**
	 * get the arrival airport of this flight
	 * @return the arrival airport of this flight
	 */
	public Airport getArrivalAirport() {
		return arriving;
	}
	/**
	 * set the arrival airport of this flight
	 * @param airport the arrival airport of this flight
	 */
	public void setArrivalAirport(Airport airport) {
		arriving = airport;
	}
	/**
	 * set the seat of this flight
	 * @param seat the seat of this flight
	 */
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	/**
	 * get the seat of this flight
	 * @return the seat of this flight
	 */
	public Seat getSeat() {
		return seat;
	}
	/**
	 * set the duration of this flight
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	/**
	 * get the duration of this flight
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	/**
	 * set the seat type of this flight
	 * @param type seat type
	 */
	public void setSeatType(String type) {
		this.seatType = type;
	}
	/**
	 * get the seat type of this flight
	 * @return the seat type
	 */
	public String getSeatType() {
		return seatType;
	}
	/**
	 * get the price of this flight
	 * @return the price of this flight
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * set the price of this flight
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Flight [number=" + number + ", duration=" + duration
				+ ", airplane=" + airplane + ", departing=" + departing
				+ ", arriving=" + arriving + ", dTime=" + dTime + ", aTime="
				+ aTime + ", seat=" + seat + "\n" + ", seatType=" + seatType
				+ ", price=" + price + "]" + "\n";
	}

}

