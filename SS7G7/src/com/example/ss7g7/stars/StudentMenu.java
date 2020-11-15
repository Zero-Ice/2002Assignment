package com.example.ss7g7.stars;

import java.util.ArrayList;
import java.util.Scanner;

import com.example.ss7g7.stars.User.UserType;

/**
* <h1>Student Menu</h1>
* Provides a console based user interface to
* students who are logged in
* 
* <p>
* Provides 7 options to the student user
* Add Course
* Drop Course
* Print Courses Registered
* Check Vacancies Available
* Change Index No.
* Swap Index No. with another student
* Logout
*
* @author  Ong Rui Peng
* @since   2020-10-15
*/
public class StudentMenu {
	private Student student;
	Scanner scanner;
	StarsDB db;
	private final int RECOMMENDED_AU = 21;

	/**
	 * Constructor for StudentMenu class
	 * 
	 * @param db
	 * @param student
	 */
	public StudentMenu(StarsDB db, Student student) {
		this.db = db;
		this.student = student;
		scanner = new Scanner(System.in);
	}

	/**
	 * This method controls the main loop of the
	 * console UI for StudentMenu
	 * Asks the user for input and executes functions
	 * based on user input. 
	 */
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
			default:
				System.out.println("Invalid choice. Try again");
				break;
			}
		}
	}

	/**
	 * This method helps the student add a new course.
	 * The method performs a series of checks such as
	 * 1. Checking if the course is already registered by the student
	 * 2. Check if the new course will clash with the student's timetable
	 * 3. Check if AU > 21
	 * Lastly, the summary is printed out and the student may confirm
	 * to add the new course or exit.
	 */
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
		
		// Step3: Check if added course > 21 AU
		if(courseToAdd.getAU() + student.getAUs() > 21) {
			System.out.println("Cannot add course as it exceeds recommended AU of 21");
			return;
		}

		// Step4: If step 1 and 2 pass, confirm to add the new course.
		

		// Summary
		// Index Number, Course Code
		// Class Type, Group, Day, Time, Venue, Remark
		
		
		System.out.println("Index Number " + indexToAdd + " Course " + courseToAdd.getCourseCode());
		System.out.println(courseToAdd.getLecDetails());
		System.out.println(index.getTutDetails());
		System.out.println(index.getLabDetails());

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

	/**
	 * This method helps the student drop a course
	 * The method performs a series of checks such as
	 * 1. Checking if the course is already registered by the student
	 * 2. Check if course exists in the db
	 * Lastly, the summary is printed out and the student may confirm
	 * to drop the new course or exit.
	 */
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
		System.out.println("Status " + student.getCourseStatus(c.getCourseCode()));

		boolean run = true;
		while (run) {
			System.out.println("(1) Confirm to Drop Course");
			System.out.println("(2) Main Menu");
			int choice = Integer.valueOf(scanner.nextLine());

			if (choice == 1) {
				
				Index index = c.getIndex(indexToDrop);
				student.dropCourse(index.getIndexNum());
				
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

	/**
	 * This method prints details of an course based on a index number.
	 * The format printed out is 
	 * Index Number <indexNo> Course <courseCode>
	 * Course Type <courseType> + Status <status>
	 * 
	 * @param indexNo
	 */
	private void printCourse(int indexNo) {
		Course c = db.getCourseByIndex(indexNo);
		if (c != null) {
			System.out.println("Index Number " + indexNo + " Course " + c.getCourseCode() + "\n"
					+ "Course Type REGISTERED + Status REGISTERED");
		} else {
			System.out.println("Cannot find course with index no " + indexNo);
		}
	}

	/**
	 * This method prints the courses registered by the student
	 * 
	 */
	private void printCoursesRegistered() {
		System.out.println(student.printCourses());
	}

	/**
	 * This method retrieves the vacancies available for an index number
	 * Asks the user to input an index number then prints out
	 * its details if it exists
	 */
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
				System.out.println("Index Number " + index.getIndexNum() + "    Course " + c.getCourseCode());
				System.out.println("Group, Day, Time, Venue, Remark");
				System.out.println(index.getLabDetails());
				System.out.println(index.getTutDetails());
				System.out.println("Places Available " + index.getNumOfVacancies() + "     Length of waitlist " + index.getWaitlistLength());
			} else {
				System.out.println("Index " + indexNo + "does not exist in the course");
			}
		} else {
			System.out.println("Cannot find course with index no " + indexNo);
		}
	}

	/**
	 * This method provides an interface to help the student change an index
	 * Asks the user to input the indexes they want to change to and from
	 * Performs a series of checks
	 * 1. Both indexes belongs to the same course
	 * 2. Checks if the new index will clash with student's timetable
	 * Prints out the summary and the student may confirm to change indexes
	 * or exit
	 */
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
		boolean willClash = student.willSwappedCourseClashTimetable(toCourse, toIndex, fromCourse);
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

	/**
	 * This method provides an interface to help the student change an index
	 * with another student
	 * Asks the user to input the indexes they want to change
	 * Asks the other student for input (login and the index they want to change)
	 * Performs a series of checks
	 * 1. Other student is registered to the index they would like to change
	 * 2. Check indexes are from the same course
	 * 3. Checks if the new index will clash with either student(s) timetable
	 * Prints out the summary and the student(s) may confirm to change indexes
	 * or exit
	 */
	private void swapIndex() {
		// TODO: Check if both index belong to the same course. If same course, swap
		// index (Remove + Add for both)
		
		// Step 0: Check if any registered courses. Cannot swap if no registered
		if(student.getCourses().size() == 0) {
			System.out.println("No registered courses.");
			return;
		}
		
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
		boolean willClash = student.willSwappedCourseClashTimetable(toCourse, toIndex, fromCourse);
		boolean willOtherStudClash = otherStudent.willSwappedCourseClashTimetable(fromCourse, fromIndex, toCourse);
		
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
				// Do not trigger waitlist.
				// An empty vacancy for each index will be the result.
				fromIndex.unassignStudent(student, false);
				toIndex.unassignStudent(otherStudent, false);
				
				// Assign the students to the empty vacancy from the result earlier
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
