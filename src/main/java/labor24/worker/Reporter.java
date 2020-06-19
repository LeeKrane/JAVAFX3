package labor24.worker;

public class Reporter implements Runnable {
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
