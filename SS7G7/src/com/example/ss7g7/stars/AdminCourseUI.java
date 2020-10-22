package com.example.ss7g7.stars;
import java.util.*;

public class AdminCourseUI {

	static List<Course> course; //= StarsDB.getAllCourse(); make it static
	
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
		
		String courseCode;
		String courseName;
		String schooName;
		int AU;
		boolean check;
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do { //check if it exist in the database
			System.out.print("Enter the course's name: ");
			courseCode = sc.nextLine();
			check = !(AdminCourseMngmt.isExistingCourseCode(courseCode));
		} while (check);
		
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
        
        //update to database
        AdminCourseMngmt.addCourse(courseCode.toUpperCase(), courseName.toUpperCase(),schooName.toUpperCase(), AU);

       
	    System.out.println();
		System.out.println("The Course has been added.");
		
		
		
		
		

	}


	private static void removeACourse() {
		
		//Course.showfullCourseDetails(); make this static
		System.out.print("Enter the course's code:"); 
		String courseCode = sc.nextLine();
		AdminCourseMngmt.removeCourse(courseCode.toUpperCase()); //check and update to database
		
	}


	private static void updateCourseIndex() {
		
		
		 int indexNum =0;
		 int NindexNum = 0;
		 int vacancy = 0;
		 String courseCode="";
		 String courseName="";
		 String schooName = "";
		 String tutVenue="";
		 String lecVenue="";
		 String labVenue="";

		 int choice;
		
		Logout:
			while(true){ //Print selection menu
				System.out.println("Welcome to admin update page");
				System.out.println("");
				System.out.println("1. Update Course Details ");
				System.out.println("2. Update Index Details");
				System.out.println("3. Update Tutorial Details");
				System.out.println("4. Update Update Details");
				System.out.println("5. Update Lecture Details");
				System.out.println("6. Exit");
				System.out.println("");
				System.out.print("Please select one of the options: ");
				choice = sc.nextInt();
				try{
					switch (choice) {
						case 1: 
							//update course detail
							
							System.out.print("Enter the updated course code:"); 
							courseCode = sc.nextLine();
							//Course.updateCourseCode(courseCode); make it static
							
							System.out.print("Enter the updated code name:"); 
							courseName = sc.nextLine();
							//Course.updateCourseName(courseCode); make it static
							
							System.out.print("Enter the updated school name:"); 
							schooName = sc.nextLine();
							//Course.updateSchooName(schooName); make it static

							break;
						case 2: 
							//update index number detail
							
							int check=0;
							System.out.print("Enter the Index Number:"); 
							indexNum = sc.nextInt();
							//check = Course.getIndex(indexNum); make it static 
							System.out.print("Enter the New Index Number:"); 
							NindexNum = sc.nextInt();
							//Course.updateIndex(check,NindexNum); make it static 
							//Course.showAllIndexDetails(); make it static
							
							break;
						case 3: 
							//update index tutorial venue 
							
							System.out.print("Enter the Index Number:"); 
							indexNum = sc.nextInt();
							
							System.out.print("Enter the updated tutorial venue:"); 
							tutVenue = sc.nextLine();
							
							((Course) course).updateTutVenue(indexNum,tutVenue);
							
							break;
						case 4: 
							//update index lab venue 
							
							System.out.print("Enter the Index Number:"); 
							indexNum = sc.nextInt();
							
							System.out.print("Enter the updated lab venue:"); 
							labVenue = sc.nextLine();
							
							((Course) course).updateTutVenue(indexNum,labVenue);
							break Logout;
						case 5: 
							//update index lecture venue 
							
							System.out.print("Enter the Index Number:"); 
							indexNum = sc.nextInt();
							
							System.out.print("Enter the updated lab venue:"); 
							lecVenue = sc.nextLine();
							
							((Course) course).updateLecVenue(lecVenue);
							break Logout;
						case 6: // Log out
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


	private static void addNewIndex() {
		
		 int indexNum =0;
		 int vacancy = 0;
		 String courseCode="";
		 String tutVenue="";
		 String lecVenue="";
		 String labVenue="";
		 int intDay= 0;
		 int startHours= 0;
		 int startMinutes= 0;
		 int endHours= 0;
		 int endMinutes= 0;

		 
		//Course.showAllIndexDetails()
			 
  
		 while(true) { // ask user to input coursecode first 
		System.out.print("Enter the course's code:"); 
		courseCode = sc.nextLine();
		if (AdminCourseMngmt.isExistingCourseCode(courseCode)) //check if it exist in database
		{System.out.print("Course Name found."); 
		sc.nextLine();
		break;
		}
		else {
		System.out.println("Course code doesnt not exist.");}
		 }
		 
		Course tempC = AdminCourseMngmt.getCourseByCode(courseCode); 
		System.out.print("Enter the course's Index Number:"); 
		indexNum = sc.nextInt();
		System.out.print("Enter the course's maximum vacancy slots:"); 
		vacancy = sc.nextInt();
		tempC.addIndex(indexNum, vacancy); // add index number and number of ava slots in that course

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
						
		//create new tutorial lesson
		((Course) course).setTutDetails(indexNum,intDay,startHours,startMinutes,endHours, 
								endMinutes,tutVenue); 
		
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
						
		((Course) course).setLabDetails(indexNum,intDay,startHours,startMinutes,endHours, 
								endMinutes,labVenue);
				

		//create new lecture lesson
		
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
						
		((Course) course).setLecDetails(intDay,startHours,startMinutes,endHours,endMinutes,
								lecVenue);

		//Course.showAllIndexDetails(); make it static
		}
	
	
		


	private static void removeAIndex() {
		
		//remove a index from database 
		
		//Course.showAllIndexDetails(); make it static
		System.out.print("Enter the course's Index Number:"); 
		int indexN = sc.nextInt();
		//Course.removeIndex(indexN); make it static
		
		
	}


	private static void viewCourse() {
		//Course.showfullCourseDetails(); make it static
		
	}


	private static void viewIndexByCourse() {
		
		boolean check = false;
		String courseCode = "";
		
		do {
			System.out.print("Enter the course's code:"); ;
			courseCode = sc.nextLine();
			check = AdminCourseMngmt.isExistingCourseCode(courseCode);
			if (check){
				System.out.println("Course code is not found in database.");
			}
		} while (check);
		Course tempCourse = AdminCourseMngmt.getCourseByCode(courseCode);
		
		if(course.size() <= 0){
			System.out.println("\nNo record is found!\n");
			return;
		}
		
		for (Course s: course){
			System.out.print(s.getCourseName() + "         \t");
			//System.out.print(s.getIndexNum() + " " + s.getLastName());
			//System.out.println();
			
			check = true;
		}
		if (!check) System.out.println("\nNo record is found!");
		
		
		
		
	}


	private static void CheckIndexSlots() {
		//Course.showAllIndexDetails(); make it static
		
	}



}
