import java.util.concurrent.locks.ReentrantLock;
public class SychronizedCunter implements Runnable {
    public static int counter = 0;
    ReentrantLock rlock = new ReentrantLock();

    @Override
    public void run() {
        while(counter < 5){
            try{
                rlock.lock();
                System.out.println(Thread.currentThread().getName() + " before: " + counter);
                counter++;
                System.out.println(Thread.currentThread().getName() + " after: " + counter);
            }
            finally {
                rlock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[5];
        for(int i=0 ; i<threads.length ; i++){
            threads[i] = new Thread(new SychronizedCunter(), "thread-" + i);
            threads[i].run();
        }
        for(int i=0 ; i<threads.length ; i++){
            threads[i].join();
        }
    }


}