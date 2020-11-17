package com.example.ss7g7.stars;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * <h1>Course Class</h1> 
 * The Course class is a class that contains the information
 * of a certain course.
 * 
 * <p>
 *  This class is used to determine the course details. <br/>
 *  The course details are as follows:
 *  Course Code, Course Name, School Name, AU, Course Availability,Lecture details. <br/>
 *  The Lecture details are as follows:
 *  Venue, Remark, Group, Timing <br/>
 *  This class also provides getters and setters for the above mentioned.
 *  
 * @author Jabir Shah Halith
 * created on 2020/10/19
 * 
 * @version %I%
 * @since 1.0
 */

public class Course implements Serializable{
	static final long serialVersionUID = 1L;

	private String courseCode;
	private String courseName;
	private String schoolName;
	private String lecVenue;
	private String lecRemark;
	private String lecGroup;
	private int AU;
	private LocalDateTime lecStartTime;
	private LocalDateTime lecEndTime;
	private ArrayList<Index> indexes; // array of indexes
	private boolean courseAvailability;
	
	/**
	 * Constructor for Course Class
	 * 
	 * @param course_Code -The intended course code of the course
	 * @param course_Name - The intended course name of the course
	 * @param school_Name - The school the course belongs to
	 * @param au - The academic unit of th course
	 */
	public Course(String course_Code, String course_Name, String school_Name, int au) {
		this.courseCode=course_Code;
		this.courseName=course_Name;
		this.AU=au;
		this.indexes = new ArrayList<Index>();
		this.schoolName = school_Name;
		this.lecVenue ="";
		this.courseAvailability = true;
		lecStartTime = null;
		lecEndTime = null;
		
	}
	
	/**
	 * This method is a getter to get index. Index is returned by iterating through the ArrayList of indexes.
	 * @param index - The intended index to get
	 * @return The index object if it was found. Otherwise it will return a null.
	 */
	public Index getIndex(int index) {
		for(int i = 0; i < indexes.size(); i++) {
			if(indexes.get(i).getIndexNum() == index) {
				return indexes.get(i); 
			}
		}
		return null;
	}
	

	/**
	 * Method to print all of the indexes in the Course
	 */
	public void printAllIndexes () {
		Iterator i = indexes.iterator();
		while(i.hasNext()) {
			System.out.println(i.next()+"\n");
		}
		
	}

	/**
	 * A method that returns a boolean if the given index number exists in the ArrayList of indexes.
	 * @param index - The intended index to check
	 * @return True - if index exists in the course <br/> False - if index does not exist in the course
	 */
	public boolean containsIndexNo(int index) {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			if(indexes.get(i).getIndexNum()==index) {
				return true;
			}
		}
		return false;
	}
	

	/**
	 * Method to print the Students who are registered for each index of the Course
	 */
	public void printStudentListByCourse() {
		System.out.println(getCourseCode()); //Prints the course code
		
		// this loops iterates through each index to print the students registered in each index
		for(int i = 0; i < indexes.size(); i++) {
			indexes.get(i).printStudentListByIndex();
		}
	}
	

	/**
	 * Method to show vacancies left for each index of the course
	 */
	public void printNumVacanciesAllIndexes() {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			System.out.println("Index: "+indexes.get(i).getIndexNum() + " Vacancies: " + indexes.get(i).getNumOfVacancies());
		}
	}
	
	/**
	 * Method to check if student has registered between indexes and return a boolean based on a clash occurring
	 * @param matricNo - The matric number of the student
	 * @return True - if clash occurred <br/> False - if there is no clash
	 */
	public boolean checkIndexClash(String matricNo) {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			if(indexes.get(i).isStudentRegistered(matricNo)==true) {
				return true;
			}
		}
		return false;
	}
	
///////////		    STUDENT assign & unassign if student registers by course        /////////////////
	/**
	 * Method to assign a Student to an index via the Course Class.
	 * @param index - The intended index student applying for
	 * @param student - The student object of the student applying
	 */
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
	
	/**
	 * Method to unassign a Student to an index via the Course Class.
	 * @param index - The intended index student applying for
	 * @param student - The student object of the student applying
	 */
	public void unassignStudent(int index, Student student) {
		String matricNo = student.getMatricNo();
		
		if(checkIndexClash(matricNo)==true) {
			getIndex(index).unassignStudent(student);
		}else {
			System.out.println(matricNo + " has not registered before");
		}
	}
	
	public void removeStudent(Student student) {
		
		ArrayList<Integer> registeredIndexes = new ArrayList<Integer>();
		
		for (int course=0; course<student.getCourses().size(); course++)
		{
			registeredIndexes.add(student.getCourses().get(course).getIndexNo());
		}
		
		for (Index courseIndex: indexes) {
			for (int index = 0; index < registeredIndexes.size(); index++) {
				if (registeredIndexes.get(index).equals(courseIndex.getIndexNum())) {
					courseIndex.unassignStudent(student);
				}
			}
		}
	}
/************************************************************************************************************/
	
///////////////////////                    Add/Edit/Delete index              ////////////////////////////
	
	/**
	 * Method to add an index to the Course
	 * @param index - The index number to be added
	 * @param vacancy - The amount of vacancy for the stated index.
	 * @return the index object. Else NULL if the index already exits in the course.
	 */
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
	
	/**
	 * Method to delete an index from the course.
	 * @param index - The intended index to be deleted
	 */
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
	
	/**
	 * Method to update the index number.
	 * @param index - The old index number
	 * @param newIndex - The new index number
	 */
	public void updateIndex(int index, int newIndex) {
		getIndex(index).setIndexNum(newIndex);
		System.out.println("Index "+index+" updated to " + newIndex);
		
	}
/************************************************************************************************************/

	
///////////////////////                    edit Course Details (setters)            ////////////////////////////
	/**
	 * Mutator method to change the current course code
	 * @param courseCode - intended new course code
	 */
	public void updateCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	/**
	 * Mutator method to change the current course name 
	 * @param courseName - intended new course name
	 */
	public void updateCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	/**
	 * Mutator method to change the current school name
	 * @param schooName - intended new school name
	 */
	public void updateSchooName(String schooName) {
		this.schoolName = schooName;
	}
	
	/**
	 * Mutator method to set course availability
	 * @param isAvailable - boolean to set the availability of the course
	 */
	public void setCourseAvailability(boolean isAvailable) {
		courseAvailability = isAvailable;
	}
	
	/**
	 * Mutator method to change the au of the course
	 * @param au - intended new au of the course
	 */
	public void updateAU(int au) {
		AU = au;
	}
	
/************************************************************************************************************/
	
///////////////////////                    get Course Details (getters)            ////////////////////////////	
	/**
	 * Accessor method to the get course code
	 * @return course code of the course
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * Accessor method to the get course name
	 * @return course name of the course
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Accessor method to the get school name the course belongs to
	 * @return the school name of the course
	 */
	public String getSchooName() {
		return schoolName;
	}

	/**
	 * Accessor method to the get au
	 * @return the au of the course
	 */
	public int getAU() {
		return AU;
	}
	
	/**
	 * Accessor method to the get course availability
	 * @return boolean of the course availability
	 */
	public boolean getCourseAvailability() {
		return courseAvailability;
	}
	
/************************************************************************************************************/

///////////////////////                    SET lec details              ////////////////////////////	
	
	/**
	 * Method to set  Lecture details such as the time,venue, group and remark at once
	 * @param intDay - the integer of the day
	 * @param startHours - the time(hour) the lecture starts
	 * @param startMinutes - the time(minutes) the lecture starts
	 * @param endHours - the time(hour) the lecture ends
	 * @param endMinutes - the time(minutes) the lecture ends
	 * @param lecVenue - the venue of the lecture
	 * @param lecRemarks - remarks of the lecture
	 * @param lecGroup - the group the lecture belongs to
	 */
	public void setLecDetails(int intDay, int startHours, int startMinutes,int endHours, int endMinutes,
			String lecVenue,String lecRemarks, String lecGroup) {
		updateLecStartTime(LocalDateTime.of(2020, Month.JUNE, intDay, startHours, startMinutes));
		updateLecEndTime(LocalDateTime.of(2020, Month.JUNE, intDay, endHours, endMinutes));
		updateLecVenue(lecVenue);
		updateLecGroup(lecGroup);
		updateLecRemark(lecRemarks);
	}


/************************************************************************************************************/

///////////////////////                    edit Lec Details (setters)            ////////////////////////////
	/**
	 * Mutator method to change the remark for lecture
	 * @param lecRemark - intended new remark for lecture
	 */
	public void updateLecRemark(String lecRemark) {
		this.lecRemark = lecRemark;
	}
	
	/**
	 * Mutator method to change the lecture group
	 * @param lecGroup - intended new lecture group
	 */
	public void updateLecGroup(String lecGroup) {
		this.lecGroup = lecGroup;
	}
	
	 /**
	  * Mutator method to change the ending time of the lecture
	  * @param lecEndTime - intended new end time of lecture 
	  */
	public void updateLecEndTime(LocalDateTime lecEndTime) {
		this.lecEndTime = lecEndTime;
	}
	
	/**
	 * Mutator method to change the starting time of the lecture
	 * @param lecStartTime - intended new start time of lecture 
	 */
	public void updateLecStartTime(LocalDateTime lecStartTime) {
		this.lecStartTime = lecStartTime;
	}
	
	/**
	 * Mutator method to change the venue of the lecture
	 * @param lecVenue - intended new venue of lecture 
	 */
	public void updateLecVenue(String lecVenue) {
		this.lecVenue = lecVenue;
	}
	
/************************************************************************************************************/
	
///////////////////////                    get Lec Details (getters)            ////////////////////////////
	
	/**
	 * Method to get all the lecture details such as Day, Time, Group, Venue and Remark  at once 
	 * @return full details of the lecture 
	 */
	public String getLecDetails() {
		if(lecStartTime == null || lecEndTime == null) return "";
		DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("hh:mm");
		
		String lecStart = getLecStartTime().format(formatter);
		String lecEnd =getLecEndTime().format(formatter);
		
		String time = lecStart + " - " + lecEnd;

		String lecDetails = "Lec "+ "\t"+ lecGroup + "\t" + lecStartTime.getDayOfWeek() + "\t" + time + "\t" + lecVenue+ "\t"+ lecRemark;		
//		System.out.println(lecDetails);
		return lecDetails;
	}

	/**
	 * Accessor method to the get venue of lecture
	 * @return venue of lecture
	 */
	public String getLecVenue() {
		return lecVenue;
	}

	/**
	 * Accessor method to the get remark of lecture
	 * @return remark of lecture 
	 */
	public String getLecRemark() {
		return lecRemark;
	}

	/**
	 *  Accessor method to the get group of lecture
	 * @return group of lecture 
	 */
	public String getLecGroup() {
		return lecGroup;
	}

	/**
	 *  Accessor method to the get starting time of lecture
	 * @return starting time of lecture 
	 */
	public LocalDateTime getLecStartTime() {
		return lecStartTime;
	}

	/**
	 *  Accessor method to the get ending time of lecture
	 * @return ending time of lecture 
	 */
	public LocalDateTime getLecEndTime() {
		return lecEndTime;
	}

}
