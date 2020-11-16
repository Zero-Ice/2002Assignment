package com.example.ss7g7.stars;

import java.util.Scanner;


/**
 * <h1>Admin User Interface</h1>
 * 
 * <p>
 * The AdminMenu provides an overview 
 * of the administrative actions that
 * an admin user would be allowed to make.
 * 
 * The options include:
 * <li>Go to AdminStudUI to edit students information of StarsSystem
 * <li>Go to AdminCourseUI to edit courses information of StarsSystem
 * <li>Go back to previous page(login page)
 * 
 * @author Ng Kah Hui
 * created on 2020/10/15
 * 
 * @version %I%
 * @since 1.0
 *
 */
public class AdminMenu {
	
	/**
	 * Provides a user interface for admin/staff who has logged in to StarsSystem.
	 */
	
	private AdminStudUI adminStudUI;
	private AdminCourseUI adminCourseUI;
	private Scanner sc; 
	
	public AdminMenu() {
		sc = new Scanner(System.in); // take input from user
		adminStudUI = new AdminStudUI();
		adminCourseUI = new AdminCourseUI();
	}
	
	/**
	 * This method prints all the administrative
	 * options available.
	 */
	public void run()
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
					case 1: // Go to Admin Student UI class
						adminStudUI.run();
						break;
					case 2: // Go to Admin Course UI class
						adminCourseUI.run();
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
