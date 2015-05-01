/** cs509 team: team07
 * Date: March 13. 2015
 * Description: Role as database connector, directly interacts with the database 
 * 				get, remove, update database. 
 */

package com.flight_ticket_search.Dao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.flight_ticket_search.Util.QueryFactory;

class XMLConnector {

	/* Singleton Design Pattern */
	private final String mUrlBase = "http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem?";
	private boolean isLocked = false;
	private static XMLConnector instance = null;

	/* private constructor */
	protected XMLConnector() {
		// only to prevent instantiation
	}

	/* Method to get the only instance from this class */
	public static XMLConnector getInstance() {
		if (instance == null) {
			instance = new XMLConnector();
		}
		return instance;
	}

	/* Interface: API: visible to same package only */
	/**
	 * Http GET calls - Read data functions include: retrieving 1. Airport 2.
	 * Airplane 3. Departing/Arriving Flight and reset database
	 */
	// created for test of purchase and lock/unlock Database
	public String resetDB() {
		return getXMLString(QueryFactory.resetDB("Team07"));
	}

	public String getAirports() {
		return getXMLString(QueryFactory.getAirports("Team07"));
	}

	public String getAirplanes() {
		return getXMLString(QueryFactory.getAirplanes("Team07"));
	}

	public String getDepartingFlights(String code, String date) {
		return getXMLString(QueryFactory.getDepartingFlights("Team07", code,
				date));
	}

	public String getArrivingFlights(String code, String date) {
		return getXMLString(QueryFactory.getArrivingFlights("Team07", code,
				date));
	}

	/**
	 * Http POST calls - Write data functions include: 1. lock/unlock database
	 * 2. reserve flight for customer
	 */
	public boolean lock() {
		System.out.println("Locking the database .......");
		if (writeToDB(QueryFactory.lockDB("Team07"))) {
			isLocked = true;
			System.out.println("Success: system is locked by team07!");
			// System.out.println("print whether the system is locked or not: "
			// + isLocked);
			return isLocked;
		}

		System.out.println("Failure: system is already locked by others!");
		return isLocked;
	}

	public boolean unlock() {
		System.out.println("Unlocking the database ......");
		if (isLocked) {
			if (writeToDB(QueryFactory.unlockDB("Team07"))) {
				System.out.println("Success: system is unlocked by team07!");
				isLocked = false;
				return true;
			}
		}

		System.out.println("Invalid call: system is unlocked!");
		return false;
	}

	public boolean reserveFlight(String xmlFlight) {
		if (isLocked) {
			if (writeToDB(QueryFactory.reserve("Team07", xmlFlight))) {
				System.out.println("ticket is successfully purchased!");
				unlock();
				return true;
			} else {
				System.out
						.println("Purchase Failure: failed to reserve flight!");
				return false;
			}

		}
		System.out.println("Unlock Failure: fail to reserve flight!");
		return false;
	}

	/* Http GET method to read data */
	protected String getXMLString(String query) {
		URL url;
		BufferedReader bReader;
		HttpURLConnection conn;
		String line;
		StringBuffer result = new StringBuffer();
		try {
			url = new URL(mUrlBase + query);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "Team07");

			int responseCode = conn.getResponseCode();
//			System.out.println(responseCode);
			if (responseCode >= 200 && responseCode <= 299) {
				InputStream inputStream = conn.getInputStream();
				String encoding = conn.getContentEncoding();
				encoding = (encoding == null ? "URF-8" : encoding);

				bReader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = bReader.readLine()) != null) {
					result.append(line);
				}
				bReader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	/* Http POST to write data to database */
	protected boolean writeToDB(String params) {
		URL url;
		HttpURLConnection connection;

		try {
			url = new URL(mUrlBase);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");

			connection.setDoOutput(true);
			connection.setDoInput(true);

			DataOutputStream writer = new DataOutputStream(
					connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();

			int responseCode = connection.getResponseCode();
			// System.out.println("\nSending 'POST' to database");
			System.out.println("Responding Code: " + responseCode);

			if ((responseCode >= 200) && (responseCode <= 299)) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				String line;
				StringBuffer response = new StringBuffer();

				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				in.close();

				// System.out.println(response.toString());

				return true;

			} else if (responseCode == 304) {
				System.out
						.println("Unsuccessful ticket purchase, the seat is already full!");
				return false;
			}

		} catch (IOException exp) {
			exp.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}


}
