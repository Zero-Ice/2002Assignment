package com.example.ss7g7.stars;

import java.util.ArrayList;

public class StarsDB {
	private String studentDataFilePath;
	private String courseDataFilePath;
	
	private ArrayList<Student> students;
	
	public StarsDB(String studentDataFilePath, String courseDataFilePath) {
		this.studentDataFilePath = studentDataFilePath;
		this.courseDataFilePath = courseDataFilePath;
		students = new ArrayList<>();
	}
	
	/*
	 * Loads data from the files using the file paths given
	 */
	public boolean init() {
		// TODO: File I/O
		
		
		return true;
	}
	
	public ArrayList<Student> getAllStudents() {
		return students;
	}
	
	public Student getStudent(String userName) {
		for(int i = 0; i < students.size(); i++) {
			if(userName == students.get(i).getUsername()) {
				return students.get(i);
			}
		}
		return null;
	}
	
	public Student getDebugStudent() {
		Student s = new Student("Dum dum", "U1969420", "DD69", "A", "Antartica");
		
		return s;
	}
	
	// TODO: courses
}
