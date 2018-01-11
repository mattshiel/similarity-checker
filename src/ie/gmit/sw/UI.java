package ie.gmit.sw;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UI {
	
	/*
	 * Member Variables
	 */
	// Threads for both files
	private Thread producer1;
	private Thread producer2;
	private Thread consumer;
	// Menu controls
	private boolean keepRunning = true;
	private int choice = -1;
	// File Locations
	private String location1;
	private String location2;
	// File IDs
	private int docID1;
	private int docID2;
	// File Parsers for both files
	private FileParser f1;
	private FileParser f2;
	// BlockingQueue
	BlockingQueue<Shingle> queue = new LinkedBlockingQueue<Shingle>(10000);
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
		
		docID1 = 1;
		docID2 = 2;
		
		f1 = new FileParser(location1, docID1, queue);
		f2 = new FileParser(location2, docID2, queue);
	}
	
	private void startThreads() {
		// Producer Threads
		producer1 = new Thread(f1);
		producer1.start();
		producer2 = new Thread(f2);
		producer2.start();		
		consumer = new Thread(new Consumer(queue));
		consumer.start();
		// Consumer Threads
	}
}

