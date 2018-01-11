package ie.gmit.sw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

	/*
	 * Member Variables
	 */
	private BlockingQueue<Shingle> queue;
	private int k;
	private int[] minhashes;
	private Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
	
	//Default constructor
	public Consumer() {
		
	}
	
	
	public Consumer(BlockingQueue<Shingle> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		// Infinite loop
		while(true) {
			try {
				Shingle value = queue.take();
				System.out.println("Shingle hashcode is: " + value.getShingleHashCode());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
