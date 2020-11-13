package com.example.ss7g7.stars;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


//can be deleted
//This class is used by AdminStudUI to check against the database.


public class StudMngmt {
	

	static StarsDB database = StarsDB.getInstance();
	static ArrayList<Student> studentList = database.getAllStudents(); //retrieve db from StarsDB
	
	static Scanner sc = new Scanner(System.in);
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
	
	
	//find a student via their matriculation number 
	public static Student getStudentByMatric(String matricNo){ 
		for (Student student : studentList) {
			if (student.getMatricNo().equals(matricNo)) {
				return student;}
		}
		return null;
	}

	//For admin to edit a student access period
	public static void updateAccessPeriod(String matricNo, Calendar newAccessStart, Calendar newAccessEnd){ 
		Student student = getStudentByMatric(matricNo);
		student.setAccessStart(newAccessStart);
		student.setAccessEnd(newAccessEnd);
		
	}
	
	
	
	//check existing username in student database
	public static Boolean isExistingUsername(String username){ 
		for (Student s : studentList) {
			if (s.getUserName().equals(username)) {
				System.out.println("This username has already been used, please try again.");
				return false;
			}
		}
		return true;
	}

	//check student via their matriculation number 
	public static Boolean isExistingMatNum(String matricNo){ 
		for (Student s : studentList) {
			if (s.getMatricNo().equals(matricNo)) {
				System.out.println("Matriculation number is found in database.");
				return false;
			}
		}
		return true;
	}
	
	// add new student to studentlist in database
	public static void addStudent(String username, String password, String name, String lastName,
			String matricNo, String gender, String nationality, int mobileNo, String email, Calendar accessStart, Calendar accessEnd) 
	
	{
        Student newStud = new Student(username, password, name, lastName, matricNo, gender, nationality, mobileNo, email, accessStart, accessEnd);
        database.addStudent(newStud);
        database.setDBInstance(database);
      
	}

	//remove a student from studentlist in database 
	public static void removeStudent(String matNum) {
		Student student = getStudentByMatric(matNum);
		database.removeStudent(student);
		database.setDBInstance(database);
		
	}
	
	public static void printStudentList(){ //show all student exists in student db
		boolean flag = false;
		System.out.println();
		System.out.println("Matriculation Number\tFull Name");
		System.out.println("---------------------------------------------------");
		
		if(studentList.size() <= 0){
			System.out.println("\nNo record is found!\n");
			return;
		}
		
		for (Student s: studentList){
			System.out.print(s.getMatricNo() + "         \t");
			System.out.print(s.getName() + " " + s.getLastName());
			System.out.println();
			
			flag = true;
		}
		if (!flag) System.out.println("\nNo record is found!");
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
}
