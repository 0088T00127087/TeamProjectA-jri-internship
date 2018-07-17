package com.base;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@WebServlet("/SignInRegistrationServlet")
public class SignInRegistrationServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private RestClient restClient = new RestClient();	
	private String registrationURL = "http://localhost:8080/ProjectA/registration/signin/";
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String encodedPassword =  URLEncoder.encode(password, "UTF-8");
			String URL = registrationURL + username + "/" + encodedPassword;
			ClientResponse clientResponse = restClient.resource(URL).get();
			String loggedPassword = clientResponse.getEntity(String.class);
			
			resp.setContentType("text/plain");
			if (passwordEncoder.matches(encodedPassword, loggedPassword)) {
				resp.getWriter().write("true");				
			} else {
				resp.getWriter().write("false");				
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
	
	

}
