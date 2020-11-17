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
 * <h1>Index Class</h1> The Index class is a subclass of Course class. It stores the
 * details of a the index for the course.
 * 
 * <p>
 *  This class is used to determine the index details. For each course the number of indexes
 *  may vary. Therefore the index class will be a subclass for the course class.
 * 
 *
 * @author Jabir Shah Halith
 * created on 2020/10/19
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
	private StarsDB database; 
	
	
	/**
	 * Constructor for Index Class
	 * @param index_Num - the index number of the index
	 * @param courseCode - the course code the index belongs to
	 * @param num_Seat - the number of seats available for the index class
	 */
	public Index(int index_Num, String courseCode, int num_Seat) {
		this.indexNum=index_Num;
		this.courseCode = courseCode;
		this.numSeats=num_Seat;
		this.indexFull = false;
		this.seatVacancy = new ArrayList<String>();
		this.studentWaitlist = new ArrayList<Student>();
		this.send = new SendEmail();
		this.database = StarsDB.getInstance();
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
	 * Method return boolean on whether student has registered to current index
	 * @param matricNo - the matric number of the student
	 * @return True - if student has been registered in the course before <br/> False - if student has not been registered in the course before
	 */
	public boolean isStudentRegistered(String matricNo) {
		if(seatVacancy.contains(matricNo)) {
			return true;
		}else{
			return false;
		}
	}

	/**
	 *  Method to print students registered in the index
	 */
	public void printStudentListByIndex() {		
		System.out.println("\nIndex "+this.getIndexNum());
		for(int i =0;i<numSeats;i++) {
			if(seatVacancy.get(i).contains("vacant")!=true) {
				Student student = database.getStudentByMatric(seatVacancy.get(i));
				System.out.println(student.getName()+student.getLastName()+" "+student.getGender()+" "+student.getNationality());
			}
		}
		System.out.println();
	}
	
	/**
	 * 
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
	 * Accessor method to get the index number
	 * @return index number
	 */
	public int getIndexNum() {
		return indexNum;
	}

	/**
	 * Mutator method to change the index number
	 * @param indexNum - intended new index number
	 */
	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}

	/**
	 * Method to return the seat vacancy list
	 * @return the vacancy list 
	 */
	public ArrayList<String> getVacancyList(){
		return seatVacancy;
	}
	
	/**
	 * Method to return the number of vacancies left in an index
	 * @return the number of vacancies left in the index
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
	 * Method to assign student to an index
	 * @param student - the student object to be added to the index
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
		
	}
	
	/**
	 * Method to unassign a student from the index
	 * @param student - the student object to be unassigned from the index
	 * @param triggerWaitlistUpdate
	 * @return the student object
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
	 * Method to add student to the waitlist
	 * @param student - the student to be added to the waiting list
	 */
	public void addStudentToWaitlist(Student student) {

		if(isStudentInWaitlist(student)==false) {
			student.addWaitingCourse(this.courseCode, this.indexNum);
			studentWaitlist.add(student);
			System.out.println("Wailist email is being sent to "+ student.getEmail()+". Please wait...");
			String subject = "Placement of waitlist for Index " + indexNum ;
			String bodyMessage = "You have been placed on waitlist for " + indexNum + ".";
			String body = "Dear " + student.getName() +" " + student.getLastName()+",\n\n"+bodyMessage;
			send.email("myprogram2830@gmail.com", subject, body);
			
		}else {
			System.out.println("Student already in waitlist");
		}
		
	}
	
	/**
	 * Method to add student from waitlist into index when there is an available slot
	 */
	public void addFromWaitlistToIndex() {
		if(studentWaitlist.size()==0) {
			System.out.println("no student in waitlist");
		}
		else {
			assignStudent(studentWaitlist.get(0));
			System.out.println("Allocation email is being sent to "+ studentWaitlist.get(0).getEmail()+". Please wait...");
			String subject = "Allocation of placement for Index " + this.indexNum ;
			String bodyMessage = "You have been allocated " + this.indexNum + ".";
			String body = "Dear " + studentWaitlist.get(0).getName() +" " + studentWaitlist.get(0).getLastName()+",\n\n"+bodyMessage;
			send.email("myprogram2830@gmail.com", subject, body);
			studentWaitlist.remove(0);
		}
	}
	
	/**
	 * Method to show the students who are in the waitlist
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
	 * Method that checks if student has already been placed on waitlist
	 * @param student
	 * @return True - if student is in waitlist <br/> False - if student is not in the waitlist
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
	
	
	/**
	 * Method to return the amount of students in waitlist
	 * @return number of student in waitlist
	 */
	public int getWaitlistLength() {
		return this.studentWaitlist.size();
	}

/************************************************************************************************************/


///////////////////////                    SET tut details              ////////////////////////////
	
	/**
	 * Method to set Tutorial details such as the time,venue, group and remark at once
	 * 
	 * @param intDay - the integer of the day of tutorial
	 * @param startHours - the time(hour) the tutorial starts
	 * @param startMinutes  - the time(minutes) the tutorial starts
	 * @param endHours - the time(hour) the tutorial ends
	 * @param endMinutes - the time(minutes) the tutorial ends
	 * @param tutVenue - the venue of the tutorial
	 * @param tutRemarks - remarks of the tutorial
	 * @param tutGroup - the group the tutorial belongs to
	 * @param occurring
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
	 * Mutator method to change the venue for tutorial
	 * @param tutVenue - intended new venue for tutorial
	 */
	public void updateTutVenue(String tutVenue) {
		this.tutVenue = tutVenue;
	}

	/**
	 * Mutator method to change the remark for tutorial
	 * @param tutRemark - intended new remark for tutorial
	 */
	public void updateTutRemark(String tutRemark) {
		this.tutRemark = tutRemark;
	}
	
	/**
	 * Mutator method to change the group for tutorial
	 * @param tutGroup - intended new group for tutorial
	 */
	public void updateTutGroup(String tutGroup) {
		this.tutGroup = tutGroup;
	}
	
/************************************************************************************************************/

///////////////////////                    get tut Details (getters)            ////////////////////////////
	
	/**
	 * Method to get all the tutorial details such as Day, Time, Group, Venue and Remark  at once 
	 * @return the full details of the tutorial
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
	 * Accessor method to the get venue of tutorial
	 * @return venue of tutorial
	 */
	public String getTutVenue() {
		return tutVenue;
	}
	
	/**
	 * Accessor method to the get remark of tutorial
	 * @return remark of tutorial
	 */
	public String getTutRemark() {
		return tutRemark;
	}
	
	/**
	 * Accessor method to the get tutorial group 
	 * @return tutorial group 
	 */
	public String getTutGroup() {
		return tutGroup;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getTutOccurring() {
		return tutOccurring;
	}
	
	/**
	 * Accessor method to the get starting time of tutorial
	 * @return starting time of tutorial
	 */
	public LocalDateTime getTutStartTime() {
		return tutStartTime;
	}
	
	/**
	 * Accessor method to the get ending time of tutorial
	 * @return ending time of tutorial
	 */
	public LocalDateTime getTutEndTime() {
		return tutEndTime;
	}

/************************************************************************************************************/

///////////////////////                    SET lab details              ////////////////////////////
	/**
	 * Method to set Lab details such as the time,venue, group and remark at once
	 * 
	 * @param intDay - the integer of the day of Lab
	 * @param startHours - the time(hour) the Lab starts
	 * @param startMinutes - the time(minutes) the Lab starts
	 * @param endHours - the time(hour) the Lab ends
	 * @param endMinutes - the time(minutes) the Lab ends
	 * @param labVenue - the venue of the Lab
	 * @param labRemarks - remarks of the Lab
	 * @param labGroup - the group the Lab belongs to
	 * @param occurring
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
	 * Mutator method to change the venue for lab
	 * @param labVenue - intended new venue for lab
	 */
	public void updateLabVenue(String labVenue) {
		this.labVenue = labVenue;
	}

	/**
	 * Mutator method to change the group for lab
	 * @param labGroup - intended new group for lab
	 */
	public void updateLabGroup(String labGroup) {
		this.labGroup = labGroup;
	}
	
	/**
	 * Mutator method to change the remark for lab
	 * @param labRemark - intended new remark for lab
	 */
	public void updateLabRemark(String labRemark) {
		this.labRemark = labRemark;
	}
	
/************************************************************************************************************/
	
///////////////////////                    get lab Details (getters)            ////////////////////////////
	/**
	 * Method to get all the lab details such as Day, Time, Group, Venue and Remark  at once 
	 * @return the full details of the lab
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
	 * Accessor method to the get lab venue 
	 * @return lab venue 
	 */
	public String getLabVenue() {
		return labVenue;
	}
	
	/**
	 * Accessor method to the get lab group 
	 * @return lab group 
	 */
	public String getLabGroup() {
		return labGroup;
	}
	
	/**
	 * Accessor method to the get lab remark 
	 * @return lab remark 
	 */
	public String getLabRemark() {
		return labRemark;
	}
	
	/**
	 * Accessor method to the get starting time of lab
	 * @return starting time of lab
	 */
	public LocalDateTime getLabStartTime() {
		return labStartTime;
	}
	
	/**
	 * Accessor method to the get ending time of lab 
	 * @return ending time of lab 
	 */
	public LocalDateTime getLabEndTime() {
		return labEndTime;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLabOccurring() {
		return labOccurring;
	}

	
/************************************************************************************************************/
	
}
