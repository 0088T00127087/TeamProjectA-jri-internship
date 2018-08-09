package com.systems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.systems.model.CourseRegistrationEntity;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistrationEntity, Long> {
	
	@Query(value= "select distinct video_tracking_no from `project-a-schema`.course_registration where user_name = :userName and course_id = :courseId",nativeQuery = true)
	String retrieveVideoStatusWithinCourse(@Param("userName")String userName,@Param("courseId")String courseId);
	
	@Query(value= "select distinct course_id from `project-a-schema`.course_registration where user_name = :userName",nativeQuery = true)
	String retrieveCourseStatus(@Param("userName")String userName);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update `project-a-schema`.course_registration set video_tracking_no = :videoTrackingNo where user_name = :userName and course_id = :courseId",nativeQuery = true)
	void updateVideoTrackingId(@Param("userName")String userName, @Param("courseId")String courseId, @Param("videoTrackingNo") String videoTrackingNo);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update `project-a-schema`.course_registration set video_tracking_no = :videoTrackingNo where user_name = :userName",nativeQuery = true)
	void updateVideoTrackingNumber(@Param("userName")String userName, @Param("videoTrackingNo") String videoTrackingNo);
	
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update `project-a-schema`.course_registration set status = :status where user_name = :userName and course_id = :courseId",nativeQuery = true)
	void updateStatus(@Param("userName")String userName, @Param("courseId")String courseId, @Param("status") String status);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update `project-a-schema`.course_registration set count_of_manager_review = :managerReviewCount, status = 0 where user_name = :userName and course_id = :courseId",nativeQuery = true)
	void updateManagerReviewCountAndStatus(@Param("userName")String userName, @Param("courseId")String courseId, @Param("managerReviewCount")int managerReviewCount);
	
	@Query(value= "select distinct count_of_manager_review from `project-a-schema`.course_registration where user_name = :userName and course_id = :courseId",nativeQuery = true)
	int examineIfUserIsLegibleToTakeTopic(@Param("userName")String userName, @Param("courseId")String courseId);
	
	@Query(value= "select distinct status from `project-a-schema`.course_registration where user_name = :userName and course_id = :courseId",nativeQuery = true)
	int examineIfUserHasNotFailedOrNavigatedAway(@Param("userName")String userName, @Param("courseId")String courseId);
	
	@Query(value= "select status from `project-a-schema`.course_registration where user_name = :userName",nativeQuery = true)
	int retrieveStatus(@Param("userName")String userName);
	
	@Query(value= "select video_tracking_no from `project-a-schema`.course_registration where user_name = :userName",nativeQuery = true)
	int retrieveVideoTrackingNo(@Param("userName")String userName);

	
	

}
