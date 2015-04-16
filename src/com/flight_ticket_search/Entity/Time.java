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
	
	
	public int getHour() {
		return hour;
	}
	
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
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

