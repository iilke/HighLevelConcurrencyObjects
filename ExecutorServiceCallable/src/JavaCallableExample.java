//what is difference between executor service and executor?
/*
Executor has run() method and run method is void, it doesn't return anything about thread.
Using ExecutorService, we have call() method similar to run() method but with a major difference
which is call() method can return objects.

Runnable interface -> void run()
Callable interface -> Object call()
*/
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class JavaCallableExample implements Callable{
    public Object call() throws Exception{
        Random randObj = new Random();
        Integer randNo = randObj.nextInt(10);
        return randNo;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask[] randomNoTasks = new FutureTask[10];

        for(int j=0 ; j<5 ; j++){
            Callable clble = new JavaCallableExample();
            randomNoTasks[j] = new FutureTask(clble);
            Thread th = new Thread(randomNoTasks[j]);
            th.start();
        }//we create 5 threads that return a random number and put them into FutureTask

        for(int j = 0; j<5; j++){ // we make main thread to print items in FutureTask
            Object o = randomNoTasks[j].get();
            System.out.println(Thread.currentThread().getName() + " : " + o);
        }
    }
}
