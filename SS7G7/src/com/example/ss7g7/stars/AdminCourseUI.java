package com.example.ss7g7.stars;
import java.util.*;

public class AdminCourseUI {

	static List<Course> course; //= StarsDB.getAllCourse(); make it static?
	
	private static Scanner sc = new Scanner(System.in); // take input from user
	
	
	public static void printAdminStudUI() {
		int choice;
		Logout:
		while(true){ //Print selection menu
			System.out.println("Welcome to admin course page");
			System.out.println("");
			System.out.println("1. Add new course");
			System.out.println("2. remove a course");
			System.out.println("3. Update course/index");
			System.out.println("4. Add new index");
			System.out.println("5. Remove a index");
			System.out.println("6. View course");
			System.out.println("7. View index by course");
			System.out.println("8. Check available slot(index)");
			System.out.println("9. Exit");
			System.out.println("");
			System.out.print("Please select one of the options: ");
			choice = sc.nextInt();
			try{
				switch (choice) {
					case 1: //Go to Edit Student Access UI Function
						addNewCourse();
						break;
					case 2: 
						removeACourse();
						break;
					case 3: 
						updateCourseIndex();
						break;
					case 4: 
						addNewIndex();
						break;
					case 5: 
						removeAIndex();
						break;
					case 6: 
						viewCourse();
						break;
					case 7:
						viewIndexByCourse();
						break;
					case 8: 
						CheckIndexSlots();
						break;
					case 9: // Log out
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


	private static void addNewCourse() {
		
		String courseCode;
		String courseName;
		String schooName;
		int AU;
		boolean check;
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course's name: ");
			courseCode = sc.nextLine();
			check = !(AdminCourseMngmt.isExistingCourseCode(courseCode));
		} while (check);
		
		System.out.print("Enter course's name: ");
		courseName = sc.nextLine();
		
		System.out.print("Enter what school the course is under(i.e SCSE): ");
		schooName = sc.nextLine();
		
        while(true){
        	try{
        		System.out.print("Enter the number of AUs: "); 
        		AU = sc.nextInt();
        		sc.nextLine();
        		break;
        	} catch (Exception e){
        		sc.nextLine();
        		System.out.println("Invalid input! Academic Unit must be a number!");
        	}
        }
        
        AdminCourseMngmt.addCourse(courseCode.toUpperCase(), courseName.toUpperCase(),schooName.toUpperCase(), AU);

       
	    System.out.println();
		System.out.println("The Course has been added.");
		
		
		
		
		

	}


	private static void removeACourse() {
		
		//Course.showfullCourseDetails(); make this static
		System.out.print("Enter the course's code:"); 
		String courseCode = sc.nextLine();
		AdminCourseMngmt.removeCourse(courseCode.toUpperCase());
		
	}


	private static void updateCourseIndex() {
		
		
		//Course.showAllIndexDetails(); make it static
		int indexNum =0;
		int NindexNum = 0;
		int check=0;
		System.out.print("Enter the Index Number:"); 
		indexNum = sc.nextInt();
		//check = Course.getIndex(indexNum); make it static 
		System.out.print("Enter the New Index Number:"); 
		NindexNum = sc.nextInt();
		//Course.updateIndex(check,NindexNum); make it static 
		//Course.showAllIndexDetails(); make it static

		
	}


	private static void addNewIndex() {
		
		 int indexNum =0;
		 int vacancy = 0;
		 String courseCode;
		 
		 while(true) {
		System.out.print("Enter the course's code:"); 
		courseCode = sc.nextLine();
		if (AdminCourseMngmt.isExistingCourseCode(courseCode))
		{System.out.print("Course Name found."); 
		sc.nextLine();
		break;
		}
		else {
		System.out.println("Course code doesnt not exist.");}
		 }
		 
		Course tempC = AdminCourseMngmt.getCourseByCode(courseCode);
		System.out.print("Enter the course's Index Number:"); 
		indexNum = sc.nextInt();
		System.out.print("Enter the course's maximum vacancy slots:"); 
		vacancy = sc.nextInt();
		tempC.addIndex(indexNum, vacancy);
		
		//Course.showAllIndexDetails(); make it static
		}


	private static void removeAIndex() {
		
		//Course.showAllIndexDetails(); make it static
		System.out.print("Enter the course's Index Number:"); 
		int indexN = sc.nextInt();
		//Course.removeIndex(indexN); make it static
		
		
	}


	private static void viewCourse() {
		// TODO Auto-generated method stub
		
	}


	private static void viewIndexByCourse() {
		// TODO Auto-generated method stub
		
	}


	private static void CheckIndexSlots() {
		//Course.showAllIndexDetails(); make it static
		
	}



}
