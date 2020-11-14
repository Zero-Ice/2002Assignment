package com.example.ss7g7.stars;

/**
* <h1>My Student Automated Registration System (MySTARS)</h1>
* MySTARS program implements a console based application
* meant for each School's academic staff and undergraduate
* students
* <p>
* The application allows the creation of courses and adding of student records as well
* as registration of courses and students. There will be an administrator mode for
* academic staff and user mode for students.
* 
*
* @author  Ong Rui Peng
* @since   2020-10-15 
*/

public class main {
	public static void main(String[] args) {
		StarsSystem stars = new StarsSystem();

		boolean successfulInit = stars.init();
		
		if(!successfulInit) {
			// TODO: Throw error
			System.out.println("Stars failed to initialize");
		}
		
		stars.run();
	}
}
