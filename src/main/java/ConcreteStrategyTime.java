import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) {

        //servers with the shortest time strategy

        if(servers == null)return;
        int minTime = servers.get(0).getWaintingPeriod();

        Server bestQueue = servers.get(0);

        for(Server server : servers){
            if(server.getWaintingPeriod()<minTime){
                minTime = server.getWaintingPeriod();
                bestQueue = server;
            }
        }

        if(bestQueue != null){
            bestQueue.addTask(t);
        }
    }
}

