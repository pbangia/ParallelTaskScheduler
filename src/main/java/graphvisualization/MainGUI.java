package graphvisualization;

import app.data.Node;
import app.exceptions.utils.NoRootFoundException;
import app.schedule.SchedulerHelper;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class MainGUI extends JFrame {

    public static MainGUI mainWindow;
    private StatisticsPanel statisticsPanel;
    private BestSolutionPanel bestSolutionPanel;
    private int numberOfCores = 1;
    private Map<String, app.data.Node> dataMap;
    private GraphPanel graphPanel;

    /**
     * MainGUI constructor that sets up the main JFrame which
     * will be populated by elements to display information
     */
    public MainGUI() {
        // temp variables that should be passed as arguments
        int numberOfElements = numberOfCores + 2;

        // creates initial frame
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 500);
        setMinimumSize(new Dimension(1200, 600));
        this.setTitle("Graph Visualisation");
        this.setLayout(new GridLayout(0, numberOfElements, 0, 0));
        // TODO Find a better layout for this JFrame

        statisticsPanel = new StatisticsPanel("file name",1,2,3);
        bestSolutionPanel = new BestSolutionPanel();

        this.add(statisticsPanel);
        this.add(bestSolutionPanel);
    }

    private Graph makeInputGraph(Map<String, app.data.Node> dataMap){
        this.dataMap = dataMap;

        Graph inputGraph = new SingleGraph("InputGraph");

        Iterator<Map.Entry<String, app.data.Node>> entryIterator = dataMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, app.data.Node> currentEntry = entryIterator.next();
            String nodeName = currentEntry.getValue().getName();

            if(inputGraph.getNode(nodeName) == null ){

                org.graphstream.graph.Node node = inputGraph.addNode(nodeName);
                try {
                    Thread.sleep(10);
                    node.addAttribute("ui.label",nodeName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Map<app.data.Node, Integer> childMap = currentEntry.getValue().getChildrenMap();

            Iterator<Map.Entry<app.data.Node, Integer>> childIterator = childMap.entrySet().iterator();
            while (childIterator.hasNext()) {
                Map.Entry<app.data.Node, Integer> childEntry = childIterator.next();
                String childName = childEntry.getKey().getName();

                if(inputGraph.getNode(childName) == null){

                    org.graphstream.graph.Node childNode = inputGraph.addNode(childName);
                    try {
                        Thread.sleep(10);
                        childNode.addAttribute("ui.label",childName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                String edgeName = nodeName + " -> " + childName;
                inputGraph.addEdge(edgeName, nodeName, childName,true);

            }
        }

        try {
            Thread.sleep(10);
        } catch (Exception e) {}
        inputGraph.addAttribute("ui.antialias");

        return inputGraph;
    }

    public void setGraphPanel(int numberOfCores, Map<String, app.data.Node> dataMap){
        this.numberOfCores = numberOfCores;

        Graph inputGraph = this.makeInputGraph(dataMap);
        GraphPanel inputGraphPanel = new GraphPanel(inputGraph);
        this.graphPanel = inputGraphPanel;
        this.add(inputGraphPanel);

        SchedulerHelper helper = new SchedulerHelper();

        try {
            ArrayList<Node> roots = helper.findRoots(dataMap);
            for (Node root : roots) {
                this.graphPanel.colorRootNodes(root.getName(), Color.green);
            }
        } catch (NoRootFoundException e){}

        int numberOfElements = numberOfCores + 2;
        this.setLayout(new GridLayout(0, numberOfElements, 0, 0));
        this.validate();
    }


    public void updateCurrentBestLength(int endTime) {
        statisticsPanel.updateCurrentBestLength(endTime);
    }

    public void updateNumberOfSolutionsExplored() {
        statisticsPanel.updateNumberOfSolutionsExplored();
    }

    public void setStatisticsInfo(String inputFileName, int numberOfNodes, int numberOfEdges, int numberOfCores) {
        statisticsPanel.setInputFileName(inputFileName);
        statisticsPanel.setNumberOfNodes(numberOfNodes);
        statisticsPanel.setNumberOfEdges(numberOfEdges);
        statisticsPanel.setNumberOfProcessors(numberOfCores);
    }

    /**
     * getInstance method for singleton pattern. Will instantiate MainGUI
     * object if null.
     * @return the MainGUI singleton instance
     */

    public static MainGUI getInstance() {
        if (mainWindow == null) {
            mainWindow = new MainGUI();
            return mainWindow;
        }
        return mainWindow;
    }

    public BestSolutionPanel getBestSolutionPanel() {
        return this.bestSolutionPanel;
    }



}

