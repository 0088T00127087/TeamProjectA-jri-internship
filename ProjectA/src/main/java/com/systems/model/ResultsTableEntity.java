package com.systems.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "results_table")
@EntityListeners(AuditingEntityListener.class)
public class ResultsTableEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name= "result_id")
    private long resultId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="video_id")
	private int videoId;
	
	@Column(name="result")
	private String result;

	@Column(name="number_of_questions_correct")
	private int numberOfQuestionsCorrect;
	
	@Column(name="number_of_questions_incorrect")
	private int numberOfQuestionsIncorrect;
	
	@Column(name="time_taken_per_question")
	private String timeTakenPerQuestion;
	
	@Column(name="wrong_answer_ids")
	private String wrongAnswerIds;
	
	@Column(name="result_submission_time")
	private Timestamp resultSubmissionTime;

	public long getResultId() {
		return resultId;
	}

	public void setResultId(long resultId) {
		this.resultId = resultId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getNumberOfQuestionsCorrect() {
		return numberOfQuestionsCorrect;
	}

	public void setNumberOfQuestionsCorrect(int numberOfQuestionsCorrect) {
		this.numberOfQuestionsCorrect = numberOfQuestionsCorrect;
	}

	public int getNumberOfQuestionsIncorrect() {
		return numberOfQuestionsIncorrect;
	}

	public void setNumberOfQuestionsIncorrect(int numberOfQuestionsIncorrect) {
		this.numberOfQuestionsIncorrect = numberOfQuestionsIncorrect;
	}

	public String getTimeTakenPerQuestion() {
		return timeTakenPerQuestion;
	}

	public void setTimeTakenPerQuestion(String timeTakenPerQuestion) {
		this.timeTakenPerQuestion = timeTakenPerQuestion;
	}

	public String getWrongAnswerIds() {
		return wrongAnswerIds;
	}

	public void setWrongAnswerIds(String wrongAnswerIds) {
		this.wrongAnswerIds = wrongAnswerIds;
	}

	public Timestamp getResultSubmissionTime() {
		return resultSubmissionTime;
	}

	public void setResultSubmissionTime() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.resultSubmissionTime = timestamp;
	}
	
}
