package com.example.ss7g7.stars;

public class User {
	private String username;
	private String password;
	
	private UserType userType;
	public enum UserType{
		NIL,
		STUDENT,
		ADMIN,
		MAX_USER_TYPE,
	}
	
	// TODO: Change constructor
	public User() {
		userType = userType.NIL;
	}
	
	public UserType getUserType() {
		return userType;
	}
	
	public String getUsername() {
		return username;
	}
}
