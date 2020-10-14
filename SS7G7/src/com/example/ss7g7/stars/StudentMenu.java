package com.example.ss7g7.stars;

import java.util.Scanner;

public class StudentMenu {
	private Student student;
	
	public StudentMenu(Student student) {
		this.student = student;
	}
	
	public void run() {
		boolean run = true;
		int choice;
		
		Scanner scanner = new Scanner(System.in);
		
		while(run) {
			System.out.println("Welcome " + student.getName() + " " + student.getUsername());
			System.out.println("Enter your choice");
			System.out.println("(1) Add Course");
			System.out.println("(2) Drop Course");
			System.out.println("(3) Print Course Registered");
			System.out.println("(4) Check Vacancies Available");
			System.out.println("(5) Change Index No.");
			System.out.println("(6) Swap Index Number with Another Student");
			System.out.println("(7) to logout");
			choice = Integer.valueOf(scanner.nextLine());
			
			switch(choice) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				System.out.println("See you again");
				run = false;
				break;
			}
		}
	}
	
	private void addCourse() {
		
	}
	
	private void dropCourse() {
		
	}
	
	private void printCoursesRegistered() {
		
	}
	
	private void checkVacanciesAvailable() {
		
	}
	
	private void changeIndexNo(String otherStudent) {
		
	}
}
