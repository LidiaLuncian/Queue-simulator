import java.util.*;

public class Scheduler {

    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer)
    {
        //for maxNoServers
        //-create server object
        //- create thread with one object

        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;

        this.strategy = new ConcreteStrategyTime();

        servers = new ArrayList<>();

        for(int i=0;i<maxNoServers;i++){
            Server s = new Server(i+1,maxTasksPerServer);
            this.servers.add(s);
            Thread th = new Thread(s);
            th.start();
        }


    }

    public void changeStrategy(SelectionPolicy policy)
    {
        //apply strategy pattern to instantiate the strategy with
        //the concrete strategy corresponding to policy

        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ConcreteStrategyQueue();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t){
        //call the strategy addTask method
        strategy.addTask(servers, t);
    }

    public List<Server> getServers(){
        return servers;
    }

    public void stopServers(){
        for(Server s : servers){
            s.stop();
        }
    }

    public boolean serversAreDone(){
        for(Server s : servers){
            if(!s.getTasks().isEmpty())
                return false;
        }
        return true;
    }

    public void updateProcessingTime(){
        for(Server server : servers){
            if(!server.getTasks().isEmpty()){
                Task[] t = server.getTasksList();
                t[0].setProcessingTime(t[0].getProcessingTime() -1);

                for (Task task : t) {
                    task.setWaitingPeriod(task.getWaitingPeriod() + 1);
                }
            }
        }
    }

    public void decrementWaitingTime(){
        for(Server s:servers){
            s.decrementWaitingPeriod();
        }
    }

    public void incrementWaitAverage(){
        for(Server s:servers){
            if(s.getTasks() != null){
                s.setWaitAverage(s.getWaitAverage() + s.getTasksList().length);
            }
        }
    }



}
