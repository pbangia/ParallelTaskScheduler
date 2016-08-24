package app.graphvisualization;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


public class ThreadCellRenderer extends DefaultTableCellRenderer{
    @Override
    public synchronized Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        Object valueAt = table.getModel().getValueAt(row, col);
        String s = "";
        if (valueAt != null) {
            s = valueAt.toString();
        }

        if (s.equalsIgnoreCase("scheduling")) {
            c.setBackground(Color.GREEN);
        } else {
            c.setBackground(Color.WHITE);
        }

        return c;
    }
}
