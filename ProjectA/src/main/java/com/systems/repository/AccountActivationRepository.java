package com.systems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.systems.model.AccountActivationEntity;

@Repository
public interface AccountActivationRepository extends JpaRepository<AccountActivationEntity, Long>{
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value= "update `project-a-schema`.account_activation set activated = 1 where auto_generated_url = :authenticator",nativeQuery = true)
	void autheticationConfirmed(@Param("authenticator")String authenticator);
	
	@Query(value= "select distinct user_name from `project-a-schema`.account_activation where auto_generated_url = :auth",nativeQuery = true)
	String retrieveAccountUserName(@Param("auth")String auth);
	
	@Query(value= "select distinct activated from `project-a-schema`.account_activation where auto_generated_url = :auth",nativeQuery = true)
	String checkActivated(@Param("auth")String auth);
	
	

}
