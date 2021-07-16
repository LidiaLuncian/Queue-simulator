import java.util.Comparator;

public class Task implements Comparable {
    //client
    private int idC;
    private int arrivalTime;
    private int processingTime;
    private int waitingPeriod;
    //FinishTime = arrivalTime + processingPeriod+ waitingPeriod
    public Task(int idC, int arrivalTime, int processingTime){
        this.idC = idC;
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
        this.waitingPeriod = 0;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    @Override
    public String toString() {
        return "(" + this.idC +
                ", " + this.arrivalTime +
                ", " + this.processingTime +
                ')';
    }

    public int getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(int waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(this.arrivalTime,((Task)o).getArrivalTime());
    }
}
