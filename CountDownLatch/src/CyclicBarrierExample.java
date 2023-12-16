import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierExample implements Runnable{
    private static final int NUMBER_OF_THREADS = 3;
    private static AtomicInteger counter = new AtomicInteger();
    private static Random random = new Random(System.currentTimeMillis());
    private static final CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
        @Override
        public void run() {
            System.out.println("Counter : " + counter);
            counter.incrementAndGet();
        }//this run() executes when barrier is reached
    });

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        for(int i=0 ; i<NUMBER_OF_THREADS ; i++){
            executorService.execute(new CyclicBarrierExample());
        }
        executorService.shutdown();
    }

    public void run(){
        try {
            while(counter.get() < 2){ //this cycle happens for 2 rounds
                int randomSleepTime = random.nextInt(100);
                System.out.println("[" + Thread.currentThread().getName() + "] Sleeping for " + randomSleepTime);
                Thread.sleep(randomSleepTime);
                System.out.println("[" + Thread.currentThread().getName() + "] Waiting for barrier.");//waits for all threads to reach barrier
                barrier.await();
                System.out.println("[" + Thread.currentThread().getName() + "] Finished.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
