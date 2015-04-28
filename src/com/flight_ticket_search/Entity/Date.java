/**
 * 
 */
package com.flight_ticket_search.Entity;

/**
 * @author lxybi_000
 *
 */
public class Date {
	private int year;
	private int month;
	private int day;
	
	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/**
	 * get the year of the date
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * set the year of the date
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * get the month of the date
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * set the month of the date
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * get the day of the date
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * set the day of the date
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	
	@Override 
	public String toString() {
		return "Date\tyear:" + year 
				+ ", month:" + month 
				+ ", day:" + day;
	}
	
}
