package com.example.ss7g7.stars;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Index {
	private int indexNum;
	private int numVacancy;
	private ArrayList<String> seatVacancy;
	private ArrayList<String>  studentWaitlist;
	private String tutVenue;
	private String labVenue;
	private String tutDay;
	private String labDay;
	private Date tutStartTime;
	private Date tutEndTime;
	private Date labStartTime;
	private Date labEndTime;
	private boolean indexFull;
	//TODO: waitlist for students
	
	public Index(int index_Num, int num_Vacancy) {
		this.indexNum=index_Num;
		this.numVacancy=num_Vacancy;
		this.tutStartTime = new Date();
		this.tutEndTime = new Date();
		this.labStartTime = new Date();
		this.labEndTime = new Date();
		this.indexFull=false;
		this.seatVacancy = new ArrayList<String>();
		this.studentWaitlist = new ArrayList<String>();
		
		//initialize all to vacant
		for(int i =0;i<numVacancy;i++) {
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
	public void assignStudent(String matricNo) {
		if(seatVacancy.contains(matricNo)) {
			System.out.println(matricNo+ " has registered before.");
			return;
		}else{
			for(int i =0;i<numVacancy;i++) {
				if(seatVacancy.get(i).contains("vacant")) {
					seatVacancy.set(i, matricNo);
					System.out.println(matricNo+ " assigned to index " +indexNum);
					return;
				}
			}
		}
		
		indexFull=true;
		System.out.println("Index Full!");
	}
	
	public void unassignStudent(String matricNo) {
		if(seatVacancy.contains(matricNo)) {
			for(int i =0;i<numVacancy;i++) {
				if(seatVacancy.get(i).contains(matricNo)) {
					seatVacancy.set(i, "vacant");
					System.out.println(matricNo+ " unassigned from index " +indexNum);
					return;
				}
			}
		}
		System.out.println(matricNo+ " was not found in index "+ indexNum);
	}
	
	@Override
	public String toString() {
		// Example
		// Class Type, Group, Day, Time, Venue, Remark
		return "Index" + Integer.toString(indexNum);
	}
	
	
	public int showNumOfVacancies() {
		int numOfVacant=0;
		
		for(int i =0;i<numVacancy;i++) {
			if(seatVacancy.get(i).contains("vacant")) {
				numOfVacant++;
			}
		}
		return numOfVacant;
	}
	
	public void showAllSeats() {
		for(int i =0;i<numVacancy;i++) {
			System.out.println(i+1 + ": "+seatVacancy.get(i));
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

	public void updateIndexTutVenue(String tutVenue) {
		this.tutVenue = tutVenue;
	}

	public void getIndexTutDetails() {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
		String tutStart = formatter.format(tutStartTime);
		String tutEnd =formatter.format(tutEndTime);
		
		String time = "Time: "+ tutStart + " - " + tutEnd;
		String venue =  "Venue: " + getTutVenue();
		
		String tutDetails = "Day: " + tutDay + "\n" + time + "\n" + venue;
		
		System.out.println(tutDetails);
	}

	public void setIndexTutDetails(int intDay, int startHours, int startMinutes,int endHours, int endMinutes,String tutVenue) {
		tutStartTime.setHours(startHours);
		tutStartTime.setMinutes(startMinutes);
		tutEndTime.setHours(endHours);
		tutEndTime.setMinutes(startMinutes);
		tutDay = setDay(intDay);
		updateIndexTutVenue(tutVenue);
	}

	public String getLabVenue() {
		return labVenue;
	}

	public void updateIndexLabVenue(String labVenue) {
		this.labVenue = labVenue;
	}

	public void getIndexLabDetails() {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
		String labStart = formatter.format(labStartTime);
		String labEnd =formatter.format(labEndTime);
		
		String time = "Time: "+ labStart + " - " + labEnd;
		String venue =  "Venue: " + getLabVenue();
		
		String labDetails = "Day: " + labDay + "\n" + time + "\n" + venue;
		
		System.out.println(labDetails);
	}

	public void setIndexLabDetails(int intDay, int startHours, int startMinutes,int endHours, int endMinutes,String labVenue) {
		labStartTime.setHours(startHours);
		labStartTime.setMinutes(startMinutes);
		labEndTime.setHours(endHours);
		labEndTime.setMinutes(startMinutes);
		labDay = setDay(intDay);
		updateIndexLabVenue(labVenue);
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


	

}
