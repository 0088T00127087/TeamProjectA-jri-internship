package com.systems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systems.model.QuestionBankEntity;

@Repository
public interface QuestionBankRepository extends JpaRepository<QuestionBankEntity, Long> {

}
