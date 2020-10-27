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
			System.out.println("2. Remove a course");
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
					case 9: // Go back
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
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode.toUpperCase());
		
		
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
        
		System.out.print("Enter the lecture day(1 for monday, 2 for tuesday...):"); 
		intDay = sc.nextInt();
		System.out.print("Enter the lecture starting time(hours)(XX:00):"); 
		startHours = sc.nextInt();
		System.out.print("Enter the lecture starting (minutes)(00:XX):"); 
		startMinutes = sc.nextInt();
		System.out.print("Enter the lecture ending time(hours)(XX:00):"); 
		endHours = sc.nextInt();
		System.out.print("Enter the lecture ending time(minutes)(00:XX):"); 
		endMinutes = sc.nextInt();
		System.out.print("Enter the lecture venue:"); 
		lecVenue = sc.nextLine();
		sc.nextLine();
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
		String courseCode ="";
		boolean check;
		
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code you want to remove: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(AdminCourseMngmt.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		
		AdminCourseMngmt.removeCourse(courseCode.toUpperCase()); //check and update to database
		System.out.println("");
		//Course.showfullCourseDetails(); //show result 
	}


	private static void updateCourseIndex() {

		 int choice;
		
		Logout:
			while(true){ //Print selection menu
				System.out.println("");
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
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode.toUpperCase());
		
		System.out.print("Enter the updated course code:"); 
		NcourseCode = sc.nextLine();
		tempCourse.updateCourseCode(NcourseCode.toUpperCase());
		
		System.out.print("Enter the updated course name:"); 
		courseName = sc.nextLine();
		tempCourse.updateCourseName(courseName.toUpperCase());
		
		System.out.print("Enter the updated school name:"); 
		schooName = sc.nextLine();
		tempCourse.updateSchooName(schooName.toUpperCase());
		
		System.out.println("");
		System.out.print("Course code " + courseCode.toUpperCase() +" has been changed to " + NcourseCode.toUpperCase() + 
				" , course name changed to " + courseName.toUpperCase() + " and school name changed to " + schooName.toUpperCase()); 
		System.out.println("");
		
	}
	
	private static void updateIndexDetail()
	{
		String courseCode = "";
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
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode.toUpperCase());
		
		
		System.out.print("Enter the index number:"); 
		int indexNum = sc.nextInt();
		System.out.print("Enter the new index number:"); 
		int NindexNum = sc.nextInt();
		System.out.println("");
		tempCourse.updateIndex(indexNum, NindexNum);
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
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode.toUpperCase());
		
		System.out.print("Enter the updated lecture venue: "); 
		lecVenue = sc.nextLine();
		System.out.print("Enter the updated lecture remark: "); 
		lecRemark = sc.nextLine();
		
		tempCourse.updateLecVenue(lecVenue);
		tempCourse.updateLecRemark(lecRemark);
		System.out.println("");
		System.out.print("Course code " + courseCode.toUpperCase() + "'s lecture venue and remark has been updated"); 
		
		
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
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode.toUpperCase());
		
		System.out.print("Enter the index number you want to change:"); 
		indexNum = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter the updated tutorial venue: "); 
		tutVenue = sc.nextLine();
		System.out.print("Enter the updated tutorial group number: "); 
		tutGroup = sc.nextLine();
		System.out.print("Enter the updated tutorial remark: "); 
		tutRemark = sc.nextLine();
		
		tempCourse.getIndex(indexNum).updateTutGroup(tutGroup);
		tempCourse.getIndex(indexNum).updateTutRemark(tutRemark);
		tempCourse.getIndex(indexNum).updateTutVenue(tutVenue);
		
		System.out.println("");
		System.out.print("Index number " + indexNum + "'s tutorial venue, group number and remark has been updated"); 
		System.out.println("");
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
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode.toUpperCase());
		
		System.out.print("Enter the index number you want to change:"); 
		indexNum = sc.nextInt();
		sc.nextLine();
		
		System.out.print("Enter the updated lab venue: "); 
		labVenue = sc.nextLine();
		System.out.print("Enter the updated lab group number: "); 
		labGroup = sc.nextLine();
		System.out.print("Enter the updated lab remark: "); 
		labRemark = sc.nextLine();

		
		tempCourse.getIndex(indexNum).updateLabGroup(labGroup);
		tempCourse.getIndex(indexNum).updateLabRemark(labRemark);
		tempCourse.getIndex(indexNum).updateLabVenue(labVenue);
		
		System.out.println("");
		System.out.print("Index Number " + indexNum + "'s lab venue, group number and remark has been updated"); 
		System.out.println("");
		
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
		Course tempC = AdminCourseMngmt.getCourseByCode(courseCode.toUpperCase());
			
		System.out.print("Enter the course's index number:"); 
		indexNum = sc.nextInt();
		System.out.print("Enter the course's maximum vacancy slots:"); 
		vacancy = sc.nextInt();
		tempC.addIndex(indexNum, vacancy); // add index number and number of ava slots in that course to db
			
		System.out.print(""); 
		System.out.print(""); 
		//create new tutorial lesson
		System.out.print("Enter the tutorial day(1 for monday, 2 for tuesday...):"); 
		intDay = sc.nextInt();
		System.out.print("Enter the tutorial starting time(hours)(XX:00):"); 
		startHours = sc.nextInt();
		System.out.print("Enter the tutorial starting (minutes)(00:XX):"); 
		startMinutes = sc.nextInt();
		System.out.print("Enter the tutorial ending time(hours)(XX:00):"); 
		endHours = sc.nextInt();
		System.out.print("Enter the tutorial ending time(minutes)(00:XX):"); 
		endMinutes = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter the tutorial venue:"); 
		tutVenue = sc.nextLine();
		System.out.print("Enter the tutorial remark:"); 
		tutRemarks = sc.nextLine();
		System.out.print("Enter the tutorial group number:"); 
		tutGroup = sc.nextLine();
		tempC.getIndex(indexNum).setTutDetails(intDay, startHours, startMinutes, 
				endHours, endMinutes, tutVenue, tutRemarks, tutGroup);
		
		System.out.print(""); 
		//create new lab lesson
		System.out.print("Enter the lab day(1 for monday, 2 for tuesday...):"); 
		intDay = sc.nextInt();
		System.out.print("Enter the lab starting time(hours)(XX:00):"); 
		startHours = sc.nextInt();
		System.out.print("Enter the lab starting (minutes)(00:XX):"); 
		startMinutes = sc.nextInt();
		System.out.print("Enter the lab ending time(hours)(XX:00):"); 
		endHours = sc.nextInt();
		System.out.print("Enter the lab ending time(minutes)(00:XX):"); 
		endMinutes = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter the lab venue:"); 
		labVenue = sc.nextLine();
		System.out.print("Enter the lab remark:"); 
		labRemarks = sc.nextLine();
		System.out.print("Enter the lab group number:"); 
		labGroup = sc.nextLine();
						
		tempC.getIndex(indexNum).setLabDetails(intDay, startHours, startMinutes, endHours, endMinutes, 
				labVenue, labRemarks, labGroup);
		
		System.out.print("");  
		System.out.println("Index Number" +indexNum + "'s lab and tutorial details has been added.");
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
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode.toUpperCase());
		
		System.out.print("Enter the course's index number:"); 
		int indexNo = sc.nextInt();
		
		System.out.println(""); 
		tempCourse.removeIndex(indexNo);
		
		
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
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode.toUpperCase());
		System.out.println("");
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
		System.out.print("Enter the index number: "); 
		indexNum = sc.nextInt();
		
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode.toUpperCase());
		System.out.println("");
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
		System.out.print("Enter the index number: "); 
		indexNum = sc.nextInt();
		
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode.toUpperCase());
		System.out.println("");
		tempCourse.getIndex(indexNum).showNumOfVacancies();
		
	}



}
