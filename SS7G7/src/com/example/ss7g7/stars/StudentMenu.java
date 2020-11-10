package com.example.ss7g7.stars;

import java.util.ArrayList;
import java.util.Scanner;

import com.example.ss7g7.stars.User.UserType;

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

		boolean run = true;
		int choice;

		while (run) {
			System.out.println("Welcome " + student.getName() + " " + student.getUserName());
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

		// Step 1: Check if course is already registered.
		// E.g CZ2002 has 10195 and 10196. If already registered 10195, do not allow
		// 10196 to be added

		Course courseToAdd = db.getCourseByIndex(indexToAdd);
		if (courseToAdd != null) {
			boolean alreadyRegistered = student.containsCourse(courseToAdd.getCourseCode());

			if (alreadyRegistered) {
				System.out.println("Course " + courseToAdd.getCourseCode()
						+ " is already registered. Try dropping or changing index first");
				return;
			}
		} else {
			System.out.println("Cannot find course with index no " + indexToAdd);
			return;
		}

		// Step2: Check if will clash with other courses
		Index index = courseToAdd.getIndex(indexToAdd);
		
		boolean willClash = student.willNewCourseClashTimetable(courseToAdd, index);
		if(willClash)
		{
			System.out.println("Cannot add course as it clashes with timetable");
			return;
		}

		// Step3: If step 1 and 2 pass, confirm to add the new course.

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
				index.assignStudent(student);
				
				db.updateStudentRecords(student);
				db.updateCourseRecords(courseToAdd);
				
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

		// Step1: Check if student contains that index
		if (!student.containsCourse(indexToDrop)) {
			System.out.println("Cannot drop course with index " + indexToDrop + " as it is not registered");
			return;
		}

		// Step2: Double check that the course exists in the db
		Course c = db.getCourseByIndex(indexToDrop);
		if (c == null) {
			System.out.println("Cannot find course with index no " + indexToDrop);
			return;
		}

		// Step3: If step 1 and 2 passes, double confirm to drop.

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
				
				Index index = c.getIndex(indexToDrop);
				
				db.updateCourseRecords(c);
				db.updateStudentRecords(index.unassignStudent(student));
				
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

	// Print the details of a Course with index
	private void printCourse(int indexNo) {
		Course c = db.getCourseByIndex(indexNo);
		if (c != null) {
			System.out.println("Index Number " + indexNo + " Course " + c.getCourseCode() + "\n"
					+ "Course Type REGISTERED + Status REGISTERED");
		} else {
			System.out.println("Cannot find course with index no " + indexNo);
		}
	}

	// Print the details of the courses registered by the student
	private void printCoursesRegistered() {
		System.out.println(student.printCourses());
	}

	// Prints the details of the vacancy based on user input
	private void checkVacanciesAvailable() {
		System.out.print("Enter index number: ");
		int indexNo = Integer.valueOf(scanner.nextLine());

		// Step 1: Check if index entered belongs to a course
		Course c = db.getCourseByIndex(indexNo);
		if (c != null) {
			Index index = c.getIndex(indexNo);

			// Double check index is not null
			if (index != null) {

				// Class type, Group, Day, Time, Venue, Remark
				System.out.println("Class type, Group, Day, Time, Venue, Remark");
				System.out.println("Vacancy " + index.getNumOfVacancies());
			} else {
				System.out.println("Index " + indexNo + "does not exist in the course");
			}
		} else {
			System.out.println("Cannot find course with index no " + indexNo);
		}
	}

	// Changes the index within a course.
	private void changeIndex() {
		System.out.println(student.printCourses());
		System.out.print("Enter the index that you want to change from: ");
		int indexFrom = Integer.valueOf(scanner.nextLine());

		// Step1: Make sure student contains the index he/she wants to change from
		if (!student.containsCourse(indexFrom)) {
			System.out.println("Index " + indexFrom + " is not found in your registered courses");
			return;
		}

		// Step2: Ask for the student to change
		System.out.print("Enter index you want to change to: ");
		int indexTo = Integer.valueOf(scanner.nextLine());

		// Step3: Double check that the index from and to belongs to the same course
		Course fromCourse = db.getCourseByIndex(indexFrom);
		Course toCourse = db.getCourseByIndex(indexTo);

		if (fromCourse == null || toCourse == null || !fromCourse.getCourseCode().equals(toCourse.getCourseCode())) {
			System.out.println("Indexes are not from the same course");
			return;
		}

		Index fromIndex = fromCourse.getIndex(indexFrom);
		Index toIndex = toCourse.getIndex(indexTo);
		
		if(fromIndex == null || toIndex == null) {
			System.out.println("Index is not found in the database");
			return;
		}
		
		// Step4: Check if new index has a clash.
		boolean willClash = student.willNewCourseClashTimetable(toCourse, toIndex);
		if(willClash) {
			System.out.println("Cannot add course as it clashes with timetable");
			return;
		}

		// Step5: Double confirm
		boolean run = true;
		while (run) {
			System.out.println(fromIndex);
			System.out.println(toIndex);
			System.out.println("(1) Confirm to Change Index");
			System.out.println("(2) Main Menu");

			int choice = Integer.valueOf(scanner.nextLine());

			if (choice == 1) {
				fromIndex.unassignStudent(student);
				toIndex.assignStudent(student);
				
				db.updateStudentRecords(student);
				db.updateCourseRecords(fromCourse);
				db.updateCourseRecords(toCourse);
				
				System.out.println("Successfully changed index");
				run = false;
			} else if (choice == 2) {
				run = false;
				System.out.println("Returning to main menu");
			} else {
				System.out.println("Invalid option");
			}
		}

	}

	private void swapIndex() {
		// TODO: Check if both index belong to the same course. If same course, swap
		// index (Remove + Add for both)
		
		// Step1: Ask index to swap
		
		int indexFrom = 0;
		int indexTo = 0;
		
		
		// TODO: Check if student contains the course he/she wants to swap from
		
		do {
			
			if(indexFrom != 0) {
				System.out.println("Index is not registered to you, please enter a valid index. ");
			}
			
			System.out.println(student.printCourses());
			System.out.print("Enter the index that you want to swap: ");
			indexFrom = Integer.valueOf(scanner.nextLine());
			
			
		}while (!student.containsCourse(indexFrom));
		
		System.out.println("...Switching to student 2\n");
		
		// Step2: Login for student 2. Check is successful and is student
		Login login = new Login(db);
		Login.LOGIN_RESULT loginResult = login.login();
		
		if(loginResult != loginResult.SUCCESSFUL_LOGIN) {
			System.out.println("Unsuccessful login");
			return;
		}
		
		User otherUser = login.getCurrentUser();
		if(otherUser == null || otherUser.getUserType() != UserType.STUDENT) {
			System.out.println("User is null or is not student");
			return;
		}
		
		Student otherStudent = db.getStudent(otherUser.getUsername());
		if(otherStudent == null) {
			System.out.println("Cannot find student in database");
			return;
		}
		
		// Step3: Ask for other user index to swap
		// TODO: Check if index entered is an index that is registered to the student
		do {
			
			if (indexTo != 0) {
				System.out.println("Index is not registered to you, please enter a valid index. ");
			}
			
			System.out.println(otherStudent.printCourses());
			System.out.print("Enter the index that you want to swap: ");
			indexTo = Integer.valueOf(scanner.nextLine());
			
			
		}while (!otherStudent.containsCourse(indexTo));
		
		
		// Step4: Check indexes are from the same course
		Course fromCourse = db.getCourseByIndex(indexFrom);
		Course toCourse = db.getCourseByIndex(indexTo);

		if (fromCourse == null || toCourse == null || !fromCourse.getCourseCode().equals(toCourse.getCourseCode())) {
			System.out.println("Indexes are not from the same course");
			return;
		}else if (indexFrom == indexTo) {
			System.out.println("Indexes are the same, swap is redundant.");
			return;
		}

		Index fromIndex = fromCourse.getIndex(indexFrom);
		Index toIndex = toCourse.getIndex(indexTo);
		
		if(fromIndex == null || toIndex == null) {
			System.out.println("Index is not found in the database");
			return;
		}
		
		// Step5: Check if new index has a clash.
		
		// Course does not matter as we are swapping index only. 
		boolean willClash = student.willNewCourseClashTimetable(fromCourse, toIndex);
		boolean willOtherStudClash = otherStudent.willNewCourseClashTimetable(toCourse, fromIndex);
		
		if(willClash || willOtherStudClash) {
			System.out.println("Cannot swap indexes as new index does not fit in timetable");
			return;
		}
		
		// Step6: Print details and double confirm.
		boolean run = true;
		while (run) {
			System.out.println(fromIndex);
			System.out.println(toIndex);
			System.out.println("(1) Confirm to Change Index");
			System.out.println("(2) Main Menu");

			int choice = Integer.valueOf(scanner.nextLine());

			if (choice == 1) {
				fromIndex.unassignStudent(student);
				toIndex.unassignStudent(otherStudent);
				
				fromIndex.assignStudent(otherStudent);
				toIndex.assignStudent(student);
				
				db.updateCourseRecords(fromCourse);
				db.updateCourseRecords(toCourse);
				
				db.updateStudentRecords(student);
				db.updateStudentRecords(otherStudent);
				
				System.out.println(student.getMatricNo() + "-Index Number " + fromIndex.getIndexNum() + " has been successfully swapped with " + otherStudent.getMatricNo() + "-Index Number " + toIndex.getIndexNum());
				run = false;
			} else if (choice == 2) {
				run = false;
				System.out.println("Returning to main menu");
			} else {
				System.out.println("Invalid option");
			}
		}
	}
}
