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

        String output = newBestSolution.toString();
        String[] definitions = output.split(NEW_LINE);

        for (int i = 0; i < definitions.length; i++) {
            String[] parts = parseDefinition(definitions[i]);
            if (model.getRowCount() > i) {  //if model already has this row, just replace the values
                for (int j = 0; j < parts.length; j++) {
                    model.setValueAt(parts[j], i, j);
                }
            } else {
                model.addRow(parts);
            }
        }

        model.fireTableDataChanged();
    }

    private static String[] parseDefinition(String definition) {
        String[] parts = new String[4];
        parts[0] = definition.substring(0, definition.indexOf("[")).trim();
        parts[1] = definition.substring(definition.indexOf("Weight=") + 7, definition.indexOf(",Start"));
        parts[2] = definition.substring(definition.indexOf("Start=") + 6, definition.indexOf(",Processor"));
        parts[3] = definition.substring(definition.indexOf("Processor=") + 10, definition.indexOf("]"));
        return parts;
    }
}

