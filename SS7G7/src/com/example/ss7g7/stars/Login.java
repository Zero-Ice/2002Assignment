package com.example.ss7g7.stars;

import java.util.*;


import java.io.Console;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login {
	
	public enum LOGIN_RESULT {
		NIL_LOGIN_RESULT,
		SUCCESSFUL_LOGIN,
		UNSUCCESSFUL_LOGIN,
		MAX_LOGIN_RESULT
	}
	private User user = new User();
	private StringBuilder passClear = new StringBuilder();
	private String passCipher;  
	private StarsDB db;

	Login(StarsDB db) {
		this.db = db; 
	}
	
	LOGIN_RESULT login(){
		String auth = new String();
        HashMap<String, String> hmap = db.getDBLoginCred();
       
		//-----------------Log user--------------------------//
		getLoginCred();
		
		//---------------Authenticate user-----------------//
		auth = authUser(hmap);
		if (auth == "denied") {
			System.out.println("Invalid credentials");
			return LOGIN_RESULT.UNSUCCESSFUL_LOGIN;
		}
		else if (auth == "student") {
			System.out.println("Welcome");
			return LOGIN_RESULT.SUCCESSFUL_LOGIN;
		}
		
		return LOGIN_RESULT.SUCCESSFUL_LOGIN;
	}
	
	User getCurrentUser () {
		return this.user;
	}
	
	User getLoginCred () {
		
		String username;
		//char [] pass;
		
		Scanner sc = new Scanner(System.in);
		//-----------FOR MASKING OF PASSWORD DO NOT DELETE
		//Console console = System.console() ;
		
		System.out.println("Please enter your username: ");
		username = sc.nextLine();
		
		//-----------FOR MASKING OF PASSWORD DO NOT DELETE
//		if (console != null) {
//			pass = console.readPassword("Enter password: ");
//			passClear.append(pass);
//		}
		
		System.out.println("Please enter your password: ");
		String pass = sc.nextLine();
		
		
		passCipher = hash(pass);
		this.user.setUser(username);
		this.user.setPass(passCipher);
		
		return this.user;
		
	}
	
	String hash (String passClear) {
		
		String hash = new String();
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedHash = digest.digest(passClear.toString().getBytes(StandardCharsets.UTF_8));
			
			// Convert byte array into signum representation  
			BigInteger number = new BigInteger(1, encodedHash);
  
	        // Convert message digest into hex value  
			StringBuilder hexString = new StringBuilder(number.toString(16)); 
			
	        // Pad with leading zeros 
	        while (hexString.length() < 32)  
	        {  
	            hexString.insert(0, '0');  
	        }  
	  
	        hash = hexString.toString();  
	 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hash;
	
	}
	
	String authUser (HashMap<String, String> hmap) {
		
	 	Set set = hmap.entrySet();
	 	Iterator i = set.iterator();
	 	String key = hmap.get(this.user.getUser());
	 	String privilege = "denied";
	 	
	 	if (key != null) {
	 		
	 		if (this.user.getPass().toString().equals(key)) {
	 			privilege = "student";
	 			return privilege;
	 		}
	 	}

	 	return privilege;
		
	}
	

	
	
}

