package com.systems.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systems.model.ResultsTableEntity;
import com.systems.repository.ResultsTableRepository;

@RestController
@RequestMapping("/results-table/")
public class ResultsTableController {

	@Autowired
	ResultsTableRepository resultsTableRepository;
	
	@PostMapping("/logTricResult")
	public String logResultsOfTricToDatabase(@Valid @RequestBody ResultsTableEntity entity) {
		try {
			entity.setResultSubmissionTime();
			resultsTableRepository.save(entity);
			return "";
		} catch (Exception ex) {
			System.out.println(ex);
			return "failure";
		}
	}
	
	@GetMapping("/getAllResults/{userName}")
	public List<String> getAllResults(@PathVariable("userName") String userName){
		List<String> listOfResults = new ArrayList<String>();
		List<ResultsTableEntity> allRows = resultsTableRepository.findAll();
		for (ResultsTableEntity row : allRows) {
			if (row.getUserName().equals(userName)) {
				listOfResults.add(row.getResult());
			}
		}
		return listOfResults;
	}
}
