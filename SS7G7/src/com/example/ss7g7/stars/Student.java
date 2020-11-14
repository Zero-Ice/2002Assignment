package com.example.ss7g7.stars;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
* <h1>Student Class</h1>
* The Student class is a subclass of User
* It stores the details of a student. 
* 
* <p>
* Provides getters and setters to certain variables.
* Print functions to output data.
* Functions to add and remove courses.
* Functions to check if the new course from swapping or
* adding will clash with student's timetable.
* 
*
* @author  Ong Rui Peng
* @since   2020-10-15
*/
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
	
	/**
	 * Constructor for Student Class
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
	
	
	/**
	 * Sets the courses a student has registered
	 * 
	 * @param indexes
	 */
	public void setCourses(ArrayList<RegisteredCourse> indexes) {
		this.courses.clear();
		this.courses = indexes;
	}
	
	/**
	 * This method adds a course to the student
	 * Returns false if the student already registered to that course
	 * true otherwise
	 * 
	 * @param courseCode
	 * @param indexNo
	 * @return boolean
	 */
	public boolean addCourse(String courseCode, int indexNo) {
		// Do not allow adding of new course if student already registered for that coursecode
		// If went through the proper procedures and checks through StudentMenu and Index, should not return false here
		if(containsCourse(courseCode)) return false;
		
		courses.add(new RegisteredCourse(courseCode, indexNo));
		
		return true;
	}
	
	/**
	 * This method drops the course registered to the student
	 * Returns true if the course is registered to the student
	 * false if the failed to drop course (not found)
	 * 
	 * @param indexNo
	 * @return boolean
	 */
	public boolean dropCourse(int indexNo) {
		for(int i = 0; i < courses.size(); i++) {
			if(courses.get(i).getIndexNo() == indexNo) {
				courses.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method returns a boolean result if the student
	 * has registered a course with an index number.
	 * 
	 * @param indexNo
	 * @return boolean
	 */
	public boolean containsCourse(int indexNo) {
		for(RegisteredCourse rc : courses) {
			if(rc.getIndexNo() == indexNo) return true;
		}
		
		return false;
	}
	
	/**
	 * This method returns a boolean result if the student
	 * has registered a course with a course code
	 * 
	 * @param indexNo
	 * @return boolean
	 */
	public boolean containsCourse(String courseCode) {
		for(RegisteredCourse rc : courses) {
			if(rc.getCourseCode().equals(courseCode)) return true;
		}
		
		return false;
	}
	
	/**
	 * This method prints the courses registered for a student
	 * The format is
	 * No courses registered
	 * <no>
	 * <courseCode> <indexNo>, <courseCode> <indexNo>, ..., <courseCode> <indexNo>
	 * 
	 * @return String
	 */
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
	
	/**
	 * This method returns a boolean result if a new course added will
	 * clash with the student's timetable
	 * 
	 * @param newCourse
	 * @param newIndex
	 * @return boolean
	 */
	public boolean willNewCourseClashTimetable(Course newCourse, Index newIndex) {
		
		for(RegisteredCourse rc : courses) 
		{
			Course c = StarsDB.getInstance().getCourseByIndex(rc.getIndexNo());
			Index index = c.getIndex(rc.getIndexNo());
			if(willCourseClashWithOtherCourse(c, index, newCourse, newIndex)) return true;
		}
		
		return false;
	}
	
	/**
	 * This method returns a boolean result whether swapping a course
	 * with another student will clash with the student's timetable.
	 * Checks if the new course will clash with any of the student's 
	 * registered courses. 
	 * Returns true if will clash, false otherwise
	 * 
	 * @param newCourse
	 * @param newIndex
	 * @param excludeCourse
	 * @return boolean
	 */
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
	
	/**
	 * This method checks if a course will clash with another course
	 * Checks each lecture, tutorial and lab timings
	 * Returns true if even a single timing clashes,
	 * false otherwise(none of the classes/timings clash)
	 * 
	 * @param course
	 * @param index
	 * @param otherCourse
	 * @param otherIndex
	 * @return boolean
	 */
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
	
	/**
	 * Override java toString function
	 * Format
	 * Name: <name>
	 * Matriculation Number: <matricNo>
	 * Username: <username>
	 * Gender: <gender>
	 * Nationality: <nationality>
	 * <courses> (refer to printCourses)
	 * 
	 * @return String
	 */
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
	
	
	// Getters & setters
	
	/**
	 * Getter function for courses
	 * 
	 * @return ArrayList<RegisteredCourse>
	 */
	public ArrayList<RegisteredCourse> getCourses() {
		return courses;
	}
	

	/**
	 * Getter function for username
	 * @return String 
	 */
	public String getUserName() {
		return username;
	}

	/**
	 * Set method for username
	 * 
	 * @param username
	 */
	public void getUserName(String username) {
		this.username = username;
	}


	/**
	 * Get method for name
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set method for name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** 
	 * Get method for last name
	 * 
	 * @return String
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set method for last name
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get method for matriculation number
	 * 
	 * @return String
	 */
	public String getMatricNo() {
		return matricNo;
	}

	/**
	 * Set method for matriculation number
	 * 
	 * @param matricNo
	 */
	public void setMatricNo(String matricNo) {
		this.matricNo = matricNo;
	}

	/**
	 * Get method for gender
	 * 
	 * @return String
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Set method for gender
	 * 
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Get method for nationality
	 * 
	 * @return String
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * Set method for nationality
	 * 
	 * @param nationality
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * Get method for mobile number
	 * 
	 * @return int
	 */
	public int getMobileNo() {
		return mobileNo;
	}

	/**
	 * Set method for mobile number
	 * 
	 * @param mobileNo
	 */
	public void setMobileNo(int mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Get method for email
	 * 
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set method for email
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get method for access start
	 * 
	 * @return Calendar
	 */
	public Calendar getAccessStart() {
		return accessStart;
	}

	/**
	 * Set method for access start
	 * 
	 * @param accessStart
	 */
	public void setAccessStart(Calendar accessStart) {
		this.accessStart = accessStart;
	}

	/**
	 * Get method for access end
	 * 
	 * @return Calendar
	 */
	public Calendar getAccessEnd() {
		return accessEnd;
	}

	/**
	 * Set method for access end
	 * 
	 * @param accessEnd
	 */
	public void setAccessEnd(Calendar accessEnd) {
		this.accessEnd = accessEnd;
	}
}
