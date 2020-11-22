package com.example.ss7g7.stars;

import java.io.File;
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
 * 
 * <p>
 * The StarsDB is a singleton that acts as a
 * 'database' Since we are not allowed to use any databases, this class helps us
 * manage the students and courses.
 * 
 * <p>
 * The StarsDB is used to store the data throughout the application. The initial
 * data is loaded through file IO from text files.
 * 
 *
 * @author Ong Rui Peng
 * @author Kah Hui
 * @author Angelina
 * 
 * @version %I%
 * @since 1.0
 * 
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

	/**
	 * This function provides a getter to the static StarsDB instance and creates a new
	 * database instance if none exists.
	 * 
	 * @param 	<code>null</code>
	 * @return	StarsDB
	 */
	public static StarsDB getInstance() {
		if (db_instance == null) {
			System.out.println("DATABASE NULL");
			db_instance = new StarsDB();
		}

		return db_instance;
	}

	/**
	 * Loads data from the files using the file paths given. Creates dummy
	 * students/courses if none was loaded from the files.
	 * 
	 * @param studentDataFilePath	refers to the .ser data file of the students
	 * @param courseDataFilePath	refers to the .ser data file of the courses
	 * @return <code>true</code>	returns upon initialization of the database
	 * 
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

			for (Course c : this.courses) {
				System.out.println(c.getCourseName() + " COURSES " + c.getCourseCode());
				c.printAllIndexes();
			}
		}

		return true;
	}

	/*
	 * 
	 * STUDENT SECTION
	 * 
	 * 
	 */

	/**
	 * This method returns all students in the database.
	 * 
	 * @param <code>null</code>
	 * @return students		which is an array list of Student objects
	 */
	public ArrayList<Student> getAllStudents() {
		return students;
	}

	/**
	 * This method retrieves a student based on a username,
	 * searches through the students data in the database linearly 
	 * and returns the first result which corresponds to the student's
	 * username. 
	 * 
	 * @param userName
	 * @return student	if student is found in database;
	 * 					<code>null</code> if student is not found.
	 */
	public Student getStudent(String userName) {
		for (int user = 0; user < students.size(); user++) {
			if (userName.equals(students.get(user).getUserName())) {
				return students.get(user);
			}
		}
		return null;
	}

	/**
	 * Adds a student object to the database and updates the .ser file.
	 * 
	 * @param currentStudent refers to the student to be added
	 * @see {@link FileIO#setStudentRecord(Student)}
	 * @see {@link FileIO#setLoginCredentials(String, String)}
	 */
	public void addStudent(Student currentStudent) {
		students = file.setStudentRecord(currentStudent);
		file.setLoginCredentials(currentStudent.getUsername(), hash(currentStudent.getPass()));
	}

	/**
	 * Removes a student from the database and updates the student file.
	 * 
	 * @param currentStudent refers to the student to be removed
	 * @see {@link FileIO#updateStudentRecords(Student, String)}
	 * @see {@link FileIO#removeLoginCredentials(String, String)}
	 */
	public void removeStudent(Student currentStudent) {
		students = file.updateStudentRecords(currentStudent, "remove");
		
		ArrayList<Course> registeredCourses = new ArrayList<Course>();
		
		for (Course existingCourse: courses) {
			for (int course = 0; course < currentStudent.getCourses().size(); course++) {
				if (existingCourse.getCourseCode().equals(currentStudent.getCourses().get(course).getCourseCode())) {
					registeredCourses.add(existingCourse);
				}
			}
		}
		
		for (Course course: registeredCourses) {
			course.removeStudent(currentStudent);
			file.updateCourseRecords(course, "update");
		}
		
		file.removeLoginCredentials(currentStudent.getUsername(), hash(currentStudent.getPass()));
	}

	/**
	 * Updates a student from the database and updates the student file
	 * 
	 * @param currentStudent refers to the updated student object
	 */
	public void updateStudentRecords(Student currentStudent) {
		students = file.updateStudentRecords(currentStudent, "update");
	}

	/**
	 * This method creates hardcoded dummy students
	 */
	public void createDummyStudents() {

		// purpose of accessStart/End is for student to only enter the stars planner at
		// certain time period
		Calendar newDate1 = Calendar.getInstance(); // create a date to accessStart
		Calendar newDate2 = Calendar.getInstance(); // create a date for accessEnd

		Student x = new Student("student1", "test1", "Mark", " Tan", "U1969420", "M", "Antartica", 96549119,
				"marktan@hotmail.com", newDate1, newDate2);
		file.setStudentRecord(x);
		file.setLoginCredentials(x.getUsername(), hash(x.getPass()));

		Student y = new Student("student2", "test2", "Laura", " Tan", "U1829091", "F", "Spain", 96533219,
				"lautan@hotmail.com", newDate1, newDate2);
		file.setStudentRecord(y);
		file.setLoginCredentials(y.getUsername(), hash(y.getPass()));
		
		Student z = new Student("BRoss", "password1", "Bob", " Ross", "U1942069B", "M", "American", 12345678,
				"bobross@gmail.com", newDate1, newDate2);
		file.setStudentRecord(z);
		file.setLoginCredentials(z.getUsername(), hash(z.getPass()));

		Student a = new Student("JShar69", "password2", "Jabob", " Shar", "U1234567J", "M", "Singaporean", 44556677,
				" jshar@ntu.edu.sg", newDate1, newDate2);
		file.setStudentRecord(a);
		file.setLoginCredentials(a.getUsername(), hash(a.getPass()));

	}

	/**
	 * This method returns a student object with the 
	 * corresponding matriculation number.
	 * Searches through the student records in the database
	 * linearly and returns the first student with
	 * the corresponding matriculation number.
	 * 
	 * 
	 * @param matricNo
	 * @return s 	if student is found in the database;
 * 					<code>null</code> if student is not found.
	 */
	public Student getStudentByMatric(String matricNo) {
		for (Student s : students) {
			if (s.getMatricNo().equals(matricNo)) {
				return s;
			}
		}
		return null;
	}

	/**
	 * Updates a student's access period.
	 * 
	 * @param matricNo
	 * @param newAccessStart
	 * @param newAccessEnd
	 */
	public void updateAccessPeriod(String matricNo, Calendar newAccessStart, Calendar newAccessEnd) {
		Student student = getStudentByMatric(matricNo);
		student.setAccessStart(newAccessStart);
		student.setAccessEnd(newAccessEnd);
		
		file.updateStudentRecords(student, "update");

	}

	/**
	 * Checks if a username already belongs to an existing student.
	 * 
	 * @param username
	 * @return 	<code>true</code> if username exists;
	 * 			<code>false</code> if username does not exist.
	 */
	public Boolean isExistingUsername(String username) {
		for (Student s : students) {
			if (s.getUserName().equals(username)) {
				System.out.println("This username has already been used, please try again.");
				return false;
			}
		}
		return true;
	}

	/**Checks if a matric number belongs to an existing student
	 * 
	 * @param matricNo
	 * @return 	<code>true</code> if matric number exists;
	 * 			<code>false</code> if it does not.
	 */
	public Boolean isExistingMatNum(String matricNo) {
		for (Student s : students) {
			if (s.getMatricNo().equals(matricNo)) {
				System.out.println("Matriculation number is found in database.");
				return false;
			}
		}
		return true;
	}

	/**
	 * This method adds a student object with all its defined parameters into the database.
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @param lastName
	 * @param matricNo
	 * @param gender
	 * @param nationality
	 * @param mobileNo
	 * @param email
	 * @param accessStart
	 * @param accessEnd
	 */

	public void addStudent(String username, String password, String name, String lastName, String matricNo,
			String gender, String nationality, int mobileNo, String email, Calendar accessStart, Calendar accessEnd)

	{
		Student newStud = new Student(username, password, name, lastName, matricNo, gender, nationality, mobileNo,
				email, accessStart, accessEnd);

		StarsDB.getInstance().addStudent(newStud);

	}

	
	/**
	 * This is to remove a student object from the database.
	 * 
	 * @param matNum refers to the matric number of the student that is to be removed
	 */
	
	public void removeStudent(String matNum) {
		Student student = getStudentByMatric(matNum);
		StarsDB.getInstance().removeStudent(student);

	}

	 /**
	 * This method prints the name and matric number of all students that exists in student database.
	 */
	
	public void printStudentList() {
		boolean flag = false;
		System.out.println();
		System.out.println("Matriculation Number\tFull Name");
		System.out.println("---------------------------------------------------");

		if (students.size() <= 0) {
			System.out.println("\nNo record is found!\n");
			return;
		}

		for (Student s : students) {
			System.out.print(s.getMatricNo() + "         \t");
			System.out.print(s.getName() + " " + s.getLastName());
			System.out.println();

			flag = true;
		}
		if (!flag)
			System.out.println("\nNo record is found!");
	}

	
	/**
	 * This method validates user input for the start and end access date.
	 * 
	 * @param mode can be the start or end of the access date
	 * @return newDate is a user input date that is correctly formatted
	 * @throws ParseException when the user inputted start or end
	 * 						  access date is incorrectly formatted.
	 */
	public Calendar getValidDateTime(String mode) throws ParseException {

		SimpleDateFormat testdate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date test1 = testdate.parse("32/13/2040 25:61"); // check if user input an out of range date
		Date test2 = testdate.parse("32/13/1991 25:61"); // check if user input an out of range date
		String date = "";

		Date parsedDate = null;
		boolean validDate = false;
		Calendar newDate = Calendar.getInstance();

		do {
			System.out.print("Enter " + mode + " (DD/MM/YYYY HH:MM): ");
			date = sc.nextLine();
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			try {
				parsedDate = dateFormat.parse(date);

			} catch (ParseException e) {
				System.out.println("Input is not in the correct format!");
				continue;
			}
			newDate.setTime(parsedDate);

			validDate = true;

			if (parsedDate.after(test1) || parsedDate.before(test2)) {
				System.out.println("Input is not in the correct format!");
				validDate = false;
			}

		} while (!validDate);

		return newDate;
	}

	/*
	 * 
	 * 
	 * COURSE SECTION
	 * 
	 * 
	 */

	/**
	 * This method returns all courses in the database.
	 * 
	 * @return courses which is an array list of Course objects
	 */
	public ArrayList<Course> getAllCourse() {
		return courses;
	}

	/**
	 * This method returns the course that belongs to an index number,
	 * searches the course data linearly and returns the first
	 * course containing the index number. 
	 * 
	 * @param indexNo
	 * @return 	course if course with given index is found;
	 * 			<code>null</code> if course is not found.
	 */
	public Course getCourseByIndex(int indexNo) {
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).containsIndexNo(indexNo))
				return courses.get(i);
		}

		return null;
	}

	/**
	 * This method returns the course belonging to a course code
	 * Searches the course data linearly and returns the first
	 * course with the course code.
	 * 
	 * @param courseCode
	 * @return Course object if course is found in the database;
	 * 				<code>null</code> if course is not found.
	 */
	public Course getCourse(String courseCode) {
		for (Course c : courses) {
			if (c.getCourseCode().equals(courseCode)) {
				return c;
			}
		}
		return null;
	}

	
	/**
	 * This method checks if the user inputted course code exists in the database.
	 * 
	 * @param courseCode refers to the user inputted course code
	 * @return 	<code>true</code> if course exists;
	 * 			<code>false</code> if course does not.
	 */
	public boolean isExistingCourseCode(String courseCode) {
		for (Course c : courses) {
			if (c.getCourseCode().equals(courseCode)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method prints the name of courses found in the database.
	 */
	public void printCourseList() { 
		boolean flag = false;
		System.out.println();
		System.out.println("Course Code\tCourse Name\tAU");
		System.out.println("---------------------------------------------------");

		if (courses.size() <= 0) {
			System.out.println("\nNo record is found!\n");
			return;
		}

		for (Course c : courses) {
			System.out.print(c.getCourseCode() + "         \t");
			System.out.print(c.getCourseName() + "          " + c.getAU());
			System.out.println();

			flag = true;
		}
		if (!flag)
			System.out.println("\nNo record is found!");
	}

	/**
	 * This method adds a new course to the database.
	 * 
	 * @param courseCode
	 * @param courseName
	 * @param SchooName
	 * @param aU
	 */
	public void addCourse(String courseCode, String courseName, String SchooName, int aU) {
		Course newCourse = new Course(courseCode, courseName, SchooName, aU);
		courses = file.setCourseRecord(newCourse);

	}

	/**
	 * Removes a course from the database with the corresponding course code,
	 * does not remove if course does not exist in the DB.
	 * Once course is removed, students that are registered to
	 * the course will also be removed.
	 * 
	 * @param courseCode refers to the course to be removed
	 * @see {@link Course#removeStudent(Student)}
	 */
	public void removeCourse(String courseCode) { // remove coursecode from db

		System.out.println(courseCode);
		if (isExistingCourseCode(courseCode)) {
			Course course = getCourse(courseCode);

			for (Student currentStudent: students) {
				for (int registeredCourse=0; registeredCourse<currentStudent.getCourses().size(); registeredCourse++) {
					if(currentStudent.getCourses().get(registeredCourse).getCourseCode().equals(courseCode)) {
						System.out.println("DROP");
						currentStudent.dropCourse(currentStudent.getCourses().get(registeredCourse).getIndexNo());
						file.updateStudentRecords(currentStudent, "update");
					}
				}
				
			}
			
			courses = file.updateCourseRecords(course, "remove");
			System.out.println("Course " + course.getCourseName() + " (" + courseCode + ") has been removed!");
			
		} else {
			System.out.println("Course code is not found!\n");
		}

	}
	
	/**
	 * Updates course code to new course code
	 * and retains old indexes 
	 * 
	 * @param oldCourse
	 * @param newCourse
	 * @see FileIO#updateCourseName(Course, String)
	 */
	public void updateCourse(String oldCourse, String newCourse) { // remove coursecode from db

		if (isExistingCourseCode(oldCourse)) {
			Course course = getCourse(oldCourse);
			course.updateCourseCode(newCourse);
			
			System.out.println("PRINTUPDATECOURSE "+course.getCourseCode());
			courses = file.updateCourseName(course, oldCourse);

			for (Student currentStudent: students) {
				for (int registeredCourse=0; registeredCourse<currentStudent.getCourses().size(); registeredCourse++) {
					if(currentStudent.getCourses().get(registeredCourse).getCourseCode().equals(oldCourse)) {
						System.out.println("UPDATE");
						currentStudent.updateCourse(newCourse, currentStudent.getCourses().get(registeredCourse).getIndexNo());
						file.updateStudentRecords(currentStudent, "update");
					}
				}
				
			}
			
			System.out.println("Course " + course.getCourseName() + " (" + newCourse + ") has been updated!");
			
		} else {
			System.out.println("Course code is not found!\n");
		}

	}

	/**
	 * This method updates the course records stored in the .ser file.
	 * 
	 * @param course refers to the course to be updated
	 */
	
	public void updateCourseRecords(Course course) {
		courses = file.updateCourseRecords(course, "update");			
	}
	
	/**
	 * This method updates the course records stored in the .ser file.
	 * When index of course is deleted, students registered to the
	 * index will also be removed.
	 * 
	 * @param course refers to the course to be updated
	 * @param indexToRemove
	 */
	
	public void updateCourseRecords(Course course, int indexToRemove) {
		
		for (Student currentStudent: students) {
			for (int registeredCourse=0; registeredCourse<currentStudent.getCourses().size(); registeredCourse++) {
				if(currentStudent.getCourses().get(registeredCourse).getIndexNo() == indexToRemove) {
					System.out.println("DROPINDEX");				
					currentStudent.dropCourse(currentStudent.getCourses().get(registeredCourse).getIndexNo());
					file.updateStudentRecords(currentStudent, "update");
				}
			}
		}
		
		try {
			course.removeIndex(indexToRemove);
			courses = file.updateCourseRecords(course, "update");
		}catch (Exception e) {
			System.out.println("Error removing index: "+e);
		}
	}
	
	/**
	 * This method updates the course records stored in the .ser file.
	 * When index of course is updated, students registered to the
	 * index will also update.
	 * 
	 * @param course
	 * @param oldIndex
	 * @param newIndex
	 */
	public void updateCourseRecords(Course course, int oldIndex, int newIndex) {
		
		courses = file.updateCourseRecords(course, "update");
		
		for (Student currentStudent: students) {
			for (int registeredCourse=0; registeredCourse<currentStudent.getCourses().size(); registeredCourse++) {
				if(currentStudent.getCourses().get(registeredCourse).getIndexNo() == oldIndex) {
					System.out.println("UPDATEINDEX");			
					//currentStudent.dropCourse(oldIndex);
					currentStudent.updateCourse(currentStudent.getCourses().get(registeredCourse).getCourseCode(), oldIndex, newIndex);
					file.updateStudentRecords(currentStudent, "update");
				}
			}
		}
		
	}

	/**
	 * This method creates hardcoded dummy course data
	 * 
	 */
	public void createDebugCourses() {
		Course c = new Course("CZ2002", "OODP", "SCSE", 3);
		c.setLecDetails(1, 12, 30, 14, 30, "LT19", "OODP", "CS2");
		Index i1 = c.addIndex(120014, 30);
		i1.setTutDetails(2, 12, 0, 14, 0, "A ROOM", "NO REMARKS", "SS1", 3);
		Index i2 = c.addIndex(100012, 30);
		i2.setTutDetails(2, 10, 0, 12, 0, "A ROOM", "NO REMARKS", "SS2", 3);

		Course c1 = new Course("CZ2005", "OS", "SCSE", 3);
		c1.setLecDetails(1, 10, 30, 12, 30, "LT19", "OS", "CS3");
		Index i3 = c1.addIndex(200005, 30);
		i3.setTutDetails(2, 11, 0, 13, 0, "C ROOM", "NO REMARKS", "SP1", 3);
		
		Course c2 = new Course("CZ2001", "ALGO", "SCSE", 3);
		c2.setLecDetails(1, 10, 30, 12, 30, "LT19", "ALGO", "CS4");
		Index i4 = c2.addIndex(200001, 30);
		i4.setTutDetails(2, 10, 0, 12, 0, "A ROOM", "NO REMARKS", "SA1", 3);

		Course c3 = new Course("EE8084", "Cyber Sec", "EE", 3);
		c3.setLecDetails(1, 12, 0, 14, 0, "ONLINE", "lmao", "dank420");
		c3.addIndex(999999, 30);
		
		Course c4 = new Course("CZ1011", "Engineering Mathematics I", "SCSE", 3);
		c4.setLecDetails(1, 12, 0, 14, 0, "ONLINE", "lmao", "dank420");
		Index i5 = c4.addIndex(123456, 30);
		i5.setTutDetails(2, 12, 0, 14, 0, "A ROOM", "NO REMARKS", "SS1", 3);
		Index i6 = c4.addIndex(654321, 30);
		i6.setTutDetails(2, 10, 0, 12, 0, "A ROOM", "NO REMARKS", "SS2", 3);


		Course c5= new Course("CZ1012", "Engineering Mathematics II", "SCSE", 3);
		c5.setLecDetails(1, 13, 0, 14, 0, "ONLINE", "Math is hard", "150");
		Index i7 = c5.addIndex(10100, 30);
		i7.setTutDetails(2, 12, 0, 14, 0, "A ROOM", "NO REMARKS", "SS1", 3);
		i7.setLabDetails(3, 12, 0, 14, 0, "Lab A", "NO REMARKS", "SSP1", 3);
		Index i8 = c5.addIndex(10101, 30);
		i8.setTutDetails(2, 8, 30, 9, 30, "A ROOM", "NO REMARKS", "SS2", 3);
		i8.setLabDetails(3, 16, 30, 18, 30, "Lab B", "NO REMARKS", "SS1", 3);
		
		Course c6= new Course("CZ2009", "Only One Course", "SCSE", 1);
		Index i9 = c6.addIndex(10300, 1);
		i9 .setTutDetails(1, 8, 30, 9, 30, "LONE ROOM", "Used For Email", "SS1", 3);

		Course c7= new Course("CZ9999", "Super final project", "SCSE", 16);
		Index iA = c7.addIndex(10400, 1);
		iA .setLabDetails(1, 16, 30, 18, 30, "Super FYP", "Used For AU", "SSG1", 3);


		file.setCourseRecord(c);
		file.setCourseRecord(c1);
		file.setCourseRecord(c2);
		file.setCourseRecord(c3);
		file.setCourseRecord(c4);
		file.setCourseRecord(c5);
		file.setCourseRecord(c6);
		file.setCourseRecord(c7);

	}

	/*
	 * 
	 * 
	 * LOGIN SECTION
	 * 
	 * 
	 */

	/**
	 * This method gets the existing credentials of users
	 * for validation.
	 * 
	 * @return hmap which contains the key value pairs of users
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

	/**
	 * This method hashes the given password before
	 * storing of user credentials
	 * 
	 * @param passClear refers to the clear text password to be hashed
	 * @return hash in SHA-256
	 */
	String hash(String passClear) {

		String hash = new String();

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedHash = digest.digest(passClear.toString().getBytes(StandardCharsets.UTF_8));

			// Convert byte array into signum representation
			BigInteger number = new BigInteger(1, encodedHash);

			// Convert message digest into hex value
			StringBuilder hexString = new StringBuilder(number.toString(16));

			// Pad with leading zeros
			while (hexString.length() < 32) {
				hexString.insert(0, '0');
			}

			hash = hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hash;

	}

	/**
	 * Creates a dummy admin account
	 * 
	 * @param username
	 * @param password
	 * @deprecated	This method was used to create the admin account.
	 * 				It is not expected to be used otherwise. 
	 * 				Use {@link StarsDB#addStudent(Student)} to add student accounts.
	 */
	public void addAdmin(String username, String password) {
		file.setLoginCredentials(username, hash(password));
	}

}
