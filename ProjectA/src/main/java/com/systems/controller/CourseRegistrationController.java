package com.systems.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systems.model.CourseRegistrationEntity;
import com.systems.repository.CourseRegistrationRepository;

@RestController
@RequestMapping("/course-registration")
public class CourseRegistrationController {
	
	@Autowired
	CourseRegistrationRepository repository;

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

}
