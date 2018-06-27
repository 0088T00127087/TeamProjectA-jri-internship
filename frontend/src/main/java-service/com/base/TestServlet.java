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
	private String nameURL = "http://localhost:8080/ProjectA/api/getName/";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			String userId = req.getParameter("userId");
			String URL = nameURL + userId;
			ClientResponse clientResponse = restClient.resource(URL).get();
			resp.setContentType("text/plain");
			resp.getWriter().write(clientResponse.getEntity(String.class));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	

}
