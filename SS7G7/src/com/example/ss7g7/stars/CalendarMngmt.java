package com.example.ss7g7.stars;

import java.text.*;
import java.util.*;

//can be deleted
//this class is used by AdminStudUI to check


public class CalendarMngmt {
	
	static Scanner sc = new Scanner(System.in);
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");	

	public static String dateToString(Calendar cal) {
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		return String.format("%02d/%02d/%4d %02d:%02d", day, month + 1, year, hour, minute);
	}

	public static String timeToString(Calendar cal) {
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		return String.format("%02d:%02d", hour, minute);
	}

	//check if user input the right access start/end format 
	public static Calendar getValidDateTime(String mode){
		
		String date = "";

	    Date parsedDate = null;
		boolean validDate = false;		
		Calendar newDate = Calendar.getInstance();
		
		do{
			System.out.print("Enter " + mode + " (DD/MM/YYYY HH:MM): ");
			date  = sc.nextLine();
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		    try {
		    	parsedDate = dateFormat.parse(date);
		    	 
		    } catch (ParseException e) {
		        System.out.println("Input is not in the correct format!");
		        continue;
		    }
		    newDate.setTime(parsedDate);

		    validDate = true;

		} while(!validDate);
				
		return newDate;
	}
	
	//conversation
	public static String dayIntToString(int day){
		String dayString = null;
		switch(day){
			case 1: dayString = "MON";
					break;
			case 2: dayString = "TUE";
					break;
			case 3: dayString = "WED";
					break;
			case 4: dayString = "THU";
					break;
			case 5: dayString = "FRI";
					break;
			case 6: dayString = "SAT";
					break;	
			case 7: dayString = "SUN";
					break;
		}
		return dayString;
	}
}
