package com.example.ss7g7.stars;

import java.io.Serializable;

public class RegisteredCourse implements Serializable{

	private String courseCode;
	private int indexNo;
	
	public RegisteredCourse(String courseCode, int indexNo) {
		this.courseCode = courseCode;
		this.indexNo = indexNo;
	}
	
	public String getCourseCode() {
		return courseCode;
	}
	
	public int getIndexNo() {
		return indexNo;
	}
}
