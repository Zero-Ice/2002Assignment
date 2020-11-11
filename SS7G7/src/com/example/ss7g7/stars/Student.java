package com.example.ss7g7.stars;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;


public class Student extends User{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 691574352314613452L;
	private String name; 
	private String lastName;
	private String matricNo;
	private String gender;
	private String nationality; 
	private int mobileNo;
	private String email;
	private Calendar accessStart;
	private Calendar accessEnd;
	private ArrayList<RegisteredCourse> courses;
	
	public Student (String username, String password, String name, String lastName, String matricNo, String gender,
					String nationality, int mobileNo, String email, Calendar accessStart, Calendar accessEnd) 
	{
		super(username, password);
		this.name = name;
		this.lastName = lastName;
		this.matricNo	= matricNo;
		this.gender	= gender;
		this.nationality = nationality;
		this.mobileNo = mobileNo;
		this.email = email;
		this.accessStart = accessStart;
		this.accessEnd = accessEnd;
		courses = new ArrayList<RegisteredCourse>();
	}
	
	
	public void setCourses(ArrayList<RegisteredCourse> indexes) {
		this.courses.clear();
		this.courses = indexes;
	}
	
	public boolean addCourse(String courseCode, int indexNo) {
		// Do not allow adding of new course if student already registered for that coursecode
		// If went through the proper procedures and checks through StudentMenu and Index, should not return false here
		if(containsCourse(courseCode)) return false;
		
		courses.add(new RegisteredCourse(courseCode, indexNo));
		
		return true;
	}
	
	public boolean dropCourse(int indexNo) {
		for(int i = 0; i < courses.size(); i++) {
			if(courses.get(i).getIndexNo() == indexNo) {
				courses.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean containsCourse(int indexNo) {
		for(RegisteredCourse rc : courses) {
			if(rc.getIndexNo() == indexNo) return true;
		}
		
		return false;
	}
	
	public boolean containsCourse(String courseCode) {
		for(RegisteredCourse rc : courses) {
			if(rc.getCourseCode().equals(courseCode)) return true;
		}
		
		return false;
	}
	
	public String printCourses() {
		String s = "";
		
		if(courses.size() == 0) {
			s += "No courses registered";
			return s;
		}
		
		for(int i = 0; i < courses.size(); i++) {
			s += courses.get(i).getCourseCode() + " " + courses.get(i).getIndexNo();
			if(i == courses.size() - 1) break;
			
			s += ", ";
		}
		return s;
	}
	
	public boolean willNewCourseClashTimetable(Course newCourse, Index newIndex) {
		
		for(RegisteredCourse rc : courses) 
		{
			Course c = StarsDB.getInstance().getCourseByIndex(rc.getIndexNo());
			Index index = c.getIndex(rc.getIndexNo());
			if(willCourseClashWithOtherCourse(c, index, newCourse, newIndex)) return true;
		}
		
		return false;
	}
	
	public boolean willSwappedCourseClashTimetable(Course newCourse, Index newIndex, Course excludeCourse) {
		ArrayList<RegisteredCourse> courseCopy = (ArrayList)courses.clone();
		
		for(int i = 0; i < courseCopy.size(); i++) {
			if(courseCopy.get(i).getCourseCode().equals(excludeCourse.getCourseCode())) {
				courseCopy.remove(i);
				break;
			}
		}
		
		for(RegisteredCourse rc : courseCopy) 
		{
			Course c = StarsDB.getInstance().getCourseByIndex(rc.getIndexNo());
			Index index = c.getIndex(rc.getIndexNo());
			if(willCourseClashWithOtherCourse(c, index, newCourse, newIndex)) return true;
		}
		
		return false;
	}
	
	private boolean willCourseClashWithOtherCourse(Course course, Index index, Course otherCourse, Index otherIndex) {
		// Check lec clash with lec
		// Check lec clash with tut
		// Check lec clash with lab
		
		// Check tut clash with lec
		// Check tut clash with tut
		// Check tut clash with lab
		
		// Check lab clash with lec
		// Check lab clash with tut
		// Check lab clash with lab
		if(
				StarsUtil.timingWillClash(course.getLecStartTime(), course.getLecEndTime(), 3, otherCourse.getLecStartTime(), otherCourse.getLecEndTime(), 3)
				|| StarsUtil.timingWillClash(course.getLecStartTime(), course.getLecEndTime(), 3, otherIndex.getTutStartTime(), otherIndex.getTutEndTime(), otherIndex.getTutOccurring())
				|| StarsUtil.timingWillClash(course.getLecStartTime(), course.getLecEndTime(), 3, otherIndex.getLabStartTime(), otherIndex.getLabEndTime(), otherIndex.getLabOccurring())
				// 
				|| StarsUtil.timingWillClash(index.getTutStartTime(), index.getTutEndTime(), index.getTutOccurring(), otherCourse.getLecStartTime(), otherCourse.getLecEndTime(), 3)
				|| StarsUtil.timingWillClash(index.getTutStartTime(), index.getTutEndTime(), index.getTutOccurring(), otherIndex.getTutStartTime(), otherIndex.getTutEndTime(), otherIndex.getTutOccurring())
				|| StarsUtil.timingWillClash(index.getTutStartTime(), index.getTutEndTime(), index.getTutOccurring(), otherIndex.getLabStartTime(), otherIndex.getLabEndTime(), otherIndex.getLabOccurring())
				//
				|| StarsUtil.timingWillClash(index.getLabStartTime(), index.getLabEndTime(), index.getLabOccurring(), otherCourse.getLecStartTime(), otherCourse.getLecEndTime(), 3)
				|| StarsUtil.timingWillClash(index.getLabStartTime(), index.getLabEndTime(), index.getLabOccurring(), otherIndex.getTutStartTime(), otherIndex.getTutEndTime(), otherIndex.getTutOccurring())
				|| StarsUtil.timingWillClash(index.getLabStartTime(), index.getLabEndTime(), index.getLabOccurring(), otherIndex.getLabStartTime(), otherIndex.getLabEndTime(), otherIndex.getLabOccurring())
				)
		{
			return true;
		}
		
		return false;
	}
	
	// Getters & setters
	
	public ArrayList<RegisteredCourse> getCourses() {
		return courses;
	}
	
	@Override
	public String toString() {
		String s = "Name: " + name + "\n"
				+ "Matriculation Number: " + matricNo + "\n"
				+ "Username: " + username + "\n"
				+ "Gender: " + gender + "\n"
				+ "Nationality: " + nationality + "\n"
				+ printCourses();
		return s;
	}
	

	public String getUserName() {
		return username;
	}

	public void getUserName(String username) {
		this.username = username;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMatricNo() {
		return matricNo;
	}

	public void setMatricNo(String matricNo) {
		this.matricNo = matricNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public int getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(int mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getAccessStart() {
		return accessStart;
	}

	public void setAccessStart(Calendar accessStart) {
		this.accessStart = accessStart;
	}

	public Calendar getAccessEnd() {
		return accessEnd;
	}

	public void setAccessEnd(Calendar accessEnd) {
		this.accessEnd = accessEnd;
	}
}
