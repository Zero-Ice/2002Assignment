package com.example.ss7g7.stars;

import java.util.Scanner;

public class StudentMenu {
	private Student student;
	Scanner scanner;
	
	public StudentMenu(Student student) {
		this.student = student;
		scanner = new Scanner(System.in);
	}
	
	public void run() {
		
		
		//////////////////////    test for shah Pls do not remove
		Course cz2007 = new Course("cz2007","DB","SCSE");
		cz2007.addIndex(1011, 20);
		cz2007.addIndex(1012, 30);
		cz2007.addIndex(1013, 15);

		Course cz2006 = new Course("cz2006","SE","SCSE");
		cz2006.addIndex(2011, 10);
		cz2006.addIndex(2012, 30);
		cz2006.addIndex(2013, 12);

		Course cz2005 = new Course("cz2005","OS","SCSE");
		cz2005.addIndex(3011, 5);
		cz2005.addIndex(3012, 20);
		cz2005.addIndex(3013, 4);
		
		System.out.println("\n");
		cz2005.showfullCourseDetails();
		cz2006.showfullCourseDetails();
		cz2007.showfullCourseDetails();
		System.out.println("\n");

		
		cz2005.assignStudent(3013, "U1921464C");
		cz2005.assignStudent(3013, "U1451201A");
		cz2005.assignStudent(3013, "U1945464C");
		cz2005.assignStudent(3013, "U1921201G");
		cz2005.assignStudent(3012, "U1921201G");
		System.out.println("\n");
		cz2005.showfullCourseDetails();
		
		
		/////////////////////////////////////
		
		boolean run = true;
		int choice;
		
		while(run) {
			System.out.println("Welcome " + student.getName() + " " + student.getUsername());
			System.out.println("Enter your choice");
			System.out.println("(1) To view your details");
			System.out.println("(2) Add Course");
			System.out.println("(3) Drop Course");
			System.out.println("(4) Print Course Registered");
			System.out.println("(5) Check Vacancies Available");
			System.out.println("(6) Change Index No.");
			System.out.println("(7) Swap Index Number with Another Student");
			System.out.println("(8) to logout");
			choice = Integer.valueOf(scanner.nextLine());
			
			switch(choice) {
			case 1:
				System.out.println(student);
				break;
			case 2:
				addCourse();
				break;
			case 3:
				dropCourse();
				break;
			case 4:
				printCoursesRegistered();
				break;
			case 5:
				checkVacanciesAvailable();
				break;
			case 6:
				changeIndex();
				break;
			case 7:
				swapIndex();
				break;
			case 8:
				System.out.println("See you again");
				run = false;
				break;
			}
		}
	}
	
	private void addCourse() {
		System.out.print("Enter an index number of the course: ");
		int indexToAdd = Integer.valueOf(scanner.nextLine());
		
		boolean studContainsCourse = false;
		boolean result = false;
		if(student.containsCourse(indexToAdd)) {
			studContainsCourse = true;
		} else {
			result = student.addCourse(indexToAdd);
		}
		
		// TODO: Print summary of course to add. 
		
		if(result) {
			System.out.println("Successfully added course with index " + indexToAdd);
		} else {
			if(studContainsCourse) {
				System.out.println("Cannot add course with index " + indexToAdd + " as it is already registered");
			} else {
				System.out.println("Failed to add course with index " + indexToAdd);
			}
		}
		
		// TODO: Update course vacancy
	}
	
	private void dropCourse() {
		System.out.println(student.printCourses());
		System.out.println("Enter the course index to drop: ");
		
		int indexToRemove = Integer.valueOf(scanner.nextLine());
		
		boolean result = false;
		boolean studContainsCourse = true;
		if(!student.containsCourse(indexToRemove)) {
			studContainsCourse = false;
		} else {
			result = student.removeCourse(indexToRemove);
		}
		
		if(result) {
			System.out.println("Successfully removed course " + indexToRemove);
		} else {
			if(studContainsCourse) {
				System.out.println("Failed to course " + indexToRemove);
			} else {
				System.out.println("Cannot find course with index " + indexToRemove + " in registered course");
			}
		}
	}
	
	private void printCoursesRegistered() {
		System.out.println(student.printCourses());
	}
	
	private void checkVacanciesAvailable() {
		System.out.print("Enter course code: ");
		String courseCode = scanner.nextLine();
		
		// TODO: For each index in course code, print it.
		
	}
	
	private void changeIndex() {
		System.out.println(student.printCourses());
		System.out.print("Enter the index that you want to change from: ");
		int indexFrom = Integer.valueOf(scanner.nextLine());
		
		if(!student.containsCourse(indexFrom)) {
			System.out.println("Index " + indexFrom + " is not found in your registered courses");
			return;
		}
		
		System.out.print("Enter index you want to change to: ");
		int indexTo = Integer.valueOf(scanner.nextLine());
		
		// TODO: Check indexFrom and indexTo belongs to the same course code.
		
		// TODO: Remove this temp print
		System.out.println("Change Index End");
	}
	
	private void swapIndex() {
		// TODO: Check if both index belong to the same course. If same course, swap index (Remove + Add for both)
		
	}
}
