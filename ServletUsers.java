// The package of this project

package com.sdzee.servlets;

// Importing the classes for the servlet

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Importing the class to take care of JSON POST requests

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;

// Importing the other class, logger

import com.sdzee.servlets.user;

public class ServletUsers extends HttpServlet {
	
	private static final long serialVersionUID = -1641096228274971485L;

	protected HttpServletRequest req;
    protected HttpServletResponse resp;
    public static final String linkApp = "/App.jsp";

	// The doGet method that will take care of JSON GET request
	
	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		
        this.getServletContext().getRequestDispatcher(linkApp).forward( req,resp);

	    }
	    
	// The doPost method that will take care of the JSON POST request
	
    
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			  
			  // Getting the data from the JSON POST request

			  try {
				  
				  // Declaring the variables to get the data from the multiple JSON POST requests
					
				  StringBuilder newJsonBuff = new StringBuilder();
				  String newLine = null; 
			 
			      BufferedReader reader = req.getReader();
			      while ((newLine = reader.readLine()) != null)
			    	  newJsonBuff.append(newLine);
			      
			      // Creating the JSON object
			      
			      JSONObject jsonObject = new JSONObject(newJsonBuff.toString());
			      
			      //Creating the user object from the JSON object
			      
			      user newUser = new user(jsonObject);

			      // Calling the method from the user Class to handle the user
			      
			      user._userLog (newUser.userPassword,newUser.userEmail, newUser.type,newUser.update);
			      
			      //Printing a message to be able to  know when a user logs in
			      
			      user._print(newUser.userPassword,newUser.userEmail, newUser.type);
				      			    	    
			  }
			  
			 //Catching the error

	  		 catch (Exception e) { 
		  
	  			 System.out.println("Error with the doPost method in the ServletUsers class"); 
	  		
	  		} 		
	}
}	
