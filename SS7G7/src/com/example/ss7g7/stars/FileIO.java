package com.example.ss7g7.stars;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;


/**
 * 
 * The FileIO class handles all
 * serialization operations to *.ser files
 *
 * @author Angelina
 * created on 2020/10/15
 * 
 * @version %I%
 * @since 1.0
 * 
 */

public class FileIO {
	
	private ArrayList<Student> students = new ArrayList<Student>();
	private ArrayList<Course> courses = new ArrayList<Course>();
	private ArrayList<Index> indexes = new ArrayList<Index>();
	
	private String loginCredentialsFilePath = "../SS7G7/lib/loginCred.ser";
	private String studentDataFilePath = "";
	private String courseDataFilePath = "";
	
	/**
	 * This method gets all user credentials that
	 * are saved in the loginCred.ser file
	 * 
	 * @return ArrayList of <code>username</code> and <code>password</code>
	 */
	public List [] getLoginCredentials () {
		
        String line = new String();
        String splitBy = ",";
        String[] temp = {};
        List<String> username = new ArrayList<String>();
        List<String> pass = new ArrayList<String>();
            
        
        FileInputStream fs = null;
		ObjectInputStream os = null;
		boolean read = true;
		
		try {
			fs = new FileInputStream(loginCredentialsFilePath);
			os = new ObjectInputStream(fs);
		
			
			while(read) {
				line =  (String) os.readObject();
								
				temp = line.split(splitBy);
				username.add(temp[0]);
				pass.add(temp[1]);
								 
				os = new ObjectInputStream(fs);
		}
			
		}
		catch (EOFException e) {
			read = false;
		}
		catch (IOException | ClassNotFoundException  e) {
			//TODO Auto-generated catch block
			System.out.println("Failed to read credentials");
			e.printStackTrace();
			
		}
		finally {
			try {
  				if (fs != null && os !=null) {
  					fs.close();
  					os.close();
  				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File failed to close");
				e.printStackTrace();
			}
		}
		
		return new List[] {username, pass};
	}
	
	/**
	 * This method is used to store login credentials in
	 * the loginCred.ser file
	 * 
	 * @param username
	 * @param cipherPass must be stored in hash
	 */
	public void setLoginCredentials(String username, String cipherPass) {
		
		//To save credentials in loginCred.ser with pass in hash
		FileOutputStream fs = null;
		ObjectOutputStream os = null;
		
		
		try{
			fs = new FileOutputStream(loginCredentialsFilePath, true);
			os = new ObjectOutputStream(fs);
			
			os.writeObject(username+","+cipherPass+"-student");
			
			
		}catch (IOException e){
			e.printStackTrace();
			
		}finally {
			
			try {
				if(fs!=null && os!=null) {
					fs.close();
					os.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * This method is to remove student credentials from
	 * loginCred.ser when student is removed
	 * 
	 * @param username
	 * @param cipherPass
	 *
	 */
	public void removeLoginCredentials(String username, String cipherPass) {
		
		String line = new String();
        ArrayList<String> credentials = new ArrayList<String>();
        
        FileInputStream fsIn = null;
		ObjectInputStream osIn = null;
		FileOutputStream fsOut = null;
		ObjectOutputStream osOut = null;
	
		
		boolean read = true;
		
		try {
			fsIn = new FileInputStream(loginCredentialsFilePath);
			osIn = new ObjectInputStream(fsIn);
		
			
			while(read) {
				line =  (String) osIn.readObject();
				credentials.add(line);
				
				osIn = new ObjectInputStream(fsIn);
			}
			
		}
		catch (EOFException e) {
			read = false;
			
			for (String user : credentials) {
				System.out.println(user);
				
				if (user.equals(username+","+cipherPass+"-student")) {
					credentials.remove(user);
					System.out.println("Removed from credentials");
					continue;
				}
			}
			
			try {
				
				fsOut = new FileOutputStream(loginCredentialsFilePath);
				osOut = new ObjectOutputStream(fsOut);
				
				
				for (String updatedUser: credentials) {
					
					osOut.writeObject(updatedUser);
					
					//If not the last object, new outputstream
					if (!(updatedUser.equals(credentials.get(credentials.size()-1).toString())))
						osOut = new ObjectOutputStream(fsOut);
				}
				
			}catch (IOException e1){
				
				System.out.println("Failed to update credentials");
				e1.printStackTrace();
				
			}	
		}
		catch (IOException | ClassNotFoundException  e2) {
			//TODO Auto-generated catch block
			System.out.println("Failed to read credentials");
			e2.printStackTrace();
			
		}
		finally {
			try {
  				if (fsIn != null && osIn !=null && fsOut != null && osOut !=null ) {
  					fsIn.close();
  					osIn.close();
  					fsOut.close();
  					osOut.close();
  				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File failed to close");
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	/**
	 * This method is to store a Student object in
	 * the studentInfo.ser file
	 * 
	 * @param s refers to a Student object
	 */
	public ArrayList<Student> setStudentRecord (Student s) {
				
		FileOutputStream fs = null;
		ObjectOutputStream os = null;
		
		
		try{
			
			students.add(s);
			
			fs = new FileOutputStream(studentDataFilePath);
			os = new ObjectOutputStream(fs);
			
			os.writeObject(students);
			
		}catch (IOException e){
			e.printStackTrace();
			
		}finally {
			
			try {
				if(fs!=null && os!=null) {
					fs.close();
					os.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		return this.students;    
	}
	
	/**
	 * This method is to store a Course object in
	 * the courseInfo.ser file
	 * 
	 * @param c refers to a Course object
	 * @return courses to update the current instance
	 * of the ArrayList <code>courses</code>
	 */
	public ArrayList<Course> setCourseRecord (Course c) {
		
		FileOutputStream fs = null;
		ObjectOutputStream os = null;
		
		
		try{
			
			courses.add(c);
			
			fs = new FileOutputStream(courseDataFilePath);
			os = new ObjectOutputStream(fs);
			
			os.writeObject(courses);
			
		}catch (IOException e){
			e.printStackTrace();
			
		}finally {
			
			try {
				if(fs!=null && os!=null) {
					fs.close();
					os.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return courses;
		
	}
	
	
	/**
	 * This method gets all Student objects stored
	 * in the studentInfo.ser file
	 * 
	 * @param path is the path to the file
	 * @return ArrayList of <code>students</code>
	 */
	public ArrayList<Student> getStudentRecords (String path) {
		
		this.studentDataFilePath = path;
		
		FileInputStream fs = null;
		ObjectInputStream os = null;
		
		try {
			fs = new FileInputStream(path);
			os = new ObjectInputStream(fs);
			
			this.students = ((ArrayList<Student>) os.readObject());
			
			for(Student s: students) {
				System.out.println(s);
			}
		}catch (IOException | ClassNotFoundException e) {
			//TODO Auto-generated catch block
			System.out.println("Failed to read file");
			e.printStackTrace();
			
		}finally {
			try {
  				if (fs != null && os !=null) {
  					fs.close();
  					os.close();
  				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File failed to close");
				e.printStackTrace();
			}
		}
		
		return this.students;
			
			
	}
	
	/**
	 * This method gets all Course objects stored
	 * in the courseInfo.ser file
	 * 
	 * @param path is the path to the file
	 * @return ArrayList of <code>courses</code>
	 */
	public ArrayList<Course> getCourseRecords (String path) {
		
		this.courseDataFilePath = path;
		
		FileInputStream fs = null;
		ObjectInputStream os = null;
		
		try {
			fs = new FileInputStream(path);
			os = new ObjectInputStream(fs);
			
			this.courses = ((ArrayList<Course>) os.readObject());
			
			for(Course c: courses) {
				System.out.println(c);
			}
		}catch (IOException | ClassNotFoundException e) {
			//TODO Auto-generated catch block
			System.out.println("Failed to read file");
			e.printStackTrace();
			
		}finally {
			try {
  				if (fs != null && os !=null) {
  					fs.close();
  					os.close();
  				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File failed to close");
				e.printStackTrace();
			}
		}
		
		return this.courses;
		
	}
	

	
	/**
	 * This method deletes or updates student records
	 * according to the parameter <code>mode</code>.
	 * 
	 * @param currentStudent refers to the Student object to be removed/updated
	 * @param mode can either be "remove" or "update"
	 */
	public ArrayList<Student> updateStudentRecords (Student currentStudent, String mode) {
		
		FileInputStream fsIn = null;
		ObjectInputStream osIn = null;
		FileOutputStream fsOut = null;
		ObjectOutputStream osOut = null;
				
		try {
			fsIn = new FileInputStream(studentDataFilePath);
			osIn = new ObjectInputStream(fsIn);
			
			this.students = ((ArrayList<Student>) osIn.readObject());
			
			
			//Removes outdated info and update ser file
			for(Student s: students) {
				if (s.getUserName().equals(currentStudent.getUserName())) {
					if(mode.equals("update")) {
						students.remove(s);
						students.add(currentStudent);
					}else if(mode.equals("remove"))
						students.remove(s);
					
					break;
				}
			}
			
		
			fsOut = new FileOutputStream(studentDataFilePath);
			osOut = new ObjectOutputStream(fsOut);
			
			osOut.writeObject(students);
			
			
		}catch (IOException | ClassNotFoundException e) {
			//TODO Auto-generated catch block
			System.out.println("Failed to update file");
			e.printStackTrace();
			
		}finally {
			
			try {
  				if (fsIn != null && osIn !=null && fsOut != null && osOut !=null) {
  					fsIn.close();
  					osIn.close();
  					fsOut.close();
  					osOut.close();
  				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File failed to close");
				e.printStackTrace();
			}
		}
		
		return this.students;
	}
	
	/**
	 * This method deletes or updates course records
	 * according to the parameter <code>mode</code>.
	 * 
	 * @param currentCourse refers to the Course object to be removed/updated
	 * @param mode can either be "remove" or "update"
	 */
	public ArrayList<Course> updateCourseRecords (Course currentCourse, String mode) {
		
		FileInputStream fsIn = null;
		ObjectInputStream osIn = null;
		FileOutputStream fsOut = null;
		ObjectOutputStream osOut = null;
				
		try {
			fsIn = new FileInputStream(courseDataFilePath);
			osIn = new ObjectInputStream(fsIn);
			
			this.courses = ((ArrayList<Course>) osIn.readObject());
			
			
			//Removes outdated info and update ser file
			for(Course c: courses) {
				if (c.getCourseCode().equals(currentCourse.getCourseCode())) {
					
					if (mode.equals("update")) {
						courses.remove(c);
						courses.add(currentCourse);
						
					}else if (mode.equals("remove"))
						courses.remove(c);
					
					break;
				}
			}
			
		
			fsOut = new FileOutputStream(courseDataFilePath);
			osOut = new ObjectOutputStream(fsOut);
			
			osOut.writeObject(courses);
			
			
		}catch (IOException | ClassNotFoundException e) {
			//TODO Auto-generated catch block
			System.out.println("Failed to update file");
			e.printStackTrace();
			
		}finally {
			
			try {
  				if (fsIn != null && osIn !=null && fsOut != null && osOut !=null) {
  					fsIn.close();
  					osIn.close();
  					fsOut.close();
  					osOut.close();
  				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File failed to close");
				e.printStackTrace();
			}
		}	
		
		return this.courses;
	}
	
	

	

}
  
	
	

