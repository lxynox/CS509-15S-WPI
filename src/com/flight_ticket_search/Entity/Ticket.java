/**
 * 
 */
package com.flight_ticket_search.Entity;

import java.util.List;


/**
 * @author lxybi_000
 *
 */
public class Ticket {

	private List<Flight> flightList;
	private double totalPrice;
	private	Time duration;
	private Time takeOffTime; // GMT time
	private Time landingTime; // GMT time
	private Time layover;
	private Time totalLeg;
	private String stopType;
	private String seatType;
	private String departureCity;
	private String arrivalCity;


	/**
	 * @return the flightList
	 */
	public List<Flight> getFlightList() {
		return flightList;
	}
	/**
	 * @param flightList the flightList to set
	 */
	public void setFlightList(List<Flight> flightList) {
		this.flightList = flightList;
	}
	/**
	 * @return the returningFlights
	 */
	
	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	/**
	 * @return the totalPrice
	 */
	public double getTotalPrice() {
		return totalPrice;
	}
	/**
	 * @return the duration
	 */
	public Time getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Time duration) {
		this.duration = duration;
	}
	/**
	 * @return the takeOffTime
	 */
	public Time getTakeOffTime() {
		return takeOffTime;
	}
	/**
	 * @param takeOffTime the takeOffTime to set
	 */
	public void setTakeOffTime(Time takeOffTime) {
		this.takeOffTime = takeOffTime;
	}
	/**
	 * @return the landingTime
	 */
	public Time getLandingTime() {
		return landingTime;
	}
	/**
	 * @param landingTime the landingTime to set
	 */
	public void setLandingTime(Time landingTime) {
		this.landingTime = landingTime;
	}
	/**
	 * @return the layover
	 */
	public Time getLayover() {
		return layover;
	}
	/**
	 * @param layover the layover to set
	 */
	public void setLayover(Time layover) {
		this.layover = layover;
	}
	/**
	 * @return the totalLeg
	 */
	public Time getTotalLeg() {
		return totalLeg;
	}
	/**
	 * @param totalLeg the totalLeg to set
	 */
	public void setTotalLeg(Time totalLeg) {
		this.totalLeg = totalLeg;
	}
	/**
	 * @return the stopType
	 */
	public String getStopType() {
		return stopType;
	}
	/**
	 * @param stopType the stopType to set
	 */
	public void setStopType(String stopType) {
		this.stopType = stopType;
	}
	/**
	 * @return the seatType
	 */
	public String getSeatType() {
		return seatType;
	}
	/**
	 * @param seatType the seatType to set
	 */
	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}
	
	/**
	 * @return the departureCity
	 */
	public String getDepartureCity() {
		return departureCity;
	}
	/**
	 * @param departureCity the departureCity to set
	 */
	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}
	/**
	 * @return the arrivalCity
	 */
	public String getArrivalCity() {
		return arrivalCity;
	}
	/**
	 * @param arrivalCity the arrivalCity to set
	 */
	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Ticket [flightList=" + flightList + ", totalPrice="
				+ totalPrice + ", duration=" + duration + ", takeOffTime="
				+ takeOffTime + ", landingTime=" + landingTime + ", layover="
				+ layover + ", totalLeg=" + totalLeg + ", stopType=" + stopType
				+ ", seatType=" + seatType + ", departureCity=" + departureCity
				+ ", arrivalCity=" + arrivalCity + "]";
	}
	
	
}
