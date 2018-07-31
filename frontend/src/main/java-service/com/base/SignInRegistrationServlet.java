package com.base;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@WebServlet("/SignInRegistrationServlet")
public class SignInRegistrationServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private RestClient restClient = new RestClient();	
	private String signInURL = "http://localhost:8080/ProjectA/registration/signin/";
	private String registrationURL = "http://localhost:8080/ProjectA/api/authenticate/";
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String encodedPassword =  URLEncoder.encode(password, "UTF-8");
			String URL = signInURL + username + "/" + encodedPassword;
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
		try {
			String firstName = req.getParameter("firstName");
			String surname = req.getParameter("surname");
			String email = req.getParameter("email");
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String URL = registrationURL + "/" + req.getParameter("auth");
			String jsonBody = "{\"firstName\":\"" + firstName + "\",\"secondName\":\"" + surname +
					"\",\"hashedPassword\":\""+ hashedPassword(password) + "\",\"userName\":\""+ username + "\",\"email\":\""+ email + "\"}";
			String clientResponse =  restClient.resource(URL).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).post(String.class, jsonBody);
			resp.setContentType("text/plain");
			resp.getWriter().write(clientResponse);	
		} catch (Exception ex) {
			resp.setContentType("text/plain");
			resp.getWriter().write("failure");	
		}
	}

	private String hashedPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}
	
	

}
