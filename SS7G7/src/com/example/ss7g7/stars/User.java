package com.example.ss7g7.stars;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4659485486899989487L;
	protected String username;
	protected String password;
	
	private UserType userType;
	public enum UserType{
		NIL,
		STUDENT,
		ADMIN,
		MAX_USER_TYPE,
	}
	
	// TODO: Change constructor
	public User(String username, String password) {
		userType = userType.NIL;
		this.username = username;
		this.password = password;
	}
	
	public UserType getUserType() {
		return userType;
	}
	
	public String getUsername() {
		return username;
	}
	

	void setUser(String username) {
		this.username = username;
	}
	
	void setPass(String password) {
		this.password = password;
	}
	
	void setUserType(UserType type) {
		this.userType = type;
	}
	
	String getUser() {
		return username;
	}
	
	String getPass() {
		return password;
	}
}
