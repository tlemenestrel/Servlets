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

import com.sdzee.servlets.logger;

public class ServletLoggers extends HttpServlet {
	
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
					
				  StringBuilder jsonBuff = new StringBuilder();
				  String line = null; 
			 
			      BufferedReader reader = req.getReader();
			      while ((line = reader.readLine()) != null)
			          jsonBuff.append(line);
			      
			      // Creating the JSON object
			      
			      JSONObject json = new JSONObject(jsonBuff.toString());
			    	  
		    	  logger log = new logger(json);
		      
		      		// Calling the debug method if the log is correct
			   
			      if (log.level.contains("debug")) {
			    	  
			    	  logger.debug(log.level,log.method,log.message,log.date);
			      }
			      
			      // Calling the error method if the log is not correct

			      else if (log.level.contains("error")) {
			    	  
			    	  logger.error(log.level,log.method,log.message,log.date);
				      }
			      
			  	}
			  
			 //Catching the error

	  		 catch (Exception e) { 
		  
	  			 System.out.println("Error with the doPost method"); 
	  		
	  		} 		
	}
}	
