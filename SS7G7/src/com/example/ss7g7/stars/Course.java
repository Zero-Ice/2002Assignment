package com.example.ss7g7.stars;

import java.util.ArrayList;

public class Course {
	private String courseCode;
	private String courseName;
	private String schooName;
	private String lecVenue;
	private String lecDateTime;
	private ArrayList<Index> indexes; // array of indexes
	private boolean courseAvailability;
	//TODO: courseAvailability set
	//TODO: Venue for lec 
	//TODO: Day and Time for lec 
	
	public Course(String course_Code, String course_Name, String school_Name) {
		this.courseCode=course_Code;
		this.courseName=course_Name;
		indexes = new ArrayList<Index>();
		this.schooName = school_Name;
	}
	
	public void showSeats(int index) {
		int sizeOfIndex= indexes.size();
		
		for(int i = 0; i<sizeOfIndex;i++) {
			if(indexes.get(i).getIndexNum()==index) {
				indexes.get(i).showAllSeats();
				return ;
			}
		}
	}
	
	
	
	//////////////////////////////////////////////////     STUDENT assign & unassign             ///////////////////////////////////////
	//TODO: check for index clash 
	public void assignStudent(int index, String matricNo) {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			if(indexes.get(i).getIndexNum()==index) {
				indexes.get(i).assignStudent(matricNo);
				return ;
			}
		}
	}
	
	public void unassignStudent(int index, String matricNo) {
		int sizeOfIndex= indexes.size();
		
		for(int i = 0; i<sizeOfIndex;i++) {
			if(indexes.get(i).getIndexNum()==index) {
				indexes.get(i).unassignStudent(matricNo);
				return ;
			}
		}
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	public void showfullCourseDetails() {
		String op = getCourseCode() +" "+getCourseName()+" "+getSchooName();
		System.out.println(op);	
		showAllIndexDetails();
	}
	
	
	public boolean containsIndexNo(int index) {
		int sizeOfIndex= indexes.size();
		
		for(int i = 0; i<sizeOfIndex;i++) {
			if(indexes.get(i).getIndexNum()==index) {
				return true;
			}
		}
		
		return false;
	}
	
	public Index getIndex(int indexNo) {
		for(int i = 0; i < indexes.size(); i++) {
			if(indexes.get(i).getIndexNum() == indexNo) {
				return indexes.get(i);
			}
		}
		
		return null;
	}
	
	public void showAllIndexDetails() {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			System.out.println("Index: "+indexes.get(i).getIndexNum() + " Vacancies: " + indexes.get(i).showNumOfVacancies());;
		}
	}
	
///////////////////////                    ADMIN Add/Edit/Remove indexes              ////////////////////////////
	public void addIndex(int index, int vacancy) {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			if(indexes.get(i).getIndexNum()==index) {
				System.out.println("Index "+index+" already exist.");
				return;
			}
		}
		
		indexes.add(new Index(index,vacancy));
		System.out.println("Index "+index+" was successfully added to "+ courseCode);
	}
	
	public void removeIndex(int index) {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			if(indexes.get(i).getIndexNum()==index) {
				indexes.remove(i);
				System.out.println("Index "+index+" was successfully removed!");
				return;
			}
		}
		System.out.println("Index "+index+" does not exist");
	}
	
	public void updateIndex(int index, int newIndex) {
		int sizeOfIndex= indexes.size();
		
		for(int i = 0; i<sizeOfIndex;i++) {
			if(indexes.get(i).getIndexNum()==index) {
				indexes.get(i).setIndexNum(newIndex);
				return;
			}
		}

		System.out.println("Index "+index+" was not found.");
		
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void updateCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void updateCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSchooName() {
		return schooName;
	}

	public void updateSchooName(String schooName) {
		this.schooName = schooName;
	}
	public String getLecVenue() {
		return lecVenue;
	}

	public void setLecVenue(String lecVenue) {
		this.lecVenue = lecVenue;
	}

	public String getLecDateTime() {
		return lecDateTime;
	}

	public void setLecDateTime(String lecDateTime) {
		this.lecDateTime = lecDateTime;
	}
	
}
