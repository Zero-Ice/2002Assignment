package com.example.ss7g7.stars;

import java.util.*;

import com.example.ss7g7.stars.User.UserType;

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
	private User user;
	private StringBuilder passClear;
	private String passCipher;  
	private StarsDB db;

	Login(StarsDB db) {
		this.db = db;
		
	}
	
	LOGIN_RESULT login(){
		
		user = new User();
		passClear = new StringBuilder();
		String passCipher = "";
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
			
			this.user.setUserType(UserType.STUDENT);
			System.out.println("Welcome");
			return LOGIN_RESULT.SUCCESSFUL_LOGIN;
		}
		else if (auth == "admin") {
			
			this.user.setUserType(UserType.ADMIN);
			System.out.println("Welcome");
			return LOGIN_RESULT.SUCCESSFUL_LOGIN;
		}
		
		return LOGIN_RESULT.SUCCESSFUL_LOGIN;
	}
	
	User getCurrentUser () {
		return this.user;
	}
	
	void getLoginCred () {
		
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
	 	String privilege = "denied";
		String key = hmap.get(this.user.getUser());
		String cipherPass = "";
	 	
		
	 	if (key != null) {
	 		
	 		cipherPass = key.split("-")[0];
	 		privilege = key.split("-")[1];
	 		
	 		if (this.user.getPass().toString().equals(cipherPass) && privilege.equals("student")) {
	 			privilege = "student";
	 		
	 		}else if (this.user.getPass().toString().equals(cipherPass) && privilege.equals("admin")) {
	 			privilege = "admin";
	 		
	 		}
	 	}

	 	return privilege;
		
	}
	

	
	
}

