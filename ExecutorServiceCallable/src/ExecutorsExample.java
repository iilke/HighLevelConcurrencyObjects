import java.util.Random;
import java.util.concurrent.*;


public class ExecutorsExample implements Callable<Integer> {
Random random = new Random();
    @Override
    public Integer call() throws Exception {
        int i = random.nextInt(100);
        System.out.println(Thread.currentThread().getName() + " - " + i);
        return i;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<Integer>[] futures = new Future[3];
        for(int i=0 ; i<futures.length ; i++){
            futures[i] = executorService.submit(new ExecutorsExample());
        }

        for (int i=0 ; i< futures.length ; i++){
            System.out.println(Thread.currentThread().getName() + "-" + futures[i].get());
        }
        executorService.shutdown();
    }
}
