package graphvisualization;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class BestSolutionPanel extends JPanel {

    private JLabel bestSolutiontitle;

    /**
     * Constructor to initialise the BestSolutionPanel and insert a
     * SchedulePanel to contain the scheduled nodes in their
     * respective processors
     */
    public BestSolutionPanel() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(250, 300));
        this.setBorder(BorderFactory.createLineBorder(Color.blue));


        this.bestSolutiontitle = new JLabel("Current Best Solution");
        this.add(bestSolutiontitle, BorderLayout.NORTH);
    }

}

