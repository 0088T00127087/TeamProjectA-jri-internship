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

import com.systems.model.MappedClass;
import com.systems.repository.Repo;


@RestController
@RequestMapping("/api")
public class Controller {
	
	@Autowired
	Repo repository;
	
	@GetMapping("/getAll")
	public List<MappedClass> getAllResults() {
	    return repository.findAll();
	}
	
	@GetMapping("/getName/{userId}")
	public String getIndividualResult(@PathVariable("userId") String userId){
		return repository.retrieve(userId); //how does this know to point to the custom method written in Repo.java
	}
	
	@GetMapping("/getAllUsers")
	public List<MappedClass> getAllUsers(){
		return repository.findAll();
	}
	
	@PostMapping("/addUser")
	public MappedClass createUser(@Valid @RequestBody MappedClass entity) {
		return repository.save(entity); //to use this I assume you have to send all the relevant data, in the correct order
	}

}
