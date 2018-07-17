package com.systems.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systems.model.UserAccounts;
import com.systems.repository.Repo;


@RestController
@RequestMapping("/api")
public class Controller {
	
	@Autowired
	Repo repository;
	
	@GetMapping("/getAll")
	public List<UserAccounts> getAllResults() {
	    return repository.findAll();
	}
	
	@GetMapping("/getName/{username}")
	public String getIndividualResult(@PathVariable("username") String username){
		return repository.retrieve(username);
	}
	
	@GetMapping("/getAllUsers")
	public List<UserAccounts> getAllUsers(){
		return repository.findAll();
	}
	
	@PostMapping("/addUser")
	public UserAccounts createUser(@Valid @RequestBody UserAccounts entity) {
		return repository.save(entity); //to use this I assume you have to send all the relevant data, in the correct order
	}

}
