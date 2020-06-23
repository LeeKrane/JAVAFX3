package labor25.piCalculator;

public class PiCalculator {
    public static double calcPiWithNThreads(int limit, int threadCnt) throws InterruptedException {
        if (threadCnt < 1)
            throw new IllegalArgumentException("The thread count must be at least 1!");
        
        PartialSumThread[] partialSums = new PartialSumThread[threadCnt];
        Thread[] threads = new Thread[threadCnt];
        double remainingRange = limit;
        // TODO: fix ranges
        for (int i = 0, min = 0, max = limit / threadCnt;
             i < threadCnt;
             i++, remainingRange = limit - max, min = max + 1, max += Math.round(remainingRange / (threadCnt - i))) {
            partialSums[i] = new PartialSumThread(min, max);
            threads[i] = new Thread(partialSums[i]);
            threads[i].start();
            System.out.format("Thread-%d: [%5d, %5d] => %5d\n", i, min, max, max - min + 1);
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
