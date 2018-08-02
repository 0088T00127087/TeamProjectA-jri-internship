package com.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.wink.client.RestClient;

@WebServlet("/ResultsTableServlet")
public class ResultsTableServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private RestClient restClient = new RestClient();	
	private String courseAssignmentURL = "http://localhost:8080/ProjectA/results-table/logTricResult";
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String userName = req.getParameter("userName");
			String videoId = req.getParameter("videoId");
			String result = req.getParameter("result");
			String numberOfQuestionsCorrect = req.getParameter("noOfQuestionsCorrect");
			String numberOfQuestionsIncorrect = req.getParameter("noOfQuestionsIncorrect");
			String timeTakenPerQuestion = req.getParameter("timeTakenPerQuestion");
			String wrongAnswerIds = req.getParameter("wrongAnswerIds");
			String jsonBody = 
					"{\"videoId\":\"" + videoId + "\","
					+ "\"userName\":\""+ userName + "\","
					+ "\"result\":\""+ result + "\","
					+ "\"numberOfQuestionsCorrect\":\""+ numberOfQuestionsCorrect + "\","
					+ "\"numberOfQuestionsIncorrect\":\""+ numberOfQuestionsIncorrect + "\","
					+ "\"timeTakenPerQuestion\":\""+ timeTakenPerQuestion + "\","
					+ "\"wrongAnswerIds\":\""+ wrongAnswerIds + "\"}";
			String clientResponse =  restClient.resource(courseAssignmentURL).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).post(String.class, jsonBody);
			resp.setContentType("text/plain");
			resp.getWriter().write(clientResponse);	
		} catch(Exception ex) {
			resp.setContentType("text/plain");
			resp.getWriter().write(ex.toString());	
		}
	}
	
	

}
