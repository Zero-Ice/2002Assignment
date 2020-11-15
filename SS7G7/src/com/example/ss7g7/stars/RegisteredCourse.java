package com.example.ss7g7.stars;

import java.io.Serializable;

public class RegisteredCourse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6490197148516823632L;
	
	private String courseCode;
	private int indexNo;
	private String status;
	
	public RegisteredCourse(String courseCode, int indexNo) {
		this.courseCode = courseCode;
		this.indexNo = indexNo;
		status = "Registered";
	}
	
	public RegisteredCourse(String courseCode, int indexNo, String status) {
		this.courseCode = courseCode;
		this.indexNo = indexNo;
		this.status = status;
	}
	
	public String getCourseCode() {
		return courseCode;
	}
	
	public int getIndexNo() {
		return indexNo;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
