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
	
//	@GetMapping("/getAllUnassignedUsers")
//	public List<String> getAllUnassignedUsers(){
//		List<UserAccounts> unassignedUsers = new ArrayList<UserAccounts>();
//		List<String> allAccounts = new ArrayList<String>();
//		List<String> assignedAccounts = new ArrayList<String>();
//		for (UserAccounts i :  userAccountsRepository.findAll()) {
//			allAccounts.add(i.getUserName());
//		}
//		for (CourseRegistrationEntity i : repository.findAll()) {
//			assignedAccounts.add(i.getUserName());
//		}
//		for (String i : allAccounts) {
//			if (!assignedAccounts.contains(i)) {
//			unassignedUsers.add(allAccounts.get(Integer.parseInt(i)));
//			}
//		}
//		return unassignedUsers;
//		
//	}
	
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
