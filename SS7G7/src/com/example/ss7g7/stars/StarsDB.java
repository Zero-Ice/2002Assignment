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
		if(db_instance == null) db_instance = new StarsDB();
		
		return db_instance;
	}

	/*
	 * Loads data from the files using the file paths given
	 */
	public boolean init(String studentDataFilePath, String courseDataFilePath) {
		// TODO: File I/O
		this.studentDataFilePath = studentDataFilePath;
		this.courseDataFilePath = courseDataFilePath;

		this.students = file.getStudentRecords(studentDataFilePath);

		if (this.students.isEmpty()) {
			System.out.print("Get failed");
		} else {
			for (Student s : this.students) {
				System.out.println(s.getName() + " " + s.getUserName());
			}
		}
		createDebugCourses();

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
		List<Boolean> admin = new ArrayList<Boolean>();
		HashMap<String, String> hmap = new HashMap<String, String>();

		if (file.getLoginCredentials() != null) {
			credentials = file.getLoginCredentials();
			username = credentials[0];
			pass = credentials[1];
			admin = credentials[2];

		} else {
			System.out.println("Error reading file");
		}

		for (int i = 0; i < username.size(); i++) {
			hmap.put(username.get(i), pass.get(i));
		}

		return hmap;

	}

	public Student getStudent(String userName) {
		for (int i = 0; i < students.size(); i++) {
			if (userName.equals(students.get(i).getUserName())) {
				return students.get(i);
			}
		}
		return null;
	}

	public Student getDebugStudent(User currentUser) {

		for (Student s : this.students) {
			if (s.getUserName().equals(currentUser.getUser())) {
				return s;
			}
		}

		if (this.students.isEmpty()) {
			System.out.println("Student file is empty\n Populating file...");
			createDummyStudents();
		}

		// System.out.println("User not found");
		return null;
	}

	public void setStudentRecord(Student s) {

	}

	public void createDummyStudents() {

		// purpose of accessStart/End is for student to only enter the stars planner at
		// certain time period
		Calendar newDate1 = Calendar.getInstance(); // create a date to accessStart
		Calendar newDate2 = Calendar.getInstance(); // create a date for accessEnd

		Student x = new Student("student1", "Mark", " Tan", "U1969420", "M", "Antartica", 96549119, "dimdim@hotmai.com",
				newDate1, newDate2);
		file.setStudentRecord(studentDataFilePath, x);
		Student y = new Student("student2", "Laura", " Tan", "U1969420", "F", "Spain", 96549119, "dimdim@hotmai.com",
				newDate1, newDate2);
		file.setStudentRecord(studentDataFilePath, y);

		this.students = file.getStudentRecords(studentDataFilePath);

	}

	// TODO: courses

	public void createDebugCourses() {
		Course c = new Course("CZ2002", "OODP", "SCSE", 3);
		c.addIndex(123456, 30);
		c.addIndex(696969, 30);
		c.setLecDetails(1, 12, 30, 14, 30, "LT19", "OODP", "CS2");
		courses.add(c);

		c = new Course("CZ2005", "OS", "SCSE", 3);
		c.addIndex(200005, 30);
		c.setLecDetails(1, 12, 30, 14, 30, "LT19", "OS", "CS3");
		courses.add(c);
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

	public void setCourseRecord() {

	}

}
