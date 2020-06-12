package labor25.piCalculator;

public class PartialSumThread implements Runnable {
    private double sum;
    private int min;
    private int max;

    public PartialSumThread (int min, int max) {
        if (min < 0)
            throw new IllegalArgumentException("The minimum must not be negative!");
        
        this.min = min;
        this.max = max;
    }

    @Override
    public void run() {
        for (int i = min; i <= max; i++) // TODO <= oder < ?
            sum += Math.pow(-1, i) / (2 * i + 1);
    }

    public double getSum() {
        return sum;
    }
}
