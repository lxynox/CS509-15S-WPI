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
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}

	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}


	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}


	/**
	 * @param offset the offset to set
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
