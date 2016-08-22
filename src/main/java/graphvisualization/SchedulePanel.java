package graphvisualization;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SchedulePanel extends JPanel {

    private ArrayList<ProcessorPanel> processorPanelList;

    /**
     * Constructor to initialise the schedulePanel and populate
     * it with the specified number of ProcessorPanels
     * @param numberOfProcessors
     */
    public SchedulePanel(int numberOfProcessors) {
        this.setLayout(new GridLayout(0 , numberOfProcessors));
    }

    /**
     * Add a ProcessorPanel to the SchedulePanel
     * @param processorPanelToAdd the ProcessorPanel we wish to add
     */
    public void addProcessorPanel(ProcessorPanel processorPanelToAdd) {
        this.processorPanelList.add(processorPanelToAdd);
        this.add(processorPanelToAdd);
    }

    public ProcessorPanel getProcessorPanel(int index) {
        return processorPanelList.get(index);
    }

    public ArrayList<ProcessorPanel> getProcessorPanelList() {
        return processorPanelList;
    }
}

