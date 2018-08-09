package com.systems.business;


import javax.mail.*;
import javax.mail.internet.*;

import com.systems.model.UserAccounts;

import java.util.*;

public class GlobalMailSenderRepository {
	
	
	public void sendAuthorisationEmailTo(String email, String authenticator) {
        try {
            Message message = new MimeMessage(authoriseEmailConnection());
            message.setFrom(new InternetAddress("teamprojectalms@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(email));
            message.setSubject("Account Registration | Team Project A");
            message.setText("Welcome To The Team Project A LMS!" + 
            		" \n\n To complete registration securely, please reigster your details"+
            		" by clicking the link below. \n\n Happy Learning! \n The Team @ Team Project A \n\n\n" +
            		"http://localhost:8080/frontend/pages/registration.html?auth=" + authenticator );

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
	}
	
	public void sendNotifierOfTopicStartToManager(String traineeUserName,String courseId) {
		try {
			Message message = new MimeMessage(authoriseEmailConnection());
			message.setFrom(new InternetAddress("teamprojectalms@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ian2009saul@gmail.com"));
			message.setSubject(traineeUserName + " has commenced training | Team Project A - LMS");
			message.setText("This is an automated message to inform you that " + traineeUserName + " has commenced '"+courseId + "'." +
			"\n\n No further action is required at this time." +
			"\n\n The Team @ Team Project A");
			 Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendNotifierOfTopicFirstFailureToManager(String traineeUserName) {
		try {
			Message message = new MimeMessage(authoriseEmailConnection());
			message.setFrom(new InternetAddress("teamprojectalms@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ian2009saul@gmail.com"));
			message.setSubject(traineeUserName + " has failed training | Team Project A - LMS");
			message.setText("This is an automated message to inform you that " + traineeUserName + " has failed 'Introduction to Python'." +
			"\n\n To take appropriate action, visit the Manager Administration Dashboard." +
			"\n\n  http://localhost:8080/frontend/pages/managerAdmin.html"+
			"\n\n The Team @ Team Project A");
			 Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendNotifierOfTopicPassToManager(String traineeUserName) {
		try {
			Message message = new MimeMessage(authoriseEmailConnection());
			message.setFrom(new InternetAddress("teamprojectalms@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ian2009saul@gmail.com"));
			message.setSubject(traineeUserName + " has completed 'Introduction to Python' | Team Project A - LMS");
			message.setText("This is an automated message to inform you that " + traineeUserName + " has passed 'Introduction to Python'." +
			"\n\n No further action is required at this time. " + traineeUserName + " is legible to be assigned new material on the Manager Administration Dashboard. " + 
			"\n\n The Team @ Team Project A");
			 Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Session authoriseEmailConnection() {
	       final String username = "teamprojectalms@gmail.com";
	        final String password = "jriamerica2018";

	        Properties props = new Properties();
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");

	        return Session.getInstance(props,
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	          });
	}};
