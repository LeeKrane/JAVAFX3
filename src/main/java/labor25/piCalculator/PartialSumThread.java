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
        for (int i = min; i <= max; i++)
            sum += Math.pow(-1.0, i) / (2.0 * i + 1.0);
    }

    public double getSum() {
        return sum;
    }
}
