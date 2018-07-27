package com.systems.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "question_bank")
@EntityListeners(AuditingEntityListener.class)
public class QuestionBankEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name= "quest_id")
    private long questId;
	
	@Column(name= "video_id")
    private int videoId;
	
	@Column(name= "question_String")
    private String questionString;
	
	@Column(name= "correct_Answer")
    private String correctAnswer;
	
	@Column(name= "wrg_ans1")
    private String wrgAns1;
	
	@Column(name= "wrg_ans2")
    private String wrgAns2;
	
	@Column(name= "wrg_ans3")
    private String wrgAns3;
	
	@Column(name= "Topic_id")
    private String topicId;

	public long getQuestId() {
		return questId;
	}

	public void setQuestId(long questId) {
		this.questId = questId;
	}

	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}

	public String getQuestionString() {
		return questionString;
	}

	public void setQuestionString(String questionString) {
		this.questionString = questionString;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getWrgAns1() {
		return wrgAns1;
	}

	public void setWrgAns1(String wrgAns1) {
		this.wrgAns1 = wrgAns1;
	}

	public String getWrgAns2() {
		return wrgAns2;
	}

	public void setWrgAns2(String wrgAns2) {
		this.wrgAns2 = wrgAns2;
	}

	public String getWrgAns3() {
		return wrgAns3;
	}

	public void setWrgAns3(String wrgAns3) {
		this.wrgAns3 = wrgAns3;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	
	

}
