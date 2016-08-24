package app.graphvisualization;

import javax.swing.*;
import java.awt.*;

public class StatisticsPanel extends JPanel {

    // Labels
    private JLabel statisticsPanelTitle;
    private JLabel inputFileName;
    private JLabel numberOfNodes;
    private JLabel numberOfEdges;
    private JLabel numberOfProcessors;
    private JLabel currentBestLength;
    private JLabel numberOfSolutionsExplored;
    private int solutionsExploredCount;

    public StatisticsPanel(String inputFileName, int numberOfNodes, int numberOfEdges, int numberOfProcessors) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(250, 300));
        this.setBorder(BorderFactory.createLineBorder(Color.gray));

        this.statisticsPanelTitle = new JLabel("Statistics");
        this.inputFileName = new JLabel("Input file name:  " + inputFileName);
        this.numberOfNodes = new JLabel("Number of Nodes:  " + numberOfNodes);
        this.numberOfEdges = new JLabel("Number of Edges:  " + numberOfEdges);
        this.numberOfProcessors = new JLabel("Number of Processes:  " + numberOfProcessors);
        this.currentBestLength = new JLabel("Current best time:  ");
        this.numberOfSolutionsExplored = new JLabel("Solutions explored:  ");
        this.solutionsExploredCount = 0;

        this.add(statisticsPanelTitle);
        this.add(new JLabel("     "));
        this.add(this.inputFileName);
        this.add(this.numberOfNodes);
        this.add(this.numberOfEdges);
        this.add(this.numberOfProcessors);
        this.add(this.currentBestLength);
        this.add(this.numberOfSolutionsExplored);
    }

    public void updateCurrentBestLength(int endTime) {
        this.currentBestLength.setText("Current best time:  " + endTime);
    }

    public void updateNumberOfSolutionsExplored() {
        this.solutionsExploredCount++;
        this.numberOfSolutionsExplored.setText("Solutions explored:  " + solutionsExploredCount);
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName.setText("Input file name:  " + inputFileName);
    }

    public void setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes.setText("Number of Nodes:  " + numberOfNodes);
    }

    public void setNumberOfEdges(int numberOfEdges) {
        this.numberOfEdges.setText("Number of Edges:  " + numberOfEdges);
    }

    public void setNumberOfProcessors(int numberOfProcessors) {
        this.numberOfProcessors.setText("Number of Processes:  " + numberOfProcessors);
    }
}

