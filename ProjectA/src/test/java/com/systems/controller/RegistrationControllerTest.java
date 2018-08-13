package com.systems.controller;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.systems.repository.CourseRegistrationRepository;
import com.systems.repository.Repo;

import junit.framework.Assert;

public class RegistrationControllerTest {

	private RegistrationController registrationController;
	private Repo repository;
	private static String USERNAME = "Username";
	private static String PASSWORD = "HashedPassword";
	
	@Before
	public void setUp() {
		repository = EasyMock.createMock(Repo.class);
		registrationController = new RegistrationController(repository);
	}
	
	@Test
	public void retrieveHashedPassword_Success() {
		EasyMock.expect(repository.retrieveHashedPassword(USERNAME)).andReturn(PASSWORD);
		EasyMock.replay(repository);
		
		String methodResponse = registrationController.retrieveHashedPassword(USERNAME);
		
		EasyMock.verify(repository);
		assertEquals(PASSWORD,methodResponse);
	}
	
}
