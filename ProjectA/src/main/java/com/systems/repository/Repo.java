package com.systems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.systems.model.UserAccounts;

@Repository
public interface Repo extends JpaRepository<UserAccounts, Long>{
	
	@Query(value= "select first_name from `project-a-schema`.user_accounts where user_name = :username",nativeQuery = true)
	String retrieve(@Param("username")String username);
	
	@Query(value= "select hashed_password from `project-a-schema`.user_accounts where user_name = :username",nativeQuery = true)
	String retrieveHashedPassword(@Param("username")String username);

}
