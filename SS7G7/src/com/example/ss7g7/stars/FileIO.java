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
	
	public void setCourseRecord (String path, Course c) {
		
		FileOutputStream fs = null;
		ObjectOutputStream os = null;
		
		
		try{
			
			courses.add(c);
			
			fs = new FileOutputStream(path);
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
	
	public void setStudentRecord (String path, Student s) {
		
		FileOutputStream fs = null;
		ObjectOutputStream os = null;
		
		
		try{
			
			students.add(s);
			
			fs = new FileOutputStream(path);
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
	
	public ArrayList<Student> getStudentRecords (String path) {
		
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
}
  
	
	

