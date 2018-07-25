package com.systems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.systems.model.UserAccounts;

@Repository
public interface Repo extends JpaRepository<UserAccounts, Long>{
	
	@Query(value= "select first_name from `project-a-schema`.user_accounts where user_name = :username",nativeQuery = true)
	String retrieve(@Param("username")String username);
	
	@Query(value= "select second_name from `project-a-schema`.user_accounts where user_name = :username",nativeQuery = true)
	String retrieveSurname(@Param("username")String username);
	
	@Query(value= "select first_name from `project-a-schema`.user_accounts where email = :email",nativeQuery = true)
	String retrieveNameFromEmail(@Param("email")String email);
	
	@Query(value= "select hashed_password from `project-a-schema`.user_accounts where user_name = :username",nativeQuery = true)
	String retrieveHashedPassword(@Param("username")String username);
	
	@Query(value= "select user_name from `project-a-schema`.user_accounts where user_name = :username",nativeQuery = true)
	String checkIfUserNameExists(@Param("username")String username);

	@Query(value= "select distinct email from `project-a-schema`.user_accounts where email = :email",nativeQuery = true)
	String checkIfEmailHasBeenUsed(@Param("email")String email);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value= "update `project-a-schema`.user_accounts set account_activated = 1 where user_name = :username",nativeQuery = true)
	void activateUserAccount(@Param("username")String username);


}
