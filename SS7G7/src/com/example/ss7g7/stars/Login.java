package com.example.ss7g7.stars;

import java.util.*;

import com.example.ss7g7.stars.User.UserType;

import java.io.Console;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * The Login class is to log and authenticate
 * users trying to access the StarsSystem
 * 
 * @author Ong Rui Peng
 * @author Angelina
 * created on 2020/10/15
 * 
 * @version %I%
 * @since 1.0
 */
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

	/**
	 * This is a constructor that
	 * requires a given instance of the singleton StarsDB
	 * to set its own private variable of the database with the 
 	 * initialized instance of the database
	 * 
	 * @param db refers to the initialized instance of the database
	 */
	Login(StarsDB db) {
		this.db = db;
		
	}
	
	/**
	 * This method gets the user inputted credentials and
	 * authenticates the user against stored user credentials
	 * in the database.
	 * 
	 * <p>
	 * Once the user has been authenticated, user will be
	 * streamed into <code>UserType.ADMIN</code> or <code>UserType.STUDENT</code>
	 * 
	 * @return	<code>UNSUCCESSFUL_LOGIN</code> if authentication fails; 
	 * 			<code>SUCCESSFUL_LOGIN</code> if authentication is successful.
	 */
	LOGIN_RESULT login(){
		
		passClear = new StringBuilder();
		String passCipher = "";
		String auth = new String();
        HashMap<String, String> hmap = db.getDBLoginCred();
       
		//-----------------Log user input---------------------//
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
		
		return LOGIN_RESULT.UNSUCCESSFUL_LOGIN;
	}
	
	/**
	 * This method returns the current user
	 * 
	 * @return object <code>user</code>
	 */
	User getCurrentUser () {
		return this.user;
	}
	
	/**
	 * This method retrieves user inputted credentials
	 * 
	 */
	void getLoginCred () {
		
		String username;
		//char [] pass = {};
		
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
		
		
		passCipher = db.hash(pass);
		
		//passCipher = db.hash(String.valueOf(pass));
		user = new User(username, passCipher);
//		this.user.setUser(username);
//		this.user.setPass(passCipher);
		
		
	}
	
	
	/**
	 * This method authenticates the user
	 * by cross checking the user credentials stored in the database
	 * 
	 * @param hmap refers to the key value pairs retrieved from the database
	 * @return String <code>privilege</code> of user 
	 */
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
	 	}else {
	 		System.out.println("User does not exist in DB");
	 	}

	 	return privilege;
		
	}
	

	
	
}

