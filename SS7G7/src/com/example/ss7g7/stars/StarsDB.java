package com.example.ss7g7.stars;

import java.util.ArrayList;

public class StarsDB {
	private String studentDataFilePath;
	private String courseDataFilePath;
	
	private ArrayList<Student> students;
	private ArrayList<Course> courses;
	
	public StarsDB(String studentDataFilePath, String courseDataFilePath) {
		this.studentDataFilePath = studentDataFilePath;
		this.courseDataFilePath = courseDataFilePath;
		students = new ArrayList<Student>();
		courses = new ArrayList<Course>();
	}
	
	/*
	 * Loads data from the files using the file paths given
	 */
	public boolean init() {
		// TODO: File I/O
		
		createDebugCourses();
		
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
		s.addCourse(123456);
		return s;
	}
	
	// TODO: courses
	
	public void createDebugCourses() {
		Course c = new Course("CZ2002", "OODP", "SCSE");
		c.addIndex(123456, 30);
		courses.add(c);
	}
	
	public Course getCourse(int indexNo) {
		for(int i = 0; i < courses.size(); i++) {
			if(courses.get(i).getIndex(indexNo)) return courses.get(i);
		}
		
		return null;
	}
}
