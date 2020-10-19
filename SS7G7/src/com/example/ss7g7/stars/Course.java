package com.example.ss7g7.stars;

import java.util.ArrayList;

public class Course {
	private String courseCode;
	private String courseName;
	private ArrayList<Index> indexes; // array of indexes
	private boolean courseAvailability;
	
	public Course(String course_Code, String course_Name) {
		this.courseCode=course_Code;
		this.courseName=course_Name;
		indexes = new ArrayList<Index>();
	}
	
	public void addIndex(int index, int vacancy) {
		indexes.add(new Index(index,vacancy));
	}
	
	public void removeIndex(int index) {
		
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
