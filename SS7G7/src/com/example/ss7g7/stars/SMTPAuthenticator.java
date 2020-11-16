package com.example.ss7g7.stars;

import javax.mail.PasswordAuthentication;

/**
 * The class SMTPAuthenticator is to
 * authenticate the dummy gmail account 
 * 
 * @author Angelina
 * created on 2020/10/15
 * 
 * @version %I%
 * @since 1.0
 */

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