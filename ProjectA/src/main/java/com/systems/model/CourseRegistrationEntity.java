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
@Table(name = "course_registration")
@EntityListeners(AuditingEntityListener.class)
public class CourseRegistrationEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name= "registration_id")
    private long registrationId;
	
	@Column(name="course_id")
	private int courseId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="status")
	private int status;
	
	@Column(name="video_tracking_no")
	private int videoTrackingNo;
	
	@Column(name="count_of_manager_review")
	private int countOfManagerReview;
	
	@Column(name="registration_date")
	private Timestamp registrationDate;

	public long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getVideoTrackingNo() {
		return videoTrackingNo;
	}

	public void setVideoTrackingNo(int videoTrackingNo) {
		this.videoTrackingNo = videoTrackingNo;
	}

	public int getCountOfManagerReview() {
		return countOfManagerReview;
	}

	public void setCountOfManagerReview(int countOfManagerReview) {
		this.countOfManagerReview = countOfManagerReview;
	}

	public Timestamp getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.registrationDate = timestamp;
	}
	
	
	
}
