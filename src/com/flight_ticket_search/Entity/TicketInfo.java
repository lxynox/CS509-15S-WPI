/**
 * 
 */
package com.flight_ticket_search.Entity;

/**
 * @author lxybi_000
 * Date: April. 10. 2015
 * Description: used to store info and retrieve at the front-end 
 * for check order Use Case
 */
public class TicketInfo {
	private String seat;
	private String number;
	private String departureCity;
	private String arrivalCity;
	private String departureTime;
	private String arrivalTime;
	
	public TicketInfo (String seat, String number, String departureCity,
				 String arrivalCity, String departureTime, String arrivalTime) {
		this.seat = seat;
		this.number = number;
		this.departureCity = departureCity;
		this.arrivalCity = arrivalCity;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		
	}

	/**
	 * @return the seat
	 */
	public String getSeat() {
		return seat;
	}

	/**
	 * @param seat the seat to set
	 */
	public void setSeat(String seat) {
		this.seat = seat;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
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

	/**
	 * @return the departureTime
	 */
	public String getDepartureTime() {
		return departureTime;
	}

	/**
	 * @param departureTime the departureTime to set
	 */
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * @return the arrivalTime
	 */
	public String getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * @param arrivalTime the arrivalTime to set
	 */
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TicketInfo [seat=" + seat + ", number="
				+ number + ", departureCity=" + departureCity
				+ ", arrivalCity=" + arrivalCity + ", departureTime="
				+ departureTime + ", arrivalTime=" + arrivalTime + "]";
	}
	
	
	
}
