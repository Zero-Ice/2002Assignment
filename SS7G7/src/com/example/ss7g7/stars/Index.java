package com.example.ss7g7.stars;

import java.time.*;

public class Index {
	private int indexNum;
	private int numVacancy;
	private String[] seatVacancy;
	private String tutVenue;
	private String tutDateTime;
	private String labVenue;
	private String labDateTime;
	private boolean indexFull;
	//TODO: waitlist for students
	//TODO: indexFull 
	//TODO: Venue for lab, tut
	//TODO: Day and Time for lab, tut
	
	public Index(int index_Num, int num_Vacancy) {
		this.indexNum=index_Num;
		this.numVacancy=num_Vacancy;
		seatVacancy = new String[numVacancy];
		
		//initialize all to vacant
		for(int i =0;i<numVacancy;i++) {
			this.seatVacancy[i]="vacant";
		}
	}
	
	// TODO: Waitlist of students
	// TODO: if index full set boolean true
	public void assignStudent(String matricNo) {
		for(int i =0;i<numVacancy;i++) {
			if(seatVacancy[i].contains(matricNo)) {
				System.out.println(matricNo+ " has registered before.");
				return;
			}else if(seatVacancy[i]== "vacant"){
				seatVacancy[i]=matricNo;
				
				return;
			}
		}
		System.out.println("Index Full!");
	}
	
	public void unassignStudent(String matricNo) {
		// TODO: if indexFull boolean was true, change to false
		for(int i =0;i<numVacancy;i++) {
			if(seatVacancy[i].contains(matricNo)) {
				seatVacancy[i] = "vacant";
				return;
			}
		}
		System.out.println(matricNo+ " was not found.");
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
			if(seatVacancy[i]=="vacant") {
				numOfVacant++;
			}
		}
		return numOfVacant;
	}
	
	public void showAllSeats() {
		for(int i =0;i<numVacancy;i++) {
			System.out.println(i+1 + ": "+seatVacancy[i]);
		}
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

	public void setTutVenue(String tutVenue) {
		this.tutVenue = tutVenue;
	}

	public String getTutDateTime() {
		return tutDateTime;
	}

	public void setTutDateTime(String tutDateTime) {
		this.tutDateTime = tutDateTime;
	}

	public String getLabVenue() {
		return labVenue;
	}

	public void setLabVenue(String labVenue) {
		this.labVenue = labVenue;
	}

	public String getLabDateTime() {
		return labDateTime;
	}

	public void setLabDateTime(String labDateTime) {
		this.labDateTime = labDateTime;
	}


	

}
