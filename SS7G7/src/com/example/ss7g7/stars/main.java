package com.example.ss7g7.stars;

public class main {
	public static void main(String[] args) {
		StarsSystem stars = new StarsSystem();

		boolean successfulInit = stars.init();
		
		if(!successfulInit) {
			// TODO: Throw error
		}
		
		stars.run();
	}
}
