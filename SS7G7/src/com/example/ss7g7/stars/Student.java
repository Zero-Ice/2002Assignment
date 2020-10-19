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
	
	public void SetMods(ArrayList<Integer> indexes) {
		this.indexes.clear();
		this.indexes = indexes;
	}
	
	public void SetMods(int[] indexes) {
		this.indexes.clear();
		for(int i : indexes) {
			this.indexes.add(i);
		}
	}
	
	public boolean AddMod(int modIndex) {
		// TODO: Check if a mod has already been added. 
		// E.g cannot have 2 indexes that belong to the same mod
		
		indexes.add(modIndex);
		
		return true;
	}
	
	public boolean RemoveMod(int modIndex) {
		return indexes.remove(Integer.valueOf(modIndex));
	}
	
	public boolean ContainsMod(int modIndex) {
		if(indexes.contains(modIndex)) return true;
		
		return false;
	}
	
	public String printMods() {
		String s = "Registered mods: [";
		for(int i = 0; i < indexes.size(); i++) {
			s += Integer.toString(indexes.get(i));
			if(i == indexes.size() - 1) break;
			
			s += ", ";
		}
		s += "]";
		return s;
	}
	
	@Override
	public String toString() {
		String s = "Name: " + name + "\n"
				+ "Matriculation Number: " + matricNo + "\n"
				+ "Username: " + username + "\n"
				+ "Gender: " + gender + "\n"
				+ "Nationality: " + nationality + "\n"
				+ printMods();
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
