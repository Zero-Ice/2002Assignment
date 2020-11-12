package com.example.ss7g7.stars;


import java.util.Scanner;
import java.util.Calendar;


public class AdminStudUI {
	
	private static StarsDB database = StarsDB.getInstance(); 
	
	private static Scanner sc = new Scanner(System.in); // take input from user
	
	
	public static void printAdminStudUI() {

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

	// Function to change a student access timing
	private static void editStudentAccess() { 
		
		StudMngmt.printStudentList(); //show result from db 
		
		Boolean check = false;
		String matNum = "";
		
		System.out.print("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter student's matriculation number: "); //check if such matNum exists
			matNum = sc.nextLine();
			check = StudMngmt.isExistingMatNum(matNum);
			if(check){
				System.out.println("Matriculation number is not found in database.");
			}
		} while (check);

		Calendar newAccessStart = StudMngmt.getValidDateTime("new start access time");
		Calendar newAccessEnd = StudMngmt.getValidDateTime("new end access time");
		StudMngmt.updateAccessPeriod(matNum, newAccessStart, newAccessEnd);
		System.out.println("");
		System.out.println("Access time has been updated successfully!");
		
	}

	//Function to add a new student to database
	private static void addStudent() {
		
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
			check = !(StudMngmt.isExistingUsername(username));
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
			check = !StudMngmt.isExistingMatNum(matricNo);
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
        
        Calendar accessStart = StudMngmt.getValidDateTime("access start");
        Calendar accessEnd = StudMngmt.getValidDateTime("access end");
        
		/*
		 * For config of credentials, please ignore
		 */
        //database.addAdmin(username, passWord);
        
        StudMngmt.addStudent(username, passWord, name, lastName, matricNo, gender, nationality, mobileNo, email, accessStart, accessEnd);
        
        // after user input in username and password, it should store inside starsDB				
       
        
	    System.out.println();
		System.out.println("The student has been added.");
		StudMngmt.printStudentList();

		
	}
	
	//Function to remove a new student to database
	private static void removeStudent() { 
		
    	Boolean check = false;
		String matricNo = "";
		
		StudMngmt.printStudentList();
		
		System.out.println();
		
		System.out.print("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		
		do {
			System.out.print("Enter student's matriculation number: "); //check if such matNum exists
			matricNo = sc.nextLine();
			check = StudMngmt.isExistingMatNum(matricNo);
			if (check){
				System.out.println("Matriculation number is not found in database.");
			}
		} while (check);
		
		StudMngmt.removeStudent(matricNo); //remove student from db 
		System.out.println("Student with matriculation number of " + matricNo + " has been removed.");
	}

	//Function to show student list with course code and index number
	private static void printStudListByIndex() { 

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
	
	//Function to show student list with course code
	private static void printStudListByCourse() { 
		
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
