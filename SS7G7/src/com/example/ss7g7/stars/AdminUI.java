package com.example.ss7g7.stars;

import java.util.Scanner;

/**
 * <h1>Admin User Interface</h1>
 * <p>
 * The AdminUI provides an overview 
 * of the administrative actions that
 * an admin user would be allowed to make.
 * 
 *<p>
 * The options include:
 * <li>Going to AdminStudUI to edit students information of StarsSystem
 * <li>Going to AdminCourseUI to edit courses information of StarsSystem
 * <li>Going back to previous page (Login page)
 * 
 * @author Ng Kah Hui
 * @version %I%
 * @since 2020-10-15
 */
public class AdminUI {
	
	private static Scanner sc = new Scanner(System.in); // take input from user
	
	/**
	 * This method prints all the administrative
	 * options available.
	 */
	public static void printAdminUI()
	{
		/**
		 * This method is entered when user entered as an admin/staff
		 * Ask user which options they want to choose
		 */
		
		int choice;
		Logout:
		while(true){ //Print selection menu
			System.out.println("Welcome to Stars System's Admin Page");
			System.out.println("");
			System.out.println("1. Student");
			System.out.println("2. Course");
			System.out.println("3. Go back to previous page");
			System.out.println("");
			System.out.print("Please select one of the options: ");
			choice = sc.nextInt();
			try{
				switch (choice) {
					case 1: // Go to Admin student UI class
						AdminStudUI.printAdminStudUI();
						break;
					case 2: // Go to Admin Course UI class
						AdminCourseUI.printAdminCourseUI();
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
