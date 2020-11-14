package com.example.ss7g7.stars;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
* <h1>Stars Database System</h1>
* The StarsDB is a singleton that acts as a 'database'
* Since we are not allowed to use any databases, this
* class helps us manage the students and courses.
* 
* <p>
* The StarsDB is used to store the data throughout the
* application. The initial data is loaded through
* file IO from text files. 
* 
*
* @author  Ong Rui Peng, Angelina
* @since   2020-10-15
*/
public class StarsDB {
	private static StarsDB db_instance = null;

	private String studentDataFilePath;
	private String courseDataFilePath;

	private ArrayList<Student> students;
	private ArrayList<Course> courses;
	
	private FileIO file = new FileIO();
	private SendEmail send = new SendEmail();
	
	
	private static Scanner sc = new Scanner(System.in);
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");	

	/**
	 * Constructor of StarsDB
	 */
	private StarsDB() {
		students = new ArrayList<Student>();
		courses = new ArrayList<Course>();
	}
	
	public static StarsDB getInstance() {
		if(db_instance == null) {
			System.out.println("DATABASE NULL");
			db_instance = new StarsDB();
		}
					
		return db_instance;
	}

	/*
	 * Loads data from the files using the file paths given
	 */
	public boolean init(String studentDataFilePath, String courseDataFilePath) {
		// TODO: File I/O
		this.studentDataFilePath = studentDataFilePath;
		this.courseDataFilePath = courseDataFilePath;
		

		/*
		 * If need be, to "restore" the stored data to initial, just delete the contents
		 * of the studentInfo and courseInfo .ser files and re-run the program twice. 
		 * Once to initialize. Files should be restored on the second run.
		 */

		this.students = file.getStudentRecords(studentDataFilePath);

		if (this.students.isEmpty()) {
			System.out.print("Get Student File failed");
			System.out.println("Student File Empty.\nPopulating file...");
			createDummyStudents();
			
			
		} else {
			for (Student s : this.students) {
				System.out.println(s.getName() + " " + s.getUserName());
			}
		}
		
		this.courses = file.getCourseRecords(courseDataFilePath);
		
		if (this.courses.isEmpty()) {
			System.out.print("Get Course File failed");
			System.out.println("Course File Empty.\nPopulating file...");
			createDebugCourses();
			
		} else {
			
			for (Course c: this.courses) {
				System.out.println(c.getCourseName()+ " COURSES "+ c.getCourseCode());
				c.printAllIndexes();
			}
		}
		
//		System.out.println("Sending test email..");
//		send.email("teststudent457@gmail.com", "Test", "Test");
				
		return true;
	}
	
	/*
	 * 
	 * STUDENT SECTION
	 * 
	 * 
	 */

	public ArrayList<Student> getAllStudents() {
		return students;
	}


	public Student getStudent(String userName) {
		for (int user = 0; user < students.size(); user++) {
			if (userName.equals(students.get(user).getUserName())) {
				return students.get(user);
			}
		}
		return null;
	}
	public void addStudent(Student currentStudent) {
		file.setStudentRecord(currentStudent);
		file.setLoginCredentials(currentStudent.getUsername(), hash(currentStudent.getPass()), currentStudent.getPass());
	}
	
	
	public void removeStudent(Student currentStudent) {
		file.updateStudentRecords(currentStudent, "remove");
		file.removeLoginCredentials(currentStudent.getUsername(), hash(currentStudent.getPass()), currentStudent.getPass());
	}
	
	public void updateStudentRecords(Student currentStudent) {
		file.updateStudentRecords(currentStudent, "update");
	}
	
	
	public void createDummyStudents() {

		// purpose of accessStart/End is for student to only enter the stars planner at
		// certain time period
		Calendar newDate1 = Calendar.getInstance(); // create a date to accessStart
		Calendar newDate2 = Calendar.getInstance(); // create a date for accessEnd

		Student x = new Student("student1", "test1", "Mark", " Tan", "U1969420", "M", "Antartica", 96549119, "marktan@hotmail.com",
				newDate1, newDate2);
		file.setStudentRecord(x);
		file.setLoginCredentials(x.getUsername(), hash(x.getPass()), x.getPass());
		
		Student y = new Student("student2", "test2", "Laura", " Tan", "U1829091", "F", "Spain", 96533219, "lautan@hotmail.com",
				newDate1, newDate2);
		file.setStudentRecord(y);
		file.setLoginCredentials(y.getUsername(), hash(y.getPass()), y.getPass());
		
	}
	
	//Function to find a student via their matriculation number 
	public Student getStudentByMatric(String matricNo){ 
		for (Student s : students) {
			if (s.getMatricNo().equals(matricNo)) {
				return s;}
		}
		return null;
	}
	
	//Function for admin to edit a student access period
	public void updateAccessPeriod(String matricNo, Calendar newAccessStart, Calendar newAccessEnd){ 
		Student student = getStudentByMatric(matricNo);
		student.setAccessStart(newAccessStart);
		student.setAccessEnd(newAccessEnd);
		
	}
	
	//Function to check existing username in student database
	public  Boolean isExistingUsername(String username){ 
		for (Student s : students) {
			if (s.getUserName().equals(username)) {
				System.out.println("This username has already been used, please try again.");
				return false;
			}
		}
		return true;
	}
	
	//Function to check student via their matriculation number 
	public  Boolean isExistingMatNum(String matricNo){ 
		for (Student s : students) {
			if (s.getMatricNo().equals(matricNo)) {
				System.out.println("Matriculation number is found in database.");
				return false;
			}
		}
		return true;
	}
	
	//Function to add new student to database
	public void addStudent(String username, String password, String name, String lastName,
			String matricNo, String gender, String nationality, int mobileNo, String email, Calendar accessStart, Calendar accessEnd) 
	
	{
        Student newStud = new Student(username, password, name, lastName, matricNo, gender, nationality, mobileNo, email, accessStart, accessEnd);
        
        StarsDB.getInstance().addStudent(newStud);
      
	}

	//Function to remove a student in database 
	public void removeStudent(String matNum) {
		Student student = getStudentByMatric(matNum);
		StarsDB.getInstance().removeStudent(student);
		
	}
	
	//Function to show all student exists in student db
	public void printStudentList(){ 
		boolean flag = false;
		System.out.println();
		System.out.println("Matriculation Number\tFull Name");
		System.out.println("---------------------------------------------------");
		
		if(students.size() <= 0){
			System.out.println("\nNo record is found!\n");
			return;
		}
		
		for (Student s: students){
			System.out.print(s.getMatricNo() + "         \t");
			System.out.print(s.getName() + " " + s.getLastName());
			System.out.println();
			
			flag = true;
		}
		if (!flag) System.out.println("\nNo record is found!");
	}
	
	//Function check if user input the right access start/end format 
	public Calendar getValidDateTime(String mode) throws ParseException{
		
		SimpleDateFormat testdate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date test1 = testdate.parse("32/13/2040 25:61"); //check if user input an out of range date
		Date test2 = testdate.parse("32/13/1991 25:61"); //check if user input an out of range date
		String date = "";

	    Date parsedDate = null;
		boolean validDate = false;		
		Calendar newDate = Calendar.getInstance();
		
		do{
			System.out.print("Enter " + mode + " (DD/MM/YYYY HH:MM): ");
			date  = sc.nextLine();
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		    try {
		    	parsedDate = dateFormat.parse(date);
		    	 
		    } catch (ParseException e) {
		        System.out.println("Input is not in the correct format!");
		        continue;
		    }
		    newDate.setTime(parsedDate);

		    validDate = true;
		    
		    if(parsedDate.after(test1) || parsedDate.before(test2))
		    {System.out.println("Input is not in the correct format!");
		    validDate = false;}

		} while(!validDate);
				
		return newDate;
	}
	
	/*
	 * 
	 * 
	 * COURSE SECTION
	 * 
	 * 
	 */
	
	public ArrayList<Course> getAllCourse() {
		return courses;
	}


	public Course getCourseByIndex(int indexNo) {
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).containsIndexNo(indexNo))
				return courses.get(i);
		}

		return null;
	}
	
	public Course getCourse(String courseCode) {
		for(Course c : courses) {
			if(c.getCourseCode().equals(courseCode)) {
				return c;
			}
		}
		return null;
	}
	
	public boolean isExistingCourseCode(String courseCode)
	{
		for(Course c : courses)
		{
			//System.out.println(c.getCourseCode()+courseCode+"COMPARE");
			if(c.getCourseCode().equals(courseCode)) 
			{return true;}
		}
		return false;
	}
	
	
	public void printCourseList(){ //print course detail
		boolean flag = false;
		System.out.println();
		System.out.println("Course Code\tCourse Name\tAU");
		System.out.println("---------------------------------------------------");
		
		if(courses.size() <= 0){
			System.out.println("\nNo record is found!\n");
			return;
		}
		
		for (Course c: courses){
			System.out.print(c.getCourseCode() + "         \t");
			System.out.print(c.getCourseName()+ "          " + c.getAU());
			System.out.println();
			
			flag = true;
		}
		if (!flag) System.out.println("\nNo record is found!");
	}
	
	
	// add new course into db
	public void addCourse(String courseCode, String courseName, String SchooName, int aU) {
		Course newCourse = new Course(courseCode, courseName,SchooName, aU);
		courses = file.setCourseRecord(newCourse);
		
	}
	
	
	public void removeCourse(String courseCode) { //remove coursecode from db 
		
		System.out.println(courseCode);
		if (isExistingCourseCode(courseCode)){
			Course course = getCourse(courseCode);

			courses = file.updateCourseRecords(course, "remove");
			System.out.println("Course " + course.getCourseName() + " (" + courseCode + ") has been removed!");
		}
		else{
			System.out.println("Course code is not found!\n");
		}
		
	}
	
	public void updateCourseRecords(Course course) {
		courses = file.updateCourseRecords(course, "update");
	}
	
	
	public void createDebugCourses() {
		Course c = new Course("CZ2002", "OODP", "SCSE", 3);
		c.setLecDetails(1, 12, 30, 14, 30, "LT19", "OODP", "CS2");
		
		Index i1 = c.addIndex(120014, 30);
		i1.setTutDetails(2, 12, 0, 14, 0, "A ROOM", "NO REMARKS", "SS1", 3);
		
		Index i2 = c.addIndex(100012, 30);
		i2.setTutDetails(2, 10, 0, 12, 0, "A ROOM", "NO REMARKS", "SS2", 3);
		
		
		Course c1 = new Course("CZ2005", "OS", "SCSE", 3);
		Index i3 = c1.addIndex(200005, 30);
		i3.setTutDetails(2, 11, 0, 13, 0, "C ROOM", "NO REMARKS", "SP1", 3);
		c1.setLecDetails(1, 10, 30, 12, 30, "LT19", "OS", "CS3");
		
		Course c2 = new Course("CZ2001", "ALGO", "SCSE", 3);
		Index i4 = c2.addIndex(200001, 30);
		i4.setTutDetails(2, 10, 0, 12, 0, "A ROOM", "NO REMARKS", "SA1", 3);
		
		Course c3 = new Course("EE8084", "Cyber Sec", "EE", 3);
		c3.setLecDetails(1, 12, 0, 14, 0, "ONLINE", "lmao", "dank420");
		c3.addIndex(999999, 30);
	
//		courses.add(c);
//		courses.add(c1);
//		courses.add(c2);
//		courses.add(c3);
		
		file.setCourseRecord(c);
		file.setCourseRecord(c1);
		file.setCourseRecord(c2);
		file.setCourseRecord(c3);

	}
	
	/*
	 * 
	 * 
	 * LOGIN SECTION
	 * 
	 * 
	 */
	
	public HashMap<String, String> getDBLoginCred() {

		List[] credentials = new List[2];
		List<String> username = new ArrayList<String>();
		List<String> pass = new ArrayList<String>();
		HashMap<String, String> hmap = new HashMap<String, String>();

		if (file.getLoginCredentials() != null) {
			credentials = file.getLoginCredentials();
			
			username = credentials[0];
			pass = credentials[1];
			
		} else {
			System.out.println("Error reading file");
		}

		for (int i = 0; i < username.size(); i++) {
			hmap.put(username.get(i), pass.get(i));
		}

		return hmap;

	}
	
	String hash (String passClear) {
		
		String hash = new String();
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedHash = digest.digest(passClear.toString().getBytes(StandardCharsets.UTF_8));
			
			// Convert byte array into signum representation  
			BigInteger number = new BigInteger(1, encodedHash);
  
	        // Convert message digest into hex value  
			StringBuilder hexString = new StringBuilder(number.toString(16)); 
			
	        // Pad with leading zeros 
	        while (hexString.length() < 32)  
	        {  
	            hexString.insert(0, '0');  
	        }  
	  
	        hash = hexString.toString();  
	 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hash;
	
	}
	
	public void addAdmin (String username, String password) {
		file.setLoginCredentials(username, hash(password), password);
	}
	
	

}
