package com.example.ss7g7.stars;

import java.util.List;


public class AdminCourseMngmt {
	static List<Course> courseList; //= StarsDB.getAllStudents(); calling from starsDB directly.
	

	public static boolean isExistingCourseCode(String courseCode) {
		for (Course c : courseList) {
			if (c.getCourseCode().equals(courseCode)) {
				return true;}
		}
		return false;
	}
	
	public static Course getCourseByCode (String courseCode){
		for (Course c : courseList) {
			if (c.getCourseCode().equals(courseCode)) {
				return c;}
		}
		return null;
	}

	public static void removeCourse(String courseCode) {
		
		if (isExistingCourseCode(courseCode)){
			Course course = getCourseByCode(courseCode);

			courseList.remove(course);
			System.out.println("Course " + course.getCourseName() + " (" + courseCode + ") has been removed!");
		}
		else{
			System.out.println("Course code is not found!\n");
		}
		
	}

	public static void addCourse(String courseCode, String courseName, String SchooName, int aU) {
		Course newCourse 		= new Course(courseCode, courseName,SchooName, aU);
		courseList.add(newCourse);
		System.out.println();
		
	}


}
