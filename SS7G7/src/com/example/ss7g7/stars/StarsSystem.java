package com.example.ss7g7.stars;

public class StarsSystem {
	private StarsDB db; 
	private Login login;
	private User currentUser;

	public StarsSystem() {
		// Temp file location for student and course.
		// TODO: Finalize txt file name and location
		db = StarsDB.getInstance();
		
		login = new Login(db);
		currentUser = null;
	}

	/*
	 * Initializes the stars system. Loads up student and course data from txtfiles
	 * 
	 */
	public boolean init() {
		boolean successful = db.init("../SS7G7/lib/studentInfo.ser", "../SS7G7/lib/courseInfo.ser","../SS7G7/lib/indexInfo.ser" );
		
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
			
			
			Login.LOGIN_RESULT loginResult = login.login();
			switch(loginResult) {
			case SUCCESSFUL_LOGIN:
				currentUser = login.getCurrentUser();
				break;
			default:
				break;
			}
			
			if(currentUser == null) break;
			
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
				AdminUI.printAdminUI();
				break;
			default:
				break;
			}
		}
	}

	
	/*
	 * Function to handle logout Called by runStudentMenu and runAdminMenu
	 */
	private void logout() {
		System.out.println("Logging out");
		currentUser = null;
	}

	private void runStudentMenu() {
		// TODO: Get Student Info based on currentUser
		
		Student student = db.getStudent(currentUser.getUser());
		
		if(student == null) {
			// throw exception. User exists but student does not exist in db
			System.out.println("User not found in database");
			return;
			
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
