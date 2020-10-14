package com.example.ss7g7.stars;

public class Student {
	private String name;
	private String matricNo;
	private String username;
	private String gender;
	private String nationality;

	public Student(String name, String matricNo, String username, String gender, String nationality) {
		this.name = name;
		this.matricNo = matricNo;
		this.username = username;
		this.gender = gender;
		this.nationality = nationality;
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
