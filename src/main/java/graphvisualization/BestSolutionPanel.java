package graphvisualization;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class BestSolutionPanel extends JPanel {

    private JLabel bestSolutiontitle;
    private SchedulePanel schedulePanel;
    // Unsure of how we should store the NodePanels
    private HashMap<String, NodePanel> graphNodePanelsMap;

    /**
     * Constructor to initialise the BestSolutionPanel and insert a
     * SchedulePanel to contain the scheduled nodes in their
     * respective processors
     * @param numberOfProcessors used to set the number of columns
     *                           for the schedulePanel's layout
     */
    public BestSolutionPanel(int numberOfProcessors) {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(250, 300));
        this.setBorder(BorderFactory.createLineBorder(Color.blue));


        this.bestSolutiontitle = new JLabel("Current Best Solution");
        this.add(bestSolutiontitle, BorderLayout.NORTH);
        this.schedulePanel = new SchedulePanel(numberOfProcessors);
        this.add(schedulePanel, BorderLayout.CENTER);
    }

    public SchedulePanel getSchedulePanel() {
        return this.schedulePanel;
    }

    public HashMap<String, NodePanel> getGraphNodePanelsMap() {
        return this.graphNodePanelsMap;
    }

}

