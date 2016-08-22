package graphvisualization;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gtiongco95 on 22/08/16.
 */
public class StatisticsPanel extends JPanel {

    // Labels
    private JLabel statisticsPanelTitle;
    private JLabel inputFileName;

    public StatisticsPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        statisticsPanelTitle = new JLabel("Statistics");
        inputFileName = new JLabel("Input file name: input.dot");
        this.setPreferredSize(new Dimension(250, 300));
        this.setBorder(BorderFactory.createLineBorder(Color.blue));
        this.add(statisticsPanelTitle);
        this.add(new JSeparator(SwingConstants.HORIZONTAL));
        this.add(inputFileName);


    }
}

