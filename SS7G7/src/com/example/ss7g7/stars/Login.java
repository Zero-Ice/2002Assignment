package com.example.ss7g7.stars;

import java.util.*;
import java.io.Console;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login {
	
	private User user = new User();
	private StringBuilder passClear = new StringBuilder();
	private String passCipher; 

	User.UserType Login() {
		
		List [] credentials = new List [2];
		List<String> username = new ArrayList<String>();
        List<String> pass = new ArrayList<String>();
        HashMap<String, String> hmap = new HashMap<String, String>();
        String auth = new String();
        
		//--------------- Get user credentials --------------------//
		CSVReader read = new CSVReader();
		read.readFile();
		credentials = read.readFile();
		username = credentials[0];
		pass = credentials[1];
		
		for (int i=0; i< username.size(); i++) {
			hmap.put(username.get(i), pass.get(i));
		}
		
		//-----------------Log user--------------------------//
		Login login = new Login();
		login.getLoginCred();
		
		//---------------Authenticate user-----------------//
		auth = login.authUser(hmap);
		if (auth == "denied") {
			System.out.println("Invalid credentials");
		}
		else if (auth == "student") {
			System.out.println("Welcome");
			return User.UserType.STUDENT;
		}
		return User.UserType.NIL;
		
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

