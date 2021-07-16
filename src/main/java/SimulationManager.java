import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationManager implements Runnable{
    //data read from the UI

    private int timeLimit; //maximum processing time - read from UI
    private int maxProcessingTime;
    private int minProcessingTime;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int numberOfServers;
    private int numberOfClients;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;


    //entity responsible with queue management and client distribution
    private Scheduler scheduler;

    //frame for displaying simulation


    //pool of tasks (client shopping in the store)
    private ArrayList<Task> generatedTasks;

    private FileWritter file1;
    private  FileWriter fw;

    private SimulationView frame;


    private float averageServiceTime = 0;


    public SimulationManager(){

    }

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int minArrivalTime, int maxArrivalTime, int numberOfClients, int numberOfServers){
        //initialize the scheduler
        // => create and start numberOfServers threads
        // => initialize selection strategy => create strategy
        //initialize frame to display simulation
        //generate numberOfClients clients using generateNRandomTasks()
        //and store them to generateTasks

        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minArrivalTime = minArrivalTime;
        this.numberOfClients = numberOfClients;
        this.numberOfServers = numberOfServers;

        file1 = new FileWritter();
        File file = null;
        try{
            String filename = "file.txt";
            file = new File(filename);
            if(file.createNewFile()){
                System.out.println("File created successfully");
            }
        }catch (IOException e){
            System.out.println("Error");
        }
        try{
            fw = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }


        this.scheduler = new Scheduler(this.numberOfServers, this.numberOfClients);
        this.generatedTasks = new ArrayList<Task>(this.numberOfClients);

        generateNRandomTasks();
        scheduler.changeStrategy(this.selectionPolicy);
        frame = new SimulationView(this);
    }

    public int getNumberOfServers(){
        return this.numberOfServers;
    }
    public Scheduler getScheduler(){
        return scheduler;
    }

    private Task[][] getTasks() {
        List<Server> servers = this.scheduler.getServers();
        Task[][] tasks = new Task[servers.size()][];
        for (int i = 0; i < servers.size(); i++) {
            tasks[i] = servers.get(i).getTasksList();
        }
        return tasks;
    }
    public int getTimeLimit(){
        return this.timeLimit;
    }
    private void generateNRandomTasks(){
        //generate N random tasks
        //-random processing time
        //minProcessingTime < processingTime < maxProcessingTime
        // - random arrival time
        //sort list with respect to arrivalTime

        this.averageServiceTime = 0;
        Random r = new Random();
        int processingTime;
        int arrivalTime;

        for(int i = 0; i < numberOfClients; i++){
            processingTime = r.nextInt(this.maxProcessingTime - this.minProcessingTime +1) + this.minProcessingTime;
            arrivalTime = r.nextInt(this.maxArrivalTime- this.minArrivalTime +1) + this.minArrivalTime;
            Task t = new Task(i+1, arrivalTime, processingTime);
            this.generatedTasks.add(t);
            averageServiceTime += processingTime;

        }
        Collections.sort(generatedTasks);
        averageServiceTime /= this.numberOfClients;

    }


    @Override
    public void run() {

        int peakHour = 0;
        int currentTime = 0;
        int maxPeak=0;
        int ok=0;
        while(currentTime < timeLimit){
            ok=0;
            //iterate generatedTasks list and pick tasks that have
            //the arrivalTime equal with the currentTime
            //- send tasks to queue by calling the dispatchTask method
            //from Scheduler
            // - delete client from list
            //update UI frame

            if(!generatedTasks.isEmpty()) {
                Task t = generatedTasks.get(0);
                while (currentTime == t.getArrivalTime()) {
                    scheduler.dispatchTask(t);
                    generatedTasks.remove(0);
                    if (generatedTasks.isEmpty()) {
                        break;
                    }
                    t = generatedTasks.get(0);
                }
            }

            file1.writeFile(currentTime, scheduler, generatedTasks, fw );

            int peak=0;

            for(Server s : scheduler.getServers()){
                System.out.println(s.toString());
                peak += s.getTasks().size();
            }
            if(peak > maxPeak){
                maxPeak = peak;
                peakHour = currentTime;
            }

            try {
                frame.displayQueues(getTasks(), generatedTasks, currentTime, ok);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scheduler.decrementWaitingTime();
            currentTime++;
            scheduler.incrementWaitAverage();

            //wait an interval of 1 second
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.out.println("Eroare");
            }

            scheduler.updateProcessingTime();

            if(generatedTasks.isEmpty()){
                if(scheduler.serversAreDone()){
                    file1 .writeFile(currentTime, scheduler,generatedTasks,fw);
                    file1.writePeakAverage(averageServiceTime, peakHour, fw);

                    try {

                        fw.close();
                    } catch (IOException e) {
                        System.out.println("");
                    }
                    break;
                }
            }
        }
        ok=1;
        if(!generatedTasks.isEmpty() || !scheduler.serversAreDone()) {

            file1.writePeakAverage(averageServiceTime, peakHour, fw);

            try {

                fw.close();
            } catch (IOException e) {
                System.out.println("");
            }
        }
        try {
            frame.displayQueues(getTasks(), generatedTasks, currentTime,ok);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scheduler.stopServers();
        // System.out.println("Average waiting time: " + averageServiceTime);
        // System.out.println("Peak hour: " + peakHour);
        System.out.println("Done");
    }



    public static void main(String[] args){
        SimulationManager gen = null;
        View frame = new View(gen);
        Controller controller = new Controller(gen, frame);


    }
}

