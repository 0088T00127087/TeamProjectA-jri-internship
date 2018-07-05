package com.systems.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "video_permissions")
@EntityListeners(AuditingEntityListener.class)
public class VideoPermissionsEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name= "role")
	private String role;
	
	@Column(name= "video_watched")
	private int videoWatched;
	
	@Id
	@Column(name= "video_number")
	private int videoNumber;
	
	@Column(name= "requires_manager_approval")
	private int requiresManagerApproval;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getVideoWatched() {
		return videoWatched;
	}

	public void setVideoWatched(int videoWatched) {
		this.videoWatched = videoWatched;
	}

	public int getVideoNumber() {
		return videoNumber;
	}

	public void setVideoNumber(int videoNumber) {
		this.videoNumber = videoNumber;
	}

	public int getRequiresManagerApproval() {
		return requiresManagerApproval;
	}

	public void setRequiresManagerApproval(int requiresManagerApproval) {
		this.requiresManagerApproval = requiresManagerApproval;
	}
	
	

	
	
}
