//optimistic locking assumes that there are
// not so many threads competing for the resource and hence
// we just try to update the value and see if this has worked.

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample implements Runnable {
    private static final AtomicInteger atomicInteger= new AtomicInteger();//initially 0

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for(int i=0; i<5; i++){
            executorService.execute(new AtomicIntegerExample());
        }
        executorService.shutdown();
    }


    //Each thread increments the atomicInteger ten times,
    // and if the incremented value becomes 42,
    // it prints this condition to the screen.

    public void run(){
        for(int i=0 ; i<10 ; i++){
            int newValue = atomicInteger.getAndIncrement();
            if(newValue == 42){
                System.out.println("[" + Thread.currentThread().getName() + "]: " + newValue);
            }
        }
    }
}


