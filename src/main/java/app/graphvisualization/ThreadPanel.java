package app.graphvisualization;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;

public class ThreadPanel extends JPanel {

    private JLabel threadPanelTitle;
    private JTable threadTable;
    private DefaultTableModel model;
    private JScrollPane tableContainer;
    private HashMap<String, Integer> columnNameMap;
    private int rowIndexOfPrevious;
    private int colIndexOfPrevious = 1;

    public ThreadPanel() {
        this.setLayout(new BorderLayout());
        //this.setPreferredSize(new Dimension(250, 300));
        this.setBorder(BorderFactory.createLineBorder(Color.gray));

        threadTable = new JTable(new DefaultTableModel(new Object[]{"Thread"}, 0));
        tableContainer = new JScrollPane(threadTable);
        this.add(tableContainer, BorderLayout.CENTER);

        this.threadPanelTitle = new JLabel("Threads");
        this.add(threadPanelTitle, BorderLayout.NORTH);
    }

    public synchronized void colorCell(String threadNumber, String nodeName) {
        int colIndex = columnNameMap.get(nodeName);
        int rowIndex = Integer.valueOf(threadNumber) - 1;

        //System.out.println("Row size=" + threadTable.getRowCount());

        threadTable.setValueAt("", rowIndexOfPrevious, colIndexOfPrevious);
        threadTable.setValueAt("Scheduling", rowIndex, colIndex);

        this.rowIndexOfPrevious = rowIndex;
        this.colIndexOfPrevious = colIndex;
    }

    public void setTableModel(DefaultTableModel model) {
        this.model = model;
        this.threadTable.setModel(model);
        model.fireTableDataChanged();
    }

    public JTable getTable() {
        return this.threadTable;
    }

    public void setColumnNameMap(HashMap<String, Integer> columnNameMap) {
        this.columnNameMap = columnNameMap;
    }
}
