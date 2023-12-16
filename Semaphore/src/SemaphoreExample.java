import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreExample implements Runnable{
    private static final Semaphore semaphore = new Semaphore(3,true);
    private static final AtomicInteger counter = new AtomicInteger();
    private static final long endMillis = System.currentTimeMillis() + 200;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0 ; i<10 ; i++){
            executorService.execute(new SemaphoreExample());
        }
        executorService.shutdown();
    }


    public void run() {
        while(System.currentTimeMillis() < endMillis){
            try{
                semaphore.acquire();// This statement tries to acquire a permit from the semaphore.
                // If permits are available (indicating that there's space to access the shared resource), the thread acquires the permit and proceeds.
                // If permits are not available (other threads are using the resource), this line puts the thread into a waiting state.

                Thread.sleep(100);
            }
            catch (InterruptedException e){
                System.out.println("[" + Thread.currentThread().getName() + "] Interrupted in acquire().");
            }

            int counterValue = counter.incrementAndGet();//This line increments the count of threads accessing the shared resource.
            // It helps keep track of how many threads are using the resource.

            System.out.println("[" + Thread.currentThread().getName() + "] semaphore acquired:" + counterValue);
            if(counterValue > 2){ //because counter starts from 0
                System.out.println("More than three threads acquired the lock");
            }
            counter.decrementAndGet();
            semaphore.release();
        }
    }

}
