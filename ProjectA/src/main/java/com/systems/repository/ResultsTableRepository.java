package com.systems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.systems.model.ResultsTableEntity;

@Repository
public interface ResultsTableRepository extends JpaRepository<ResultsTableEntity, Long> {
	
	@Query(value= "select distinct wrong_answer_ids from `project-a-schema`.results_table where user_name = :userName and result_submission_time = (select max(result_submission_time) from `project-a-schema`.results_table where user_name = :userName)",nativeQuery = true)
	String retrieveAllWrongQuestions(@Param("userName")String username);

}
