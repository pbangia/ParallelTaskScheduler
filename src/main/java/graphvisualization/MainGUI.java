package graphvisualization;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainGUI extends JFrame {

    public static MainGUI mainWindow;
    private HashMap<Integer, GraphPanel> graphPanelsMap;
    private StatisticsPanel statisticsPanel;
    private BestSolutionPanel bestSolutionPanel;

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ex) { ex.printStackTrace(); }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainGUI.getInstance().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * MainGUI constructor that sets up the main JFrame which
     * will be populated by elements to display information
     */
    public MainGUI() {
        // temp variables that should be passed as arguments
        int numberOfElements = 6;
        int numberOfProcessors = 4;
        int numberOfThreads = 4;

        // creates initial frame
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 500);
        setMinimumSize(new Dimension(1200, 600));
        this.setTitle("Graph Visualisation");
        this.setLayout(new GridLayout(0, numberOfElements, 0, 0));
        // TODO Find a better layout for this JFrame

        statisticsPanel = new StatisticsPanel();
        bestSolutionPanel = new BestSolutionPanel();

        this.add(statisticsPanel);
        this.add(bestSolutionPanel);

//        for (int i = 0; i < numberOfThreads; i++) {
//            GraphPanel graphPanel = new GraphPanel();
//            graphPanelsMap.put(i, graphPanel);
//        }

    }

    /**
     * getInstance method for singleton pattern. Will instantiate MainGUI
     * object if null.
     * @return the MainGUI singleton instance
     */
    public static MainGUI getInstance() {
        if (mainWindow == null) {
            return new MainGUI();
        }
        return mainWindow;
    }

    public BestSolutionPanel getBestSolutionPanel() {
        return this.bestSolutionPanel;
    }

}

