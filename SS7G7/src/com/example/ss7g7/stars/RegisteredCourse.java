package com.example.ss7g7.stars;

import java.io.Serializable;

/**
 * The class RegisteredCourse
 * is stored as an ArrayList in the base class
 * Student as a record of all the classes that
 * the Student object is registered in.
 * 
 * @author 
 * @version 1.0
 * @since 2020/10/15
 */
public class RegisteredCourse implements Serializable{

	private static final long serialVersionUID = -6490197148516823632L;
	
	private String courseCode;
	private int indexNo;
	private String status;
	
	/**
	 * This is a constructor that
	 * sets a course as registered
	 * 
	 * @param courseCode
	 * @param indexNo
	 */
	public RegisteredCourse(String courseCode, int indexNo) {
		this.courseCode = courseCode;
		this.indexNo = indexNo;
		status = "Registered";
	}
	
	/**
	 * This is a constructor that
	 * takes in the parameter of registration status
	 * 
	 * @param courseCode
	 * @param indexNo
	 * @param status
	 */
	public RegisteredCourse(String courseCode, int indexNo, String status) {
		this.courseCode = courseCode;
		this.indexNo = indexNo;
		this.status = status;
	}
	
	/**
	 * This method will return the course code
	 * 
	 * @return String <code>courseCode</code> if not <code>null</code>;
	 */
	public String getCourseCode() {
		return courseCode;
	}
	
	/**
	 * This method will return the index code of the course
	 * 
	 * @return Integer <code>indexNo</code> if not <code>null</code>;
	 */
	public int getIndexNo() {
		return indexNo;
	}
	
	/**
	 * This method will return the registration 
	 * status of the course
	 * 
	 * @return String <code>status</code> if not <code>null</code>;
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * This method sets the 
	 * registration status of the course
	 * 
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
