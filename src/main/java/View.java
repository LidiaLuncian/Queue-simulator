import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {


    private SimulationManager manager;
    private Container container;

    JFrame mainFrame=new JFrame("Simulator");
    JPanel panel = new JPanel();
    JButton startButton = new JButton("Start");

    JLabel noClients = new JLabel("No. of Clients: ");
    JLabel noQueues = new JLabel("No. of Queues: ");
    JLabel simulationInterval = new JLabel("Simulation interval: ");
    JLabel minArrivalTime = new JLabel("Min arrival time: ");
    JLabel maxArrivalTime = new JLabel("Max arrival time: ");
    JLabel minServiceTime = new JLabel("Min service time: ");
    JLabel maxServiceTime = new JLabel("Max service time: ");

    JTextField noClientsText = new JTextField("");
    JTextField noQueuesText = new JTextField("");
    JTextField simulationIntText = new JTextField("");
    JTextField minArrivalText = new JTextField("");
    JTextField maxArrivalText = new JTextField("");
    JTextField minServiceText = new JTextField("");
    JTextField maxServiceText = new JTextField("");


    //second frame


    public View(SimulationManager manager){
        this.manager = manager;
        mainFrame.setVisible(true);
        mainFrame.setSize(500, 400);
        mainFrame.setLocation(500,280);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        container = getContentPane();
        container.setLayout(new BorderLayout());

        panel.setLayout(null);
        container.add(panel);
        mainFrame.add(container);


        noClients.setBounds(10, 20, 100, 20);
        noClientsText.setBounds(160, 20, 100,20);
        noQueues.setBounds(10, 60,100,20);
        noQueuesText.setBounds(160, 60, 100,20);
        simulationInterval.setBounds(10,100,150,20);
        simulationIntText.setBounds(160, 100, 100, 20);
        minArrivalTime.setBounds(10, 140, 100, 20);
        minArrivalText.setBounds(160,140,100,20);
        maxArrivalTime.setBounds(10, 180, 100, 20);
        maxArrivalText.setBounds(160,180,100,20);
        minServiceTime.setBounds(10, 220, 100, 20);
        minServiceText.setBounds(160,220,100,20);
        maxServiceTime.setBounds(10, 260, 120, 20);
        maxServiceText.setBounds(160,260,100,20);

        startButton.setBounds(60,300,100,20);

        panel.add(noClients);
        panel.add(noClientsText);
        panel.add(noQueues);
        panel.add(noQueuesText);
        panel.add(simulationInterval);
        panel.add(simulationIntText);
        panel.add(minArrivalTime);
        panel.add(minArrivalText);
        panel.add(maxArrivalTime);
        panel.add(maxArrivalText);
        panel.add(minServiceTime);
        panel.add(minServiceText);
        panel.add(maxServiceTime);
        panel.add(maxServiceText);
        panel.add(startButton);

    }


    public void addStartListener(ActionListener st){
        startButton.addActionListener(st);
    }
    public String getNoClients(){
        return noClientsText.getText();
    }
    public String getNoQueues(){
        return noQueuesText.getText();
    }
    public String getSimulationInt(){
        return simulationIntText.getText();
    }
    public String getMinArrival(){
        return minArrivalText.getText();
    }
    public String getMaxArrival(){
        return maxArrivalText.getText();
    }
    public String getMinService(){
        return minServiceText.getText();
    }
    public String getMaxService(){
        return maxServiceText.getText();
    }

}
