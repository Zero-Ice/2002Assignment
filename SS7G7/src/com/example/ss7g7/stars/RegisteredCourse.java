package com.example.ss7g7.stars;

public class RegisteredCourse {

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
