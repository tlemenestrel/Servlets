// The package of this project

package com.sdzee.servlets;

// Importing the classes for the SQL Connection and the statement

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

// Importing the package to take care of the JSON object for the constructor

import org.json.JSONException;
import org.json.JSONObject;

public class Logger {
	
		 // Declaration of the variables of the constructor 
		 public String level;
		 public String date;
		 public String message;
		 public String method;
		 
		// Constructor
		 
		Logger (JSONObject request) throws JSONException {
			 
			//  Adding values to the variables by reading the JSON POST request
			
			this.level  = request.getString("level");
			this.message = request.getString("message");
			this.method  = request.getString("method");
			this.date  = request.getString("date");
			  		
		} 
	
		// Void to insert data into a table 
		
		public static void _log (String level,String method,String message, String date) {
			
			try {
				
				// Variables for the connection
				
				String driver = "com.mysql.jdbc.Driver";
				String url = "jdbc:mysql://LocalHost:3306/dbuser?useSSL=false";
				String username = "root";
				String databasePassword = "Carrera-1";
				
				//Establishing the connection
				
				Class.forName(driver);
				Connection conn = DriverManager.getConnection(url, username, databasePassword);
				System.out.println("Database connected");

				//String for the statement
				
			    String sql = "INSERT INTO logs (date, method, level, message)" +
			            "VALUES (?, ?, ?, ?)";
			    
			    //Adding the  variables  to the statement and executing it
			    
			    PreparedStatement preparedStatement = conn.prepareStatement(sql);
			    preparedStatement.setString(1, date);
			    preparedStatement.setString(2, method);
			    preparedStatement.setString(3, level);
			    preparedStatement.setString(4, message);
			    preparedStatement.executeUpdate(); 
			    
			    //Closing the connection
			    
			    conn.close(); 
			     
			}
			
			//Catching the exception
			
			catch (Exception e) {
				
				System.out.println("Error with the method _log in the logger class");
				
			}
			
		}
		
		// Method to print in the console when someone logs in
		
		public static void _print (String level,String method,String message, String date) {

			System.out.format("[%s]\t%s\t%s", level, method, message, date);
			
		}
		
		//Method to call when the log is done correctly and that will log the data of the log in the database
		
		public static void debug (String level,String method,String message, String date) {
			
			Logger._print(level, method, message, date);
			Logger._log(level, method, message, date);
			
		}
		
		//Method to call when the log is not done correctly and that will log the data of the log in the database

		public static void error (String level,String method,String message, String date) {
			
			Logger._print(level, method, message, date);
			Logger._log(level, method, message, date);
		}
}
