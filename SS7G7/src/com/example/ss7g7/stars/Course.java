package com.example.ss7g7.stars;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;



public class Course implements Serializable{
	static final long serialVersionUID = 1L;

	private String courseCode;
	private String courseName;
	private String schooName;
	private String lecVenue;
	private String lecRemark;
	private String lecGroup;
	private int AU;
	private LocalDateTime lecStartTime;
	private LocalDateTime lecEndTime;
	private ArrayList<Index> indexes; // array of indexes
	private boolean courseAvailability;

	// Constructor for Course Class
	public Course(String course_Code, String course_Name, String school_Name, int au) {
		this.courseCode=course_Code;
		this.courseName=course_Name;
		this.AU=au;
		this.indexes = new ArrayList<Index>();
		this.schooName = school_Name;
		this.lecVenue ="";
		this.courseAvailability =true;
		lecStartTime = null;
		lecEndTime = null;
		
	}
	
	// getter to get the inputted index by iterating through the ArrayList of indexes
	// returns the index object if it was found otherwise it will return a null
	public Index getIndex(int index) {
		for(int i = 0; i < indexes.size(); i++) {
			if(indexes.get(i).getIndexNum() == index) {
				return indexes.get(i); 
			}
		}
		return null;
	}
	
	
	// Method to print all of the indexes in the Course
	public void printAllIndexes () {
		Iterator i = indexes.iterator();
		while(i.hasNext()) {
			System.out.println(i.next()+"\n");
		}
		
	}

	// A method that returns a boolean if the given index number is in ArrayList of indexes
	public boolean containsIndexNo(int index) {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			if(indexes.get(i).getIndexNum()==index) {
				return true;
			}
		}
		return false;
	}
	

	// To print the Students who are registered for each index of the Course
	public void printStudentListByCourse() {
		System.out.println(getCourseCode()); //Prints the course code
		
		// this loops iterates through each index to print the students registered in each index
		for(int i = 0; i < indexes.size(); i++) {
			System.out.println("Index "+indexes.get(i).getIndexNum());
			indexes.get(i).printStudentListByIndex();
		}
	}
	

	// Method to show vacancies for each index
	public void printNumVacanciesAllIndexes() {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			System.out.println("Index: "+indexes.get(i).getIndexNum() + " Vacancies: " + indexes.get(i).getNumOfVacancies());
		}
	}
	
	// check if student has registered between indexes and return a boolean, whether there is a clash
	public boolean checkIndexClash(String matricNo) {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			if(indexes.get(i).isStudentRegistered(matricNo)==true) {
				return true;
			}
		}
		return false;
	}
	
//////////////////////////////////////////////////     STUDENT assign & unassign if student registers by course            ///////////////////////////////////////
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
/************************************************************************************************************/
	
///////////////////////                    Add/Edit/Delete index              ////////////////////////////
	
	// Method to add index to Course
	public Index addIndex(int index, int vacancy) {
		
		// if index does not exists previously index will be added
		if(getIndex(index)==null) {
			Index i = new Index(index, courseCode, vacancy);
			indexes.add(i);
			System.out.println("Index "+index+" was successfully added to "+ courseCode);
			return i;
		}else { 
			// if index exists in the Course it will print the following and return a null
			System.out.println("Index "+index+" already exists in "+ courseCode + "!");
		}
		return null;
	}
	
	// Method to delete a index
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
	
	
	// Method to update index number
	public void updateIndex(int index, int newIndex) {
		getIndex(index).setIndexNum(newIndex);
		System.out.println("Index "+index+" updated to " + newIndex);
		
	}
/************************************************************************************************************/

	
///////////////////////                    edit Course Details (setters)            ////////////////////////////
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
	
/************************************************************************************************************/
	
///////////////////////                    get Course Details (getters)            ////////////////////////////	
	public String getCourseCode() {
		return courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getSchooName() {
		return schooName;
	}

	public int getAU() {
		return AU;
	}
	
	public boolean getCourseAvailability() {
		return courseAvailability;
	}
	
/************************************************************************************************************/

///////////////////////                    SET lec details              ////////////////////////////	
	
// Method to set  Lecture details such as the time,venue, group and remark
public void setLecDetails(int intDay, int startHours, int startMinutes,int endHours, int endMinutes,
String lecVenue,String lecRemarks, String lecGroup) {
updateLecStartTime(LocalDateTime.of(2020, Month.JANUARY, intDay, startHours, startMinutes));
updateLecEndTime(LocalDateTime.of(2020, Month.JANUARY, intDay, endHours, endMinutes));
updateLecVenue(lecVenue);
updateLecGroup(lecGroup);
updateLecRemark(lecRemarks);
}


/************************************************************************************************************/

///////////////////////                    edit Lec Details (setters)            ////////////////////////////
	public void updateLecRemark(String lecRemark) {
		this.lecRemark = lecRemark;
	}
	
	public void updateLecGroup(String lecGroup) {
		this.lecGroup = lecGroup;
	}
	
	public void updateLecEndTime(LocalDateTime lecEndTime) {
		this.lecEndTime = lecEndTime;
	}
	
	public void updateLecStartTime(LocalDateTime lecStartTime) {
		this.lecStartTime = lecStartTime;
	}
	
	public void updateLecVenue(String lecVenue) {
		this.lecVenue = lecVenue;
	}
	
/************************************************************************************************************/
	
///////////////////////                    get Lec Details (getters)            ////////////////////////////
	
	// Method to get all the details at once
	public String getLecDetails() {
		String lecStart = getLecStartTime().toString();
		String lecEnd =getLecEndTime().toString();
		
		String time = lecStart + " - " + lecEnd;

		String lecDetails = "Lec "+ "\t"+ lecGroup + "\t" + lecStartTime.getDayOfWeek() + "\t" + time + "\t" + lecVenue+ "\t"+ lecRemark;		
//		System.out.println(lecDetails);
		return lecDetails;
	}

	public String getLecVenue() {
		return lecVenue;
	}

	public String getLecRemark() {
		return lecRemark;
	}

	public String getLecGroup() {
		return lecGroup;
	}

	public LocalDateTime getLecStartTime() {
		return lecStartTime;
	}

	public LocalDateTime getLecEndTime() {
		return lecEndTime;
	}

}
