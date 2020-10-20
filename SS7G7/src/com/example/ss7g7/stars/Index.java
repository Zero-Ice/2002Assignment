package com.example.ss7g7.stars;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Index {
	private int indexNum;
	private int numVacancy;
	private String[] seatVacancy;
	private String tutVenue;
	private Date tutDateTime;
	private String labVenue;
	private Date labDateTime;
	private boolean indexFull;
	//TODO: waitlist for students
	//TODO: indexFull 
	//TODO: Venue for lab, tut
	//TODO: Day and Time for lab, tut
	
	public Index(int index_Num, int num_Vacancy) {
		this.indexNum=index_Num;
		this.numVacancy=num_Vacancy;
		seatVacancy = new String[numVacancy];
		this.tutDateTime = new Date();
		this.labDateTime = new Date();
		
		
		//initialize all to vacant
		for(int i =0;i<numVacancy;i++) {
			this.seatVacancy[i]="vacant";
		}
	}
	
	public String getStudent(String matricNo) {
		for(int i =0;i<numVacancy;i++) {
			if(seatVacancy[i].contains(matricNo)) {
				return seatVacancy[i];
			}
		}
		return null;
	}
	
	// TODO: Waitlist of students
	// TODO: if index full set boolean true
	public void assignStudent(String matricNo) {
		if(getStudent(matricNo)==matricNo) {
			System.out.println(matricNo+ " has registered before.");
			return;
		}else{
			for(int i =0;i<numVacancy;i++) {
				if(seatVacancy[i]=="vacant") {
					seatVacancy[i]=matricNo;
//					System.out.println(matricNo + " assigned to " + indexNum);
					return;
				}
			}
		}
		System.out.println("Index Full!");
	}
	
	public void unassignStudent(String matricNo) {
		// TODO: if indexFull boolean was true, change to false
		if(getStudent(matricNo)== matricNo) {
			for(int i =0;i<numVacancy;i++) {
				if(seatVacancy[i]==matricNo) {
					seatVacancy[i]="vacant";
					return;
				}
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

	public void getTutDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("E hh:mm a");
		System.out.println(formatter.format(tutDateTime));
	}

	public void setTutDateTime(int hours, int minutes) {
		tutDateTime.setDate(21);
		tutDateTime.setHours(hours);
		tutDateTime.setMinutes(minutes);
	}

	public String getLabVenue() {
		return labVenue;
	}

	public void setLabVenue(String labVenue) {
		this.labVenue = labVenue;
	}

	public void getLabDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("E hh:mm a");
		System.out.println(formatter.format(labDateTime));
	}

	public void setLabDateTime(int hours, int minutes) {
		labDateTime.setDate(21);
		labDateTime.setHours(hours);
		labDateTime.setMinutes(minutes);
	}


	

}
