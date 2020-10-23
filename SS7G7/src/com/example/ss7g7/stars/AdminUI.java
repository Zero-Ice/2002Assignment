package com.example.ss7g7.stars;

import java.util.*;

public class AdminUI {
	
	
	private static Scanner sc = new Scanner(System.in); // take input from user
	
	
	public static void printAdminUI(StarsDB db)
	{
		
		int choice;
		Logout:
		while(true){ //Print selection menu
			System.out.println("Welcome to admin page");
			System.out.println("");
			System.out.println("1. Student");
			System.out.println("2. Course");
			System.out.println("3. Exit");
			System.out.println("");
			System.out.print("Please select one of the options: ");
			choice = sc.nextInt();
			try{
				switch (choice) {
					case 1: // Go to Admin student UI class
						AdminStudUI.printAdminStudUI(db);
						break;
					case 2: // Go to Admin Course UI class
						AdminCourseUI.printAdminCourseUI(db);
						break;
					case 3: // Log out
						System.out.println("Logged out successfully");
						System.out.println();
						break Logout;
					default:
						System.out.println("Incorrect Input, please try again"); //when user input incorrect value
				}}
			catch (Exception e) {
				System.out.println("Incorrect Input, please try again"); //when user input incorrect value
			}
			System.out.println();
		}
	}
}
