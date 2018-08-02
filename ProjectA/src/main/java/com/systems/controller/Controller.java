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

import com.systems.model.AccountActivationEntity;
import com.systems.model.UserAccounts;
import com.systems.business.GlobalMailSenderRepository;
import com.systems.repository.AccountActivationRepository;
import com.systems.repository.Repo;


@RestController
@RequestMapping("/api")
public class Controller {
	
	@Autowired
	Repo repository;
	
	@Autowired 
	AccountActivationRepository accountActivationRepository;

	GlobalMailSenderRepository mailSender = new GlobalMailSenderRepository();
	
	// NB
	@GetMapping("/getNumIds")
	public int countUserIds() {
		return repository.countUserIds();
	}
	
	@GetMapping("/getAll")
	public List<UserAccounts> getAllResults() {
	    return repository.findAll();
	}
	
	@GetMapping("/getName/{username}")
	public String getIndividualResult(@PathVariable("username") String username){
		return repository.retrieve(username) + " " + repository.retrieveSurname(username);
	}
	
	@GetMapping("/getAllUsers")
	public List<UserAccounts> getAllUsers(){
		return repository.findAll();
	}
	
	@PostMapping("/addUser")
	public UserAccounts createUser(@Valid @RequestBody UserAccounts entity) {
		return repository.save(entity);
	}
	
	@PostMapping("/authenticate/{auth}")
	public String checkNewAccountValidity(@PathVariable("auth") String auth,@Valid @RequestBody UserAccounts entity) {
		accountActivationRepository.autheticationConfirmed(auth);
	if (repository.checkIfEmailHasBeenUsed(entity.getEmail()) == null) {
			if (repository.checkIfUserNameExists(entity.getUserName()) == null) {
				entity.setAccountActivated();
				repository.save(entity);
				//	mailSender.sendAuthorisationEmailTo(entity, accountActivator.getAutoGeneratedUrl());
				return "";
			}
		}
		return "userNameTaken";
	}
	
	@GetMapping("/sendRegistrationInvite/{emailAddress}")
	public String sendRegistrationInvite(@PathVariable("emailAddress") String emailAddress) {
		try {
			AccountActivationEntity accountActivator = new AccountActivationEntity();
			accountActivator.setAutoGeneratedUrl();
			accountActivator.setUserName(accountActivator.getAutoGeneratedUrl());
			accountActivator.setActivated();
			accountActivationRepository.save(accountActivator);
			mailSender.sendAuthorisationEmailTo(emailAddress, accountActivator.getAutoGeneratedUrl());		
			return "";
		} catch (Exception ex) {
			System.out.println(ex);
			return "failure";
		}
	}
	
	@PostMapping("/activate/{auth}")
	public void checkNewAccountValidity(@PathVariable("auth") String auth) {
		try {
			accountActivationRepository.autheticationConfirmed(auth);
			repository.activateUserAccount(accountActivationRepository.retrieveAccountUserName(auth));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	@GetMapping("/expiredCheck/{auth}")
	public String checkIfActivationHasExpired(@PathVariable("auth") String auth){
		return accountActivationRepository.checkActivated(auth);
	}
	
	@GetMapping("/getAllUnassignedUsers")
	public void getAllUnassignedUsers() {
		List<UserAccounts> returned = repository.findAll();
		System.out.println(returned);
	}
}
