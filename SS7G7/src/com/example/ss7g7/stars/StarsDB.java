package com.example.ss7g7.stars;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class StarsDB {
	private String studentDataFilePath;
	private String courseDataFilePath;
	
	private ArrayList<Student> students;
	private ArrayList<Course> courses;
	
	
	public StarsDB(String studentDataFilePath, String courseDataFilePath) {
		this.studentDataFilePath = studentDataFilePath;
		this.courseDataFilePath = courseDataFilePath;
		students = new ArrayList<Student>();
		courses = new ArrayList<Course>();
	}
	
	/*
	 * Loads data from the files using the file paths given
	 */
	public boolean init() {
		// TODO: File I/O
		
		createDebugCourses();
		
		return true;
	}
	
	public ArrayList<Student> getAllStudents() {
		return students;
	}
	
	public ArrayList<Course> getAllCourse() {
		return courses;
	}
	
	public Student getStudent(String userName) {
		for(int i = 0; i < students.size(); i++) {
			if(userName == students.get(i).getUserName()) {
				return students.get(i);
			}
		}
		return null;
	}
	
	public Student getDebugStudent() {
		//purpose of accessStart/End is for student to only enter the stars planner at certain time periodd
		Calendar newDate1 = Calendar.getInstance(); //create a date to accessStart 
		Calendar newDate2 = Calendar.getInstance(); // create a date for accessEnd
		Student s = new Student("Dum123","Dum dum", " Tan", "U1969420", "M", "Antartica, ", 96549119, "dimdim@hotmai.com",newDate1, newDate2);
		return s;
	}
	
	// TODO: courses
	
	public void createDebugCourses() {
		Course c = new Course("CZ2002", "OODP", "SCSE",3);
		c.addIndex(123456, 30);
		c.addIndex(696969, 30);
		courses.add(c);
		
		c = new Course("CZ2005", "OS", "SCSE",3);
		c.addIndex(200005, 30);
		courses.add(c);
	}
	
	public Course getCourse(int indexNo) {
		for(int i = 0; i < courses.size(); i++) {
			if(courses.get(i).containsIndexNo(indexNo)) return courses.get(i);
		}
		
		return null;
	}
	
	public HashMap<String, String> getDBLoginCred () {
		
		List [] credentials = new List [3];
		List<String> username = new ArrayList<String>();
        List<String> pass = new ArrayList<String>();
        List<Boolean> admin = new ArrayList<Boolean>();
        HashMap<String, String> hmap = new HashMap<String, String>();
       
        
		CSVReader run = new CSVReader();
		
		if (run.readFile()!=null) {
			credentials = run.readFile();
			username = credentials[0];
			pass = credentials[1];
			admin = credentials[2];
			
		}else {
			System.out.println("Error reading file");
		}		
		
		
		for (int i=0; i< username.size(); i++) {
			hmap.put(username.get(i), pass.get(i));
		}
		
	
		return hmap;
				
	}
}
