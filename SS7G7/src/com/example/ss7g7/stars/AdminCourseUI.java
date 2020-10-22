package com.example.ss7g7.stars;
import java.util.*;



public class AdminCourseUI {

	static List<Course> course;
	
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

	}


	private static void removeACourse() {
		// TODO Auto-generated method stub
		
	}


	private static void updateCourseIndex() {
		// TODO Auto-generated method stub
		
	}


	private static void addNewIndex() {
		// TODO Auto-generated method stub
		
	}


	private static void removeAIndex() {
		// TODO Auto-generated method stub
		
	}


	private static void viewCourse() {
		// TODO Auto-generated method stub
		
	}


	private static void viewIndexByCourse() {
		// TODO Auto-generated method stub
		
	}


	private static void CheckIndexSlots() {
		// TODO Auto-generated method stub
		
	}



}
