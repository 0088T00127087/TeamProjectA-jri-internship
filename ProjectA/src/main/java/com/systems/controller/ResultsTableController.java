package com.systems.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systems.business.GlobalMailSenderRepository;
import com.systems.model.ResultsTableEntity;
import com.systems.repository.CourseRegistrationRepository;
import com.systems.repository.ResultsTableRepository;

@RestController
@RequestMapping("/results-table/")
public class ResultsTableController {

	@Autowired
	ResultsTableRepository resultsTableRepository;
	
	@Autowired
	CourseRegistrationRepository courseRegistrationRepository;
	
	@Autowired
	public ResultsTableController(ResultsTableRepository resultsTableRepository,
			CourseRegistrationRepository courseRegistrationRepository) {
		this.resultsTableRepository = resultsTableRepository;
		this.courseRegistrationRepository = courseRegistrationRepository;
	}
	
	
	GlobalMailSenderRepository sender = new GlobalMailSenderRepository();
	
	@PostMapping("/logTricResult")
	public String logResultsOfTricToDatabase(@Valid @RequestBody ResultsTableEntity entity) {
		try {
			if (entity.getResult().equals("pass")) {
				sender.sendNotifierOfTopicPassToManager(entity.getUserName());
				courseRegistrationRepository.updateStatus(entity.getUserName(), String.valueOf(entity.getVideoId()),"2");	
			//	courseRegistrationRepository.updateVideoTrackingId(entity.getUserName(), String.valueOf(entity.getVideoId()), "2");
			} else if (entity.getResult().equals("fail")) {
				courseRegistrationRepository.updateStatus(entity.getUserName(), String.valueOf(entity.getVideoId()),"0");	
				courseRegistrationRepository.updateVideoTrackingId(entity.getUserName(), String.valueOf(entity.getVideoId()), "1");
				int managerReviewCount = courseRegistrationRepository.examineIfUserIsLegibleToTakeTopic(entity.getUserName(), String.valueOf(entity.getVideoId()));
				courseRegistrationRepository.updateManagerReviewCount(entity.getUserName(),String.valueOf(entity.getVideoId()), ++managerReviewCount);
				sender.sendNotifierOfTopicFirstFailureToManager(entity.getUserName());
			}
			entity.setResultSubmissionTime();
			resultsTableRepository.save(entity);
			return "";
		} catch (Exception ex) {
			System.out.println(ex);
			return "failure";
		}
	}
	
	@GetMapping("/getAllResults/{userName}")
	public List<String> getAllResults(@PathVariable("userName") String userName){
		List<String> listOfResults = new ArrayList<String>();
		List<ResultsTableEntity> allRows = resultsTableRepository.findAll();
		for (ResultsTableEntity row : allRows) {
			if (row.getUserName().equals(userName)) {
				listOfResults.add(row.getResult());
			}
		}
		return listOfResults;
	}
	
	@GetMapping("/retrieveWrongQuestions/{userName}")
	public List<String> retrieveWrongIfQuestions(@PathVariable("userName") String userName){
		List<String> listOfResults = new ArrayList<String>();
		List<ResultsTableEntity> allRows = resultsTableRepository.findAll();
		for (ResultsTableEntity row : allRows) {
			if (row.getUserName().equals(userName)) {
				listOfResults.add(row.getResult());
			}
		}
		return listOfResults;
	}
	
	
	
	@GetMapping("/retrieveQuestionsAnsweredIncorrectly/{userName}")
	public String retrieveWrongQuestions(@PathVariable("userName") String userName) {
		try {
			return resultsTableRepository.retrieveAllWrongQuestions(userName);
		} catch (Exception ex) {
			return "";
		}
	}
	
	
	@GetMapping("/retrieveMostRecentResults")
	public List<ResultsTableEntity> retrieveMostRecentResults(){
		List<ResultsTableEntity> mostRecentResults = new ArrayList<ResultsTableEntity>();
		try {
			List<String> allUsersThatTookATest = resultsTableRepository.retrieveDistinctUserList();
			for (int i=0; i < allUsersThatTookATest.size();i++) {
				mostRecentResults.add(resultsTableRepository.retrieveMostRecentResultForUser(allUsersThatTookATest.get(i)));
			}			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return mostRecentResults;
	}
	
	@GetMapping("/getUserResult/{userAccount}")
	public String getUserResult(@PathVariable("userAccount") String userAccount) {
		String answer = "";
		
		answer = resultsTableRepository.getUserResult(userAccount);
		
		return answer;
	}
	
	
	@GetMapping("/getOverallResult")
	public int getOverallResult() {
		int answer = 0;
		List<String> userNames = resultsTableRepository.retrieveDistinctUserList();
		
		for (int i=0; i < userNames.size(); i++) {
			answer += Integer.valueOf(resultsTableRepository.getUserResult(userNames.get(i)));
		}
		
		answer /= userNames.size();
		
		return answer;
	}
	
	@GetMapping("/getCountFails")
    public int getCountFails() {
        int count = 0;
        List<ResultsTableEntity> userNames = resultsTableRepository.findAll();
        for (int i=0; i < userNames.size(); i++) {
        	if (userNames.get(i).getResult().equals("fail")) {
        		count++;
        	}
        }
        return count;
    }

    

    @GetMapping("/getCountPass")
    public int getCountPass() {
        int count = 0;
        List<ResultsTableEntity> userNames = resultsTableRepository.findAll();
        for (int i=0; i < userNames.size(); i++) {
        	if (userNames.get(i).getResult().equals("pass")) {
        		count++;
        	}
        }
        return count;
    }
    
    
    
    /*
     * Get Average of times 
     */
//    @GetMapping("/getAvgOverallTime")
//    public List<String> getAvgOverallTime() {
//    	List<String> listOfResults = new ArrayList<String>();
//    	List<String> listOfTimes = new ArrayList<String>();
//    
//    	listOfTimes = listOfResults.get().getTimeTakenPerQuestion();
//     
//    }       
    
    /*
     * Get All Times
     */
    
//    @GetMapping("/getAllTime")
//	public List<String> getAllResults(){
//    	List<String> listOfTimes = new ArrayList<String>();
//		List<ResultsTableEntity> allRows = resultsTableRepository.findAll();
//		for (ResultsTableEntity row : allRows) {
//			
//				listOfTimes.add(row.getTimeTakenPerQuestion());
//			
//		}
//		return listOfTimes;
//	}
//    
    
    
//    @GetMapping("/getAvgAllTime")
//    public void getAvgTime(List<String> times) {
//    	List<String> avgTimes = new ArrayList<String>();
//    	String timeString ="";
//    	int timeToInt;
//    	
//    	
//    	for(int i = 0; i <= times.size(); i++) {
//    		timeToInt = Integer.parse(times.get(i).substring(i, 5))
//    	}
//    }
    
    
    
	
}
