import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchExample implements Runnable {
    private static final int NUMBER_OF_THREADS = 5;
    private static final CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREADS);
    private static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        for(int i=0 ; i<NUMBER_OF_THREADS ; i++){
            executorService.execute(new CountDownLatchExample());
        }
        executorService.shutdown();
    }

    public void run(){
        try{
            int randomSleepTime = random.nextInt(20000);
            System.out.println("[" + Thread.currentThread().getName() + "] Sleeping for " + randomSleepTime);
            Thread.sleep(randomSleepTime); //thread sleeps
            latch.countDown(); //wakes up and decreases the counter to indicate it has finished
            System.out.println("[" + Thread.currentThread().getName() + "] Waiting for latch.");
            latch.await();//waits here until countDownLatch is 0
            System.out.println("[" + Thread.currentThread().getName() + "] Finished.");
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
