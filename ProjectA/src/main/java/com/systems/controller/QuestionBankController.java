package com.systems.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systems.model.QuestionBankEntity;
import com.systems.repository.QuestionBankRepository;

@RestController
@RequestMapping("/question-bank")
public class QuestionBankController {
	
	@Autowired
	QuestionBankRepository questionBankRepository;
	
	@GetMapping("/retrieveAllQuestions")
	public List<String> retrieveAllQuestions() {
		List<QuestionBankEntity> allRows = questionBankRepository.findAll();
		List<String> allQuestions = new ArrayList<String>();
		for (QuestionBankEntity i : allRows) {
			allQuestions.add(Long.toString(i.getQuestId()));
		}
		return allQuestions;
	}
	
	@GetMapping("/retrieveDataForQuestions/{q1}/{q2}/{q3}/{q4}/{q5}/{q6}")
	public List<Optional<QuestionBankEntity>> retrieveDataForQuestions(@PathVariable("q1") String q1,@PathVariable("q2") String q2,
			@PathVariable("q3") String q3,@PathVariable("q4") String q4,@PathVariable("q5") String q5,@PathVariable("q6") String q6) {
		List<String> chosenQuestionId = new ArrayList<String>();
		List<Optional<QuestionBankEntity>> questionData = new ArrayList<Optional<QuestionBankEntity>>();
		chosenQuestionId.add(q1);
		chosenQuestionId.add(q2);
		chosenQuestionId.add(q3);
		chosenQuestionId.add(q4);
		chosenQuestionId.add(q5);
		chosenQuestionId.add(q6);
		for (String i : chosenQuestionId) {
			questionData.add(questionBankRepository.findById(Long.parseLong(i)));
		}
		return questionData;
	}

}
