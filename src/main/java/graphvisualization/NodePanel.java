package graphvisualization;

import javax.swing.*;
import java.awt.*;

public class NodePanel extends JPanel{

    private JLabel nodeNameLabel;
    private String nodeName;
    private int startTime;
    private int weight;


    /**
     * Constructor to initialise the NodePanel to display the
     * name of the node it represents and be of respective size
     * to signify the node's weight.
     * @param nodeName name of the node
     * @param startTime start time of node
     * @param weight weight of node
     */
    public NodePanel(String nodeName, int startTime, int weight) {
        this.nodeName = nodeName;
        this.startTime = startTime;
        this.weight = weight;

        this.setLayout(new BorderLayout());
        this.nodeNameLabel = new JLabel(nodeName);
        this.add(this.nodeNameLabel, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(50, weight * 3));
        this.setBorder(BorderFactory.createLineBorder(Color.blue));

        // Do not add blank panels to the map
        if (!nodeName.equals("")) {
            MainGUI.getInstance().getBestSolutionPanel().getGraphNodePanelsMap().put(this.nodeName, this);
        }

        // Will need to implement check to see if there are gaps and if
        // so create blank panels to fit those gaps will just be NodePanels
        // with a blank name
    }

}

