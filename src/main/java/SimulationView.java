import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimulationView extends JFrame{

    private SimulationManager manager;
    private Container content;

    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();

    JLabel currentTimeL = new JLabel("Current Time = 0");
    JLabel generatedTasksL = new JLabel("");

    private boolean contor = true;

    public SimulationView(SimulationManager manager){
        this.manager = manager;
        content = getContentPane();
        content.setLayout(new BorderLayout());

    }


    public void displayQueues(Task[][] tasks, List<Task> generatedTasks, int currentTime, int ok) throws InterruptedException {
        // remove all previous elements
        panel.removeAll();
        content.removeAll();
        content.repaint();

        content.setLayout((new BorderLayout()));
        GridLayout layout = new GridLayout(2, manager.getNumberOfServers());
        panel.setLayout(layout);
        panel1 = new JPanel(new BorderLayout());
        panel2 = new JPanel(new BorderLayout());


        if (ok==0)
            currentTimeL.setText("Current Time=" + currentTime);
        else {
            currentTimeL.setText("The end");
        }


        panel1.add(currentTimeL, BorderLayout.NORTH);


        generatedTasksL.setText("Remaining Tasks");
        panel2.add(generatedTasksL, BorderLayout.NORTH);

        DefaultListModel listModel;
        listModel = new DefaultListModel();
        for (int i = 0; i < generatedTasks.size(); i++) {
            listModel.addElement(generatedTasks.get(i));
        }
        // Create the list and put it in a scroll pane.
        JList list;
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        panel2.add(listScrollPane, BorderLayout.SOUTH);

        for (int i = 0; i < manager.getNumberOfServers(); i++) {
            int k=i+1;
            JLabel serverNumberL = new JLabel("Queue " + k +": ");
            serverNumberL.setVerticalAlignment(JLabel.BOTTOM);
            serverNumberL.setHorizontalAlignment(JLabel.CENTER);
            panel.add(serverNumberL);
        }
        for (int i = 0; i < manager.getNumberOfServers(); i++) {
            // System.out.println("i="+i);
            listModel = new DefaultListModel();
            for (int j = 0; j < tasks[i].length; j++) {
                listModel.addElement(tasks[i][j]);
            }

            // creaza lista si o pune in scroll pane.
            list = new JList(listModel);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setVisibleRowCount(5);
            listScrollPane = new JScrollPane(list);
            panel.add(listScrollPane);
        }

        content.add(panel, BorderLayout.CENTER);
        content.add(panel1, BorderLayout.NORTH);
        content.add(panel2, BorderLayout.SOUTH);

        if (contor) {
            this.pack();
            contor = false;
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
