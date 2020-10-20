package com.example.ss7g7.stars;

import java.util.ArrayList;

public class Student {
	private String name;
	private String matricNo;
	private String username;
	private String gender;
	private String nationality;
	private ArrayList<RegisteredCourse> courses;

	public Student(String name, String matricNo, String username, String gender, String nationality) {
		this.name = name;
		this.matricNo = matricNo;
		this.username = username;
		this.gender = gender;
		this.nationality = nationality;
		courses = new ArrayList<RegisteredCourse>();
	}
	
	public void setCourses(ArrayList<RegisteredCourse> indexes) {
		this.courses.clear();
		this.courses = indexes;
	}
	
	public boolean addCourse(String courseCode, int indexNo) {
		// TODO: Check if a mod has already been added. 
		// E.g cannot have 2 indexes that belong to the same mod
		
		courses.add(new RegisteredCourse(courseCode, indexNo));
		
		return true;
	}
	
	public boolean dropCourse(int indexNo) {
		for(int i = 0; i < courses.size(); i++) {
			if(courses.get(i).getIndexNo() == indexNo) {
				courses.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean containsCourse(int indexNo) {
		for(RegisteredCourse rc : courses) {
			if(rc.getIndexNo() == indexNo) return true;
		}
		
		return false;
	}
	
	public boolean containsCoure(String courseCode) {
		for(RegisteredCourse rc : courses) {
			if(rc.getCourseCode() == courseCode) return true;
		}
		
		return false;
	}
	
	public String printCourses() {
		String s = "";
		
		if(courses.size() == 0) {
			s += "No courses registered";
			return s;
		}
		
		for(int i = 0; i < courses.size(); i++) {
			s += courses.get(i).getCourseCode() + " " + courses.get(i).getIndexNo();
			if(i == courses.size() - 1) break;
			
			s += ", ";
		}
		return s;
	}
	
	public ArrayList<RegisteredCourse> getCourses() {
		return courses;
	}
	
	@Override
	public String toString() {
		String s = "Name: " + name + "\n"
				+ "Matriculation Number: " + matricNo + "\n"
				+ "Username: " + username + "\n"
				+ "Gender: " + gender + "\n"
				+ "Nationality: " + nationality + "\n"
				+ printCourses();
		return s;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMatricNo() {
		return matricNo;
	}

	public String getUsername() {
		return username;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getNationality() {
		return nationality;
	}
}
