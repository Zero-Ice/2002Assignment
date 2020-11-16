package com.example.ss7g7.stars;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import com.example.ss7g7.stars.User.UserType;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * The Index class holds additional and more detailed
 * information of the course it belongs to.
 * This class must be serializable to enable the storage
 * of a Course object in a file.
 * 
 * @author Shah
 * created on 2020/10/15
 * 
 * @version %I%
 * @since 1.0
 */
public class Index implements Serializable{
	
	private static final long serialVersionUID = -464339125814698090L;
	
	private int indexNum;
	private String courseCode;
	private int numSeats;
	private ArrayList<String> seatVacancy; 
	private ArrayList<Student> studentWaitlist;
	private String tutVenue;
	private String tutGroup;
	private String tutRemark;
	private String labVenue;
	private String labGroup;
	private String labRemark;
	private LocalDateTime tutStartTime;
	private LocalDateTime tutEndTime;
	private int tutOccurring;
	private LocalDateTime labStartTime;
	private LocalDateTime labEndTime;
	private int labOccurring;
	private boolean indexFull;
	private SendEmail send;
	
	//TODO: email body and subject
	//TODO: return waitlist
	
 
	/**
	 * Constructor for Index Class
	 * 
	 * @param index_Num
	 * @param courseCode
	 * @param num_Seat
	 */
	public Index(int index_Num, String courseCode, int num_Seat) {
		this.indexNum=index_Num;
		this.courseCode = courseCode;
		this.numSeats=num_Seat;
		this.indexFull = false;
		this.seatVacancy = new ArrayList<String>();
		this.studentWaitlist = new ArrayList<Student>();
		this.send = new SendEmail();
		tutOccurring = 0;
		labOccurring = 0;
		tutStartTime = null;
		tutEndTime = null;
		labStartTime = null;
		labEndTime = null;
		
		//initialize all to vacant
		for(int i =0;i<numSeats;i++) {
			this.seatVacancy.add("vacant");
		}
	}
	
	/**
	 * This is a method to check if a student is already 
	 * registered to the current Index object
	 * 
	 * @param matricNo
	 * @return
	 */
	public boolean isStudentRegistered(String matricNo) {
		if(seatVacancy.contains(matricNo)) {
			return true;
		}else{
			return false;
		}
	}

	/**
	 * This is a method to print all students registered
	 * to the current Index object
	 * 
	 */
	public void printStudentListByIndex() {
		System.out.println(indexNum);
		for(int i =0;i<numSeats;i++) {
			if(seatVacancy.get(i).contains("vacant")!=true) {
				System.out.println(seatVacancy.get(i));
			}
		}
		System.out.println();
	}
	
	/**
	 * This method prints the details of the
	 * current Index object
	 * 
	 * @see {@link Index#getTutDetails()}
	 * @see {@link Index#getLabDetails()}
	 */
	@Override
	public String toString() {
		// Example
		// Class Type, Group, Day, Time, Venue, Remark
		String s = "";
		s += "Index" + Integer.toString(indexNum) + "\n";
		s += getTutDetails() + "\n";
		s += getLabDetails();
		return s;
	}
	
///////////////////////    Index Related          ////////////////////////////
	
	/**
	 * This method returns the current index code
	 * 
	 * @return indexNum if not <code>null</code>;
	 */
	public int getIndexNum() {
		return indexNum;
	}

	/**
	 * This method sets the code for the index
	 * 
	 * @param indexNum 
	 */
	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}

	/**
	 * This method returns the list of students registered
	 * to the current Index object
	 * 
	 * @return ArrayList<String> <code>seatVacancy</code>
	 */
	public ArrayList<String> getVacancyList(){
		return seatVacancy;
	}
	
	/**
	 * This method returns the number of vacant seats of
	 * the current Index object
	 * 
	 * @return seatVacancy if not <code>null</code>;
	 */
	public int getNumOfVacancies() {
		int numOfVacant=0;
		
		for(int i =0;i<numSeats;i++) {
			if(seatVacancy.get(i).contains("vacant")) {
				numOfVacant++;
			}
		}
		return numOfVacant;
	}
	
/************************************************************************************************************/
///////////////////////                    Assign and Unassign Student from index          ////////////////////////////
	/**
	 * This method registers student to index
	 * if index has available seats.
	 * 
	 * @param student refers to the student object to be added to the index
	 */
	public void assignStudent(Student student) {
		String matricNo = student.getMatricNo();
		
		if(seatVacancy.contains(matricNo)) {
			System.out.println(matricNo+ " has registered before.");
			return;
		}else{
			for(int seat=0; seat<numSeats; seat++) {
				if(seatVacancy.get(seat).contains("vacant")) {
					seatVacancy.set(seat, matricNo);
					
					student.addCourse(this.courseCode, this.indexNum);
					
					System.out.println(matricNo+ " assigned to index " +indexNum);
					return;
				}
			}
		}
		indexFull=true;
		System.out.println("Index Full!");
		addStudentToWaitlist(student);
		student.addWaitingCourse(this.courseCode, this.indexNum);
	}
	
	/**
	 * This method removes student from index 
	 * 
	 * @param student refers to the student to be removed from the index
	 * @param triggerWaitlistUpdate is a boolean parameter-
	 * 								if <code>true</code> then update index vacancy list with student in waitlist
	 * 							
	 * @return Student <code>student</code>
	 * @see {@link Index#addFromWaitlistToIndex}
	 */
	public Student unassignStudent(Student student, boolean triggerWaitlistUpdate) {
		String matricNo = student.getMatricNo();
		
		if(seatVacancy.contains(matricNo)) {
			for(int seat =0;seat<numSeats;seat++) {
				if(seatVacancy.get(seat).contains(matricNo)) {
					seatVacancy.set(seat, "vacant");
					student.dropCourse(this.indexNum);	
					System.out.println(matricNo+ " unassigned from index " +indexNum);
					if(indexFull==true) {
						indexFull=false;
						if(triggerWaitlistUpdate) {
							addFromWaitlistToIndex();
						}
					}
					
				}
			}
		}else if(isStudentInWaitlist(student)==true) {
			student.dropCourse(this.indexNum);
			for(int seat=0;seat<studentWaitlist.size();seat++) {
				if(studentWaitlist.get(seat).getMatricNo()==matricNo) {
					studentWaitlist.remove(seat);
				}
			}
		}else  
			System.out.println(matricNo+ " was not found in index "+ indexNum);
		
		return student;
	}
	
	/**
	 * This method removes student from index and automatically
	 * updates index vacancy list with students in waitlist
	 * 
	 * @param student refers to the student to be removed from the index
	 * 
	 * @return Student <code>student</code>
	 * @see {@link Index#unassignStudent(Student, boolean)}
	 * 
	 */
	public Student unassignStudent(Student student) {
		return unassignStudent(student, true);
	}

/************************************************************************************************************/

///////////////////////                    Waitlist        ////////////////////////////
	
	/**
	 * This method is to add student to waitlist
	 * 
	 * @param student refers to the student to be added
	 * to the waitlsit
	 */
	public void addStudentToWaitlist(Student student) {

		if(isStudentInWaitlist(student)==false) {
			student.addCourse(this.courseCode, this.indexNum);
			studentWaitlist.add(student);
			System.out.println("Email to be sent to "+ student.getEmail());
//			send.email(student.getEmail(), Subject, Body);
			
		}else {
			System.out.println("Student already in waitlist");
		}
		
	}
	
	/**
	 * This method is to register the student that
	 * is next in line from the waitlist
	 * to the current Index object
	 */
	public void addFromWaitlistToIndex() {
		if(studentWaitlist.size()==0) {
			System.out.println("no student in waitlist");
		}
		else {
			assignStudent(studentWaitlist.get(0));
			studentWaitlist.remove(0);
		}
	}
	
	/**
	 * This method is to print the
	 * matric number of the students
	 * who are in the waitlist
	 */
	public void showStudentWaitlist() {
		if(studentWaitlist.size()==0) {
			System.out.println("no student in waitlist");
		}else {
			for(int i=0;i<studentWaitlist.size();i++) {
				System.out.println(studentWaitlist.get(i).getMatricNo());
			}
		}
	}
	
	/**
	 * This method is to check if a student has already 
	 * been placed on waitlist
	 * 
	 * @param student refers to a Student object to be checked
	 * @return <code>true</code> if student is in waitlist;
	 * 			<code>false</code> if student is not.
	 */
	public boolean isStudentInWaitlist(Student student) {
		String matricNo = student.getMatricNo();
		for(int i =0;i<studentWaitlist.size();i++) {
			if(studentWaitlist.get(i).getMatricNo()==matricNo) {
				System.out.println("Student already in waitlist");
				return true;
			}
		}
		return false;
	}

/************************************************************************************************************/


///////////////////////                    SET tut details              ////////////////////////////
	/**
	 * This method sets the tutorial details
	 * of the current Index object
	 * 
	 * @param intDay refers to the day of the week- represented by 1 to 7
	 * @param startHours 
	 * @param startMinutes
	 * @param endHours
	 * @param endMinutes
	 * @param tutVenue
	 * @param tutRemarks
	 * @param tutGroup 
	 * @param occurring refers to the frequency of occurrence- even/odd/every week
	 */
	public void setTutDetails(int intDay, int startHours, int startMinutes,int endHours,
			int endMinutes,String tutVenue, String tutRemarks, String tutGroup, int occurring) {
		tutStartTime = LocalDateTime.of(2020, Month.JUNE, intDay, startHours, startMinutes);
		tutEndTime = LocalDateTime.of(2020, Month.JUNE, intDay, endHours, endMinutes);
		tutOccurring = occurring;
		
		updateTutVenue(tutVenue);
		updateTutRemark(tutRemarks);
		updateTutGroup(tutGroup);
	}
	
/************************************************************************************************************/
	
///////////////////////                    edit tut Details (setters)            ////////////////////////////
	
	/**
	 * This method sets the tutorial venue
	 * 
	 * @param tutVenue
	 */
	public void updateTutVenue(String tutVenue) {
		this.tutVenue = tutVenue;
	}

	/**
	 * This method sets the tutorial remark
	 * 
	 * @param tutRemark
	 */
	public void updateTutRemark(String tutRemark) {
		this.tutRemark = tutRemark;
	}
	
	/**
	 * This method sets the tutorial group
	 * 
	 * @param tutGroup
	 */
	public void updateTutGroup(String tutGroup) {
		this.tutGroup = tutGroup;
	}
	
/************************************************************************************************************/

///////////////////////                    get tut Details (getters)            ////////////////////////////
	
	/**
	 * This method returns the details of
	 * the tutorial which includes:
	 * <li>Tutorial Group
	 * <li>Tutorial Start Time
	 * <li>Tutorial End Time
	 * <li>Tutorial Venue
	 * <li>Tutorial Remark
	 * 
	 * @return String <code>tutDetails</code>
	 */
	public String getTutDetails() {
		if(tutStartTime == null || tutEndTime == null) return "";
		
		DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("hh:mm");
		String tutStart = tutStartTime.format(formatter);
		String tutEnd = tutEndTime.format(formatter);
		
		String time = tutStart + " - " + tutEnd;
		
		String tutDetails = "Tut "+ "\t"+ tutGroup + "\t" + tutStartTime.getDayOfWeek() + "\t" + time + "\t" + tutVenue+ "\t"+ tutRemark;
		
//		System.out.println(tutDetails);
		return tutDetails;
	}
	
	/**
	 * This method returns the venue of the tutorial
	 * 
	 * @return tutVenue if not <code>null</code>;
	 */
	public String getTutVenue() {
		return tutVenue;
	}
	
	/**
	 * This method returns the tutorial remark
	 * 
	 * @return tutRemark if not <code>null</code>;
	 */
	public String getTutRemark() {
		return tutRemark;
	}
	
	/**
	 * This method returns the tutorial group
	 * 
	 * @return tutGroup if not <code>null</code>;
	 */
	public String getTutGroup() {
		return tutGroup;
	}
	
	/**
	 * This method returns the frequency of
	 * occurrence of the tutorial
	 * 
	 * @return tutOccurring if not <code>null</code>;
	 */
	public int getTutOccurring() {
		return tutOccurring;
	}
	
	/**
	 * This method returns the start time of the tutorial
	 * 
	 * @return tutStartTime if not <code>null</code>;
	 */
	public LocalDateTime getTutStartTime() {
		return tutStartTime;
	}
	
	/**
	 * This method returns the end time of the tutorial
	 * 
	 * @return tutEndTime if not <code>null</code>;
	 */
	public LocalDateTime getTutEndTime() {
		return tutEndTime;
	}

/************************************************************************************************************/

///////////////////////                    SET lab details              ////////////////////////////
	
	/**
	 * This method sets the details of the
	 * lab for the current Index object
	 * 
	 * @param intDay refers to the day of the week- represented by 1 to 7
	 * @param startHours
	 * @param startMinutes
	 * @param endHours
	 * @param endMinutes
	 * @param labVenue
	 * @param labRemarks
	 * @param labGroup
	 * @param occurring refers to the frequency of occurrence- even/odd/every week
	 */
	public void setLabDetails(int intDay, int startHours, int startMinutes,int endHours, int endMinutes,
			String labVenue,String labRemarks, String labGroup, int occurring) {
		
		labStartTime = LocalDateTime.of(2020, Month.JUNE, intDay, startHours, startMinutes);
		labEndTime = LocalDateTime.of(2020, Month.JUNE, intDay, endHours, endMinutes);
		labOccurring = occurring;
		
		updateLabVenue(labVenue);
		updateLabRemark(labRemarks);
		updateLabGroup(labGroup);
	}

/************************************************************************************************************/
	
///////////////////////                    edit lab Details (setters)            ////////////////////////////
	
	/**
	 * This method sets the lab venue
	 * 
	 * @param labVenue
	 */
	public void updateLabVenue(String labVenue) {
		this.labVenue = labVenue;
	}

	/**
	 * This method sets the lab group
	 * 
	 * @param labGroup
	 */
	public void updateLabGroup(String labGroup) {
		this.labGroup = labGroup;
	}
	
	/**
	 * This method sets the lab remark
	 * 
	 * @param labRemark
	 */
	public void updateLabRemark(String labRemark) {
		this.labRemark = labRemark;
	}
	
/************************************************************************************************************/
	
///////////////////////                    get lab Details (getters)            ////////////////////////////
	
	/**
	 * This method returns the details of the lab
	 * which includes:
	 * <li>Lab Group
	 * <li>Lab Start Time
	 * <li>Lab End Time
	 * <li>Lab Venue
	 * <li>Lab Remark
	 * 
	 * @return labDetails
	 */
	public String getLabDetails() {
		if(labStartTime == null || labEndTime == null) return "";
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
		String labStart = labStartTime.format(formatter);
		String labEnd = labEndTime.format(formatter);
		
		String time = labStart + " - " + labEnd;
		String labDetails = "Lab \t" + labGroup+ "\t" +labStartTime.getDayOfWeek() + "\t" + time + "\t" + labVenue+ "\t" +labRemark;
//		System.out.println(labDetails);
		return labDetails;
	}
	
	/**
	 * This method returns the lab venue
	 * 
	 * @return labVenue if not <code>null</code>;
	 */
	public String getLabVenue() {
		return labVenue;
	}
	
	
	/**
	 * This method returns the lab group
	 * 
	 * @return labGroup if not <code>null</code>;
	 */
	public String getLabGroup() {
		return labGroup;
	}
	
	/**
	 * This method returns the lab remark
	 * 
	 * @return labRemark if not <code>null</code>;
	 */
	public String getLabRemark() {
		return labRemark;
	}
	
	/**
	 * This method returns the lab start time
	 * 
	 * @return labStartTime if not <code>null</code>;
	 */
	public LocalDateTime getLabStartTime() {
		return labStartTime;
	}
	
	/**
	 * This method returns the lab end time
	 * 
	 * @return labEndTime if not <code>null</code>;
	 */
	public LocalDateTime getLabEndTime() {
		return labEndTime;
	}
	
	/**
	 * This method returns the frequency of occurrence
	 * of lab
	 * 
	 * @return labOccurring if not <code>null</code>;
	 */
	public int getLabOccurring() {
		return labOccurring;
	}
	
	/**
	 * This method returns the number of students in
	 * the waitlist
	 * 
	 * @return <code>studentWaitlist.size()</code> if not <code>null</code>;
	 */
	public int getWaitlistLength() {
		return this.studentWaitlist.size();
	}
	
/************************************************************************************************************/
	
}
