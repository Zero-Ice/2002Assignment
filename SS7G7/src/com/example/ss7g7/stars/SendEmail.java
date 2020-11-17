package com.example.ss7g7.stars;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 * The class SendEmail is to establish
 * a connection with the SMTP server "gmail.com"
 * to send a customized email from a dummy gmail account
 * 
 * @author Angelina
 * created on 2020/10/15
 * 
 * @version %I%
 * @since 1.0
 */

public class SendEmail {
	
	private String senderEmailID;
	private String senderPassword;
	private String emailSMTPserver;
	private String emailServerPort;
	private String receiverEmailID;
	private String emailSubject;
	private String emailBody;
	
	/**
	 * This is the constructor of the class SendEmail
	 */
	SendEmail(){
		
		senderEmailID = "testperson324@gmail.com";
		senderPassword = "Pass,12345";
		emailSMTPserver = "smtp.gmail.com";
		emailServerPort = "465";
		receiverEmailID = "";
		emailSubject = "Test Mail";
		emailBody = "";
	}
	
	
	/**
	 * This method sends a customized email
	 * based on the given parameters via a dummy account
	 * 
	 * @param receiverEmailID
	 * @param Subject
	 * @param Body
	 * 
	 * @see {@link SMTPAuthenticator#SMTPAuthenticator(String, String)}
	 */
	public void email(String receiverEmailID, String Subject, String Body){
	     
	    // Receiver Email Address
	    this.receiverEmailID=receiverEmailID; 
	    // Subject
	    this.emailSubject=Subject;
	    // Body
	    this.emailBody=Body;
	    
	    Properties props = new Properties();
	    props.put("mail.smtp.user",senderEmailID);
	    props.put("mail.smtp.host", emailSMTPserver);
	    props.put("mail.smtp.port", emailServerPort);
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.socketFactory.port", emailServerPort);
	    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.socketFactory.fallback", "false");
	    SecurityManager security = System.getSecurityManager();
	    
	    
	    try{  
		    Authenticator auth = new SMTPAuthenticator(senderEmailID, senderPassword);
		    Session session = Session.getInstance(props, auth);
		    MimeMessage msg = new MimeMessage(session);
		    msg.setText(emailBody);
		    msg.setSubject(emailSubject);
		    msg.setFrom(new InternetAddress(senderEmailID));
		    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmailID));
		    Transport.send(msg);
		    System.out.println("Message send Successfully"); 
	    }
	    catch (Exception e){
	    	e.printStackTrace();
	    }
	    
	    
    }

}
