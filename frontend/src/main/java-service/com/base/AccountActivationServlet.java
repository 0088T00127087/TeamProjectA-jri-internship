package com.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;


@WebServlet("/AccountActivationServlet")
public class AccountActivationServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private RestClient restClient = new RestClient();	
	private static final String accountActivationURL = "http://localhost:8080/ProjectA/api/activate/";
	private static final String accountExpiredCheckURL = "http://localhost:8080/ProjectA/api/expiredCheck/";

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String auth = req.getParameter("auth");
			String URL = accountExpiredCheckURL + auth;
			ClientResponse clientResponse = restClient.resource(URL).get();
			String response = clientResponse.getEntity(String.class);
			resp.setContentType("text/plain");
			resp.getWriter().write(response);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}



	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String auth = req.getParameter("auth");
			String URL = accountActivationURL + auth;
			ClientResponse clientResponse = restClient.resource(URL).post(null);
			String response = clientResponse.getEntity(String.class);
			resp.setContentType("text/plain");
			resp.getWriter().write(response);
		} catch (Exception ex) {
			resp.setContentType("text/plain");
			resp.getWriter().write("failure");
		}
	}
	
	

}
