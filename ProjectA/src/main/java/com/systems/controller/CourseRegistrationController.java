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
	public String getCurrentVideo(@PathVariable("userName") String userName) {
		return repository.retrieveCourseStatus(userName);
	}
	
	@GetMapping("/updateToInProgress/{userName}/{videoTracker}")
	public void chosenTopicInProgressUpdate(@PathVariable("userName") String userName,
			@PathVariable("videoTracker") String videoTracker) {
		String topicName = "";
		repository.updateToInProgress(userName,videoTracker);
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
		List<String> assignedAccounts = new ArrayList<String>();
		List<String> allUserNames = new ArrayList<String>();
		for (UserAccounts i :  allAccounts) {
			allUserNames.add(i.getUserName());
		}
		for (CourseRegistrationEntity i : repository.findAll()) {
			assignedAccounts.add(i.getUserName());
		}
		for (int i = 0; i < allUserNames.size(); i++ ) {
			if (!assignedAccounts.contains(allUserNames.get(i))) {
			unassignedUsers.add(allAccounts.get(i));
			}
		}
		return unassignedUsers;
	}
	
	
	

}
