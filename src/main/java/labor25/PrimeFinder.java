package labor25;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimeFinder implements Runnable {
	private int start;
	private int end;
	private List<Long> primes = new ArrayList<>();
	private List<PrimeChecker> primeCheckers;
	private List<Thread> threads;
	final int delay;
	
	PrimeFinder (int delay, int start, int end) {
		this.delay = delay;
		this.start = start;
		this.end = end;
	}
	
	public PrimeFinder (int delay) {
		this.delay = delay;
	}
	
	public PrimeFinder () {
		delay = 0;
	}
	
	public static void main (String[] args) {
		PrimeFinder finder = new PrimeFinder(10, 2, 25);
		finder.run();
	}
	
	@Override
	public void run () {
		int n = end - start;
		primeCheckers = new ArrayList<>();
		threads = new ArrayList<>();
		
		for (int i = 0, j = start; i < n; i++, j++) {
			primeCheckers.add(new PrimeChecker(j, this));
			Thread t = new Thread(primeCheckers.get(i));
			threads.add(t);
			t.start();
		}
		
		do {
			System.out.println("Active Checkers: " + countRunningCheckers());
			Collections.sort(primes);
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
	
	synchronized int countRunningCheckers () { // TODO: fix method to fit test
		int activeCheckers = 0;
		for (Thread thread : threads) {
			if (thread.isAlive())
				activeCheckers++;
		}
		return activeCheckers;
	}
	
	Stream<Long> getPrimes () {
		return primes.stream();
	}
	
	synchronized void addPrime (long prime) {
		primes.add(prime);
	}
}

class PrimeChecker implements Runnable {
	private final long checkPrime;
	private final PrimeFinder primeFinder;
	
	public PrimeChecker (long checkPrime, PrimeFinder primeFinder) {
		this.checkPrime = checkPrime;
		this.primeFinder = primeFinder;
	}
	
	@Override
	public void run () {
		if (isPrimeWithDelay())
			primeFinder.addPrime(checkPrime);
	}
	
	public boolean isPrimeWithDelay () {
		boolean ret = checkPrime == 2
				||
				(checkPrime > 2
						&&
						(checkPrime % 2) != 0
						&&
						IntStream.rangeClosed(3, (int) Math.sqrt(checkPrime))
								.filter(n -> n % 2 != 0)
								.noneMatch(n -> (checkPrime % n == 0)));
		try {
			Thread.sleep(primeFinder.delay);
		} catch (InterruptedException e) {
			System.err.println(e);
		}
		return ret;
	}
}