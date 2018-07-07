package com.systems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systems.model.VideoPermissionsEntity;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VideoPermissionsRepository extends JpaRepository<VideoPermissionsEntity, Long> {
	
	@Query(value= "select video_watched from `project-a-schema`.video_permissions where video_number = :videoNumber",nativeQuery = true)
	int isVideoWatched(@Param("videoNumber")String videoNumber);
	
	@Query(value= "select requires_manager_approval from `project-a-schema`.video_permissions where video_number = :videoNumber",nativeQuery = true)
	int isVideoRequiringManagerApproval(@Param("videoNumber")String videoNumber);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value= "update `project-a-schema`.video_permissions set video_watched = 1,requires_manager_approval = 1 where video_number = :videoNumber",nativeQuery = true)
	void updateVideoWatched(@Param("videoNumber")String videoNumber);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value= "update `project-a-schema`.video_permissions set video_watched = 0,requires_manager_approval = 0 where video_number = :videoNumber",nativeQuery = true)
	void reassignVideo(@Param("videoNumber")String videoNumber);

}
