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
import com.systems.model.CourseRegistrationEntity;
import com.systems.model.UserAccounts;
import com.systems.repository.CourseRegistrationRepository;
import com.systems.repository.Repo;

@RestController
@RequestMapping("/course-registration")
public class CourseRegistrationController {
	
	@Autowired
	CourseRegistrationRepository repository;
	
	@Autowired
	Repo userAccountsRepo;
	
	@Autowired
	public CourseRegistrationController(CourseRegistrationRepository repository,Repo userAccountsRepo) {
		this.repository = repository;
		this.userAccountsRepo = userAccountsRepo;
	}
	
	GlobalMailSenderRepository sender = new GlobalMailSenderRepository();
	
	@Autowired
	Repo userAccountsRepository;

	@PostMapping("/assign")
	public void assignCourseToUser(@Valid @RequestBody CourseRegistrationEntity entity){
		entity.setRegistrationDate();
		repository.save(entity);
	}
	
	@GetMapping("/getCurrentVideo/{userName}/{courseId}")
	public String getCurrentVideo(@PathVariable("userName") String userName,@PathVariable("courseId") String courseId) {
		return repository.retrieveVideoStatusWithinCourse(userName,courseId);
	}
	
	@GetMapping("/getCurrentCourse/{userName}")
	public String getCurrentCourse(@PathVariable("userName") String userName) {
		return repository.retrieveCourseStatus(userName);
	}
	
	@GetMapping("/getCurrentStatus/{userName}")
	public int getCurrentStatus(@PathVariable("userName") String userName) {
		return repository.retrieveStatus(userName);
	}
	
	@GetMapping("/updateToInProgress/{userName}/{videoTracker}")
	public void chosenTopicInProgressUpdate(@PathVariable("userName") String userName,
			@PathVariable("videoTracker") String videoTracker) {
		String topicName = "";
		repository.updateStatus(userName,videoTracker, "1");
		if (videoTracker.equals("1")) {
			topicName = "Introduction To Python";
		} else {
			topicName = "";
		}
		sender.sendNotifierOfTopicStartToManager(userAccountsRepo.retrieve(userName) + " " + userAccountsRepo.retrieveSurname(userName),topicName);
	}
	
	@GetMapping("/retrieveUserTopicLegibility/{userName}/{videoTracker}")
	public String retrieveUserTopicLegibility(@PathVariable("userName") String userName,
			@PathVariable("videoTracker") String videoTracker) {
		int managerReviewCount = repository.examineIfUserIsLegibleToTakeTopic(userName,videoTracker);
		if (managerReviewCount < 2) {
			return "true";
		} else {
			return "false";
		}
	}
	
	@GetMapping("/updateVideoTrackingNo/{userName}/{courseNo}")
	public String updateVideoTrackingNo(@PathVariable("userName") String userName,
			@PathVariable("courseNo") String courseNo) {
		try {
			repository.updateVideoTrackingNumber(userName, courseNo);
			return "";
		} catch (Exception ex) {
			return "failure";
		}
	}
	
	@GetMapping("/retrieveAllUsersThatRequireManagerReview")
	public List<CourseRegistrationEntity> retrieveAllUsersThatRequireManagerReview(){
		List<CourseRegistrationEntity> usersRequiringReview = new ArrayList<CourseRegistrationEntity>();
		List<CourseRegistrationEntity> allAssignments = repository.findAll();
		for (CourseRegistrationEntity ent : allAssignments) {
			if (ent.getCountOfManagerReview() == 2) {
				usersRequiringReview.add(ent);
			}
		}
		return usersRequiringReview;
	}
	
	@GetMapping("/retrieveCourseProgressFor/{userName}")
	public List<Integer> retrieveCourseProgressFor(@PathVariable("userName") String userName){
		try {
			int status = repository.retrieveStatus(userName);
			int videoTrackingNo = repository.retrieveVideoTrackingNo(userName);
			List<Integer> response = new ArrayList<Integer>();
			response.add(status);
			response.add(videoTrackingNo);
			return response;			
		} catch (Exception ex) {
			List<Integer> noContent = new ArrayList<Integer>();
			noContent.add(-1);
			noContent.add(-1);
			return noContent;
		}
	}
	
	@GetMapping("/checkTheUsersTopicStatus/{userName}/{videoTracker}")
	public int checkTheUsersTopicStatus(@PathVariable("userName") String userName,
			@PathVariable("videoTracker") String videoTracker) {
		return repository.examineIfUserHasNotFailedOrNavigatedAway(userName, videoTracker);
	}
	
	@GetMapping("/registerFirstFailure/{userName}/{videoTracker}")
		public void registerUsersFirstFailure(@PathVariable("userName") String userName,
			@PathVariable("videoTracker") String videoTracker) {
		int countOfManagerReview = repository.examineIfUserIsLegibleToTakeTopic(userName, videoTracker);
		if (countOfManagerReview < 2) {
			repository.updateManagerReviewCountAndStatus(userName, videoTracker, ++countOfManagerReview);			
		} 	
	}
	
	@GetMapping("/reAssignCourseToUser/{userName}/{videoTracker}")
	public void reassignCourseToUser(@PathVariable("userName") String userName,
		@PathVariable("videoTracker") String videoTracker) {
		repository.updateManagerReviewCountAndStatus(userName, videoTracker, 0);			

}
	
	@GetMapping("/retrieveAllUnassignedUsers")
	public List<UserAccounts> retrieveAllUnassignedUsers(){
		List<UserAccounts> unassignedUsers = new ArrayList<UserAccounts>();
		List<UserAccounts> allAccounts = userAccountsRepository.findAll();
		List<CourseRegistrationEntity> assignedAccounts = new ArrayList<CourseRegistrationEntity>();
		List<String> assignedAccountNames = new ArrayList<String>();
		List<String> allUserNames = new ArrayList<String>();
		List<String> allNamesAwaitingFurtherAssignment = new ArrayList<String>();
		for (UserAccounts i :  allAccounts) {
			allUserNames.add(i.getUserName());
		}
		for (CourseRegistrationEntity i : repository.findAll()) {
			assignedAccounts.add(i);
			assignedAccountNames.add(i.getUserName());
		}
		for (int i = 0; i < allUserNames.size(); i++ ) {
			if (!assignedAccountNames.contains(allUserNames.get(i))) {
			unassignedUsers.add(allAccounts.get(i));
			}
		}
		for (int i = 0; i < assignedAccountNames.size(); i++) {
				if (assignedAccounts.get(i).getStatus() == 2 && assignedAccounts.get(i).getVideoTrackingNo() == 1) {
					allNamesAwaitingFurtherAssignment.add(assignedAccounts.get(i).getUserName());
				}				
		}
		for (int i = 0; i < allNamesAwaitingFurtherAssignment.size(); i++) {
			for (int j = 0; j < allAccounts.size(); j++) {
				if (allAccounts.get(j).getUserName().equals(allNamesAwaitingFurtherAssignment.get(i))) {
					unassignedUsers.add(allAccounts.get(j));
				}
			}
		}
		
		return unassignedUsers;
	}
	
	
	

}
