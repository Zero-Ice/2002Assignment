package com.example.ss7g7.stars;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Course {
	private String courseCode;
	private String courseName;
	private String schooName;
	private String lecVenue;
	private Date lecDateTime;
	private ArrayList<Index> indexes; // array of indexes
	private boolean courseAvailability;
	//TODO: courseAvailability set
	//TODO: Venue for lec 
	//TODO: Day and Time for lec 
	
	public Course(String course_Code, String course_Name, String school_Name) {
		this.courseCode=course_Code;
		this.courseName=course_Name;
		this.indexes = new ArrayList<Index>();
		this.schooName = school_Name;
		this.lecDateTime= new Date();
		this.lecVenue ="";
		this.courseAvailability =true;
	}
	
	///getter-- use  inputted index to get index object (to access everything in specified index)
	public Index getIndex(int index) {
		for(int i = 0; i < indexes.size(); i++) {
			if(indexes.get(i).getIndexNum() == index) {
				return indexes.get(i);
			}
		}
		
		return null;
	}
	
	public void showSeats(int index) {
		getIndex(index).showAllSeats();
	}
	
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
	
	
	
	
	public void showAllIndexDetails() {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			System.out.println("Index: "+indexes.get(i).getIndexNum() + " Vacancies: " + indexes.get(i).showNumOfVacancies());
		}
	}
	
	public void checkClash(String matricNo) {
		int sizeOfIndex= indexes.size();
		
		
		for(int i = 0; i<sizeOfIndex;i++) {
			indexes.get(i);
		}
	}
	
//////////////////////////////////////////////////     STUDENT assign & unassign             ///////////////////////////////////////
	public void assignStudent(int index, String matricNo) {
		getIndex(index).assignStudent(matricNo);
	}
	
	public void unassignStudent(int index, String matricNo) {
		getIndex(index).unassignStudent(matricNo);
	}
	
///////////////////////                    ADMIN indexes              ////////////////////////////
	public void addIndex(int index, int vacancy) {

		if(getIndex(index)==null) {
			indexes.add(new Index(index,vacancy));
			System.out.println("Index "+index+" was successfully added to "+ courseCode);
		}else {
			System.out.println("Index "+index+" already exists in "+ courseCode + "!");
		}
		
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
		getIndex(index).setIndexNum(newIndex);
		System.out.println("Index "+index+" updated to " + newIndex);
		
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
///////////////////////                    ADMIN lec/tut/lab venue              ////////////////////////////
	
	public void setTutVenue(int index, String tutVenue) {
		getIndex(index).setTutVenue(tutVenue);
		System.out.println("Tutorial veneue "+tutVenue+" was successfully added to index "+ getIndex(index).getIndexNum());
	}
	
	public void setLabVenue(int index, String labVenue) {
		getIndex(index).setLabVenue(labVenue);
		System.out.println("Lab veneue "+labVenue+" was successfully added to index "+ getIndex(index).getIndexNum());
	}
	
	public void setLecVenue(String lecVenue) {
		this.lecVenue = lecVenue;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////////////////////                    SET lec/tut/lab dateTime              ////////////////////////////	
	
	public void setTutDateTime(int index,int hours, int minutes) {
		getIndex(index).setTutDateTime(hours, minutes);
	}
	
	public void setLabDateTime(int index,int hours, int minutes) {
		getIndex(index).setTutDateTime(hours, minutes);
	}
	
	public void setLecDateTime(int index,int hours, int minutes) {
		lecDateTime.setDate(21);
		lecDateTime.setHours(hours);
		lecDateTime.setMinutes(minutes);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
///////////////////////                    edit Course Details             ////////////////////////////
	public void updateCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	public void updateCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public void updateSchooName(String schooName) {
		this.schooName = schooName;
	}
	
	public void setCourseAvailability(boolean isAvailable) {
		courseAvailability = isAvailable;
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public String getCourseCode() {
		return courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getSchooName() {
		return schooName;
	}

	
	public String getLecVenue() {
		return lecVenue;
	}
	
	public boolean getCourseAvailability() {
		return courseAvailability;
	}

	public void getLecDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("E hh:mm a");
		System.out.println(formatter.format(lecDateTime));
	}
	
	public void getTutDateTime(int index) {
		getIndex(index).getTutDateTime();
	}
	
	public void getLabDateTime(int index) {
		getIndex(index).getLabDateTime();
	}
	
	
}
