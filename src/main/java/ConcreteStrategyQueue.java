import java.util.*;

public class ConcreteStrategyQueue implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task t) {

        //server with the shortest waiting queue strategy
        if(servers == null )return;
        int minQueue = servers.get(0).getTasks().size();

        Server bestQueue = servers.get(0);

        for(Server server: servers){
            if(server.getTasks().size() < minQueue){
                minQueue = server.getTasks().size();
                bestQueue = server;
            }
        }
        if(bestQueue != null){
            bestQueue.addTask(t);
        }
    }
}
