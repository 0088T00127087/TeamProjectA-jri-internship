package com.systems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.systems.model.MappedClass;

@Repository
public interface Repo extends JpaRepository<MappedClass, Long>{
	
	@Query(value= "select first_name from `project-a-schema`.user_accounts where user_id = :userId",nativeQuery = true)
	String retrieve9(@Param("userId")String userId);

}
