import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorExample implements Runnable {
    private static AtomicInteger counter = new AtomicInteger();
    private final int taskID;
    public int getTaskID(){
        return taskID;
    }
    public ThreadPoolExecutorExample(int taskID){ //debug showing up here means object created
        this.taskID = taskID;
    }

    public void run(){
        try{
            Thread.sleep(300); //let main thread sleep
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        //The queue implementation here is a LinkedBlockingQueue with a capacity of 10 Runnable instances.
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(10);


        //We implement a simple ThreadFactory in order to track the thread creation
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r){ //debug showing up here means thread created
                int currentCount = counter.getAndIncrement();
                System.out.println("Creating new thread: " + currentCount);
                return new Thread(r, "myThread" + currentCount);
            }
        }; //thread factory is a java.util.concurrent element


        RejectedExecutionHandler rejectHandler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if(r instanceof ThreadPoolExecutorExample){
                    ThreadPoolExecutorExample example = (ThreadPoolExecutorExample) r;
                    System.out.println("Rejecting task with if " + example.getTaskID());
                }
            }
        };


        //The ThreadPoolExecutor starts with 5 core threads and allows the pool to grow up to 10 threads at the maximum.
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,1,TimeUnit.SECONDS,queue,threadFactory, rejectHandler);

        //when a thread is in the queue, object for them is definitely created, but thread ot them gets created only if they run in the runtime.

        for(int i=0 ; i<30 ; i++){
            executor.execute(new ThreadPoolExecutorExample(i));
        }
        //queue size is 10, but why 10-19 is not rejected and 20-30 gets rejected?
        //it's because the first 10 gets to run as threads in thread pool,
        //and when they run queue has now empty spaces so 10-19 gets in to queue and wait
        //first 10 runs, second 10 waits in queue, last 10 is left out and rejected.

        executor.shutdown();
    }
}
