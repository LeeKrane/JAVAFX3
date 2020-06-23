package labor25;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimeFinder implements Runnable {
	private int start;
	private int end;
	private final List<Long> primes = new ArrayList<>();
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
	
	void findPrimes () {
		int n = end - start;
		threads = new ArrayList<>();
		
		for (int i = 0, j = start; i < n; i++, j++) {
			Thread t = new Thread(new PrimeChecker(j, this));
			threads.add(t);
			t.start();
		}
	}
	
	synchronized int countRunningCheckers () {
		return (int) threads.stream().filter(Thread::isAlive).count();
	}
	
	Stream<Long> getPrimes () {
		return primes.stream();
	}
	
	synchronized void addPrime (long prime) {
		primes.add(prime);
	}
	
	@Override
	public void run () {
		do {
			System.out.println("Active Checkers: " + countRunningCheckers());
			Collections.sort(primes);
			System.out.println(primes);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (countRunningCheckers() > 0);
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
		return checkPrime == 2
				||
				(checkPrime > 2
						&&
						(checkPrime % 2) != 0
						&&
						IntStream.rangeClosed(3, (int) Math.sqrt(checkPrime))
								.filter(n -> {
									try {
										Thread.sleep(primeFinder.delay);
									} catch (InterruptedException ignored) {}
									return n % 2 != 0;
								})
								.noneMatch(n -> (checkPrime % n == 0)));
	}
}