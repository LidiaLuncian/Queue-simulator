import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int id;
    private boolean running;
    private int waitAverage;

    public Server(int id, int maxTasksPerServer)
    {
        //initialize queue and waitingPeriod
        tasks = new ArrayBlockingQueue<Task>(maxTasksPerServer);
        waitingPeriod = new AtomicInteger(0);
        this.id = id;
        running = true;
        this.waitAverage = 0;
    }


    public void addTask(Task newTask)
    {
        //add task to queue
        //increment the waitingPeriod
        this.tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getProcessingTime());
    }

    public int getWaintingPeriod(){
        return waitingPeriod.intValue();
    }

    public void stop(){
        this.running = false;

    }
    @Override
    public void run() {

        while(running)
        {
            //take next task from queue
            //stop the thread for a time equal with the task's processing time
            //decrement waitingPeriod
            try{

                if(tasks.isEmpty()){
                    Thread.sleep(1000);
                }else{
                    Task t = tasks.peek();
                    Thread.sleep((t.getProcessingTime() -1) * 1000L);

                    this.tasks.take();
                    if(!tasks.isEmpty()){
                        t = tasks.peek();
                        t.setProcessingTime(t.getProcessingTime() +1);
                    }
                    Thread.sleep(1000);
                }

            }catch (InterruptedException e){
                System.out.println("Thread interrupted");
            }
        }
    }

    public BlockingQueue<Task> getTasks()
    {
        return this.tasks;
    }


    public int getWaitAverage() {
        return waitAverage;
    }

    public void setWaitAverage(int waitAverage) {
        this.waitAverage = waitAverage;

    }

    public Task[] getTasksList(){
        Task[] t = new Task[tasks.size()];
        int i=0;
        for(Task tt : tasks){
            t[i] = tt;
            i++;
        }
        return t;
    }

    public void decrementWaitingPeriod(){
        waitingPeriod.getAndDecrement();
    }

    public void incrementWaitingPeriod(){
        waitingPeriod.getAndIncrement();
    }
    public String toString(){
        String text ="Queue " + id + ": ";
        if(tasks.isEmpty())return  text + "closed";
        for(Task t : tasks){
            text += t;
        }
        return text;
    }
}
