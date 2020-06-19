package labor25;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

public class PrimeFinder implements Runnable {
	private int start;
	private int end;
	private Collection<Integer> primes = new ArrayList<>();
	private PrimeChecker[] primeCheckers;
	private Thread[] threads;
	private int delay;
	
	public PrimeFinder (int delay, int start, int end) {
		this.delay = delay;
		this.start = start;
		this.end = end;
	}
	
	public static void main (String[] args) {
		PrimeFinder finder = new PrimeFinder(10, 2, 25);
		finder.run();
	}
	
	@Override
	public void run () {
		int n = end - start;
		primeCheckers = new PrimeChecker[n];
		threads = new Thread[n];
		
		for (int i = 0, j = start; i < n; i++, j++) {
			primeCheckers[i] = new PrimeChecker(j, this);
			threads[i] = new Thread(primeCheckers[i]);
			threads[i].start();
		}
		
		do {
			System.out.println("Active Checkers: " + countRunningCheckers());
			System.out.println(primes);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		} while (countRunningCheckers() > 0);
	}
	
	void findPrimes () {
		run();
	}
	
	int countRunningCheckers () {
		int activeCheckers = 0;
		for (int i = 0; i < threads.length; i++) {
			if (threads[i].isAlive())
				activeCheckers++;
		}
		return activeCheckers;
	}
	
	Collection<Integer> getPrimes () {
		return primes;
	}
	
	synchronized void addPrime (int prime) {
		primes.add(prime);
	}
}

class PrimeChecker implements Runnable {
	private final int checkPrime;
	private final PrimeFinder primeFinder;
	
	public PrimeChecker (int checkPrime, PrimeFinder primeFinder) {
		this.checkPrime = checkPrime;
		this.primeFinder = primeFinder;
	}
	
	@Override
	public void run () {
		if (checkPrime == 2
			||
				(checkPrime > 2
				&&
				(checkPrime % 2) != 0
				&&
				IntStream.rangeClosed(3, (int) Math.sqrt(checkPrime))
						.filter(n -> n % 2 != 0)
						.noneMatch(n -> (checkPrime % n == 0))))
			primeFinder.addPrime(checkPrime);
	}
}