package com.example.ss7g7.stars;
import java.util.*;

public class AdminCourseUI {

	static StarsDB database = StarsDB.getInstance();
	static ArrayList<Course> courseList = database.getAllCourse();
	static ArrayList<Student> studentList = database.getAllStudents(); //retrieve db from StarsDB
	
	private static Scanner sc = new Scanner(System.in); // take input from user
	
	
	public static void printAdminCourseUI() {
		
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
			System.out.println("8. Check available slot by index number");
			System.out.println("9. Exit");
			System.out.println("");
			System.out.print("Please select one of the options: ");
			choice = sc.nextInt();
			try{
				switch (choice) {
					case 1: 
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


	private static void addNewCourse() { //add new course to db 
		
		String courseCode = "";
		String courseName = "";
		String schooName = "";
		String lecVenue = "";
		String lecRemarks = "";
		String lecGroup = "";
        int intDay=0;
		int startHours=0;
		int startMinutes=0;
		int endHours=0;
		int endMinutes=0;
		int AU=0;
		boolean check;
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do { //check if it exist in the database
			System.out.print("Enter the course's code: ");
			courseCode = sc.nextLine();
			check = AdminCourseMngmt.isExistingCourseCode(courseCode.toUpperCase());
			if(check)
			{System.out.println("Course name already found in database, please enter another code");}
		} while (check);
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode);
		
		System.out.print("Enter course's name: ");
		courseName = sc.nextLine();
		
		System.out.print("Enter what school the course is under(i.e SCSE): ");
		schooName = sc.nextLine();
		
        while(true){ //check if user input the correct input format 
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
        
		System.out.print("Enter the lecture day:"); 
		intDay = sc.nextInt();
		System.out.print("Enter the lecture starting time(hours):"); 
		startHours = sc.nextInt();
		System.out.print("Enter the lecture starting (minutes):"); 
		startMinutes = sc.nextInt();
		System.out.print("Enter the lecture ending time(hours):"); 
		endHours = sc.nextInt();
		System.out.print("Enter the lecture ending time(minutes):"); 
		endMinutes = sc.nextInt();
		System.out.print("Enter the lecture venue:"); 
		lecVenue = sc.nextLine();
		System.out.print("Enter the lecture remark:"); 
		lecRemarks = sc.nextLine();
		System.out.print("Enter the lecture group number:"); 
		lecGroup = sc.nextLine();
        
        //update to database
        AdminCourseMngmt.addCourse(courseCode.toUpperCase(), courseName.toUpperCase(),schooName.toUpperCase(), AU);

		tempCourse.setLecDetails(intDay, startHours, startMinutes, endHours, endMinutes, 
        		lecVenue, lecRemarks, lecGroup);
        
       //Course.showfullCourseDetails(); //show result 
	    System.out.println();
		System.out.println("The Course has been added.");
		


	}


	private static void removeACourse() {
		
		//Course.showfullCourseDetails(); //show result 
		System.out.print("Enter the course's code:"); 
		String courseCode = sc.nextLine();
		AdminCourseMngmt.removeCourse(courseCode.toUpperCase()); //check and update to database
		
	}


	private static void updateCourseIndex() {

		 int choice;
		
		Logout:
			while(true){ //Print selection menu
				System.out.println("Welcome to admin update page");
				System.out.println("");
				System.out.println("1. Update Course Details ");
				System.out.println("2. Update Index Details");
				System.out.println("3. Update Lecture Details");
				System.out.println("4. Update Tutorial Details");
				System.out.println("5. Update Lab Details");
				System.out.println("6. Exit");
				System.out.println("");
				System.out.print("Please select one of the options: ");
				choice = sc.nextInt();
				try{
					switch (choice) {
						case 1: 
							//update course detail
							updateCourseDetail();
							break;
						case 2: 
							//update index number detail
							updateIndexDetail();
							break;
						case 3: 
							//update lecture detail
							updateLecDetail();
							break;
						case 4: 
							//update tutorial detail
							updateTutDetail();
	
							break;
						case 5: 
							//updaet lab detail
							updateLabDetail();
							break;
						case 6: // Log out
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
	
	private static void updateCourseDetail()
	{
		String courseCode = "";
		String NcourseCode = "";
		String courseName = "";
		String schooName = "";
		boolean check = false;
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code you want to update: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(AdminCourseMngmt.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode);
		
		System.out.print("Enter the updated course code:"); 
		NcourseCode = sc.nextLine();
		tempCourse.updateCourseCode(NcourseCode);
		
		System.out.print("Enter the updated code name:"); 
		courseName = sc.nextLine();
		tempCourse.updateCourseName(courseName);
		
		System.out.print("Enter the updated school name:"); 
		schooName = sc.nextLine();
		tempCourse.updateSchooName(schooName);
		
		System.out.print(courseName +"has been changed to " + NcourseCode + 
				"course name changed to" + courseName + "school name changed to" + schooName); 
		
		
	}
	
	private static void updateIndexDetail()
	{
		String courseCode = "";
		boolean check = false;
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code you want to update: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(AdminCourseMngmt.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode);
		
		
		System.out.print("Enter the Index Number:"); 
		int indexNum = sc.nextInt();
		System.out.print("Enter the New Index Number:"); 
		int NindexNum = sc.nextInt();
		
		tempCourse.updateIndex(indexNum, NindexNum);
		System.out.print(indexNum +"has been changed to " + NindexNum); 
		//tempCourse.showAllIndexDetails(); /show result
		
	}
	
	private static void updateLecDetail()
	{
		boolean check = false;
		String courseCode  = "";
		String lecVenue = "";
		String lecRemark = "";
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(AdminCourseMngmt.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode);
		
		System.out.print("Enter the updated lecture venue: "); 
		lecVenue = sc.nextLine();
		System.out.print("Enter the updated lecture remark: "); 
		lecRemark = sc.nextLine();
		
		tempCourse.updateLecVenue(lecVenue);
		tempCourse.updateLecRemark(lecRemark);
		
		System.out.print(courseCode + "'s lecture venue and remark has been updated"); 
		
		
	}
	
	private static void updateTutDetail()
	{
		
		boolean check = false;
		String courseCode  = "";
		String tutGroup = "";
		String tutRemark = "";
		String tutVenue = "";
		int indexNum = 0;
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(AdminCourseMngmt.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode);
		
		System.out.print("Enter the updated tutorial venue: "); 
		tutVenue = sc.nextLine();
		System.out.print("Enter the updated tutorial group number: "); 
		tutGroup = sc.nextLine();
		System.out.print("Enter the updated tutorial remark: "); 
		tutRemark = sc.nextLine();
		
		tempCourse.getIndex(indexNum).updateTutGroup(tutGroup);
		tempCourse.getIndex(indexNum).updateTutRemark(tutRemark);
		tempCourse.getIndex(indexNum).updateTutVenue(tutVenue);
		
		System.out.print(courseCode + "'s tutorial venue, group number and remark has been updated"); 
	}
	
	private static void updateLabDetail()
	{
		boolean check = false;
		String courseCode  = "";
		String labGroup = "";
		String labRemark = "";
		String labVenue = "";
		int indexNum = 0;
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(AdminCourseMngmt.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode);
		
		System.out.print("Enter the updated lab venue: "); 
		labVenue = sc.nextLine();
		System.out.print("Enter the updated lab group number: "); 
		labGroup = sc.nextLine();
		System.out.print("Enter the updated lab remark: "); 
		labRemark = sc.nextLine();
		
		tempCourse.getIndex(indexNum).updateLabGroup(labGroup);
		tempCourse.getIndex(indexNum).updateLabRemark(labRemark);
		tempCourse.getIndex(indexNum).updateLabVenue(labVenue);
		
		System.out.print(courseCode + "'s lab venue, group number and remark has been updated"); 
		
	}
	


	private static void addNewIndex() {// add new index to db
		
		 int indexNum =0;
		 int vacancy = 0;
		 String courseCode="";
		 String tutVenue="";
		 String labVenue="";
		 String labRemarks = "";
		 String tutRemarks = "";
		 String tutGroup ="";
		 String labGroup = "";
		 int intDay= 0;
		 int startHours= 0;
		 int startMinutes= 0;
		 int endHours= 0;
		 int endMinutes= 0;
		 boolean check = false;
			 
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(AdminCourseMngmt.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempC = AdminCourseMngmt.getCourseByCode(courseCode);
			
		System.out.print("Enter the course's Index Number:"); 
		int indexNo = sc.nextInt();
		System.out.print("Enter the course's maximum vacancy slots:"); 
		vacancy = sc.nextInt();
		tempC.addIndex(indexNum, vacancy); // add index number and number of ava slots in that course to db
		System.out.println(courseCode + "of" + indexNum +"has been added.");
			
		
		//create new tutorial lesson
		System.out.print("Enter the tutorial day:"); 
		intDay = sc.nextInt();
		System.out.print("Enter the tutorial starting time(hours):"); 
		startHours = sc.nextInt();
		System.out.print("Enter the tutorial starting (minutes):"); 
		startMinutes = sc.nextInt();
		System.out.print("Enter the tutorial ending time(hours):"); 
		endHours = sc.nextInt();
		System.out.print("Enter the tutorial ending time(minutes):"); 
		endMinutes = sc.nextInt();
		System.out.print("Enter the tutorial venue:"); 
		tutVenue = sc.nextLine();
		System.out.print("Enter the tutorial remark:"); 
		tutRemarks = sc.nextLine();
		System.out.print("Enter the tutorial group number:"); 
		tutGroup = sc.nextLine();
		tempC.getIndex(indexNo).setTutDetails(intDay, startHours, startMinutes, 
				endHours, endMinutes, tutVenue, tutRemarks, tutGroup);
		

		//create new lab lesson
		System.out.print("Enter the lab day:"); 
		intDay = sc.nextInt();
		System.out.print("Enter the lab starting time(hours):"); 
		startHours = sc.nextInt();
		System.out.print("Enter the lab starting (minutes):"); 
		startMinutes = sc.nextInt();
		System.out.print("Enter the lab ending time(hours):"); 
		endHours = sc.nextInt();
		System.out.print("Enter the lab ending time(minutes):"); 
		endMinutes = sc.nextInt();
		System.out.print("Enter the lab venue:"); 
		labVenue = sc.nextLine();
		System.out.print("Enter the lab remark:"); 
		labRemarks = sc.nextLine();
		System.out.print("Enter the lab group number:"); 
		labGroup = sc.nextLine();
						
		tempC.getIndex(indexNo).setLabDetails(intDay, startHours, startMinutes, endHours, endMinutes, 
				labVenue, labRemarks, labGroup);
				
		System.out.println(indexNo + "'s lab and tutorial details has been added.");
		//tempC.showAllIndexDetails();
		}
	
	
		


	private static void removeAIndex() {
		
		//remove a index from database 
		boolean check = false;
		String courseCode  = "";
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(AdminCourseMngmt.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode);
		
		System.out.print("Enter the course's Index Number:"); 
		int indexNo = sc.nextInt();
		
		tempCourse.removeIndex(indexNo);
		System.out.println(indexNo + "of " + courseCode + " has been removed.");
		
		
	}


	private static void viewCourse() {
		boolean check = false;
		String courseCode  = "";
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(AdminCourseMngmt.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode);
		//tempCourse.showfullCourseDetails();
		
	}


	private static void viewIndexByCourse() {
		
		boolean check = false;
		String courseCode  = "";
		int indexNum;
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(AdminCourseMngmt.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		System.out.print("Enter the index code: "); 
		indexNum = sc.nextInt();
		
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode);
		tempCourse.getIndex(indexNum).showNumOfVacancies();
		
		
		
		
	}


	private static void CheckIndexSlots() {
		boolean check = false;
		String courseCode  = "";
		int indexNum;
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(AdminCourseMngmt.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		System.out.print("Enter the index code: "); 
		indexNum = sc.nextInt();
		
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode);
		tempCourse.getIndex(indexNum).showNumOfVacancies();
		
	}



}
