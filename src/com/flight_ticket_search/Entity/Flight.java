/**
 * @author lxybi_000
 * Description: Entity to store/pass Flight information
 */

package com.flight_ticket_search.Entity;

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
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public Airplane getAirplane() {
		return airplane;
	}
	
	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}
	
	public Time getDepartureTime() {
		return dTime;
	}
	
	public void setDepartureTime(Time time) {
		dTime = time;
	}
	
	public Time getArrivalTime() {
		return aTime;
	}
	
	public void setArrivalTime(Time time) {
		aTime = time;
	}
	
	public Airport getDepartureAirport() {
		return departing;
	}
	
	public void setDepartureAirport(Airport airport) {
		departing = airport;
	}
	
	public Airport getArrivalAirport() {
		return arriving;
	}
	
	public void setArrivalAirport(Airport airport) {
		arriving = airport;
	}
	
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	
	public Seat getSeat() {
		return seat;
	}
	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}


	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
	public void setSeatType(String type) {
		this.seatType = type;
	}
	
	public String getSeatType() {
		return seatType;
	}
	
	
	
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}


	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}


	/**
	 * clone method created for the flightIterator package
	 */
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
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
	
	/*@Override
	public String toString() {
		return "Flight number\t" + number + "\n" +
				airplane + "\n" + 
				"Departing " + departing + "\n" + 
				"Arriving " + arriving + "\n" +
				seat + "\n" +
				"firtclass\tremains: " + (airplane.getFirstClassSeats()-seat.getFirstClassSeats()) + "\n"+
				"coach\tremains: " + (airplane.getCoachSeats()-seat.getCoachSeats()) + "\n" +
				dTime + "\t" + aTime + "\n" + 
				duration + "\n";
//				d_time + "\t" + a_time + "\n";
	}*/

	
}

