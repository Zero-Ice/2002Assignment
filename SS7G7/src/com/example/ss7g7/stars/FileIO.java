package com.example.ss7g7.stars;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class FileIO {
	
	private ArrayList<Student> students = new ArrayList<Student>();
	private ArrayList<Course> courses = new ArrayList<Course>();
	private ArrayList<Index> indexes = new ArrayList<Index>();
	
	private String studentDataFilePath = "";
	private String courseDataFilePath = "";
	
	public List [] getLoginCredentials () {
		
		String path = "../SS7G7/lib/logincred.txt";
        String line = new String();
        String splitBy = ",";
        String [] temp = {};
        List<String> username = new ArrayList<String>();
        List<String> pass = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        	
            while ((line = br.readLine()) != null) {
            	
                temp = line.split(splitBy);
                username.add(temp[0]);
                pass.add(temp[1]);
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new List[] {username, pass};
	}
	
	
	public void setStudentRecord (Student s) {
				
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
		
		    
	}
	
	public void setCourseRecord (Course c) {
		
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
		
	}
	
	
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
	

	
	public void updateStudentRecords (Student currentStudent, String mode) {
		
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
	}
	
	public void updateCourseRecords (Course currentCourse, String mode) {
		
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
	}
	
	
	public void removeCourse() {
		
	}
	

}
  
	
	

