package com.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;

@WebServlet("/QuestionBankServlet")
public class QuestionBankServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private RestClient restClient = new RestClient();
	private Random randomGenerator = new Random();
	private static final String questionBankRetrievalURL = "http://localhost:8080/ProjectA/question-bank/retrieveAllQuestions";
	private static final String questionDataRetrieval = "http://localhost:8080/ProjectA/question-bank/retrieveDataForQuestions";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> chosenQuestionIds = new ArrayList<String>();
		try {
			String userName = req.getParameter("userName");
			ClientResponse clientResponse = restClient.resource(questionBankRetrievalURL).get();
			String allQuestions = clientResponse.getEntity(String.class);
			allQuestions = allQuestions.substring(1, allQuestions.length() - 1);
			List<String> myList = new ArrayList<String>(Arrays.asList(allQuestions.split(",")));
			for (int i = 0; chosenQuestionIds.size() < 6;i++) {
				int chosenQuestion = randomGenerator.nextInt(myList.size());
				chosenQuestionIds.add(myList.get(chosenQuestion).replaceAll("\"", ""));
				myList.remove(chosenQuestion);
			}
			String URL = questionDataRetrieval + "/" + chosenQuestionIds.get(0) + "/"
			+ chosenQuestionIds.get(1) + "/" + chosenQuestionIds.get(2) + "/" + chosenQuestionIds.get(3) 
			 + "/" + chosenQuestionIds.get(4) + "/" + chosenQuestionIds.get(5);
			ClientResponse clientResp = restClient.resource(URL).get();
			String questionData = clientResp.getEntity(String.class);
			resp.setContentType("text/plain");
			resp.getWriter().write(questionData);
		} catch (Exception ex) {
			System.out.println(ex);
			resp.setContentType("text/plain");
			resp.getWriter().write("failure");
		}
	}
	
	

}
