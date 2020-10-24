package com.example.ss7g7.stars;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Course implements Serializable{
	private String courseCode;
	private String courseName;
	private String schooName;
	private String lecVenue;
	private String lecRemark;
	private String lecGroup;
	private int AU;
	private Date lecStartTime;
	private Date lecEndTime;
	private String lecDay;
	private ArrayList<Index> indexes; // array of indexes
	private boolean courseAvailability;
	
	public Course(String course_Code, String course_Name, String school_Name, int au) {
		this.courseCode=course_Code;
		this.courseName=course_Name;
		this.AU=au;
		this.indexes = new ArrayList<Index>();
		this.schooName = school_Name;
		this.lecStartTime= new Date();
		this.lecEndTime= new Date();
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
	
	public void showAllSeatVacancyForCourse() {
		System.out.println(getCourseCode()+" "+ getCourseName());
		for(int i = 0; i < indexes.size(); i++) {
			System.out.println("Index "+indexes.get(i).getIndexNum());
			indexes.get(i).showAllSeats();;
		}
	}
	
	public void showfullCourseDetails() {
		String op = getCourseCode() +" "+getCourseName()+" "+getSchooName();
		System.out.println(op);	
		showAllIndexDetails();
	}
	
	
	//TODO: verify need of this function
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
	
	// check if student has registered between indexes
	public boolean checkClash(String matricNo) {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			if(indexes.get(i).indexSeatClash(matricNo)==true) {
				return true;
			}
		}
		return false;
	}
	
//////////////////////////////////////////////////     STUDENT assign & unassign             ///////////////////////////////////////
	public void assignStudent(int index, Student student) {
		String matricNo = student.getMatricNo();
		
		if(containsIndexNo(index)==true)
		{
			if(checkClash(matricNo)==true) {
				System.out.println(matricNo + " has registered before in "+courseCode);
			}else {
				getIndex(index).assignStudent(student);
			}
		}else {
			System.out.println("index has not been registered before");
		}
	}
	
	public void unassignStudent(int index, Student student) {
		String matricNo = student.getMatricNo();
		
		if(checkClash(matricNo)==true) {
			getIndex(index).unassignStudent(student);
		}else {
			System.out.println(matricNo + " has not registered before");
		}
	}
	
	
///////////////////////                    ADMIN indexes              ////////////////////////////
	public void addIndex(int index, int vacancy) {

		if(getIndex(index)==null) {
			indexes.add(new Index(index, courseCode, vacancy));
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
	
	public void updateLecVenue(String lecVenue) {
		this.lecVenue = lecVenue;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////////////////////                    SET lec/tut/lab dateTime              ////////////////////////////	
		
	public void setLecDetails(int intDay, int startHours, int startMinutes,int endHours, int endMinutes,
			String lecVenue,String lecRemarks, String lecGroup) {
		lecStartTime.setHours(startHours);
		lecStartTime.setMinutes(startMinutes);
		lecEndTime.setHours(endHours);
		lecEndTime.setMinutes(startMinutes);
		lecDay = setDay(intDay);
		updateLecVenue(lecVenue);
		updateLecGroup(lecGroup);
		updateLecRemark(lecRemarks);
	}
	
	private String setDay(int intDay) {
		switch(intDay) {
		case 1: return "Monday";
		case 2: return "Tuesday";
		case 3: return "Wednesday";
		case 4: return "Thursday";
		case 5: return "Friday";
		}
		
		return null;
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
	
	public void updateAU(int au) {
		AU = au;
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
	
	public void getLecDetails() {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
		String lecStart = formatter.format(lecStartTime);
		String lecEnd =formatter.format(lecEndTime);
		
		String time = lecStart + " - " + lecEnd;

		String lecDetails = "Lec "+ "\t"+ lecGroup + "\t" + lecDay + "\t" + time + "\t" + lecVenue+ "\t"+ lecRemark;
		
		System.out.println(lecDetails);
	}

	public int getAU() {
		return AU;
	}

	public String getLecDay() {
		return lecDay;
	}

	public String getLecRemark() {
		return lecRemark;
	}

	public void updateLecRemark(String lecRemark) {
		this.lecRemark = lecRemark;
	}

	public String getLecGroup() {
		return lecGroup;
	}

	public void updateLecGroup(String lecGroup) {
		this.lecGroup = lecGroup;
	}
	
	
	
}
