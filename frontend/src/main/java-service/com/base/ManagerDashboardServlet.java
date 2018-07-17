package com.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;

@WebServlet("/ManagerDashboardServlet")
public class ManagerDashboardServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private RestClient restClient = new RestClient();	
	private String videoWatchedURL = "http://localhost:8080/ProjectA/video/assign/";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String videoNumber = req.getParameter("videoNumber");
			String URL = videoWatchedURL + videoNumber;
			ClientResponse clientResponse = restClient.resource(URL).get();
			resp.setContentType("text/plain");
			resp.getWriter().write(clientResponse.getEntity(String.class));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	 

}
