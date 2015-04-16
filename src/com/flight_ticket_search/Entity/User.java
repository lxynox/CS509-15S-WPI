/**
 * 
 */
package com.flight_ticket_search.Entity;

/**
 * @author lxybi_000
 * Date: April, 10, 2015
 *
 */
public class User {
	
	private String userID;
	private String firstName;
	private String lastName;
	
	public User(String string, String string2, String string3) {
		// TODO Auto-generated constructor stub
		userID = string;
		firstName = string2;
		lastName = string3;
	}
	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userID=" + userID + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}
	
	
}
