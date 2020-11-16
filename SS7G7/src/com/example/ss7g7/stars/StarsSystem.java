package com.example.ss7g7.stars;

import com.example.ss7g7.stars.Login.LOGIN_RESULT;

/**
* <h1>Stars System</h1>
* The Stars System is the base framework that
* is used for authentication and decides which
* menu to show depending on the user who logged in.
* 
* <p>
* The StarsSystem class initializes the database, StarsDB
* It runs the login and keeps track of the current user.
* 
*
* @author Ong Rui Peng
* @author Angelina
* created on 2020/10/15
* 
* @version %I%
* @since 1.0
* 
*/

public class StarsSystem {
	private StarsDB db; 
	private Login login;
	private User currentUser;
	private StudentMenu studentMenu;
	private AdminUI adminMenu;

	/**
	 * Constructor of StarsSystem.
	 * Gets instance of StarsDB.
	 */
	public StarsSystem() {
		// Temp file location for student and course.
		// TODO: Finalize txt file name and location
		db = StarsDB.getInstance();
		
		login = new Login(db);
		currentUser = null;
		studentMenu = null;
		adminMenu = null;
	}

	/**
	 * Initializes the stars system. Calls the StarsDB singleton and passes
	 * the relative string file paths to the data files to the database to initialize.
	 * 
	 * @return <code>true</code> if database successfully initialized;
	 * 
	 */
	public boolean init() {
		boolean successful = db.init("../SS7G7/lib/studentInfo.ser", "../SS7G7/lib/courseInfo.ser");
		
		return successful;
	}

	/**
	 * Main loop of the system that will run as long as user exists the Stars System.
	 * Starts by asking user for login and running respective menus(UI) depending
	 * on the user which logged in.
	 * 
	 * 
	 */
	public void run() {
		/*
		 * Step 1: Login If logged in as an admin, bring user to the admin UI If logged
		 * in as student, bring user to the student UI
		 */

		boolean run = true;

		while (run) {
			System.out.println("Welcome to the Stars System");
			
			
			Login.LOGIN_RESULT loginResult = login.login();
			if(loginResult == LOGIN_RESULT.SUCCESSFUL_LOGIN) {
				currentUser = login.getCurrentUser();
			} else {
				System.out.println("Unsuccessful login. Please try again");
				continue;
			}
			
			if(currentUser == null) {
				System.out.println("Error, null user. Please try again");
				continue;
			}
			
			User.UserType currentUserType = currentUser.getUserType();
			
			/*
			 * PLEASE USE CREDENTIALS PROVIDED IN LOGINCRED.TXT AND README.TXT FOR THE
			 * PASS IN CLEAR TEXT
			 */			
			
			// TODO: Based on the user, create a new student/admin object. Create here or inside Student/Admin UI
			
			switch (currentUserType) {
			// Terminate stars
			case NIL:
				System.out.println("Thank you for using the stars system, goodbye");
				run = false;
				break;
			case STUDENT:
				runStudentMenu();
				break;
			case ADMIN:
				runAdminMenu();
				break;
			default:
				break;
			}
		}
	}

	
	/**
	 * Function to handle logout that is called by runStudentMenu and runAdminMenu.
	 * Sets the currentUser variable to null
	 * 
	 */
	private void logout() {
		System.out.println("Logging out");
		currentUser = null;
	}

	/**
	 * This method is used to create a StudentMenu and runs it.
	 * It also checks beforehand if the current user exists
	 * in the database or not. 
	 * 
	 * If user does not exist, it prints an error message and
	 * returns to the main loop of StarsSystem.
	 */
	private void runStudentMenu() {
		Student student = db.getStudent(currentUser.getUsername());
		
		if(student == null) {
			// throw exception. User exists but student does not exist in db
			System.out.println("User not found in database");
			return;
			
		}
		
		studentMenu = new StudentMenu(db, student);
		
		studentMenu.run();
		
		logout();
	}

	
	/**
	 * This method is used to create an AdminUI/AdminMenu and runs it.
	 * 
	 */
	private void runAdminMenu() {
		AdminUI.printAdminUI();
		logout();
		// Notes: Once this function ends, it goes back to login. see run()
	}
}
