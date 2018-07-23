package com.systems.business;


import javax.mail.*;
import javax.mail.internet.*;

import com.systems.model.UserAccounts;
import com.systems.repository.Repo;

import java.util.*;

public class GlobalMailSenderRepository {
	
	public void sendAuthorisationEmailTo(UserAccounts entity, Repo repository, String authenticator) {
        try {
            Message message = new MimeMessage(authoriseEmailConnection());
            message.setFrom(new InternetAddress("teamprojectalms@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(entity.getEmail()));
            message.setSubject("Account Registration | Team Project A");
            message.setText("Dear " + entity.getFirstName() + 
            		", \n\n To complete registration securely, please authenticate your details"+
            		" by clicking the link below. \n\n Happy Learning! \n The Team @ Team Project A \n\n\n" +
            		"http://localhost:8080/frontend/pages/activation.html?auth=" + authenticator );

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
