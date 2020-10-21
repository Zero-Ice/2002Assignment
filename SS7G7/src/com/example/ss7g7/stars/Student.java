package com.example.ss7g7.stars;

import java.util.ArrayList;
import java.util.Calendar;


public class Student {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String matricNo;
	private String gender;
	private String nationality;
	private int mobileNo;
	private String email;
	private Calendar accessStart;
	private Calendar accessEnd;
	private ArrayList<RegisteredCourse> courses;



	public Student(String username, String password, String firstName, String lastName, String matricNo, String gender, String nationality,int mobileNo, String email, Calendar accessStart, Calendar accessEnd) {
		
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.matricNo = matricNo;
		this.gender = gender;
		this.nationality = nationality;
		this.mobileNo = mobileNo;
		this.email = email;
		this.accessStart = accessStart;
		this.accessEnd = accessEnd;
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
	

	public String getUserName() {
		return username;
	}

	public void getUserName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void getPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String matricNo() {
		return matricNo;
	}

	public void matricNo(String matricNo) {
		this.matricNo = matricNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public int getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(int mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getAccessStart() {
		return accessStart;
	}

	public void setAccessStart(Calendar accessStart) {
		this.accessStart = accessStart;
	}

	public Calendar getAccessEnd() {
		return accessEnd;
	}

	public void setAccessEnd(Calendar accessEnd) {
		this.accessEnd = accessEnd;
	}

}
