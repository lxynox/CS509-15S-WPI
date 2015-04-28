/**
 * @author lxybi_000
 * Description: Entity to store/pass Time information
 */

package com.flight_ticket_search.Entity;


public class Time {
	
	private int hour;
	private int minute;
	private Date date;
	
	public Time(int hour, int minute, Date date) {
		this.hour = hour;
		this.minute = minute;
		this.date = date;
	}
	
	public Time(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}
	
	public Time() {}
	/*  getter && setter of Time */
	
	/**
	 * get the hour of the time
	 * @return the hour of the time
	 */
	public int getHour() {
		return hour;
	}
	
	/**
	 * set the hour of the time
	 * @param hour of the time
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}
	/**
	 * get the minute of the time 
	 * @return the minute of the time
	 */
	public int getMinute() {
		return minute;
	}
	/**
	 * set the minute of the time 
	 * @param minute of the time
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}
	/**
	 * set the date of the time
	 * @param date of the time
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * get the date of the time
	 * @return the date of the time
	 */
	public Date getDate() {
		return date;
	}
	
	
	@Override 
	public String toString() {
		return "Time\thour:" + hour 
				+ ", minute:" + minute 
				+ ", date:" + date;
				//+ calendar.getTime().toString();
	}
	

}

