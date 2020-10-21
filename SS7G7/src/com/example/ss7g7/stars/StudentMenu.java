package com.example.ss7g7.stars;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentMenu {
	private Student student;
	Scanner scanner;
	StarsDB db;

	public StudentMenu(StarsDB db, Student student) {
		this.db = db;
		this.student = student;
		scanner = new Scanner(System.in);
	}

	public void run() {

		////////////////////// test for shah Pls do not remove
		Course cz2007 = new Course("cz2007", "DB", "SCSE",3);
		cz2007.addIndex(1011, 20);
		cz2007.addIndex(1012, 30);
		cz2007.addIndex(1013, 15);

		Course cz2006 = new Course("cz2006", "SE", "SCSE",3);
		cz2006.addIndex(2011, 10);
		cz2006.addIndex(2012, 30);
		cz2006.addIndex(2013, 12);


		Course cz2005 = new Course("cz2005", "OS", "SCSE",3);
		cz2005.addIndex(3011, 5);
		cz2005.addIndex(3012, 20);
		cz2005.addIndex(3013, 4);


		System.out.println("\n");
		cz2005.showfullCourseDetails();
		cz2006.showfullCourseDetails();
		cz2007.showfullCourseDetails();


		cz2005.assignStudent(3013, "U1921464C");
		cz2005.assignStudent(3013, "U1451201A");
		cz2005.assignStudent(3013, "U1945445D");
		cz2005.assignStudent(3013, "U1921201G");
		System.out.println("\n");
		cz2005.showSeats(3013);
		
		cz2005.assignStudent(3012, "U1921201G");
		System.out.println("\n");
		cz2005.showSeats(3012);
		System.out.println("\n");
		cz2005.showfullCourseDetails();
		
		System.out.println("\nlec Details");
		cz2005.setLecDetails(5, 12, 30, 14, 30, "LT19");
		cz2005.getLecDetails();
		
		System.out.println("\nlab Details");
		cz2005.setLabDetails(3013, 1, 10, 30, 12, 30, "SPL");
		cz2005.getLabDetails(3013);
		
		/////////////////////////////////////

		boolean run = true;
		int choice;

		while (run) {
			System.out.println("Welcome " + student.getName() + " " + student.getUsername());
			System.out.println("Enter your choice");
			System.out.println("(1) Add Course");
			System.out.println("(2) Drop Course");
			System.out.println("(3) Print Course Registered");
			System.out.println("(4) Check Vacancies Available");
			System.out.println("(5) Change Index No.");
			System.out.println("(6) Swap Index Number with Another Student");
			System.out.println("(7) to logout");
			choice = Integer.valueOf(scanner.nextLine());

			switch (choice) {
			case 1:
				addCourse();
				break;
			case 2:
				dropCourse();
				break;
			case 3:
				printCoursesRegistered();
				break;
			case 4:
				checkVacanciesAvailable();
				break;
			case 5:
				changeIndex();
				break;
			case 6:
				swapIndex();
				break;
			case 7:
				System.out.println("See you again");
				run = false;
				break;
			}
		}
	}

	private void addCourse() {
		System.out.print("Enter an index number of the course: ");
		int indexToAdd = Integer.valueOf(scanner.nextLine());

		// Check if course is already registered.
		// E.g CZ2002 has 10195 and 10196. If already registered 10195, do not allow 10196 to be added
		
		Course courseToAdd = db.getCourse(indexToAdd);
		if(courseToAdd != null) {
			boolean alreadyRegistered = student.containsCoure(courseToAdd.getCourseCode());
			
			if(alreadyRegistered) {
				System.out.println("Course " + courseToAdd.getCourseCode() + " is already registered. Try dropping or changing index first");
				return;
			}
		}
		else {
			System.out.println("Cannot find course with index no " + indexToAdd);
			return;
		} 

		// Summary
		// Index Number, Course Code
		// Class Type, Group, Day, Time, Venue, Remark
		System.out.println("Index Number " + indexToAdd + " Course " + courseToAdd.getCourseCode());
		System.out.println("<Details>");

		boolean run = true;
		while (run) {
			System.out.println("(1) Confirm to Add Course");
			System.out.println("(2) Main Menu");
			int choice = Integer.valueOf(scanner.nextLine());

			if (choice == 1) {
				boolean result = student.addCourse(courseToAdd.getCourseCode(), indexToAdd);
				Index index = courseToAdd.getIndex(indexToAdd);
				index.assignStudent(student.getMatricNo());
				System.out.println("Successfully added index " + indexToAdd);
				run = false;
			} else if (choice == 2) {
				run = false;
				System.out.println("Returning to main menu");
			} else {
				System.out.println("Invalid option");
			}
		}
	}

	private void dropCourse() {
		System.out.print("Enter an index number of the course to drop: ");
		int indexToDrop = Integer.valueOf(scanner.nextLine());

		if (!student.containsCourse(indexToDrop)) {
			System.out.println("Cannot drop course with index " + indexToDrop + " as it is not registered");
			return;
		}

		Course c = db.getCourse(indexToDrop);
		if(c == null) {
			System.out.println("Cannot find course with index no " + indexToDrop);
			return;
		}

		// Summary
		// Index Number, Course Code
		System.out.println("Index Number " + indexToDrop + " Course " + c.getCourseCode());
		System.out.println("Course Type " + /* CORE */ "<CourseType> " + " Status REGISTERED");

		boolean run = true;
		while (run) {
			System.out.println("(1) Confirm to Drop Course");
			System.out.println("(2) Main Menu");
			int choice = Integer.valueOf(scanner.nextLine());

			if (choice == 1) {
				boolean result = student.dropCourse(indexToDrop);
				Index index = c.getIndex(indexToDrop);
				index.unassignStudent(student.getMatricNo());
				System.out.println("Successfully dropped index " + indexToDrop);
				run = false;
			} else if (choice == 2) {
				run = false;
				System.out.println("Returning to main menu");
			} else {
				System.out.println("Invalid option");
			}
		}
	}
	
	private void printCourse(int indexNo) {
		Course c = db.getCourse(indexNo);
		if(c != null) {
			System.out.println("Index Number " + indexNo + " Course " + c.getCourseCode() + "\n"
					+ "Course Type REGISTERED + Status REGISTERED");
		} else {
			System.out.println("Cannot find course with index no " + indexNo);
		}
	}

	private void printCoursesRegistered() {
		System.out.println(student.printCourses());
	}

	private void checkVacanciesAvailable() {
		System.out.print("Enter index number: ");
		int indexNo = Integer.valueOf(scanner.nextLine());

		Course c = db.getCourse(indexNo);
		if(c != null) {
			Index index = c.getIndex(indexNo);
			// CLass type, Group, Day, Time, Venue, Remark
			System.out.println("Class type, Group, Day, Time, Venue, Remark");
		} else {
			System.out.println("Cannot find course with index no " + indexNo);
		}
	}

	private void changeIndex() {
		System.out.println(student.printCourses());
		System.out.print("Enter the index that you want to change from: ");
		int indexFrom = Integer.valueOf(scanner.nextLine());

		if (!student.containsCourse(indexFrom)) {
			System.out.println("Index " + indexFrom + " is not found in your registered courses");
			return;
		}

		System.out.print("Enter index you want to change to: ");
		int indexTo = Integer.valueOf(scanner.nextLine());

		// TODO: Check indexFrom and indexTo belongs to the same course code.
		Course fromCourse = db.getCourse(indexFrom);
		Course toCourse = db.getCourse(indexTo);
		
		if(fromCourse != null && toCourse != null) {
			Index fromIndex = fromCourse.getIndex(indexFrom);
			Index toIndex = toCourse.getIndex(indexTo);
			
			boolean run = true;
			while(run) {
				System.out.println(fromIndex);
				System.out.println(toIndex);
				System.out.println("(1) Confirm to Change Index");
				System.out.println("(2) Main Menu");
				
				int choice = Integer.valueOf(scanner.nextLine());

				if (choice == 1) {
					fromIndex.unassignStudent(student.getMatricNo());
					toIndex.assignStudent(student.getMatricNo());
					System.out.println("Successfully changed index");
					run = false;
				} else if (choice == 2) {
					run = false;
					System.out.println("Returning to main menu");
				} else {
					System.out.println("Invalid option");
				}
			}
			
		} else {
			System.out.println("Indexes are not from the same course");
		}
	}

	private void swapIndex() {
		// TODO: Check if both index belong to the same course. If same course, swap
		// index (Remove + Add for both)

	}
}
