import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWritter {

    public void writeFile(int time, Scheduler scheduler, List<Task> taskList, FileWriter w )
    {
        try{
            StringBuilder text = new StringBuilder("Time: " + time + "\n");
            text.append("Waiting: ");

            text.append(taskList.toString());


            text.append("\n");

            for(Server s: scheduler.getServers()){
                text.append(s.toString());
                text.append("\n");
            }

            text.append("\n");
            w.append(text.toString());
            w.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePeakAverage(float averageTime, int peak, FileWriter w){
        try{
            w.append("Peak hour: " + peak + "\n");
            w.append("Average waiting time: " + averageTime);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
