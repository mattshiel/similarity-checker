package ie.gmit.sw;

import java.util.Scanner;

public class UI {
	private boolean keepRunning = true;
	private int choice = -1;
	private String location1;
	private String location2;
	private Scanner sc = new Scanner(System.in);
	
	public UI() {
		
	}
	
	public void mainMenu() {
		do {
			System.out.println("**** Main Menu ****\n1) Compare Documents\n2) Quit Application");
			choice = sc.nextInt();
			
			switch (choice) {
			case 1: 
				compareDocuments();
				break;
			
			case 2:
				keepRunning = false;
				System.out.println("Closing application....Goodbye!");
				break;
			}
			
		} while(keepRunning);
	}

	private void compareDocuments() {
		System.out.println("**** Document Comparison Service ****");
		
		System.out.println("Enter file name / URL 1: ");
		location1 = sc.next();
		System.out.println("Enter file name / URL 2: ");
		location2 = sc.next();
		
		Parser.parseLocation(location1);
		Parser.parseLocation(location2);

	}
}

