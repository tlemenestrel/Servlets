// The package of this project

package com.sdzee.servlets;

// Importing the classes for the SQL Connection and the statement

import java.sql.*;

// Importing the package to take care of the JSON object for the constructor

import org.json.JSONException;
import org.json.JSONObject;

public class user {
	
	// Variables for the user constructor
	
	public String userPassword;
	public String userEmail;
	public String type;
	public String update;
	
	//Constructor for the user object
	
	user (JSONObject request) throws JSONException {
		
		this.userPassword  = request.getString("password");
		this.userEmail = request.getString("email");
		this.type = request.getString("type");
		this.update = request.getString("update");
		
	}
	// Method to call when the user logs in
	
	public static void _userLog (String password,String email,String type,String update) {
		
		try {
			
			// Variables for the connection
			
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://LocalHost:3306/dbuser?useSSL=false";
			String username = "root";
			String dbPassword = "Carrera-1";
			
			//Establishing the connection
			
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username, dbPassword);
			System.out.println("Database connected");			
			
			//Strings for the SQL statements
			
			String checkExistenceSQL = "SELECT CASE WHEN EXISTS ( SELECT *\n" + 
					"    FROM users WHERE UserID = 20070022) THEN CAST(1 AS BIT ELSE CAST(0 AS BIT) END";
			
		    String saveUserSQL = "INSERT INTO users (password, email, type)" +
		            "VALUES (?, ?, ?)";
		    
		    String updateInformationSQL = "";
		    
		    // Statement to see if the user exists
		    
		    PreparedStatement checkingStatement = conn.prepareStatement(checkExistenceSQL);
		    
		    checkingStatement.setString(1, password);
		    checkingStatement.setString(2, email);
		    checkingStatement.setString(3, type);
		    checkingStatement.executeUpdate();
		    
		    // Boolean that indicates if the user exists or not
		    
		    boolean userExistence;
		    
		    //Adding the  variables  to the statement and executing it

		    if (((type.contains("login")) && (update.contains("yes")))) {
		    	
		    	// Statement to add the user's password and email to the database
		    	
		    	PreparedStatement updateStatement = conn.prepareStatement(updateInformationSQL);
		    	updateStatement.setString(1, password);
		    	updateStatement.setString(2, email);
		    	updateStatement.setString(3, type);
		    	updateStatement.executeUpdate(); 
			    
			    //Closing the connection
			    
			    conn.close(); 
		    
		    }
		    
		    // If the user is not in the database and wants to register
		    
		    else if (type.contains("save")) {
		    	
		    	// Statement to add the user's password and email to the database
		    	
		    	PreparedStatement saveStatement = conn.prepareStatement(saveUserSQL);
		    	saveStatement.setString(1, password);
		    	saveStatement.setString(2, email);
		    	saveStatement.setString(3, type);
		    	saveStatement.executeUpdate(); 
			    
			    //Closing the connection
			    
			    conn.close(); 
		    }
		}
		
		catch (Exception e) {
			
			System.out.println(e);
			
		}
	}
	
		private static void _userCheck (String password,String email,String type) {
			
			
		}
		
		
		
		public static void _print (String user,String password,String type) {

			System.out.format("[%s]\t%s\t%s", user, password, type);
			
		}
}
