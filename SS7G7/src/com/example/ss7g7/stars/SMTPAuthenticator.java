package com.example.ss7g7.stars;

import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends javax.mail.Authenticator
{
	private String senderEmailID;
	private String senderPassword;
	
	SMTPAuthenticator(String senderEmailID, String senderPassword) {
		this.senderEmailID = senderEmailID;
		this.senderPassword = senderPassword;
	}
	
    public PasswordAuthentication getPasswordAuthentication()
    {
    	return new PasswordAuthentication(senderEmailID, senderPassword);
    }
}