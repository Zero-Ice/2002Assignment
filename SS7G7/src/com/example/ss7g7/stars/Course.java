package com.example.ss7g7.stars;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;



public class Course implements Serializable{
	private String courseCode;
	private String courseName;
	private String schooName;
	private String lecVenue;
	private String lecRemark;
	private String lecGroup;
	private int AU;
	private LocalTime lecStartTime;
	private LocalTime lecEndTime;
	private String lecDay;
	private ArrayList<Index> indexes; // array of indexes
	private boolean courseAvailability;

	
	public Course(String course_Code, String course_Name, String school_Name, int au) {
		this.courseCode=course_Code;
		this.courseName=course_Name;
		this.AU=au;
		this.indexes = new ArrayList<Index>();
		this.schooName = school_Name;
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
	

	public boolean containsIndexNo(int index) {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			if(indexes.get(i).getIndexNum()==index) {
				return true;
			}
		}
		return false;
	}
	

	
	public void printStudentListByCourse() {
		System.out.println(getCourseCode());
		for(int i = 0; i < indexes.size(); i++) {
			System.out.println("Index "+indexes.get(i).getIndexNum());
			indexes.get(i).printStudentListByIndex();
		}
	}
	
//	public void showfullCourseDetails() {
//		String op = getCourseCode() +" "+getCourseName()+" "+getSchooName();
//		System.out.println(op);	
//		showAllIndexDetails();
//	}
	public void printNumVacanciesAllIndexes() {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			System.out.println("Index: "+indexes.get(i).getIndexNum() + " Vacancies: " + indexes.get(i).getNumOfVacancies());
		}
	}
	
	// check if student has registered between indexes
	public boolean checkIndexClash(String matricNo) {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			if(indexes.get(i).indexSeatClash(matricNo)==true) {
				return true;
			}
		}
		return false;
	}
	
	//TODO: Check lec Clash
	public void checkLecClash(Student student, Course courseStudentApplying) {
		ArrayList<RegisteredCourse> studentRegCourse=student.getCourses();
		
		ArrayList<Course> allCourses = StarsDB.getInstance().getAllCourse();
	
		for(int i =0; i<allCourses.size();i++ ) {
			for(int j =0; j<studentRegCourse.size();j++ ) {
				if(allCourses.get(i).getCourseCode()==studentRegCourse.get(j).getCourseCode()) {
					
//					System.out.println(courseStudentApplying.getLecStartTime().isAfter(allCourses.get(i).getLecEndTime()));
//					System.out.println(courseStudentApplying.getLecEndTime().isBefore(allCourses.get(i).getLecStartTime()));
					
					//Time same as the lec from another course
					if(allCourses.get(i).getLecDay()==courseStudentApplying.getLecDay() &&
							allCourses.get(i).getLecStartTime().compareTo(courseStudentApplying.getLecStartTime())==0 &&
							allCourses.get(i).getLecEndTime().compareTo(courseStudentApplying.getLecEndTime())==0) 
					{
						System.out.println("wont be able"); //return true;
					}else if(courseStudentApplying.getLecStartTime().isAfter(allCourses.get(i).getLecEndTime())==false || 
							courseStudentApplying.getLecEndTime().isBefore(allCourses.get(i).getLecStartTime())==false) 
					{
						System.out.println("wont be able"); //return true;
					}
				}
			}
		}
		
		//return false;
		
	}
	
//////////////////////////////////////////////////     STUDENT assign & unassign             ///////////////////////////////////////
	public void assignStudent(int index, Student student) {
		String matricNo = student.getMatricNo();
		
		if(containsIndexNo(index)==true)
		{
			if(checkIndexClash(matricNo)==true) {
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
		
		if(checkIndexClash(matricNo)==true) {
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
		updateLecStartTime(lecStartTime.of(startHours, startMinutes));
		updateLecEndTime(lecEndTime.of(endHours, endMinutes));
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
		String lecStart = getLecStartTime().toString();
		String lecEnd =getLecEndTime().toString();
		
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

	public LocalTime getLecStartTime() {
		return lecStartTime;
	}

	public void updateLecStartTime(LocalTime lecStartTime) {
		this.lecStartTime = lecStartTime;
	}

	public LocalTime getLecEndTime() {
		return lecEndTime;
	}

	public void updateLecEndTime(LocalTime lecEndTime) {
		this.lecEndTime = lecEndTime;
	}


	
	
	
}
