package com.example.ss7g7.stars;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Course is a base class that defines all of
 * the parameters that a course object would have.
 * This class must be serializable to enable the storage
 * of a Course object in a file.
 * 
 * @author 
 * created on 2020/10/15
 * 
 * @version %I%
 * @since 1.0
 *
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
	 * Constructor for Course Class.
	 * 
	 * @param course_Code
	 * @param course_Name
	 * @param school_Name
	 * @param au
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
	 * This method checks if the given index exists in the current Course object.
	 * @param index
	 * @return 	index if index is found in the database;
	 * 			<code>null</code> if index is not found.
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
	 * This method is to print all of the indexes in the current Course object.
	 */
	public void printAllIndexes () {
		Iterator i = indexes.iterator();
		while(i.hasNext()) {
			System.out.println(i.next()+"\n");
		}
		
	}

	/**
	 * This method checks if the given index exists in the current Course object.
	 * 
	 * @param index
	 * @return <code>true</code> if index is found in the course;
	 * 			<code>false</code> if index is not found.
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
	 * This method is to print the Students who are registered for each index of the Course.
	 */
	public void printStudentListByCourse() {
		System.out.println(getCourseCode()); //Prints the course code
		
		// this loops iterates through each index to print the students registered in each index
		for(int i = 0; i < indexes.size(); i++) {
			System.out.println("Index "+indexes.get(i).getIndexNum());
			indexes.get(i).printStudentListByIndex();
		}
	}
	
	/**
	 * This method is to show the vacancies for each index.
	 */
	public void printNumVacanciesAllIndexes() {
		int sizeOfIndex= indexes.size();
		for(int i = 0; i<sizeOfIndex;i++) {
			System.out.println("Index: "+indexes.get(i).getIndexNum() + " Vacancies: " + indexes.get(i).getNumOfVacancies());
		}
	}
	
	/**
	 * This method is to check if student is trying to 
	 * register two indexes from the same course.
	 * 
	 * @param matricNo
	 * @return <code>true</code> if there is a clash;
	 * 			<code>false</code> if there is not.
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
	 * This method registers the student to the course by index
	 * 
	 * @param index refers to the chosen index for the current Course object
	 * @param student refers to the student that is to be registered to the course
	 * 
	 * @see {@link Index#assignStudent(Student)}
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
	 * This method unregisters the student from the course by index
	 * 
	 * @param index refers to the chosen index for the current Course object
	 * @param student refers to the student to be removed from the course
	 * 
	 * @see {@link Index#unassignStudent(Student)}
	 */
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
	
	/**
	 * This method is to add index to current Course object.
	 * 
	 * @param index refers to the index to be added
	 * @param vacancy refers to the maximum number of students allowed in the course
	 * @return i if index is successfully added;
	 * 			<code>null</code> if index was not added.
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
	 * This method is to delete an index.
	 * 
	 * @param index refers to the index to be removed
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
	 * This is a method to update index number.
	 * 
	 * @param index refers to the old index number
	 * @param newIndex refers to the new index number
	 */
	public void updateIndex(int index, int newIndex) {
		getIndex(index).setIndexNum(newIndex);
		System.out.println("Index "+index+" updated to " + newIndex);
		
	}
/************************************************************************************************************/

	
///////////////////////                    edit Course Details (setters)            ////////////////////////////
	/**
	 * This is to set the course code of the Course object
	 * 
	 * @param courseCode
	 */
	public void updateCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	/**
	 * This is to set the course name of the Course object
	 * 
	 * @param courseName
	 */
	public void updateCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	/**
	 * This is to set the school name of the Course object
	 * @param schooName
	 */
	public void updateSchooName(String schooName) {
		this.schoolName = schooName;
	}
	
	/**
	 * This is to set the course availability of the Course object
	 * 
	 * @param isAvailable is a boolean parameter
	 */
	public void setCourseAvailability(boolean isAvailable) {
		courseAvailability = isAvailable;
	}
	
	/**
	 *  This is to set the course academic unit of the Course object
	 *  
	 * @param au
	 */
	public void updateAU(int au) {
		AU = au;
	}
	
/************************************************************************************************************/
	
///////////////////////                    get Course Details (getters)            ////////////////////////////	
	/**
	 * This method is to get the course code of the Course object
	 * 
	 * @return courseCode if not <code>null</code>;
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * This method is to get the course name of the Course object
	 * 
	 * @return courseName if not <code>null</code>;
	 */
	public String getCourseName() {
		return courseName;
	}
	
	/**
	 * This method is to get the school name of the Course object
	 * 
	 * @return schoolName if not <code>null</code>;
	 */
	public String getSchooName() {
		return schoolName;
	}

	/**
	 * This method is to get the academic unit of the Course object
	 * 
	 * @return AU if not <code>null</code>;
	 */
	public int getAU() {
		return AU;
	}
	
	/**
	 * This method is to get the course availability of the Course object
	 * 
	 * @return courseAvailability- <code>true</code> by default;
	 * 			
	 */
	public boolean getCourseAvailability() {
		return courseAvailability;
	}
	
/************************************************************************************************************/

///////////////////////                    SET lec details              ////////////////////////////	
	
	/**
	 * This method is to set lecture details such as the time, venue, group and remark
	 * 
	 * @param intDay
	 * @param startHours
	 * @param startMinutes
	 * @param endHours
	 * @param endMinutes
	 * @param lecVenue
	 * @param lecRemarks
	 * @param lecGroup
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
	 * This method is to set the lecture remark of the current Course object
	 * 
	 * @param lecRemark
	 */
	public void updateLecRemark(String lecRemark) {
		this.lecRemark = lecRemark;
	}
	
	/**
	 * This method is to set the lecture group of the current Course object
	 * 
	 * @param lecGroup
	 */
	public void updateLecGroup(String lecGroup) {
		this.lecGroup = lecGroup;
	}
	
	/**
	 * This method is to set the end time of the lecture of the current Course object
	 * 
	 * @param lecEndTime
	 */
	public void updateLecEndTime(LocalDateTime lecEndTime) {
		this.lecEndTime = lecEndTime;
	}
	
	/**
	 * This method is to set the start time of the lecture of the current Course object
	 * 
	 * @param lecStartTime
	 */
	public void updateLecStartTime(LocalDateTime lecStartTime) {
		this.lecStartTime = lecStartTime;
	}
	
	/**
	 * This method is to set the lecture venue of the current Course object
	 * 
	 * @param lecVenue
	 */
	public void updateLecVenue(String lecVenue) {
		this.lecVenue = lecVenue;
	}
	
/************************************************************************************************************/
	
///////////////////////                    get Lec Details (getters)            ////////////////////////////
	
	/**
	 * This method is to get the lecture details.
	 * 
	 * @return lecDetails if not <code>null</code>;
	 */
	public String getLecDetails() {
		DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("hh:mm");
		
		String lecStart = getLecStartTime().format(formatter);
		String lecEnd =getLecEndTime().format(formatter);
		
		String time = lecStart + " - " + lecEnd;

		String lecDetails = "Lec "+ "\t"+ lecGroup + "\t" + lecStartTime.getDayOfWeek() + "\t" + time + "\t" + lecVenue+ "\t"+ lecRemark;		
//		System.out.println(lecDetails);
		return lecDetails;
	}

	/**
	 * This method gets the lecture venue of the current Course object
	 * 
	 * @return lecVenue if not <code>null</code>;
	 */
	public String getLecVenue() {
		return lecVenue;
	}

	/**
	 * This method gets the lecture remark of the current Course object
	 * 
	 * @return lecRemark if not <code>null</code>;
	 */
	public String getLecRemark() {
		return lecRemark;
	}

	/**
	 * This method gets the lecture group of the current Course object
	 * 
	 * @return lecGroup if not <code>null</code>;
	 */
	public String getLecGroup() {
		return lecGroup;
	}

	/**
	 * This method gets the start time of the lecture of the current Course object
	 * 
	 * @return lecStartTime if not <code>null</code>;
	 */
	public LocalDateTime getLecStartTime() {
		return lecStartTime;
	}

	/**
	 * This method gets the end time of the lecture of the current Course object
	 * 
	 * @return lecEndTime if not <code>null</code>;
	 */
	public LocalDateTime getLecEndTime() {
		return lecEndTime;
	}

}
