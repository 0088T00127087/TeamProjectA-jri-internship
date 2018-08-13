package com.systems.controller;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.systems.repository.CourseRegistrationRepository;
import com.systems.repository.Repo;

public class CourseRegistrationControllerTest {
	
	private CourseRegistrationRepository courseRegistrationRepository;
	private Repo userAccountsRepository;
	private CourseRegistrationController controller;
	private static String USERNAME = "Aquemini";
	private static String COURSE_ID = "1";
	private static int IN_PROGRESS = 1;
	
	@Before
	public void setUp() {
		courseRegistrationRepository = EasyMock.createMock(CourseRegistrationRepository.class);
		userAccountsRepository = EasyMock.createMock(Repo.class);
		controller = new CourseRegistrationController(courseRegistrationRepository, userAccountsRepository);
	}
	
	@Test
	public void getCurrentVideo_Success() {
		EasyMock.expect(courseRegistrationRepository.retrieveVideoStatusWithinCourse(USERNAME, COURSE_ID)).andReturn("1");
		EasyMock.replay(courseRegistrationRepository);
		
		String methodResponse = controller.getCurrentVideo(USERNAME, COURSE_ID);
		
		EasyMock.verify(courseRegistrationRepository);
		assertEquals("1",methodResponse);
	}
	
	@Test
	public void getCurrentStatus_ReturnsInProgress() {
		EasyMock.expect(courseRegistrationRepository.retrieveStatus(USERNAME)).andReturn(IN_PROGRESS);
		EasyMock.replay(courseRegistrationRepository);
		
		int methodResponse = controller.getCurrentStatus(USERNAME);
		
		EasyMock.verify(courseRegistrationRepository);
		assertEquals(IN_PROGRESS,methodResponse);
	}
	
	@Test
	public void getCurrentCourse_Success() {
		EasyMock.expect(courseRegistrationRepository.retrieveCourseStatus(USERNAME)).andReturn("1");
		EasyMock.replay(courseRegistrationRepository);
		
		String methodResponse = controller.getCurrentCourse(USERNAME);
		
		EasyMock.verify(courseRegistrationRepository);
		assertEquals("1",methodResponse);
	}
	
	@Test
	public void examineUserLegibility_ValidAttempt() {
		EasyMock.expect(courseRegistrationRepository.examineIfUserIsLegibleToTakeTopic(USERNAME, "1")).andReturn(0);
		EasyMock.replay(courseRegistrationRepository);
		
		String methodResponse = controller.retrieveUserTopicLegibility(USERNAME, "1");
		
		EasyMock.verify(courseRegistrationRepository);
		assertEquals("true",methodResponse);
	}
	
	@Test
	public void examineUserLegibility_InvalidAttempt() {
		EasyMock.expect(courseRegistrationRepository.examineIfUserIsLegibleToTakeTopic(USERNAME, "1")).andReturn(2);
		EasyMock.replay(courseRegistrationRepository);
		
		String methodResponse = controller.retrieveUserTopicLegibility(USERNAME, "1");
		
		EasyMock.verify(courseRegistrationRepository);
		assertEquals("false",methodResponse);
	}
	
	@Test
	public void retrieveCourseProgress_Success() {
		EasyMock.expect(courseRegistrationRepository.retrieveStatus(USERNAME)).andReturn(2);
		EasyMock.expect(courseRegistrationRepository.retrieveVideoTrackingNo(USERNAME)).andReturn(1);
		EasyMock.replay(courseRegistrationRepository);
		
		List<Integer> methodResponse = controller.retrieveCourseProgressFor(USERNAME);
		
		EasyMock.verify(courseRegistrationRepository);
		assertEquals(2,methodResponse.get(0).intValue());
		assertEquals(1,methodResponse.get(1).intValue());
		assertEquals(2,methodResponse.size());
	}
	
	@Test
	public void retrieveCourseProgress_Failure() throws Exception{
		EasyMock.expect(courseRegistrationRepository.retrieveStatus(USERNAME)).andThrow(new RuntimeException());
		EasyMock.replay(courseRegistrationRepository);
		
		List<Integer> methodResponse = controller.retrieveCourseProgressFor(USERNAME);
		
		EasyMock.verify(courseRegistrationRepository);
		assertEquals(-1,methodResponse.get(0).intValue());
		assertEquals(-1,methodResponse.get(1).intValue());
		assertEquals(2,methodResponse.size());
	}

}
