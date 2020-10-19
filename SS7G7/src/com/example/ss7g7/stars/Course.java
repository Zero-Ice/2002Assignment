package com.example.ss7g7.stars;

public class Course {
	private String courseCode;
	private String courseName;
	
	public Course(String course_Code, String course_Name) {
		this.setCourseCode(course_Code);
		this.setCourseName(course_Name);
		
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}
