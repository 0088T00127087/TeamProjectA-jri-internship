package com.base;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private RestClient restClient = new RestClient();	
	private String nameURL = "http://localhost:8080/ProjectA/api/getName/"; //telling the servlet where to go to get data - backend
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			String userId = req.getParameter("userId"); //get User name parameter from the http request
			String URL = nameURL + userId; //append the userId to the backend URL
			ClientResponse clientResponse = restClient.resource(URL).get();  //declare a response object, which holds the value the restClient extracts from the backend URL
			resp.setContentType("text/plain"); //context of the response to plain text
			resp.getWriter().write(clientResponse.getEntity(String.class)); // write the cleintResponse object to the http response as a string
			//go back to logIn.js
			
			//Question in what part of the code will data manipulation happen if needed?
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	

}
