package com.example.ss7g7.stars;
import java.util.Scanner;

public class AdminCourseUI {

	private static StarsDB database = StarsDB.getInstance();
	private static Scanner sc = new Scanner(System.in); // take input from user
	
	public static void printAdminCourseUI() {
		
		int choice;
		GoBack:
		while(true){ //Print selection menu
			System.out.println("Welcome to Admin Course Page");
			System.out.println("");
			System.out.println("1. Add new course");
			System.out.println("2. Remove a course");
			System.out.println("3. Update course/index");
			System.out.println("4. Add new index");
			System.out.println("5. Remove a index");
			System.out.println("6. View course");
			System.out.println("7. View index by course");
			System.out.println("8. Check available slot by index number");
			System.out.println("9. Go back to previous page");
			System.out.println("");
			System.out.print("Please select one of the options: ");
			choice = sc.nextInt();
			try{
				switch (choice) {
					case 1: 
						addNewCourse(); //Add new course to database
						break;
					case 2: 
						removeACourse(); //Remove existing course from database
						break;
					case 3: 
						updateCourseIndex(); //Update existing course/index detail
						break;
					case 4: 
						addNewIndex(); //Add new index number to existing course code
						break;
					case 5: 
						removeAIndex();//Remove existing index number from course code
						break;
					case 6: 
						viewCourse();//View course code detail by course code
						break;
					case 7:
						viewIndexByCourse();//View index number detail by course code
						break;
					case 8: 
						CheckIndexSlots();//View index number's vacancies 
						break;
					case 9: // Go back
						System.out.println();
						break GoBack;
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

	//Function to add new course to database
	private static void addNewCourse() { 
		
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
		do { 
			System.out.print("Enter the course's code: "); //check if user input existing course code
			courseCode = sc.nextLine();
			check = database.isExistingCourseCode(courseCode.toUpperCase());
			if(check)
			{System.out.println("Course name already found in database, please enter another code");}
		} while (check);
		
		System.out.print("Enter course's name: ");
		courseName = sc.nextLine();
		
		System.out.print("Enter what school the course is under(i.e SCSE): ");
		schooName = sc.nextLine();
		
        while(true){ 
        	try{
        		System.out.print("Enter the number of AUs: "); //check if user input the correct input format 
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
		database.addCourse(courseCode.toUpperCase(), courseName.toUpperCase(),schooName.toUpperCase(), AU);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());
		database.setDBInstance(database);
		tempCourse.setLecDetails(intDay, startHours, startMinutes, endHours, endMinutes, 
        		lecVenue, lecRemarks, lecGroup);
       
	    System.out.println();
		System.out.println("The Course has been added.");
		
		database.printCourseList(); //show result
		


	}

	//Function to remove course from database
	private static void removeACourse() { 
		String courseCode ="";
		boolean check;
		
		database.printCourseList(); //show result
		System.out.println();
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code you want to remove: "); //check if such course code exists 
			courseCode = sc.nextLine();
			check =!(database.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		
		database.removeCourse(courseCode.toUpperCase()); //check and update to database
		database.setDBInstance(database);
		
		System.out.println("");
		database.printCourseList(); //show result 
	}

	//Function to add a new index via course code to database
	private static void updateCourseIndex() {
		int choice;
		
		GoBack:
			while(true){ //Print selection menu
				System.out.println("");
				System.out.println("Welcome to Admin Update Page");
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
							//Update course detail
							updateCourseDetail();
							break;
						case 2: 
							//Update index number detail
							updateIndexDetail();
							break;
						case 3: 
							//Update lecture detail
							updateLecDetail();
							break;
						case 4: 
							//Update tutorial detail
							updateTutDetail();
	
							break;
						case 5: 
							//Update lab detail
							updateLabDetail();
							break;
						case 6: // Go back
							System.out.println();
							break GoBack;
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
		int AU = 0;
		boolean check = false;
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code you want to update: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(database.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());
		
		System.out.print("Enter the updated course code:"); 
		NcourseCode = sc.nextLine();
		
		//tempCourse.updateCourseCode(NcourseCode.toUpperCase());
		
		System.out.print("Enter the updated course name:"); 
		courseName = sc.nextLine();
		//tempCourse.updateCourseName(courseName.toUpperCase());
		
        while(true){ //check if user input the correct input format 
        	try{
        		System.out.print("Enter the updated AU:"); 
        		AU = sc.nextInt();
        		sc.nextLine();
        		break;
        	} catch (Exception e){
        		sc.nextLine();
        		System.out.println("Invalid input! Academic Unit must be a number!");
        	}
        }
        //tempCourse.updateAU(AU);
        
    
        if (courseCode.equals(NcourseCode)) {
        	
			tempCourse.updateCourseCode(NcourseCode.toUpperCase());
			tempCourse.updateCourseName(courseName.toUpperCase());
			tempCourse.updateAU(AU);
			database.updateCourseRecords(tempCourse);
        }
        else {
        	database.removeCourse(courseCode);
        	database.addCourse(NcourseCode, courseName.toUpperCase(), tempCourse.getSchooName(), AU);
        }
        
    	database.setDBInstance(database);       
        
		System.out.println("");
		System.out.print("Course code " + courseCode.toUpperCase() +" has been changed to " + NcourseCode.toUpperCase() + 
				" , course name changed to " + courseName.toUpperCase() + " and AU value changed to " + AU); 
		System.out.println("");
		
	}
	
	private static void updateIndexDetail()
	{
		String courseCode = "";
		boolean check = false;
		int indexNum =0;
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such username exists 
			courseCode = sc.nextLine();
			check =!(database.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());
		
		do {
			System.out.print("Enter the index number: "); //check if such index number exists
			indexNum = sc.nextInt();
			check = !(tempCourse.containsIndexNo(indexNum));
			if(check)
			{
				{System.out.println("Index number not found in database.");}
			}
		}while(check);
		
		System.out.print("Enter the new index number:"); 
		int NindexNum = sc.nextInt();
		System.out.println("");
		tempCourse.updateIndex(indexNum, NindexNum);
		
		database.updateCourseRecords(tempCourse);
        database.setDBInstance(database);
		
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
			check =!(database.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());
		
		System.out.print("Enter the updated lecture venue: "); 
		lecVenue = sc.nextLine();
		System.out.print("Enter the updated lecture remark: "); 
		lecRemark = sc.nextLine();
		
		tempCourse.updateLecVenue(lecVenue);
		tempCourse.updateLecRemark(lecRemark);
		System.out.println("");
		System.out.print("Course code " + courseCode.toUpperCase() + "'s lecture venue and remark has been updated"); 
		
		database.updateCourseRecords(tempCourse);
        database.setDBInstance(database);
		
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
			check =!(database.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());
		
		do {
			System.out.print("Enter the index number: ");  //check if such index number exists
			indexNum = sc.nextInt();
			check = !(tempCourse.containsIndexNo(indexNum));
			if(check)
			{
				{System.out.println("Index number not found in database.");}
			}
		}while(check);
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
		
		database.updateCourseRecords(tempCourse);
        database.setDBInstance(database);
		
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
			check =!(database.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());
		
		do {
			System.out.print("Enter the index number: ");  //check if such index number exists
			indexNum = sc.nextInt();
			check = !(tempCourse.containsIndexNo(indexNum));
			if(check)
			{
				{System.out.println("Index number not found in database.");}
			}
		}while(check);
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
		
		database.updateCourseRecords(tempCourse);
        database.setDBInstance(database);
		
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
		 int tutOccurring = 3;
		 int labOccurring = 3;
		 boolean check = false;
			 
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such course code exists 
			courseCode = sc.nextLine();
			check =!(database.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());
		
		while(true){
			System.out.print("Enter the course's index number:");   //check if such index number exists
			indexNum = sc.nextInt();
			System.out.print("");  
			check = !(tempCourse.containsIndexNo(indexNum));
			if(!check)
			{
				{System.out.println("Index number found in database, please try again");
				}
			}
			else
			{break;}
		}
		
		System.out.print("Enter the course's maximum vacancy slots:"); 
		vacancy = sc.nextInt();
		
		tempCourse.addIndex(indexNum, vacancy); // add index number and number of ava slots in that course to db
			
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
		System.out.print("Enter the occurring week for tutorial\n(1)Even, (2)Odd, (3)Every");
		tutOccurring = sc.nextInt();
		tempCourse.getIndex(indexNum).setTutDetails(intDay, startHours, startMinutes, 
				endHours, endMinutes, tutVenue, tutRemarks, tutGroup, tutOccurring);
		
		System.out.print(""); 

		while (true){
		System.out.print("Enter the lab day(1 for monday, 2 for tuesday...):"); 
		intDay = sc.nextInt();
		if(intDay > 7 || intDay == 0)
			{System.out.println("input value cant be 0 or higher than 7, please try again"); }
		else
			{break;}	
		}
		
		while(true) //loop to check if starting time is higher than ending time
		{	
					
			while(true){
				System.out.print("Enter the lab starting time(hours)(XX:00):"); 
				startHours = sc.nextInt();
					if(startHours < 8 || startHours > 24)
					{
						System.out.println("input value cant be lower than 8 or higher than 24, please try again"); 
					}
					else
					{break;}
					}

			while(true){
				System.out.print("Enter the lab starting (minutes)(00:XX):");
				startMinutes = sc.nextInt();
					if(startMinutes >60)
					{
						System.out.println("input value cant be higher than 60, please try again"); 
					}
					else
					{break;}
					}
					 
			while(true){
				System.out.print("Enter the lab ending time(hours)(XX:00):"); 
				endHours = sc.nextInt();
					if(endHours <8 || endHours >24)
					{
						System.out.println("input value cant be lower than 8 or higher than 24, please try again"); 
					}
					else
					{break;}
					}
					
			while(true){
				System.out.print("Enter the lab ending time(minutes)(00:XX):"); 
				endMinutes = sc.nextInt();
					if(endMinutes >60)
					{
						System.out.println("input value cant be higher than 60, please try again"); 
					}
					else
					{break;}
					}
					
			if(startHours > endHours){
				System.out.println("Lab starting time cant be later than its ending time, please try again");
			}
			else
			{break;}
			
			}

		sc.nextLine();
		System.out.print("Enter the lab venue:"); 
		labVenue = sc.nextLine();
		System.out.print("Enter the lab remark:"); 
		labRemarks = sc.nextLine();
		System.out.print("Enter the lab group number:"); 
		labGroup = sc.nextLine();
		System.out.print("Enter the occurring week for tutorial\n(1)Even, (2)Odd, (3)Every");
		labOccurring = sc.nextInt();
						
		tempCourse.getIndex(indexNum).setLabDetails(intDay, startHours, startMinutes, endHours, endMinutes, 
				labVenue, labRemarks, labGroup, labOccurring);
		
		database.updateCourseRecords(tempCourse);
        database.setDBInstance(database);
		
		System.out.println("");  
		System.out.println("Index Number " +indexNum + "'s lab and tutorial details has been added.");

		}
	
	//Function to remove an index number from a course from database
	private static void removeAIndex() {
		
		boolean check = false;
		String courseCode  = "";
		int indexNum=0;
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such course code exists 
			courseCode = sc.nextLine();
			check =!(database.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());
		
		do {
			System.out.print("Enter the course's index number:");  //check if such index number exists  
			indexNum = sc.nextInt();
			check = !(tempCourse.containsIndexNo(indexNum));
			if(check)
			{
				{System.out.println("Index number not found in database.");}
			}
		}while(check);
		
		System.out.println(""); 
		tempCourse.removeIndex(indexNum);
		
		database.updateCourseRecords(tempCourse);
        database.setDBInstance(database);
		
	}

	private static void viewCourse() { 
		database.printCourseList();
		
	}

	//Function to view index number detail by course code
	private static void viewIndexByCourse() { 
		
		boolean check = false;
		String courseCode  = "";
		int indexNum=0;
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such course code exists 
			courseCode = sc.nextLine();
			check =!(database.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());
		
		do {
			System.out.print("Enter the course's index number:"); //check if such index number exists 
			indexNum = sc.nextInt();
			check = !(tempCourse.containsIndexNo(indexNum));
			if(check)
			{
				{System.out.println("Index number not found in database.");}
			}
		}while(check);
	
		System.out.println("");
		tempCourse.getIndex(indexNum).printStudentListByIndex();
		
		
		
		
	}

	//Function to view index number number of vacancies(slots)
	private static void CheckIndexSlots() { 
		boolean check = false;
		String courseCode  = "";
		int indexNum;
		
		System.out.println("Press any key to continue");
		sc.nextLine();
		sc.nextLine();
		do {
			System.out.print("Enter the course code: "); //check if such course code exists 
			courseCode = sc.nextLine();
			check =!(database.isExistingCourseCode(courseCode.toUpperCase()));
			if(check)
			{System.out.println("Course code is not found in database.");}
		} while (check);
		Course tempCourse = database.getCourse(courseCode.toUpperCase());
		
		do {
			System.out.print("Enter the course's index number:"); //check if such index number exists 
			indexNum = sc.nextInt();
			check = !(tempCourse.containsIndexNo(indexNum));
			if(check)
			{
				{System.out.println("Index number not found in database.");}
			}
		}while(check);
		
		System.out.println("");
		tempCourse.getIndex(indexNum).getNumOfVacancies();
		
	}
}
		
	
	

	