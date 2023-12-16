import java.util.concurrent.CountDownLatch;
import java.util.Random;

public class BasicCountDownLatchExample {
    public static void main(String[] args) {
        final int threadCount = 5;
        final CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new Worker(latch));
            thread.start();
        }

        try {
            // Main thread waits until all worker threads are done
            latch.await();
            System.out.println("All threads have finished their work.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Worker implements Runnable {
        private final CountDownLatch latch;
        private final Random rand = new Random();

        public Worker(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                // Simulating different tasks with random sleep times
                int sleepTime = rand.nextInt(5000); // Sleep time between 0 to 5 seconds
                System.out.println(Thread.currentThread().getName() + " will sleep for " + sleepTime + " milliseconds");
                Thread.sleep(sleepTime);

                // Work done by the thread
                System.out.println(Thread.currentThread().getName() + " has finished its work.");

                // Count down the latch to indicate completion
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

