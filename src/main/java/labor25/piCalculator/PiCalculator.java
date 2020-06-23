package labor25.piCalculator;

public class PiCalculator {
    public static double calcPiWithNThreads(int limit, int threadCnt) throws InterruptedException {
        if (threadCnt < 1)
            throw new IllegalArgumentException("The thread count must be at least 1!");
        
        PartialSumThread[] partialSums = new PartialSumThread[threadCnt];
        Thread[] threads = new Thread[threadCnt];
        int remainingRange = ++limit;
        int range, min, max;
    
        for (int i = 0; i < threadCnt; i++) {
            range = (int) Math.ceil((double) remainingRange / (threadCnt - i));
            min = limit - remainingRange;
            max = min + range - 1;
            System.out.format("Thread-%d: [%5d, %5d] => %5d\n", i, min, max, range);
            (threads[i] = new Thread((partialSums[i] = new PartialSumThread(min, max + 1)))).start();
            remainingRange -= range;
        }
        
        double pi = 0.0;
        
        for (int i = 0; i < threadCnt; i++) {
            if (threads[i].isAlive())
                threads[i].join();
            pi += partialSums[i].getSum();
        }
        
        pi *= 4;
        
        System.out.println("pi = " + pi);
        return pi;
    }
}
