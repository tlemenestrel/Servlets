// The package of this project

package com.sdzee.servlets;

// Importing the classes for the SQL Connection and the statement

import java.sql.*;

// Importing the package to take care of the JSON object for the constructor

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	
	// Variables for the user constructor
	
	public String userPassword;
	public String userEmail;
	public String type;
	public String update;
	
	//Constructor for the user object
	
	User (JSONObject request) throws JSONException {
		
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
			boolean userExists;
			
			//Establishing the connection
			
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username, dbPassword);
			System.out.println("Database connected");			
			
			//Strings for the SQL statements
					    
		    // Statement to see if the user exists
		    
			String checkExistenceSQL = "SELECT * FROM users WHERE email = ?";

		    PreparedStatement checkingStatement = conn.prepareStatement(checkExistenceSQL);
		    
		    checkingStatement.setString(1, email);	

		    ResultSet resultSet = checkingStatement.executeQuery();
		    
		    if(resultSet.next()){
		    	
	            userExists = true;
	            
	        }
		
			else {
				
			
	        	userExists = false;
		    
			}
		    		    
	    	// Statement to update the user's information

		    if (((userExists = true) && (type.contains("login")) && (update.contains("yes")))) {
		    	
		    	
			    String updateInformationSQL = "UPDATE users SET cat = ?,colour = ?, food = ? WHERE email = ?";

		    	PreparedStatement updateStatement = conn.prepareStatement(updateInformationSQL);
		    	
		    	updateStatement.setString(1, email);
			    
			    //Closing the connection
			    
			    conn.close(); 
		    
		    }
		    
		    // If the user is not in the database and wants to register
		    
		    else if ((userExists = false) && (type.contains("new"))) {
		    	
		    	// Statement to add the user's password and email to the database
		    	
		    	String saveUserSQL = "INSERT INTO users (password, email, type, update, animal, colour, food)" +
			            "VALUES (?, ?, ?, ?, ?, ?, ?)";
		    	
		    	PreparedStatement saveStatement = conn.prepareStatement(saveUserSQL);
		    	saveStatement.setString(1, password);
		    	saveStatement.setString(2, email);
		    	saveStatement.setString(3, type);
		    	saveStatement.setString(4, update);
		    	saveStatement.setString(5, "cat");
		    	saveStatement.setString(6, "red");
		    	saveStatement.setString(7, "meat");

		    	saveStatement.executeUpdate(); 
		    	
			    //Closing the connection
			    
			    conn.close(); 
		    }
		    
		}
		
		catch (Exception e) {
			
			System.out.println("Error with the method _userLog in the user class");
			
		}
	}
	
		public static void _print (String user,String password,String type) {

			System.out.format("[%s]\t%s\t%s", user, password, type);
			
		}
}
