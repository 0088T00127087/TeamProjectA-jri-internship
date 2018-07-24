package com.systems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.systems.model.CourseRegistrationEntity;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistrationEntity, Long> {
	
	@Query(value= "select distinct video_tracking_no from `project-a-schema`.course_registration where user_name = :userName and course_id = :courseId",nativeQuery = true)
	String retrieveVideoStatusWithinCourse(@Param("userName")String userName,@Param("courseId")String courseId);
	
	@Query(value= "select distinct course_id from `project-a-schema`.course_registration where user_name = :userName",nativeQuery = true)
	String retrieveCourseStatus(@Param("userName")String userName);

}
