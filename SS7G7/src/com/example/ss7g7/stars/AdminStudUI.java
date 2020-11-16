package com.example.ss7g7.stars;


import java.util.Scanner;
import java.text.ParseException;
import java.util.Calendar;


/**
 * <h1>Admin Student User Interface</h1>
 * 
 * <p>
 * The AdminStudUI provides a streamlined 
 * overview of the administrative actions that
 * an admin user would be allowed to make on
 * students.
 * <p>
 * The options include:
 * <li>Editing an existing student access period to StarsSystem 
 * <li>Adding a new student to StarsSystem 
 * <li>Removing an existing student from StarsSystem 
 * <li>Printing a student list by entering an existing course code's index number 
 * <li>Printing a student list by entering an existing course code 
 * <li>Going  back to previous page (AdminUI) 
 *  
 * @author Ng Kah Hui 
 * @version %I%
 * @since 2020-10-15 
 * 
 * 
 */
public class AdminStudUI {
	
	private StarsDB database; 
	private Scanner sc;
	
	/**
	 * This method prints all the administrative
	 * options available.
	 */
	public AdminStudUI() {
		database = StarsDB.getInstance();
		sc = new Scanner(System.in); // take input from user
	}
	
	public void run() {

		int choice;
		GoBack:
		while(true){ //Print selection menu
			System.out.println("Welcome to Admin Student Page");
			System.out.println("");
			System.out.println("1. Edit student access period");
			System.out.println("2. Add a student");
			System.out.println("3. Remove a student");
			System.out.println("4. Print student list by index number");
			System.out.println("5. Print student list by course name");
			System.out.println("6. Go back to previous page");
			System.out.println("");
			System.out.print("Please select one of the options: ");
			choice = sc.nextInt();
			try{
				switch (choice) {
					case 1: // Edit an existing student access period
						editStudentAccess();
						break;
					case 2: // Add a new student to database
						addStudent();
						break;
					case 3: // Remove an existing student from database
						removeStudent();
						break;
					case 4: // Print student list by index number
						printStudListByIndex();
						break;
					case 5: // Print student list by course code
						printStudListByCourse();
						break;
					case 6: // Go back
						System.out.println();
						break GoBack;
					default:
						System.out.println("Incorrect Input, please try again"); //when user input incorrect value
				}
			}
			catch (Exception e) {
				//System.out.println("Incorrect Input, please try again"); //when user input incorrect value
			}
			System.out.println();
		}
		
	}
	/**
	 * This method allows user to edit an existing student access time to database.
	 * 
	 * 1.Method ask user to enter a student matriculation number and check if it exists in the database
	 * 2.If such matriculation number exists in the database, method will ask user to input the student updated new start/end access time
	 * 3.Method also check if user input in an specific format
	 * 
	 * @throws ParseException when user inputs the wrong format for access time.
	 */
	private void editStudentAccess() throws ParseException { 
		
		database.printStudentList(); //show result from db 
		
		Boolean check = false;
		String matNum = "";
		
		System.out.print("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter student's matriculation number: "); //check if such matNum exists
			matNum = sc.nextLine();
			check = database.isExistingMatNum(matNum);
			if(check){
				System.out.println("Matriculation number is not found in database.");
			}
		} while (check);

		Calendar newAccessStart = database.getValidDateTime("new start access time");
		Calendar newAccessEnd = database.getValidDateTime("new end access time");
		database.updateAccessPeriod(matNum, newAccessStart, newAccessEnd);
		System.out.println("");
		System.out.println("Access time has been updated successfully!");
		
	}

	/**
	 * This method allows user to add a new student to database
	 * 1.Method ask user to input the new student matriculation number and check if it already exists in the database
	 * 2.If it does not exist, method continue to ask user to input other necessary information
	 * 3.Method add new student information to database
	 * 4.Method print out list of all students in database
	 * @throws ParseException when user didnt input in an specific format for access time 
	 */
	private void addStudent() throws ParseException {
		
		String username = "";
		String passWord = "";
		String matricNo = "";
		String name = "";
		String lastName = "";
		String nationality ="";
		String email = "";
		int mobileNo = 0;
		String gender;
		boolean check;
		
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the student's username: "); //check if such username exists 
			username = sc.nextLine();
			check = !(database.isExistingUsername(username));
		} while (check);
		
		System.out.print("Enter student's default password: ");
		passWord = sc.nextLine();
		
		System.out.print("Enter student's first name: ");
		name = sc.nextLine();
		System.out.print("Enter student's last name: ");
		lastName = sc.nextLine();
		
		do {
			System.out.print("Enter student matriculation number: ");
			matricNo = sc.nextLine();
			check = !database.isExistingMatNum(matricNo);
		} while (check);

		System.out.print("Enter student's gender(M/F): ");
		gender = sc.nextLine();
		
        System.out.print("Enter student's nationality: ");
        nationality = sc.nextLine();
        
        while(true){ //check if user input the correct input format 
        	try{
        		System.out.print("Enter student's mobile number: "); 
        		mobileNo = sc.nextInt();
        		sc.nextLine();
        		break;
        	} catch (Exception e){
        		sc.nextLine();
        		System.out.println("Invalid input! Please input numberical values!");
        	}
        }
    
        System.out.print("Enter student's Email Address: ");
        email = sc.nextLine();
        
        Calendar accessStart = database.getValidDateTime("access start");
        Calendar accessEnd = database.getValidDateTime("access end");
        
        // after user input in username and password, it should store inside starsDB				
        database.addStudent(username, passWord, name, lastName, matricNo, gender, nationality, mobileNo, email, accessStart, accessEnd);       
        
	    System.out.println();
		System.out.println("The student has been added.");
		database.printStudentList();

		
	}
	
	/**
	 * This method allows user to remove an existing student from database
	 * 1.Method print out list of all students in database
	 * 2.Method ask user to input student matriculation number and check if it exists in the database
	 * 3.If it exists in the database, method remove student from database
	 */
	private void removeStudent() { 
		
    	Boolean check = false;
		String matricNo = "";
		
		database.printStudentList();
		
		System.out.println();
		
		System.out.print("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		
		do {
			System.out.print("Enter student's matriculation number: "); //check if such matNum exists
			matricNo = sc.nextLine();
			check = database.isExistingMatNum(matricNo);
			if (check){
				System.out.println("Matriculation number is not found in database.");
			}
		} while (check);
		
		database.removeStudent(matricNo); //remove student from db 
		System.out.println("Student with matriculation number of " + matricNo + " has been removed.");
	}

	/**
	 * Method allows user to print student list via a course code's index number
	 * 1.Method ask user to input course code and check if it exists in the database
	 * 2.If it does exists, method ask user to input the course code's index number and check if it exists in the database
	 * 3.If it does exists, method print out student list
	 */
	private void printStudListByIndex() { 

		boolean check = false;
		String courseCode  = "";
		int indexNum=0;
		
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such course code exists 
			courseCode = sc.nextLine();
			check =!(database.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code not found in database.");}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());
		
		do {
			System.out.print("Enter the index number: ");  //check if such index number exists 
			indexNum = sc.nextInt();
			check = !(tempCourse.containsIndexNo(indexNum));
			if(check)
			{
				{System.out.println("Index number not found in database.");}
			}
		}while(check);

		tempCourse.getIndex(indexNum).printStudentListByIndex();
	}
	
	/**
	 * This method allows user to print student list by a course code
	 * 1.Method ask user to input course code and check if it exists in the database
	 * 2/If it does exists, print out student list
	 */
	private void printStudListByCourse() { 
		
		boolean check = false;
		String courseCode  = "";
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such course code exists 
			courseCode = sc.nextLine();
			check =!(database.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code not found in database.");}
		} while (check);
		
		Course tempCourse = database.getCourse(courseCode.toUpperCase()); 
		tempCourse.printStudentListByCourse();
	
		
	}


	
}
