package graphvisualization;

import app.data.PartialSolution;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static app.io.Syntax.DEFINITION_DELIMITER;
import static app.io.Syntax.DEPENDENCY_ARROW;
import static app.io.Syntax.NEW_LINE;

public class BestSolutionPanel extends JPanel {

    private JLabel bestSolutionTitle;
    private JTable solutionTable;
    private DefaultTableModel model;
    private JScrollPane tableContainer;

    /**
     * Constructor to initialise the BestSolutionPanel and insert a
     * SchedulePanel to contain the scheduled nodes in their
     * respective processors
     */
    public BestSolutionPanel() {
        this.setLayout(new BorderLayout());
        //this.setPreferredSize(new Dimension(250, 300));
        this.setBorder(BorderFactory.createLineBorder(Color.gray));

        solutionTable = new JTable(new DefaultTableModel(new Object[]{"Name", "Weight", "Start time", "Processor"}, 0));
        tableContainer = new JScrollPane(solutionTable);
        this.add(tableContainer, BorderLayout.CENTER);

        this.bestSolutionTitle = new JLabel("Current Best Solution");
        this.add(bestSolutionTitle, BorderLayout.NORTH);



        DefaultTableModel model = (DefaultTableModel) solutionTable.getModel();
        model.addRow(new Object[]{"a", "2", "0", "0"});
    }

    /**
     * Updates the table in the GUI to show the values of the new current
     * best solution.
     * @param newBestSolution PartialSolution object containing info for
     *                        new best solution.
     */
    public void updateSolutionTable(PartialSolution newBestSolution) {
        DefaultTableModel model = (DefaultTableModel) solutionTable.getModel();
        int rowCount = model.getRowCount();
        // Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        String output = newBestSolution.toString();
        String[] definitions = output.split(NEW_LINE);
        for (String definition : definitions) {
            String nodeName = definition.substring(0, definition.indexOf("[")).trim();
            String weight = definition.substring(definition.indexOf("Weight=") + 7, definition.indexOf(",Start"));
            String start = definition.substring(definition.indexOf("Start=") + 6, definition.indexOf(",Processor"));
            String processor = definition.substring(definition.indexOf("Processor=") + 10, definition.indexOf("]"));

            model.addRow(new Object[]{nodeName, weight, start, processor});
        }

        model.fireTableDataChanged();

    }
}

