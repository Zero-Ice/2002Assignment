package com.example.ss7g7.stars;


import java.util.*;


public class AdminStudUI {
	
	static StarsDB database = StarsDB.getInstance();
	static ArrayList<Course> courseList = database.getAllCourse();
	static ArrayList<Student> studentList = database.getAllStudents(); //retrieve db from StarsDB
	
	private static Scanner sc = new Scanner(System.in); // take input from user
	
	
	public static void printAdminStudUI() {

		int choice;
		Logout:
		while(true){ //Print selection menu
			System.out.println("Welcome to admin student page");
			System.out.println("");
			System.out.println("1. Edit student access period");
			System.out.println("2. Add a student");
			System.out.println("3. Remove a student");
			System.out.println("4. Print student list by index number");
			System.out.println("5. Print student list by course name");
			System.out.println("6. Exit");
			System.out.println("");
			System.out.print("Please select one of the options: ");
			choice = sc.nextInt();
			try{
				switch (choice) {
					case 1: //Go to Edit Student Access UI Function
						editStudentAccess();
						break;
					case 2: // Go to Add Student UI Function
						addStudent();
						break;
					case 3: // Go to RemoveStudentUI Function
						removeStudent();
						break;
					case 4: // Go to PrintListByIndexUI Function
						printStudListByIndex();
						break;
					case 5: // Go to PrintListBycourseUI Function
						printStudListByCourse();
						break;
					case 6: // Go back
						System.out.println();
						break Logout;
					default:
						System.out.println("Incorrect Input, please try again"); //when user input incorrect value
								}
				}
			catch (Exception e) {
				System.out.println("Incorrect Input, please try again"); //when user input incorrect value
			}
			System.out.println();
		}
		
	}


	private static void editStudentAccess() {
		
		StudMngmt.printStudentList(); //show result from db 
		
		Boolean check = false;
		String matNum = "";
		
		System.out.print("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter student's matriculation number: ");
			matNum = sc.nextLine();
			check = StudMngmt.isExistingMatNum(matNum);
			if(check){
				System.out.println("Matriculation number is not found in database.");
			}
		} while (check);

		Calendar newAccessStart = CalendarMngmt.getValidDateTime("new start access time");
		Calendar newAccessEnd = CalendarMngmt.getValidDateTime("new end access time");
		StudMngmt.updateAccessPeriod(matNum, newAccessStart, newAccessEnd);
		System.out.println("Access time is updated Successfully!");
		
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
        
  
        System.out.print("Enter student's Mobile Number: ");
        mobileNo = sc.nextInt();
        sc.nextLine();
    
        System.out.print("Enter student's Email Address: ");
        email = sc.nextLine();
        
        Calendar accessStart = CalendarMngmt.getValidDateTime("access start");
        Calendar accessEnd = CalendarMngmt.getValidDateTime("access end");
       
        StudMngmt.addStudent(username, name,lastName, matricNo, gender, nationality, mobileNo, email, accessStart, accessEnd);
        
        // after user input in username and password, it should store inside starsDB				
       
        
	    System.out.println();
		System.out.println("The Student has been added.");
		StudMngmt.printStudentList();

		
	}
	
	//Function to remove a new student to database
	private static void removeStudent() { //remove student from student db
		
    	Boolean check;
		String matricNo = "";
		
		StudMngmt.printStudentList();
		
		System.out.println();
		
		System.out.print("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		
		do {
			System.out.print("Enter student's matriculation number: "); //check if such matricNo exists
			matricNo = sc.nextLine();
			check = StudMngmt.isExistingMatNum(matricNo);
			if (check){
				System.out.println("Matriculation number is not found in database.");
			}
		} while (check);
		
		StudMngmt.removeStudent(matricNo); //remove student from db 
		System.out.println("Student with matriculation number of " + matricNo + " has been removed.");
	}


	private static void printStudListByIndex() { //show studentlist when user type in a course code and index number

		boolean check = false;
		String courseCode  = "";
		int indexNum;
		
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(AdminCourseMngmt.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode.toUpperCase());
		do {
			System.out.print("Enter the index number: "); 
			indexNum = sc.nextInt();
			check = !(tempCourse.containsIndexNo(indexNum));
			if(check)
			{
				{System.out.println("Index number is not found in database.");
			}
			}
		}while(check);

		tempCourse.getIndex(indexNum).printStudentListByIndex();
	}
	
	
	private static void printStudListByCourse() { //show studentlist when user type in a course code
		
		boolean check = false;
		String courseCode  = "";
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(database.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());
		tempCourse.printStudentListByCourse();
	
		
	}


	
}
