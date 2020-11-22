package com.example.ss7g7.stars;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * <h1>Admin Course User Interface</h1>
 * 
 * <p>
 * The AdminCourseUI provides a streamlined 
 * overview of the administrative actions that
 * an admin user would be allowed to make on
 * courses.
 * 
 * Provides 9 options
 * 1)Add a new course to StarsSystem
 * 2)Remove an existing from StarsSystem
 * 3)Update an existing course/index which calls another method(UI)
 * 4)Add a new index number to an existing course to StarsSystem
 * 5)Remove an existing index number from an existing course from StarsSystem 
 * 6)View course detail 
 * 7)View a course code's index number detail 
 * 8)Check a course code's index number available slots left
 * 9)Go back to previous page(AdminMenu)
 * 
 * @author Ng Kah Hui 
 * @version 1.0
 * @since 2020/10/15
 *
 */
public class AdminCourseUI {

	private Scanner sc;

	public AdminCourseUI() {
		sc = new Scanner(System.in); // take input from user
	}
	
	/**
	 * This method prints all the administrative
	 * options available of courses.
	 */
	public void run() {
		try {
		int choice;
		GoBack: while (true) { // Print selection menu
			System.out.println("Welcome to Admin Course Page");
			System.out.println("");
			System.out.println("1. Add new course");
			System.out.println("2. Remove a course");
			System.out.println("3. Update course/index");
			System.out.println("4. Add new index");
			System.out.println("5. Remove a index");
			System.out.println("6. View course");
			System.out.println("7. View index by course");
			System.out.println("8. Check available slot by index number");
			System.out.println("9. Go back to previous page");
			System.out.println("");
			System.out.print("Please select one of the options: ");
			choice = sc.nextInt();
			try {
				switch (choice) {
				case 1:
					addNewCourse(); // Add new course to database
					break;
				case 2:
					removeACourse(); // Remove existing course from database
					break;
				case 3:
					updateCourseIndex(); // Update existing course/index detail
					break;
				case 4:
					addNewIndex(); // Add new index number to existing course code
					break;
				case 5:
					removeAIndex();// Remove existing index number from course code
					break;
				case 6:
					viewCourse();// View course code detail by course code
					break;
				case 7:
					viewIndexByCourse();// View index number detail by course code
					break;
				case 8:
					CheckIndexSlots();// View index number's vacancies
					break;
				case 9: // Go back
					System.out.println();
					break GoBack;
				default:
					System.out.println("Incorrect Input, please try again"); // when user input incorrect value
				}
			} catch (Exception e) {}
			System.out.println();
		}
		}catch(Exception e)
		{
			System.out.println("String input is not valid, Please try again!");
			sc.next();
		}

	}

	/**
	 * This method allows user to add a new course to database by:
	 * <li>asking user to input a course code and check if it already exists
	 * <li>if it does not exists, Method will ask for other necessary information
	 * <li>checking for input errors like string for integer and if user input unrealistic timing for etc
	 * <li>adding course to database.
	 * <li>printing out list of all courses in database
	 */
	private void addNewCourse() {
		StarsDB database = StarsDB.getInstance();

		String courseCode = "";
		String courseName = "";
		String schooName = "";
		String lecVenue = "";
		String lecRemarks = "";
		String lecGroup = "";
		int intDay = 0;
		int startHours = 0;
		int startMinutes = 0;
		int endHours = 0;
		int endMinutes = 0;
		int AU = 0;
		boolean check;

		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course's code(enter -1 to exit): "); // check if user input existing course code
			courseCode = sc.nextLine();
			if(courseCode.equals("-1"))
			{return;};
			check = database.isExistingCourseCode(courseCode.toUpperCase());
			if (check) {
				System.out.println("Course name already found in database, please enter another code");
			}
		} while (check);

		System.out.print("Enter course's name: ");
		courseName = sc.nextLine();

		System.out.print("Enter what school the course is under(i.e SCSE): ");
		schooName = sc.nextLine();

		while (true) {
			try {
				System.out.print("Enter the number of AUs: "); // check if user input the correct input format
				AU = sc.nextInt();
				sc.nextLine();
				break;
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Invalid input! Academic Unit must be a number!");
			}
		}

		while (true) { 
			try { // try/catch if user input string input when program ask for integer 
				while (true) { // loop if user input out of range value
					System.out.print("Enter the lecture day(1 for monday, 2 for tuesday...):");
					intDay = sc.nextInt();
					if (intDay > 5 || intDay < 1) {
						System.out.println("input value cant be 0 or higher than 5, please try again");
					} else {
						break;
					}
				}
				while (true) // loop to check if starting time is higher than ending time
				{
					while (true) {// loop if user input out of range value
						System.out.print("Enter the lecture starting time(hours)(XX:00):");
						startHours = sc.nextInt();
						if (startHours < 8 || startHours > 24) {
							System.out.println("input value cant be lower than 8 or higher than 24, please try again");
						} else {
							break;
						}
					}
					while (true) {// loop if user input out of range value
						System.out.print("Enter the lecture starting (minutes)(00:XX):");
						startMinutes = sc.nextInt();
						if (startMinutes > 60) {
							System.out.println("input value cant be higher than 60, please try again");
						} else {
							break;
						}
					}
					while (true) {// loop if user input out of range value
						System.out.print("Enter the lecture ending time(hours)(XX:00):");
						endHours = sc.nextInt();
						if (endHours < 8 || endHours > 24) {
							System.out.println("input value cant be lower than 8 or higher than 24, please try again");
						} else {
							break;
						}
					}
					while (true) {// loop if user input out of range value
						System.out.print("Enter the lecture ending time(minutes)(00:XX):");
						endMinutes = sc.nextInt();
						if (endMinutes > 60) {
							System.out.println("input value cant be higher than 60, please try again");
						} else {
							break;
						}
					}
					if (startHours > endHours) {
						System.out
								.println("lecture starting time cant be later than its ending time, please try again");
					} else {
						break;
					}
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Please enter numerical value only"); // when user input incorrect value
				sc.next();
			}

		}
		sc.nextLine();
		System.out.print("Enter the lecture venue:");
		lecVenue = sc.nextLine();
		System.out.print("Enter the lecture remark:");
		lecRemarks = sc.nextLine();
		System.out.print("Enter the lecture group number:");
		lecGroup = sc.nextLine();

		// update to database
		database.addCourse(courseCode.toUpperCase(), courseName.toUpperCase(), schooName.toUpperCase(), AU);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());

		tempCourse.setLecDetails(intDay, startHours, startMinutes, endHours, endMinutes, lecVenue, lecRemarks,
				lecGroup);

		System.out.println();
		System.out.println("The Course has been added.");

		database.printCourseList(); // show result

	}

	/**
	 * This method allows user to remove an existing course from database by:
	 * <li>asking the user to input a course code and check if it already exists
	 * <li>if it does exists, Method remove course from database
	 * <li>printing out list of all courses in database
	 */
	private void removeACourse() {
		StarsDB database = StarsDB.getInstance();

		String courseCode = "";
		boolean check;

		database.printCourseList(); // show result
		System.out.println();
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code you want to remove(enter -1 to exit): "); // check if such course code exists
			courseCode = sc.nextLine();
			if(courseCode.equals("-1"))
			{return;};
			check = !(database.isExistingCourseCode(courseCode.toUpperCase()));
			if (check) {
				System.out.println("Course code is not found in database.");
			}
		} while (check);

		database.removeCourse(courseCode.toUpperCase()); // check and update to database

		System.out.println("");
		database.printCourseList(); // show result
	}

	/**
	 * This method is entered when user chose option 3 from main UI method.
	 * Prints all the administrative update
	 * options available.
	 */
	private void updateCourseIndex() {
		int choice;

		try {
		GoBack: while (true) { // Print selection menu
			System.out.println("");
			System.out.println("Welcome to Admin Update Page");
			System.out.println("");
			System.out.println("1. Update Course Details ");
			System.out.println("2. Update Index Details");
			System.out.println("3. Update Lecture Details");
			System.out.println("4. Update Tutorial Details");
			System.out.println("5. Update Lab Details");
			System.out.println("6. Go back to previous page");
			System.out.println("");
			System.out.print("Please select one of the options: ");
			choice = sc.nextInt();
			try {
				switch (choice) {
				case 1:
					// Update course detail
					updateCourseDetail();
					break;
				case 2:
					// Update index number detail
					updateIndexDetail();
					break;
				case 3:
					// Update lecture detail
					updateLecDetail();
					break;
				case 4:
					// Update tutorial detail
					updateTutDetail();
					break;
				case 5:
					// Update lab detail
					updateLabDetail();
					break;
				case 6: // Go back
					System.out.println();
					break GoBack;
				default:
					System.out.println("Incorrect Input, please try again"); // when user input incorrect value
				}
			} catch (Exception e) {}
			System.out.println();
			}
		}catch(Exception e)
		{
			System.out.println("String input is not valid, Please try again!");
			sc.next();
		}

	}

	/**
	 * This method allows user to update an existing course detail from database by:
	 * <li>asking user to input a course code and check if it already exists
	 * <li>if it does exists, method ask user to input new course detail
	 * <li>checking if new course detail = old course detail
	 * <li>if yes, update it to database. if no, remove old course from database and add new course to database
	 */
	private void updateCourseDetail() {
		StarsDB database = StarsDB.getInstance();

		String courseCode = "";
		String NcourseCode = "";
		String courseName = "";
		int AU = 0;
		boolean check = false;

		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code you want to update(enter -1 to exit): "); // check if such course code exists
			courseCode = sc.nextLine();
			if(courseCode.equals("-1"))
			{return;};
			check = !(database.isExistingCourseCode(courseCode.toUpperCase()));
			if (check) {
				System.out.println("Course code is not found in database.");
			}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());

		System.out.print("Enter the updated course code:");
		NcourseCode = sc.nextLine();


		System.out.print("Enter the updated course name:");
		courseName = sc.nextLine();

		while (true) { // check if user input the correct input format
			try {
				System.out.print("Enter the updated AU:");
				AU = sc.nextInt();
				sc.nextLine();
				break;
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Invalid input! Academic Unit must be a number!");
			}
		}

		//If course code to be updated equals the new course code, update course
		if (courseCode.equals(NcourseCode)) {

			tempCourse.updateCourseCode(NcourseCode.toUpperCase());
			tempCourse.updateCourseName(courseName.toUpperCase());
			tempCourse.updateAU(AU);
			database.updateCourseRecords(tempCourse);
		
		//Else, remove existing course and add new course
		} else {
//			database.removeCourse(courseCode);
//			database.addCourse(NcourseCode, courseName.toUpperCase(), tempCourse.getSchooName(), AU);
			database.updateCourse(courseCode,NcourseCode); 
		}
		
		database = StarsDB.getInstance();
		System.out.println("");
		System.out.print("Course code " + courseCode.toUpperCase() + " has been changed to " + NcourseCode.toUpperCase()
				+ " , course name changed to " + courseName.toUpperCase() + " and AU value changed to " + AU);
		System.out.println("");

	}

	/**
	 * This method allows user to update an existing index detail via course code by:
	 * <li>asking user to input a course code and check if it already exists
	 * <li>if it does exists, method ask user to input index number and check if it already exists
	 * <li>if it does exists, method ask user to input updated index number
	 */
	private void updateIndexDetail() {
		StarsDB database = StarsDB.getInstance();

		String courseCode = "";
		boolean check = false;
		int indexNum = 0;
		int NindexNum =0;

		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code(enter -1 to exit): "); // check if such course name exists
			courseCode = sc.nextLine();
			if(courseCode.equals("-1"))
			{return;};
			check = !(database.isExistingCourseCode(courseCode.toUpperCase()));
			if (check) {
				System.out.println("Course code is not found in database.");
			}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());

		try {
		do {
			System.out.print("Enter the index number: "); // check if such index number exists
			indexNum = sc.nextInt();
			check = !(tempCourse.containsIndexNo(indexNum));
			if (check) {
				{
					System.out.println("Index number not found in database.");
				}
			}
		} while (check);
		}catch(Exception e)
		{
			System.out.println("String input is not valid, Please try again!");
			sc.next();
			return;
		}
		
		try {
		System.out.print("Enter the new index number:");
		NindexNum = sc.nextInt();
		}catch(Exception e)
		{
			System.out.println("String input is not valid, Please try again!");
			sc.next();
			return;	
		}
		System.out.println("");
		
		tempCourse.updateIndex(indexNum, NindexNum);
		database.updateCourseRecords(tempCourse, indexNum, NindexNum);
		database = StarsDB.getInstance();

	}

	/**
	 * This method allows user to update an existing lecture detail via course code by:
	 * <li>asking user to input a course code and check if it already exists
	 * <li>if it does exists, method ask user to input updated lecture detail
	 */
	private void updateLecDetail() {
		StarsDB database = StarsDB.getInstance();

		boolean check = false;
		String courseCode = "";
		String lecVenue = "";
		String lecRemark = "";

		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code(enter -1 to exit): "); // check if such course code exists
			courseCode = sc.nextLine();
			if(courseCode.equals("-1"))
			{return;};
			check = !(database.isExistingCourseCode(courseCode.toUpperCase()));
			if (check) {
				System.out.println("Course code is not found in database.");
			}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());

		System.out.print("Enter the updated lecture venue: ");
		lecVenue = sc.nextLine();
		System.out.print("Enter the updated lecture remark: ");
		lecRemark = sc.nextLine();

		tempCourse.updateLecVenue(lecVenue);
		tempCourse.updateLecRemark(lecRemark);
		System.out.println("");
		System.out.print("Course code " + courseCode.toUpperCase() + "'s lecture venue and remark has been updated");

		database.updateCourseRecords(tempCourse);

	}

	/**
	 * This method allows user to update an existing tutorial detail via course code/index number by:
	 * <li>asking user to input a course code and check if it already exists
	 * <li>if it does exists, method ask user to input index number and check if it already exists
	 * <li>if it does exists, method ask user to input updated tutorial detail
	 */
	private void updateTutDetail() {
		StarsDB database = StarsDB.getInstance();

		boolean check = false;
		String courseCode = "";
		String tutGroup = "";
		String tutRemark = "";
		String tutVenue = "";
		int indexNum = 0;

		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code(enter -1 to exit): "); // check if such course code exists
			courseCode = sc.nextLine();
			if(courseCode.equals("-1"))
			{return;};
			check = !(database.isExistingCourseCode(courseCode.toUpperCase()));
			if (check) {
				System.out.println("Course code is not found in database.");
			}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());

		try {
		do {
			System.out.print("Enter the index number: "); // check if such index number exists
			indexNum = sc.nextInt();
			check = !(tempCourse.containsIndexNo(indexNum));
			if (check) {
				{
					System.out.println("Index number not found in database.");
				}
			}
		} while (check);
		}catch(Exception e)
		{
			System.out.println("String input is not valid, Please try again!");
			sc.next();
			return;	
		}
		sc.nextLine();
		System.out.print("Enter the updated tutorial venue: ");
		tutVenue = sc.nextLine();
		System.out.print("Enter the updated tutorial group number: ");
		tutGroup = sc.nextLine();
		System.out.print("Enter the updated tutorial remark: ");
		tutRemark = sc.nextLine();

		tempCourse.getIndex(indexNum).updateTutGroup(tutGroup);
		tempCourse.getIndex(indexNum).updateTutRemark(tutRemark);
		tempCourse.getIndex(indexNum).updateTutVenue(tutVenue);

		database.updateCourseRecords(tempCourse);

		System.out.println("");
		System.out.print("Index number " + indexNum + "'s tutorial venue, group number and remark has been updated");
		System.out.println("");

	}

	/**
	 * This method allows user to update an existing lab detail via course code/index number by:
	 * <li>asking user to input a course code and check if it already exists
	 * <li>if it does exists, method ask user to input index number and check if it already exists
	 * <li>if it does exists, method ask user to input updated lab detail
	 */
	private void updateLabDetail() {
		StarsDB database = StarsDB.getInstance();

		boolean check = false;
		String courseCode = "";
		String labGroup = "";
		String labRemark = "";
		String labVenue = "";
		int indexNum = 0;

		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code(enter -1 to exit): "); // check if such course code exists
			courseCode = sc.nextLine();
			if(courseCode.equals("-1"))
			{return;};
			check = !(database.isExistingCourseCode(courseCode.toUpperCase()));
			if (check) {
				System.out.println("Course code is not found in database.");
			}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());

		try {
		do {
			System.out.print("Enter the index number: "); // check if such index number exists
			indexNum = sc.nextInt();
			check = !(tempCourse.containsIndexNo(indexNum));
			if (check) {
				{
					System.out.println("Index number not found in database.");
				}
			}
		} while (check);
		}catch(Exception e)
		{
			System.out.println("String input is not valid, Please try again!");
			sc.next();
			return;	
		}
		sc.nextLine();
		System.out.print("Enter the updated lab venue: ");
		labVenue = sc.nextLine();
		System.out.print("Enter the updated lab group number: ");
		labGroup = sc.nextLine();
		System.out.print("Enter the updated lab remark: ");
		labRemark = sc.nextLine();

		tempCourse.getIndex(indexNum).updateLabGroup(labGroup);
		tempCourse.getIndex(indexNum).updateLabRemark(labRemark);
		tempCourse.getIndex(indexNum).updateLabVenue(labVenue);

		database.updateCourseRecords(tempCourse);

		System.out.println("");
		System.out.print("Index Number " + indexNum + "'s lab venue, group number and remark has been updated");
		System.out.println("");

	}

	/**
	 * This method allows user to add a new index number via course code/index number by:
	 * <li>asking user to input a course code and check if it already exists
	 * <li>if it does exists, method ask user to input index number and check if it already exists
	 * <li>if it does not exists, method ask for other necessary information
	 * <li>checking for input errors like string for integer and if user input unrealistic timing for etc
	 */
	private void addNewIndex() {

		StarsDB database = StarsDB.getInstance();

		int indexNum = 0;
		int vacancy = 0;
		String courseCode = "";
		String tutVenue = "";
		String labVenue = "";
		String labRemarks = "";
		String tutRemarks = "";
		String tutGroup = "";
		String labGroup = "";
		int intDay = 0;
		int startHours = 0;
		int startMinutes = 0;
		int endHours = 0;
		int endMinutes = 0;
		int tutOccurring = 3;
		int labOccurring = 3;
		boolean check = false;

		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code(enter -1 to exit): "); // check if such course code exists
			courseCode = sc.nextLine();
			if(courseCode.equals("-1"))
			{return;};
			check = !(database.isExistingCourseCode(courseCode.toUpperCase()));
			if (check) {
				System.out.println("Course code is not found in database.");
			}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());

		try {
		while (true) {
			System.out.print("Enter the course's index number:"); // check if such index number exists
			indexNum = sc.nextInt();
			System.out.print("");
			check = !(tempCourse.containsIndexNo(indexNum));
			if (!check) {
				{
					System.out.println("Index number found in database, please try again");
				}
			} else {
				break;
			}
		}
		}catch(Exception e)
		{
			System.out.println("String input is not valid, Please try again!");
			sc.next();
			return;	
		}

		System.out.print("Enter the course's maximum vacancy slots:");
		vacancy = sc.nextInt();

		tempCourse.addIndex(indexNum, vacancy); // add index number and number of ava slots in that course to db

		System.out.print("");
		System.out.print("");
		// create new tutorial lesson

		while (true) { 
			try { // try/catch if user input string input when program ask for integer 

				while (true) { // loop if user input out of range value
					System.out.print("Enter the tutorial day(1 for monday, 2 for tuesday...):");
					intDay = sc.nextInt();
					if (intDay > 5 || intDay < 1) {
						System.out.println("input value cant be 0 or higher than 5, please try again");
					} else {
						break;
					}
				}

				while (true) // loop to check if starting time is higher than ending time
				{
					while (true) {// loop if user input out of range value
						System.out.print("Enter the tutorial starting time(hours)(XX:00):");
						startHours = sc.nextInt();
						if (startHours < 8 || startHours > 24) {
							System.out.println("input value cant be lower than 8 or higher than 24, please try again");
						} else {
							break;
						}
					}
					while (true) {// loop if user input out of range value
						System.out.print("Enter the tutorial starting (minutes)(00:XX):");
						startMinutes = sc.nextInt();
						if (startMinutes > 60) {
							System.out.println("input value cant be higher than 60, please try again");
						} else {
							break;
						}
					}
					while (true) {// loop if user input out of range value
						System.out.print("Enter the tutorial ending time(hours)(XX:00):");
						endHours = sc.nextInt();
						if (endHours < 8 || endHours > 24) {
							System.out.println("input value cant be lower than 8 or higher than 24, please try again");
						} else {
							break;
						}
					}
					while (true) {// loop if user input out of range value
						System.out.print("Enter the tutorial ending time(minutes)(00:XX):");
						endMinutes = sc.nextInt();
						if (endMinutes > 60) {
							System.out.println("input value cant be higher than 60, please try again");
						} else {
							break;
						}
					}
					if (startHours > endHours) {
						System.out
								.println("tutorial starting time cant be later than its ending time, please try again");
					} else {
						break;
					}
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Please enter numerical value only"); // when user input incorrect value
				sc.next();

			}
		}

		sc.nextLine();
		System.out.print("Enter the tutorial venue:");
		tutVenue = sc.nextLine();
		System.out.print("Enter the tutorial remark:");
		tutRemarks = sc.nextLine();
		System.out.print("Enter the tutorial group number:");
		tutGroup = sc.nextLine();
		System.out.print("Enter the occurring week for tutorial\n(1)Even, (2)Odd, (3)Every : ");
		tutOccurring = sc.nextInt();
		tempCourse.getIndex(indexNum).setTutDetails(intDay, startHours, startMinutes, endHours, endMinutes, tutVenue,
				tutRemarks, tutGroup, tutOccurring);

		System.out.print("");

		while (true) { 
			try { // try/catch if user input string input when program ask for integer

				while (true) { // loop if user input out of range value
					System.out.print("Enter the lab day(1 for monday, 2 for tuesday...):");
					intDay = sc.nextInt();
					if (intDay > 5 || intDay == 0) {
						System.out.println("input value cant be 0 or higher than 5, please try again");
					} else {
						break;
					}
				}

				while (true) // loop to check if starting time is higher than ending time
				{
					while (true) {// loop if user input out of range value
						System.out.print("Enter the lab starting time(hours)(XX:00):");
						startHours = sc.nextInt();
						if (startHours < 8 || startHours > 24) {
							System.out.println("input value cant be lower than 8 or higher than 24, please try again");
						} else {
							break;
						}
					}
					while (true) {// loop if user input out of range value
						System.out.print("Enter the lab starting (minutes)(00:XX):");
						startMinutes = sc.nextInt();
						if (startMinutes > 60) {
							System.out.println("input value cant be higher than 60, please try again");
						} else {
							break;
						}
					}
					while (true) {// loop if user input out of range value
						System.out.print("Enter the lab ending time(hours)(XX:00):");
						endHours = sc.nextInt();
						if (endHours < 8 || endHours > 24) {
							System.out.println("input value cant be lower than 8 or higher than 24, please try again");
						} else {
							break;
						}
					}
					while (true) {// loop if user input out of range value
						System.out.print("Enter the lab ending time(minutes)(00:XX):");
						endMinutes = sc.nextInt();
						if (endMinutes > 60) {
							System.out.println("input value cant be higher than 60, please try again");
						} else {
							break;
						}
					}
					if (startHours > endHours) {
						System.out.println("Lab starting time cant be later than its ending time, please try again");
					} else {
						break;
					}
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Please enter numerical value only"); // when user input incorrect value
				sc.next();

			}
		}
		sc.nextLine();
		System.out.print("Enter the lab venue:");
		labVenue = sc.nextLine();
		System.out.print("Enter the lab remark:");
		labRemarks = sc.nextLine();
		System.out.print("Enter the lab group number:");
		labGroup = sc.nextLine();
		System.out.print("Enter the occurring week for tutorial\n(1)Even, (2)Odd, (3)Every : ");
		labOccurring = sc.nextInt();

		tempCourse.getIndex(indexNum).setLabDetails(intDay, startHours, startMinutes, endHours, endMinutes, labVenue,
				labRemarks, labGroup, labOccurring);

		database.updateCourseRecords(tempCourse);

		System.out.println("");
		System.out.println("Index Number " + indexNum + "'s lab and tutorial details has been added.");

	}

	/**
	 * This method allows user to remove an existing index number via course code by:
	 * <li>asking user to input a course code and check if it already exists
	 * <li>if it does exists, method ask user to input index number and check if it already exists
	 * <li>if it does exists, method remove index number from database
	 */
	private void removeAIndex() {

		StarsDB database = StarsDB.getInstance();

		boolean check = false;
		String courseCode = "";
		int indexNum = 0;

		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code(enter -1 to exit): "); // check if such course code exists
			courseCode = sc.nextLine();
			if(courseCode.equals("-1"))
			{return;};
			check = !(database.isExistingCourseCode(courseCode.toUpperCase()));
			if (check) {
				System.out.println("Course code is not found in database.");
			}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());

		try {
		do {
			System.out.print("Enter the course's index number:"); // check if such index number exists
			indexNum = sc.nextInt();
			check = !(tempCourse.containsIndexNo(indexNum));
			if (check) {
				{
					System.out.println("Index number not found in database.");
				}
			}
		} while (check);
		}catch(Exception e)
		{
			System.out.println("String input is not valid, Please try again!");
			sc.next();
			return;	
		}

		System.out.println("");
		//tempCourse.removeIndex(indexNum);

		database.updateCourseRecords(tempCourse, indexNum);

	}

	/**
	 * This method is to print all existing course from database
	 */
	private void viewCourse() {
		StarsDB database = StarsDB.getInstance();

		database.printCourseList();

	}

	/**
	 * This method allows user to view a index number detail via course code by:
	 * <li>asking user to input a course code and check if it already exists
	 * <li>if it does exists, method ask user to input index number and check if it already exists
	 * <li>if it does exists, method print index number detail list (student list)
	 */
	private void viewIndexByCourse() {
		StarsDB database = StarsDB.getInstance();

		boolean check = false;
		String courseCode = "";
		int indexNum = 0;

		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code(enter -1 to exit): "); // check if such course code exists
			courseCode = sc.nextLine();
			if(courseCode.equals("-1"))
			{return;};
			check = !(database.isExistingCourseCode(courseCode.toUpperCase()));
			if (check) {
				System.out.println("Course code is not found in database.");
			}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());

		try {
		do {
			System.out.print("Enter the course's index number: "); // check if such index number exists
			indexNum = sc.nextInt();
			check = !(tempCourse.containsIndexNo(indexNum));
			if (check) {
				{
					System.out.println("Index number not found in database.");
				}
			}
		} while (check);
		}catch(Exception e)
		{
			System.out.println("String input is not valid, Please try again!");
			sc.next();
			return;	
		}

		System.out.println("");
		tempCourse.getIndex(indexNum).printStudentListByIndex();

	}

	/**
	 * This method allows user to view a index number slot via course code by
	 * <li>asking user to input a course code and check if it already exists
	 * <li>if it does exists, method ask user to input index number and check if it already exists
	 * <li>if it does exists, method print number available slots via index number 
	 */
	private void CheckIndexSlots() {
		StarsDB database = StarsDB.getInstance();

		boolean check = false;
		String courseCode = "";
		int indexNum;

		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code(enter -1 to exit): "); // check if such course code exists
			courseCode = sc.nextLine();
			if(courseCode.equals("-1"))
			{return;};
			check = !(database.isExistingCourseCode(courseCode.toUpperCase()));
			if (check) {
				System.out.println("Course code is not found in database.");
			}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());

		try {
		do {
			System.out.print("Enter the course's index number:"); // check if such index number exists
			indexNum = sc.nextInt();
			check = !(tempCourse.containsIndexNo(indexNum));
			if (check) {
				{
					System.out.println("Index number not found in database.");
				}
			}
		} while (check);
		}catch(Exception e)
		{			
			System.out.println("String input is not valid, Please try again!");
			sc.next();
			return;	
		}

		System.out.println("");
		tempCourse.getIndex(indexNum).getNumOfVacancies();
	}
}
