package com.example.ss7g7.stars;

import java.util.Scanner;

public class StudentMenu {
	private Student student;
	Scanner scanner;
	
	public StudentMenu(Student student) {
		this.student = student;
		scanner = new Scanner(System.in);
	}
	
	public void run() {
		boolean run = true;
		int choice;
		
		while(run) {
			System.out.println("Welcome " + student.getName() + " " + student.getUsername());
			System.out.println("Enter your choice");
			System.out.println("(1) To view your details");
			System.out.println("(2) Add Course");
			System.out.println("(3) Drop Course");
			System.out.println("(4) Print Course Registered");
			System.out.println("(5) Check Vacancies Available");
			System.out.println("(6) Change Index No.");
			System.out.println("(7) Swap Index Number with Another Student");
			System.out.println("(8) to logout");
			choice = Integer.valueOf(scanner.nextLine());
			
			switch(choice) {
			case 1:
				System.out.println(student);
				break;
			case 2:
				addMod();
				break;
			case 3:
				dropMod();
				break;
			case 4:
				printCoursesRegistered();
				break;
			case 5:
				checkVacanciesAvailable();
				break;
			case 6:
				changeIndex();
				break;
			case 7:
				swapIndex();
				break;
			case 8:
				System.out.println("See you again");
				run = false;
				break;
			}
		}
	}
	
	private void addMod() {
		System.out.print("Enter an index: ");
		int modIndexToAdd = Integer.valueOf(scanner.nextLine());
		
		boolean studContainMod = false;
		boolean result = false;
		if(student.ContainsMod(modIndexToAdd)) {
			studContainMod = true;
		} else {
			result = student.AddMod(modIndexToAdd);
		}
		
		if(result) {
			System.out.println("Successfully added mod with index " + modIndexToAdd);
		} else {
			if(studContainMod) {
				System.out.println("Cannot add mod with index " + modIndexToAdd + " as it is already registered");
			} else {
				System.out.println("Failed to add mod with index " + modIndexToAdd);
			}
		}
		
		// TODO: Update course vacancy
	}
	
	private void dropMod() {
		System.out.println(student.printMods());
		System.out.println("Enter the index to drop: ");
		
		int modIndexToRemove = Integer.valueOf(scanner.nextLine());
		
		boolean result = false;
		boolean studContainMod = true;
		if(!student.ContainsMod(modIndexToRemove)) {
			studContainMod = false;
		} else {
			result = student.RemoveMod(modIndexToRemove);
		}
		
		if(result) {
			System.out.println("Successfully removed mod " + modIndexToRemove);
		} else {
			if(studContainMod) {
				System.out.println("Failed to remove " + modIndexToRemove);
			} else {
				System.out.println("Cannot find mod with index " + modIndexToRemove + " in registered mods");
			}
		}
	}
	
	private void printCoursesRegistered() {
		System.out.println(student.printMods());
	}
	
	private void checkVacanciesAvailable() {
		System.out.print("Enter module code: ");
		String modCode = scanner.nextLine();
		
		// TODO: For each index in mod code, print it.
		
	}
	
	private void changeIndex() {
		System.out.println(student.printMods());
		System.out.print("Enter the index that you want to change from: ");
		int indexFrom = Integer.valueOf(scanner.nextLine());
		
		if(!student.ContainsMod(indexFrom)) {
			System.out.println("Index " + indexFrom + " is not found in your registered mods");
			return;
		}
		
		System.out.print("Enter index you want to change to: ");
		int indexTo = Integer.valueOf(scanner.nextLine());
		
		// TODO: Check indexFrom and indexTo belongs to the same course code.
		
		// TODO: Remove this temp print
		System.out.println("Change Index End");
	}
	
	private void swapIndex() {
		// TODO: Check if same module. If same module, swap index (Remove + Add for both)
		
	}
}
