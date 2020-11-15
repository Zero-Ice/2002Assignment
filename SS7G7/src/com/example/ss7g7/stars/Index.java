package com.example.ss7g7.stars;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import com.example.ss7g7.stars.User.UserType;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Index implements Serializable{
	/**
	 * 
	 */
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
	
	
	// Constructor for Index Class
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
	
	// Method return boolean on whether student has registered to current index
	public boolean isStudentRegistered(String matricNo) {
		if(seatVacancy.contains(matricNo)) {
			return true;
		}else{
			return false;
		}
	}

	// Method to print students registered for the index
	public void printStudentListByIndex() {
		System.out.println(indexNum);
		for(int i =0;i<numSeats;i++) {
			if(seatVacancy.get(i).contains("vacant")!=true) {
				System.out.println(seatVacancy.get(i));
			}
		}
		System.out.println();
	}
	
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
	
	// getter for index number
	public int getIndexNum() {
		return indexNum;
	}

	// setter for index number ( to update index number)
	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}

	// returns the vacancy list 
	public ArrayList<String> getVacancyList(){
		return seatVacancy;
	}
	
	// return number of vacancies left
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
	
	public Student unassignStudent(Student student, boolean triggerWaitlistUpdate) {
		String matricNo = student.getMatricNo();
		
		if(seatVacancy.contains(matricNo)) {
			for(int i =0;i<numSeats;i++) {
				if(seatVacancy.get(i).contains(matricNo)) {
					seatVacancy.set(i, "vacant");
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
			for(int i=0;i<studentWaitlist.size();i++) {
				if(studentWaitlist.get(i).getMatricNo()==matricNo) {
					studentWaitlist.remove(i);
				}
			}
		}else  
			System.out.println(matricNo+ " was not found in index "+ indexNum);
		
		return student;
	}
	
	public Student unassignStudent(Student student) {
		return unassignStudent(student, true);
	}

/************************************************************************************************************/

///////////////////////                    Waitlist        ////////////////////////////
	
	// Method to add student to waitlist
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
	
	// Method to add student from waitlist into index
	public void addFromWaitlistToIndex() {
		if(studentWaitlist.size()==0) {
			System.out.println("no student in waitlist");
		}
		else {
			assignStudent(studentWaitlist.get(0));
			studentWaitlist.remove(0);
		}
	}
	
	// Shows the students who are in waitlist
	public void showStudentWaitlist() {
		if(studentWaitlist.size()==0) {
			System.out.println("no student in waitlist");
		}else {
			for(int i=0;i<studentWaitlist.size();i++) {
				System.out.println(studentWaitlist.get(i).getMatricNo());
			}
		}
	}
	
	// checks if student has already been placed on waitlist
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
	
	public void updateTutVenue(String tutVenue) {
		this.tutVenue = tutVenue;
	}

	public void updateTutRemark(String tutRemark) {
		this.tutRemark = tutRemark;
	}
	
	public void updateTutGroup(String tutGroup) {
		this.tutGroup = tutGroup;
	}
	
/************************************************************************************************************/

///////////////////////                    get tut Details (getters)            ////////////////////////////
	
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
	
	public String getTutVenue() {
		return tutVenue;
	}
	
	public String getTutRemark() {
		return tutRemark;
	}
	
	public String getTutGroup() {
		return tutGroup;
	}
	
	public int getTutOccurring() {
		return tutOccurring;
	}
	
	public LocalDateTime getTutStartTime() {
		return tutStartTime;
	}
	
	public LocalDateTime getTutEndTime() {
		return tutEndTime;
	}

/************************************************************************************************************/

///////////////////////                    SET lab details              ////////////////////////////
	
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
	
	public void updateLabVenue(String labVenue) {
		this.labVenue = labVenue;
	}

	public void updateLabGroup(String labGroup) {
		this.labGroup = labGroup;
	}
	
	public void updateLabRemark(String labRemark) {
		this.labRemark = labRemark;
	}
	
/************************************************************************************************************/
	
///////////////////////                    get lab Details (getters)            ////////////////////////////
	
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
	
	public String getLabVenue() {
		return labVenue;
	}
	
	
	public String getLabGroup() {
		return labGroup;
	}
	
	public String getLabRemark() {
		return labRemark;
	}
	
	public LocalDateTime getLabStartTime() {
		return labStartTime;
	}
	
	public LocalDateTime getLabEndTime() {
		return labEndTime;
	}
	
	public int getLabOccurring() {
		return labOccurring;
	}
	
	public int getWaitlistLength() {
		return this.studentWaitlist.size();
	}
	
/************************************************************************************************************/
	
}
