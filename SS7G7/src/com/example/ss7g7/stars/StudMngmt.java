package hihi;

import java.util.ArrayList;
import java.util.Calendar;


//This class is used by AdminStudUI to check against the database.

public class StudMngmt {
	static ArrayList<Student> studentList = Database.studentList; // student Arraylist from database
	
	//find a student via their matriculation number 
	public static Student getStudentByMatric(String matNum){ 
		for (Student student : studentList) {
			if (student.matricNo().equals(matNum)) {
				return student;}
		}
		return null;
	}

	//For admin to edit a student access period
	public static void updateAccessPeriod(String matNum, Calendar newAccessStart, Calendar newAccessEnd){ 
		Student student = getStudentByMatric(matNum);
		student.setAccessStart(newAccessStart);
		student.setAccessEnd(newAccessEnd);
	}
	
	//check existing username in student database
	public static Boolean isExistingUsername(String username){ 
		for (Student s : studentList) {
			if (s.getUserName().equals(username)) {
				System.out.println("This username has already been used, please try again.");
				return false;
			}
		}
		return true;
	}

	//check student via their matriculation number 
	public static Boolean isExistingMatNum(String matricNo){ 
		for (Student s : studentList) {
			if (s.matricNo().equals(matricNo)) {
				System.out.println("Matriculation number is found in database.");
				return false;
			}
		}
		return true;
	}
	
	// add new student to studentlist in database
	public static void addStudent(String username, String password,String firstName, String lastName,
			String matNum, String gender, String nationality, int mobileNo, String email, Calendar accessStart, Calendar accessEnd) 
	
	{
        Student newStud = new Student(username, password, firstName, lastName, matNum, gender, nationality, mobileNo, email, accessStart, accessEnd);
        studentList.add(newStud);
	}

	//remove a student from studentlist in database 
	public static void removeStudent(String matNum) {
		Student student = getStudentByMatric(matNum);
		studentList.remove(student);
		

		}
	
	
	

}
