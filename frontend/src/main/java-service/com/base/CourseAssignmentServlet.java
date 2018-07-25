package com.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.wink.client.RestClient;

@WebServlet("/CourseAssignmentServlet")
public class CourseAssignmentServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private RestClient restClient = new RestClient();	
	private String courseAssignmentURL = "http://localhost:8080/ProjectA/course-registration/assign";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String courseId = req.getParameter("courseId");
			String userName = req.getParameter("userName");
			String status = req.getParameter("status");
			String countOfManagerReview = req.getParameter("countOfManagerReview");
			String videoTrackingNo = req.getParameter("videoTrackingNo");
			String URL = courseAssignmentURL;
			String jsonBody = 
					"{\"courseId\":\"" + courseId + "\","
					+ "\"userName\":\""+ userName + "\","
					+ "\"status\":\""+ status + "\","
					+ "\"countOfManagerReview\":\""+ countOfManagerReview + "\","
					+ "\"videoTrackingNo\":\""+ videoTrackingNo + "\"}";
			String clientResponse =  restClient.resource(URL).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).post(String.class, jsonBody);
			resp.setContentType("text/plain");
			resp.getWriter().write(clientResponse);	
		} catch (Exception ex) {
			resp.setContentType("text/plain");
			resp.getWriter().write("failure");	
		}
	}
	
	

}
