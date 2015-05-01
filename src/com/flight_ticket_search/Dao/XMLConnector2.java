/**
 * 
 */
package com.flight_ticket_search.Dao;

import com.flight_ticket_search.Util.QueryFactory;

/**
 * @author lxynox
 *
 */
public class XMLConnector2 extends XMLConnector {
	protected static XMLConnector2 instance = null;
	
	public static XMLConnector2 getInstance() {
		if (instance == null) {
			instance = new XMLConnector2();
		}
		return instance;
	}
	
	public boolean lock() {
		System.out.println("Locking the database .......");
		if (writeToDB(QueryFactory.lockDB("Team09"))) {
			System.out.println("Success: system is locked by team09!");
			// System.out.println("print whether the system is locked or not: "
			// + isLocked);
			return true;
		}

		System.out.println("Failure: system is already locked by others!");
		return false;
	}
	
	public static void main (String[] args) {
		System.out.println(XMLConnector2.getInstance().lock());
	}
}
