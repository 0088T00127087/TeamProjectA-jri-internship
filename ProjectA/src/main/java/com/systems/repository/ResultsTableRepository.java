package com.systems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.systems.model.ResultsTableEntity;

@Repository
public interface ResultsTableRepository extends JpaRepository<ResultsTableEntity, Long> {

}
