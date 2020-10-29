package com.example.ss7g7.stars;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Index {
	private int indexNum;
	private String courseCode;
	private int numSeats;
	private ArrayList<String> seatVacancy; 
	private ArrayList<String> studentWaitlist;
	private String tutVenue;
	private String tutGroup;
	private String tutRemark;
	private String tutDay;
	private String labVenue;
	private String labDay;
	private String labGroup;
	
	
	private String labRemark;
	private Date tutStartTime;
	private Date tutEndTime;
	private Date labStartTime;
	private Date labEndTime;
	private boolean indexFull;
	//TODO: waitlist for students
	
	public Index(int index_Num, String courseCode, int num_Seat) {
		this.indexNum=index_Num;
		this.courseCode = courseCode;
		this.numSeats=num_Seat;
		this.tutStartTime = new Date();
		this.tutEndTime = new Date();
		this.labStartTime = new Date();
		this.labEndTime = new Date();
		this.indexFull=false;
		this.seatVacancy = new ArrayList<String>();
		this.studentWaitlist = new ArrayList<String>();
		
		//initialize all to vacant
		for(int i =0;i<numSeats;i++) {
			this.seatVacancy.add("vacant");
		}
	}
	
	
	public boolean indexSeatClash(String matricNo) {
		if(seatVacancy.contains(matricNo)) {
			return true;
		}else{
			return false;
		}
	}
	
	// TODO: Waitlist of students
	public void assignStudent(Student student) {
		String matricNo = student.getMatricNo();
		
		if(seatVacancy.contains(matricNo)) {
			System.out.println(matricNo+ " has registered before.");
			return;
		}else{
			for(int i =0;i<numSeats;i++) {
				if(seatVacancy.get(i).contains("vacant")) {
					seatVacancy.set(i, matricNo);
					student.addCourse(this.courseCode, this.indexNum);
					System.out.println(matricNo+ " assigned to index " +indexNum);
					return;
				}
			}
		}
		indexFull=true;
		System.out.println("Index Full!");
		addStudentToWaitlist(matricNo);
		
	}
	
	public void addStudentToWaitlist(String matricNo) {
		if(studentWaitlist.contains(matricNo)) {

		}else {
			studentWaitlist.add(matricNo);
		}
	}
	
	public void unassignStudent(Student student) {
		String matricNo = student.getMatricNo();
		
		if(seatVacancy.contains(matricNo)) {
			for(int i =0;i<numSeats;i++) {
				if(seatVacancy.get(i).contains(matricNo)) {
					seatVacancy.set(i, "vacant");
					student.dropCourse(this.indexNum);
					System.out.println(matricNo+ " unassigned from index " +indexNum);
					return;
				}
			}
		}
		System.out.println(matricNo+ " was not found in index "+ indexNum);
	}
	
	//TODO
	public void addFromWaitlistToIndex() {
		
	}
	
	
	public void showStudentWaitlist() {
		if(studentWaitlist.size()==0) {
			System.out.println("no studet");
		}else {
			for(int i=0;i<studentWaitlist.size();i++) {
				System.out.println(studentWaitlist.get(i));
			}
		}
	}
	
	@Override
	public String toString() {
		// Example
		// Class Type, Group, Day, Time, Venue, Remark
		return "Index" + Integer.toString(indexNum);
	}
	
	
	public int getNumOfVacancies() {
		int numOfVacant=0;
		
		for(int i =0;i<numSeats;i++) {
			if(seatVacancy.get(i).contains("vacant")) {
				numOfVacant++;
			}
		}
		return numOfVacant;
	}
	
	public void printStudentListByIndex() {
		System.out.println(indexNum);
		for(int i =0;i<numSeats;i++) {
			if(seatVacancy.get(i).contains("vacant")!=true) {
				System.out.println(seatVacancy.get(i));
			}
			
		}
		System.out.println();
	}
	

	public int getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}

	public String getTutVenue() {
		return tutVenue;
	}

	public void updateTutVenue(String tutVenue) {
		this.tutVenue = tutVenue;
	}

	
	//return string
	public void getTutDetails() {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
		String tutStart = formatter.format(tutStartTime);
		String tutEnd =formatter.format(tutEndTime);
		
		String time = tutStart + " - " + tutEnd;
		
		String tutDetails = "Tut "+ "\t"+ tutGroup + "\t" + tutDay + "\t" + time + "\t" + tutVenue+ "\t"+ tutRemark;
		
		System.out.println(tutDetails);
	}

	
	public void setTutDetails(int intDay, int startHours, int startMinutes,int endHours,
			int endMinutes,String tutVenue, String tutRemarks, String tutGroup) {
		tutStartTime.setHours(startHours);
		tutStartTime.setMinutes(startMinutes);
		tutEndTime.setHours(endHours);
		tutEndTime.setMinutes(startMinutes);
		tutDay = setDay(intDay);
		updateTutVenue(tutVenue);
		updateTutRemark(tutRemarks);
		updateTutGroup(tutGroup);
	}

	public String getLabVenue() {
		return labVenue;
	}

	public void updateLabVenue(String labVenue) {
		this.labVenue = labVenue;
	}

	
	//return string
	public void getLabDetails() {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
		String labStart = formatter.format(labStartTime);
		String labEnd =formatter.format(labEndTime);
		
		String time = labStart + " - " + labEnd;
		String labDetails = "Lab \t" + labGroup+ "\t" +labDay + "\t" + time + "\t" + labVenue+ "\t" +labRemark;
		
		System.out.println(labDetails);
	}

	public void setLabDetails(int intDay, int startHours, int startMinutes,int endHours, int endMinutes,
			String labVenue,String labRemarks, String labGroup) {
		labStartTime.setHours(startHours);
		labStartTime.setMinutes(startMinutes);
		labEndTime.setHours(endHours);
		labEndTime.setMinutes(startMinutes);
		labDay = setDay(intDay);
		updateLabVenue(labVenue);
		updateLabRemark(labRemarks);
		updateLabGroup(labGroup);
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


	public String getTutRemark() {
		return tutRemark;
	}


	public void updateTutRemark(String tutRemark) {
		this.tutRemark = tutRemark;
	}


	public String getTutGroup() {
		return tutGroup;
	}


	public void updateTutGroup(String tutGroup) {
		this.tutGroup = tutGroup;
	}
	
	
	public String getLabGroup() {
		return labGroup;
	}


	public void updateLabGroup(String labGroup) {
		this.labGroup = labGroup;
	}


	public String getLabRemark() {
		return labRemark;
	}


	public void updateLabRemark(String labRemark) {
		this.labRemark = labRemark;
	}
	
	public ArrayList<String> getVacancyList()
	{return seatVacancy;}

	

}
