package app.graphvisualization;


import app.schedule.datatypes.PartialSolution;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import static app.io.Syntax.NEW_LINE;

public class BestSolutionPanel extends JPanel {

    private JLabel bestSolutionTitle;
    private JTable solutionTable;
    private JScrollPane tableContainer;

    /**
     * Constructor to initialise the BestSolutionPanel and insert a
     * SchedulePanel to contain the scheduled nodes in their
     * respective processors
     */
    public BestSolutionPanel() {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.gray));

        solutionTable = new JTable(new DefaultTableModel(new Object[]{"Name", "Weight", "Start time", "Processor"}, 0));
        tableContainer = new JScrollPane(solutionTable);
        this.add(tableContainer, BorderLayout.CENTER);

        this.bestSolutionTitle = new JLabel("Current Best Solution");
        this.add(bestSolutionTitle, BorderLayout.NORTH);
    }

    /**
     * Updates the table in the GUI to show the values of the new current
     * best solution.
     * @param newBestSolution PartialSolution object containing info for
     *                        new best solution.
     */
    public synchronized void updateSolutionTable(PartialSolution newBestSolution) {
        DefaultTableModel model = (DefaultTableModel) solutionTable.getModel();
        int rowCount = model.getRowCount();

        String output = newBestSolution.toString();
        String[] definitions = output.split(NEW_LINE);

        if (rowCount == 0){
            for (String definition : definitions) {
                String nodeName = definition.substring(0, definition.indexOf("[")).trim();
                String weight = definition.substring(definition.indexOf("Weight=") + 7, definition.indexOf(",Start"));
                String start = definition.substring(definition.indexOf("Start=") + 6, definition.indexOf(",Processor"));
                String processor = definition.substring(definition.indexOf("Processor=") + 10, definition.indexOf("]"));
                model.addRow(new Object[]{nodeName, weight, start, processor});
            }
        }

        int j = 0;
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);

            String nodeName = definitions[j].substring(0, definitions[j].indexOf("[")).trim();
            String weight = definitions[j].substring(definitions[j].indexOf("Weight=") + 7, definitions[j].indexOf(",Start"));
            String start = definitions[j].substring(definitions[j].indexOf("Start=") + 6, definitions[j].indexOf(",Processor"));
            String processor = definitions[j].substring(definitions[j].indexOf("Processor=") + 10, definitions[j].indexOf("]"));

            model.addRow(new Object[]{nodeName, weight, start, processor});
            j++;
            model.fireTableDataChanged();
        }



    }
}

