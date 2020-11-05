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
