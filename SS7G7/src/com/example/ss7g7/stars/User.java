package com.example.ss7g7.stars;

import java.io.Serializable;

/**
 * The User class is a base class
 * that evaluates the privilege of
 * the current user
 * 
 * @author Angelina
 * @version 1.0
 * @since 2020/10/15
 */

public class User implements Serializable {
	
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
	/**
	 * This is the constructor for User class
	 * that requires the username and password for
	 * instantiation
	 * 
	 * @param username
	 * @param password
	 */
	public User(String username, String password) {
		userType = userType.NIL;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * This method returns the enum <code>UserType</code>
	 * 
	 * @return userType if not <code>null</code>;
	 */
	public UserType getUserType() {
		return userType;
	}
	
	/**
	 * This method returns the username of
	 * the current User object
	 * 
	 * @return username if not <code>null</code>;
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * This method returns the username
	 * of the current user
	 * 
	 * @return username if not <code>null</code>;
	 * @deprecated Use {@link User#getUsername()};
	 */
	String getUser() {
		return username;
	}
	
	/**
	 * This method returns the password
	 * of the current user
	 * 
	 * @return password
	 */
	String getPass() {
		return password;
	}
	

	/**
	 * This method sets the username of
	 * the User object
	 * 
	 * @param username
	 */
	void setUser(String username) {
		this.username = username;
	}
	
	/**
	 * This method sets the password of
	 * the User object
	 * 
	 * @param password
	 */
	void setPass(String password) {
		this.password = password;
	}
	
	/**
	 * This method sets the user type of
	 * the User object which includes:
	 * <li>NIL
	 * <li>STUDENT
	 * <li>ADMIN
	 * <li>MAX_USER_TYPE
	 * 
	 * @param type
	 */
	void setUserType(UserType type) {
		this.userType = type;
	}
	
	
}
