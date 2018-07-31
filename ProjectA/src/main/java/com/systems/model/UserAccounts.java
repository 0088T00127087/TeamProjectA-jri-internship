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
@Table(name = "user_accounts")
@EntityListeners(AuditingEntityListener.class)
public class UserAccounts implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name= "user_id")
    private long userId;
	
	@Column(name= "first_name")
    private String firstName; 
	
	@Column(name= "second_name")
    private String secondName;
	
	@Column(name = "hashed_password")
	private String hashedPassword;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "account_activated")
	private int accountActivated;
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String password) {
		this.hashedPassword = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAccountActivated() {
		return accountActivated;
	}

	public void setAccountActivated() {
		this.accountActivated = 1;
	}
	
	
}
