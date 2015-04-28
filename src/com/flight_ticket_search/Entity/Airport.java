/**
 * @author lxybi_000
 * Description: Entity to store/pass Airport information
 */

package com.flight_ticket_search.Entity;

public class Airport {
	
	private String code;
	private String name;
	private double latitude;
	private double longitude;

	private int offset;

	
	/* public constructor */
	public Airport(String code, String name, double longitude, double latitude, int offset) {
		this.code = code;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.setOffset(offset);
	}
	
	
	 /* getter && setter method */
	/**
	 * set the code of airport 
	 * @param code 3 digits airport code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * get the  code of airport
	 * @param name airport full name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * set the latitude of the airport
	 * @param latitude the latitude of the airport
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * set the longitude of the airport
	 * @param longitude the longitude of the airport
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * get 3 digits airport code
	 * @return 3 digits airport code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * get the full name of airport 
	 * @return full name of the airport
	 */
	public String getName() {
		return name;
	}

	/**
	 * get the latitude of airport
	 * @return the latitude of airport
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * get the longitude of airport
	 * @return the longitude of airport
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * get the offset of the airport
	 * @return the offset the offset of local time to GMT time
	 */
	public int getOffset() {
		return offset;
	}


	/**
	 * set the offset of the airport
	 * @param offset the offset of local time to GMT time
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Airport [code=" + code + ", name=" + name + ", latitude="
				+ latitude + ", longitude=" + longitude + ", offset=" + offset
				+ "]";
	}
	
	
	
	
	
}
