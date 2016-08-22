package graphvisualization;

import javax.swing.*;
import java.util.List;

public class ProcessorPanel extends JPanel{

    private List<NodePanel> processorNodeList;

    /**
     * Constructor to initialise the ProcessorPanel to contain NodePanels
     * for displaying the scheduling scheme of the algorithm
     */
    public ProcessorPanel() {
        // TODO test this layout (can find a new one)
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Add a NodePanel to a ProcessorPanel
     * @param nodePanelToAdd the NodePanel we wish to add
     */
    public void addNodePanel(NodePanel nodePanelToAdd) {
        this.add(nodePanelToAdd);
        this.processorNodeList.add(nodePanelToAdd);
    }

    public void checkNeedForGapPanel() {
        // TODO implement check for determining gap if one needed.
        // iterate through nodeList to check for gaps.
        // If one is present create a blank NodePanel and
        // add it where needed.
        // Only really need to check the last added node and
        // see the difference in nodeToAdd's start time and
        // lastNodeAdded's finish time.
    }

    public void removeNodePanel(NodePanel nodePanelToRemove) {
        // TODO implement the removal of specified NodePanel
        // Should only really be removing and moving it to
        // another ProcessorPanel. Will need to remove
        // preceeding blank NodePanels
    }

    public List<NodePanel> getProcessorNodeList() {
        return this.processorNodeList;
    }
}

