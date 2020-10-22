package com.example.ss7g7.stars;

public class StarsSystem {
	private StarsDB db; 
	private Login login;
	private User currentUser;

	public StarsSystem() {
		// Temp file location for student and course.
		// TODO: Finalize txt file name and location
		db = new StarsDB("studentInfo.txt", "courseInfo.txt");
		
		login = new Login();
		currentUser = null;
	}

	/*
	 * Initializes the stars system. Loads up student and course data from txtfiles
	 * 
	 */
	public boolean init() {
		boolean successful = db.init();
		
		return successful;
	}

	/*
	 * Main loop of the system
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
			
			
			// @ K, use this to test the user returned by runLogin()
//			User.UserType currentUserType = currentUser.getUserType();
			
			// Temp variable for debugging purposes. 
			// @Kah Hui change it to ADMIN to test your menu
			
			User.UserType currentUserType;
			currentUserType = login.Login();
			
			//currentUserType = ;
			
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
				AdminUI.printAdminUI();
				break;
			default:
				break;
			}
		}
	}

	/*
	 * 
	 * Returns the User who has logged in. 
	 * If User.userType is NIL, it indicates the program to terminate. see run()
	 */
//	private User.UserType runLogin() {
//		// Handled by K
//		
//		// Note: 1 Requirement is that Password should not be displayed on console when
//		// entering it
//		currentUser = login.Login();
//		// TODO Change this temp return
//		return null;
//	}

	/*
	 * Function to handle logout Called by runStudentMenu and runAdminMenu
	 */
	private void logout() {
		System.out.println("Logging out");
		currentUser = null;
	}

	private void runStudentMenu() {
		// TODO: Get Student Info based on currentUser
		
		Student student = db.getDebugStudent();
		
		if(student == null) {
			// throw exception. User exists but student does not exist in db
		}
		
		StudentMenu studentMenu = new StudentMenu(db, student);
		
		studentMenu.run();
		
		logout();
	}

	private void runAdminMenu() {
		// TODO some while loop
		// Notes: Once this function ends, it goes back to login. see run()
	}
}
