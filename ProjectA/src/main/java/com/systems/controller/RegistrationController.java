package com.systems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systems.repository.Repo;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
	
	@Autowired
	Repo repository;
	

	@GetMapping("/signin/{username}/{password}")
	public String checkIfUserExists(@PathVariable("username") String username, @PathVariable("password") String password){
		return repository.retrieveHashedPassword(username);
	}
	
	
}
