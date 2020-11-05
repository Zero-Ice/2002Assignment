package com.example.ss7g7.stars;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class StarsDB {
	private static StarsDB db_instance = null;

	private String studentDataFilePath;
	private String courseDataFilePath;

	private ArrayList<Student> students;
	private ArrayList<Course> courses;
	
	private FileIO file = new FileIO();

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
			System.out.print("Get failed");
			System.out.println("Student File Empty.\nPopulating file...");
			createDummyStudents();
			
			
		} else {
			for (Student s : this.students) {
				System.out.println(s.getName() + " " + s.getUserName());
			}
		}
		
		this.courses = file.getCourseRecords(courseDataFilePath);
		
		if (this.courses.isEmpty()) {
			System.out.print("Get failed");
			System.out.println("Course File Empty.\nPopulating file...");
			createDebugCourses();
			
		} else {
			
			for (Course c: this.courses) {
				System.out.println(c.getCourseName()+ " COURSES "+ c.getCourseCode());
				c.printAllIndexes();
			}
		}
				
		return true;
	}

	public ArrayList<Student> getAllStudents() {
		return students;
	}

	public ArrayList<Course> getAllCourse() {
		return courses;
	}

	public HashMap<String, String> getDBLoginCred() {

		List[] credentials = new List[3];
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

	public Student getStudent(String userName) {
		for (int user = 0; user < students.size(); user++) {
			if (userName.equals(students.get(user).getUserName())) {
				return students.get(user);
			}
		}
		return null;
	}




	public Course getCourse(int indexNo) {
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
	
	
	public void addStudent(Student currentStudent) {
		file.setStudentRecord(currentStudent);
	}
	
	public void removeStudent(Student currentStudent) {
		file.updateStudentRecords(currentStudent, "remove");
	}
	
	public void updateStudentRecords(Student currentStudent) {
		file.updateStudentRecords(currentStudent, "update");
	}
	
	public void updateCourseRecords(Course course) {
		file.updateCourseRecords(course, "update");
	}
	
	public void removeCourse(String courseCode) { //remove coursecode from db 
		
		if (isExistingCourseCode(courseCode)){
			Course course = getCourse(courseCode);

			file.updateCourseRecords(course, "remove");
			System.out.println("Course " + course.getCourseName() + " (" + courseCode + ") has been removed!");
		}
		else{
			System.out.println("Course code is not found!\n");
		}
		
	}
	// add new course into db
	public void addCourse(String courseCode, String courseName, String SchooName, int aU) {
		Course newCourse = new Course(courseCode, courseName,SchooName, aU);
		file.setCourseRecord(newCourse);
		
	}
	
	public void createDummyStudents() {

		// purpose of accessStart/End is for student to only enter the stars planner at
		// certain time period
		Calendar newDate1 = Calendar.getInstance(); // create a date to accessStart
		Calendar newDate2 = Calendar.getInstance(); // create a date for accessEnd

		Student x = new Student("student1", "Mark", " Tan", "U1969420", "M", "Antartica", 96549119, "marktan@hotmail.com",
				newDate1, newDate2);
		file.setStudentRecord(x);
		Student y = new Student("student2", "Laura", " Tan", "U1829091", "F", "Spain", 96533219, "lautan@hotmail.com",
				newDate1, newDate2);
		file.setStudentRecord(y);

	}
	

	public void createDebugCourses() {
		Course c = new Course("CZ2002", "OODP", "SCSE", 3);
		c.addIndex(123456, 30);
		c.addIndex(696969, 30);
		c.setLecDetails(1, 12, 30, 14, 30, "LT19", "OODP", "CS2");
		
		file.setCourseRecord(c);
		
		Course c1 = new Course("CZ2005", "OS", "SCSE", 3);
		c1.addIndex(200005, 30);
		c1.setLecDetails(1, 13, 30, 15, 30, "LT19", "OS", "CS3");
	
		
		file.setCourseRecord(c1);
		
	}
	
	public void setDBInstance(StarsDB db)
	{
		this.db_instance = db;
	}

}
