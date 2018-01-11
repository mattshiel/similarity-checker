package ie.gmit.sw;

import java.util.Scanner;

public class UI {
	
	/*
	 * Member Variables
	 */
	// Threads for both files
	private Thread t1;
	private Thread t2;
	// Menu controls
	private boolean keepRunning = true;
	private int choice = -1;
	// File Locations
	private String location1;
	private String location2;
	// File Parsers for both files
	private FileParser f1;
	private FileParser f2;
	//Scanner
	private Scanner sc = new Scanner(System.in);
	
	// Default Constructor
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
		getFileLocations();
		startThreads();
	}

	private void getFileLocations() {
		System.out.println("**** Document Comparison Service ****");
		
		System.out.println("Enter file name / URL 1: ");
		this.location1 = sc.next();
		System.out.println("Enter file name / URL 2: ");
		this.location2 = sc.next();
		
		f1 = new FileParser(location1);
		f2 = new FileParser(location2);
	}
	
	private void startThreads() {
		t1 = new Thread(f1);
		t1.start();
		t2 = new Thread(f2);
		t2.start();		
	}
}

