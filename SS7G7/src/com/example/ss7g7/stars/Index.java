package com.example.ss7g7.stars;

public class Index {
	private int indexNum;
	private int numVacancy;
	private String[] vacancy;
	private String tutVenue;
	private String tutDateTime;
	private String labVenue;
	private String labDateTime;
	private String lecVenue;
	private String lecDateTime;
	private boolean indexFull;
	
	
	public Index(int index_Num, int num_Vacancy) {
		this.indexNum=index_Num;
		this.numVacancy=num_Vacancy;
		vacancy = new String[numVacancy];
		
		for(int i =0;i<numVacancy;i++) {
			this.vacancy[i]="no student";
		}
	}
	
	public int showNumOfVacancies() {
		int numOfVacant=0;
		
		for(int i =0;i<numVacancy;i++) {
			if(vacancy[i]=="no student") {
				numOfVacant++;
			}
		}
		
		return numOfVacant;
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

	public String getLecVenue() {
		return lecVenue;
	}

	public void setLecVenue(String lecVenue) {
		this.lecVenue = lecVenue;
	}

	public String getLecDateTime() {
		return lecDateTime;
	}

	public void setLecDateTime(String lecDateTime) {
		this.lecDateTime = lecDateTime;
	}
	
	

}
