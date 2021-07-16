import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    private SimulationManager m_manager;
    private View v_view;


    public Controller(SimulationManager manager, View view){
        this.m_manager = manager;
        this.v_view = view;

        v_view.addStartListener(new startListener());
    }

    class startListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            try{
                int numberOfClients = Integer.parseInt(v_view.getNoClients());
                int numberOfServers = Integer.parseInt(v_view.getNoQueues());
                int timeLimit = Integer.parseInt(v_view.getSimulationInt());
                int minArrivalTime = Integer.parseInt(v_view.getMinArrival());
                int maxArrivalTime = Integer.parseInt(v_view.getMaxArrival());
                int minProcessingTime = Integer.parseInt(v_view.getMinService());
                int maxProcessingTime = Integer.parseInt(v_view.getMaxService());
                m_manager = new SimulationManager(timeLimit, maxProcessingTime, minProcessingTime, minArrivalTime, maxArrivalTime,numberOfClients, numberOfServers);
                Thread t = new Thread(m_manager);
                t.start();

            }catch (NumberFormatException nf){
                JOptionPane.showMessageDialog(null ,"Introduceti date valide");

            }

        }
    }
}


