/**
 * 
 */
package com.flight_ticket_search.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.flight_ticket_search.Entity.TicketInfo;
import com.flight_ticket_search.Entity.User;
import com.flight_ticket_search.Util.QueryFactory;

/**
 * @author lxybi_000
 *
 */


class SQLConnector {

	// JDBC driver name and database URL
	    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	    private static final String DB_URL = "jdbc:mysql://10.0.1.10/my_db?";
	    
//	    JDBC Credentials
		private static final String USER_NAME = "root";  
		private static final String PASSWORD = "root";
		
		private static SQLConnector instance = null;
		
		/* Singleton Design pattern */
		protected SQLConnector () {
//			only to prevent instantiation
		}
	 
		public static SQLConnector getInstance () {
			
			if (instance == null) {
				instance = new SQLConnector();
			}
			return instance;
		}
		
		/* retrieving data from database */
		private List<List<String>> dbGetter(String query) {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conn = null;
			List<List<String>> resultList = new ArrayList<List<String>>();
			
			try {
				Class.forName(JDBC_DRIVER).newInstance();
				conn =
						DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);    
				
//				 Do something with the Connection
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				
				// Now do something with the ResultSet ....
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnNumber = rsmd.getColumnCount();
				System.out.println(columnNumber);
				
				for(int i=1; i <= columnNumber; i++) {
					System.out.print(rsmd.getColumnLabel(i)+ (i < columnNumber?" ":"\n"));
				}
				
				while (rs.next()) {
				
					List<String> rowInfo = new ArrayList<String>();
					for(int i=1; i<= columnNumber; i++) {
						//System.out.print(rs.getString(i)+(i<columnNumber? " ":"\n"));
						rowInfo.add(rs.getString(i));
					}
					
					resultList.add(rowInfo);
					//System.out.println();
				}
				
				if(rs.isClosed()) {
					System.out.println("the cursor is now closed");
				}else {
					System.out.println("sorry, not yet!");
					rs.close();
				}
				// return resultList;
				
			} catch (SQLException ex) {
				// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			finally {
//		it is a good idea to release resources in a finally{} block in reverse-order of their creation
//					 if they are no-longer needed
				
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException sqlEx) { } // ignore
					
					rs = null;
				}
				
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException sqlEx) { } // ignore
					
					stmt = null;
				}
			}
			
			return resultList;
		}	
		
		
		/* modifying the database */
		 private void dbSetter(String query) {
			   Connection conn = null;
			   Statement stmt = null;
			   try{
			      //STEP 2: Register JDBC driver
			      Class.forName(JDBC_DRIVER);

			      //STEP 3: Open a connection
			      conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			      
			      //STEP 4: Execute a query
			      System.out.println("Inserting records into the table...");
			      stmt = conn.createStatement();
			      stmt.executeUpdate(query);
			      System.out.println("Inserted records into the table...");

			   } catch (SQLException se) {
			      //Handle errors for JDBC
			      se.printStackTrace();
			   } catch (Exception e) {
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   } 
			   finally {
			      //finally block used to close resources
			      try {
			         if(stmt!=null)
			            conn.close();
			      } catch (SQLException se) {
			      }// do nothing
			      try {
			         if(conn!=null)
			            conn.close();
			      } catch (SQLException se) {
			         se.printStackTrace();
			      }//end finally try
			   }//end try
			   System.out.println("Goodbye!");
		}//end main
		
		 
		 
		 
		public boolean addUser (User user) {
			String sql = QueryFactory.addUser(user);
			dbSetter(sql);
			return true;
		}
		 
		public boolean addTicket (TicketInfo ticketInfo) {
			String sql = QueryFactory.addTicket(ticketInfo);
			dbSetter(sql);
			return true;
		} 
		
		public boolean addOrder (String userID, String number) {
			String sql = QueryFactory.addOrder(userID, number);
			dbSetter(sql);
			return true;
		}
		
		public List<TicketInfo> getOrder (String userID) {
			
			String sql = QueryFactory.getOrder(userID);
			List<List<String>> ticketListList = dbGetter(sql);
			
			List<TicketInfo> ticketList = new ArrayList<TicketInfo>();
			for (List<String> tickList: ticketListList) {
				ticketList.add(new TicketInfo(tickList.get(0), tickList.get(1),
									tickList.get(2), tickList.get(3),
									tickList.get(4), tickList.get(5)));
			}
			
			return ticketList;
			
		}
		
		public boolean cancelOrder (String number) {
			String sql = QueryFactory.deleteOrder(number);
			dbSetter(sql);
			return true;
		}
		
		public User getUser (String userID) {
			String sql = QueryFactory.getUser(userID);
			List<List<String>> userInfo = dbGetter(sql);
			
			User user = null;
			for (List<String> userList: userInfo) {
				user = new User(userList.get(0), userList.get(1), userList.get(2));
			}
			
			return user;
			
		}

		
		public static void main(String[] args) {
			SQLConnector sqlConnector = new SQLConnector();
			
			System.out.println(sqlConnector.getOrder("lc"));
			
			System.out.println(sqlConnector.getUser("lxynox"));
			
		}
}
