package labor24.worker;

import java.util.Random;

public class Worker implements Runnable {
	Stadium currentStadium;
	private static final Random random = new Random();
	
	public Worker () {
		Stadium.incrementStadium(this);
	}
	
	@Override
	public void run () {
		try {
			while (currentStadium != Stadium.DONE) {
				Thread.sleep((Math.abs(random.nextInt()) % (currentStadium.end - currentStadium.start) + currentStadium.start) * 1000);
				Stadium.incrementStadium(this);
			}
		} catch (InterruptedException ignored) {}
	}
}

class Reporter implements Runnable {
	private Thread[] workers;
	
	public Reporter (int workerCount) {
		workers = new Thread[workerCount];
		for (int i = 0; i < workers.length; i++)
			workers[i] = new Thread(new Worker());
	}
	
	@Override
	public void run () {
		for (Thread worker : workers) worker.start();
		
		System.out.println("St 1|St 2|St 3|Done");
		
		try {
			while (Stadium.DONE.workerCount < workers.length) {
				System.out.format("%4d|%4d|%4d|%4d\n", Stadium.STD1.workerCount, Stadium.STD2.workerCount, Stadium.STD3.workerCount, Stadium.DONE.workerCount);
				Thread.sleep(1000);
			}
			System.out.format("%4d|%4d|%4d|%4d\n", Stadium.STD1.workerCount, Stadium.STD2.workerCount, Stadium.STD3.workerCount, Stadium.DONE.workerCount);
		} catch (InterruptedException ignored) {}
		
		System.out.println("All Workers done!");
	}
}

enum Stadium {
	STD1(3, 7),
	STD2(6, 10),
	STD3(2, 10),
	DONE(0, 0);
	
	final int start;
	final int end;
	int workerCount;
	
	Stadium(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	Stadium getNextStadium() {
		return switch (toString()) {
			case "STD1" -> STD2;
			case "STD2" -> STD3;
			default -> DONE;
		};
	}
	
	static synchronized void incrementStadium (Worker worker) {
		if (worker.currentStadium == null) {
			worker.currentStadium = STD1;
			STD1.workerCount++;
		} else if (!worker.currentStadium.equals(DONE)) {
			worker.currentStadium.workerCount--;
			worker.currentStadium = worker.currentStadium.getNextStadium();
			worker.currentStadium.workerCount++;
		}
	}
}