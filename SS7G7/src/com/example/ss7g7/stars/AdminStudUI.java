package com.example.ss7g7.stars;


import java.util.*;



public class AdminStudUI {
	
	static ArrayList<Student> studentList = Database.studentList;
	
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
					case 6: // Log out
						System.out.println("Logged out successfully");
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
		
		printStudentList();
		
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
		String password = "";
		String matricNo = "";
		String firstName = "";
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
			System.out.print("Enter the student's username: ");
			username = sc.nextLine();
			check = !(StudMngmt.isExistingUsername(username));
		} while (check);

		System.out.print("Enter student's default password ");
		password = sc.nextLine();
		
		System.out.print("Enter student's first name: ");
		firstName = sc.nextLine();
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
       
        StudMngmt.addStudent(username, password, firstName, lastName, matricNo, gender, nationality, mobileNo, email, accessStart, accessEnd);

        					
       
        
	    System.out.println();
		System.out.println("The Student has been added.");
		//CourseMgr.printStudentList();

		
	}
	
	//Function to remove a new student to database
	private static void removeStudent() {
		
    	Boolean check = false;
		String matricNo = "";
		
		//CourseMngmt.printStudentList();
		System.out.println();
		
		System.out.print("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		
		do {
			System.out.print("Enter student's matriculation number: ");
			matricNo = sc.nextLine();
			check = StudMngmt.isExistingMatNum(matricNo);
			if (check){
				System.out.println("Matriculation number is not found in database.");
			}
		} while (check);
		
		StudMngmt.removeStudent(matricNo);
		System.out.println("Student with matriculation number of " + matricNo + " has been removed.");
	}




	
	public static void printStudentList(){
		boolean flag = false;
		System.out.println();
		System.out.println("Matriculation Number\tFull Name");
		System.out.println("---------------------------------------------------");
		
		if(studentList.size() <= 0){
			System.out.println("\nNo record is found!\n");
			return;
		}
		
		for (Student s: studentList){
			System.out.print(s.matricNo() + "         \t");
			System.out.print(s.getFirstName() + " " + s.getLastName());
			System.out.println();
			
			flag = true;
		}
		if (!flag) System.out.println("\nNo record is found!");
	}
	

	private static void printStudListByIndex() {
		// TODO Auto-generated method stub
		
	}
	
	private static void printStudListByCourse() {
		// TODO Auto-generated method stub
		
	}


	
}
