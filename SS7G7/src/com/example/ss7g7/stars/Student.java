package com.example.ss7g7.stars;

import java.util.ArrayList;

public class Student {
	private String name;
	private String matricNo;
	private String username;
	private String gender;
	private String nationality;
	private ArrayList<Integer> indexes;

	public Student(String name, String matricNo, String username, String gender, String nationality) {
		this.name = name;
		this.matricNo = matricNo;
		this.username = username;
		this.gender = gender;
		this.nationality = nationality;
		indexes = new ArrayList<Integer>();
	}
	
	public void setCourses(ArrayList<Integer> indexes) {
		this.indexes.clear();
		this.indexes = indexes;
	}
	
	public void setCourses(int[] indexes) {
		this.indexes.clear();
		for(int i : indexes) {
			this.indexes.add(i);
		}
	}
	
	public boolean addCourse(int indexNo) {
		// TODO: Check if a mod has already been added. 
		// E.g cannot have 2 indexes that belong to the same mod
		
		indexes.add(indexNo);
		
		return true;
	}
	
	public boolean dropCourse(int indexNo) {
		return indexes.remove(Integer.valueOf(indexNo));
	}
	
	public boolean containsCourse(int indexNo) {
		if(indexes.contains(indexNo)) return true;
		
		return false;
	}
	
	public String printCourses() {
		String s = "";
		for(int i = 0; i < indexes.size(); i++) {
			s += Integer.toString(indexes.get(i));
			if(i == indexes.size() - 1) break;
			
			s += ", ";
		}
		return s;
	}
	
	public ArrayList<Integer> getCourseIndexes() {
		return indexes;
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
