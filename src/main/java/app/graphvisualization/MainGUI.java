package app.graphvisualization;

import app.exceptions.utils.NoRootFoundException;
import app.schedule.SchedulerHelper;
import app.schedule.datatypes.Node;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class MainGUI extends JFrame {

    public static MainGUI mainWindow;
    private StatisticsPanel statisticsPanel;
    private BestSolutionPanel bestSolutionPanel;
    private ThreadPanel threadPanel;
    private int numberOfCores = 1;
    private Map<String, Node> dataMap;
    private GraphPanel graphPanel;

    /**
     * MainGUI constructor that sets up the main JFrame which
     * will be populated by elements to display information
     */
    public MainGUI() {

        // creates initial frame
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 600);
        setMinimumSize(new Dimension(1200, 600));
        this.setTitle("Scheduler Visualisation");
        this.setLayout(new GridLayout(2, 2, 0, 0));

        statisticsPanel = new StatisticsPanel("file name",1,2,3);
        bestSolutionPanel = new BestSolutionPanel();
        threadPanel = new ThreadPanel();

        this.add(statisticsPanel);
        this.add(bestSolutionPanel);
        this.add(threadPanel);
    }

    private Graph makeInputGraph(Map<String, Node> dataMap){
        this.dataMap = dataMap;

        Graph inputGraph = new SingleGraph("InputGraph");

        Iterator<Map.Entry<String, Node>> entryIterator = dataMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, Node> currentEntry = entryIterator.next();
            String nodeName = currentEntry.getKey();


            if(inputGraph.getNode(nodeName) == null ){

                org.graphstream.graph.Node node = inputGraph.addNode(nodeName);
                try {
                    Thread.sleep(10);
                    node.addAttribute("ui.label",nodeName+" ("+currentEntry.getValue().getWeight()+")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Map<Node, Integer> childMap = currentEntry.getValue().getChildrenMap();

            Iterator<Map.Entry<Node, Integer>> childIterator = childMap.entrySet().iterator();
            while (childIterator.hasNext()) {
                Map.Entry<Node, Integer> childEntry = childIterator.next();
                int dependency = childEntry.getValue();
                String childName = childEntry.getKey().getName();

                if(inputGraph.getNode(childName) == null){

                    org.graphstream.graph.Node childNode = inputGraph.addNode(childName);
                    try {
                        Thread.sleep(10);
                        childNode.addAttribute("ui.label",childName+" ("+childEntry.getKey().getWeight()+")");
                        childNode.addAttribute("ui.style","text-size:10px; fill-color: #333333;");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                String edgeName = nodeName + " -> " + childName;

                org.graphstream.graph.Edge edge = inputGraph.addEdge(edgeName, nodeName, childName,true);
                edge.addAttribute("ui.label", dependency);
                edge.addAttribute("ui.style", "arrow-size:4px; fill-color: #737373; text-size:8px;");

            }

        }

        try {
            Thread.sleep(10);
        } catch (Exception e) {}
        inputGraph.addAttribute("ui.antialias");

        return inputGraph;
    }

    public void setGraphPanel(int numberOfCores, Map<String, Node> dataMap){
        if (numberOfCores == 0) {
            numberOfCores = 1;
        }
        this.numberOfCores = numberOfCores;

        Graph inputGraph = this.makeInputGraph(dataMap);
        GraphPanel inputGraphPanel = new GraphPanel(inputGraph);
        this.graphPanel = inputGraphPanel;
        this.add(inputGraphPanel);

        SchedulerHelper helper = new SchedulerHelper();

        try {
            ArrayList<Node> roots = (ArrayList<Node>) helper.findRoots(dataMap);
            for (Node root : roots) {
                this.graphPanel.colorRootNodes(root.getName());
            }
        } catch (NoRootFoundException e){}

        validate();
    }

    public void setUpThreadTableModel(Map<String, Node> dataMap) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Thread"}, 0);
        Set<String> nodeNames = dataMap.keySet();
        HashMap<String, Integer> columnNameMap = new HashMap<>();
        int j = 1;

        for (String nodeName : nodeNames) {
            model.addColumn(nodeName);
            columnNameMap.put(nodeName, j);
            j++;
        }
        // add rows for number of threads
        for (int i = 1; i < this.numberOfCores + 1; i++) {
            model.addRow(new Object[]{i});
        }


        threadPanel.setTableModel(model);
        threadPanel.setColumnNameMap(columnNameMap);
        threadPanel.getTable().setDefaultRenderer(Object.class, new ThreadCellRenderer());
    }


    public synchronized void updateCurrentBestLength(int endTime) {
        statisticsPanel.updateCurrentBestLength(endTime);
    }

    public synchronized void updateNumberOfSolutionsExplored() {
        statisticsPanel.updateNumberOfSolutionsExplored();
    }


    public void setStatisticsInfo(String inputFileName, int numberOfNodes, int numberOfEdges, int numberOfCores) {
        statisticsPanel.setInputFileName(inputFileName);
        statisticsPanel.setNumberOfNodes(numberOfNodes);
        statisticsPanel.setNumberOfEdges(numberOfEdges);
        statisticsPanel.setNumberOfProcessors(numberOfCores);
    }

    public void setCompleteStatus(){
        statisticsPanel.updateStatus("Status: Complete");
    }

    /**
     * get method for singleton pattern. Will instantiate MainGUI
     * object if null.
     * @return the MainGUI singleton instance
     */

    public static MainGUI get() {
        if (mainWindow == null) {
            mainWindow = new MainGUI();
            return mainWindow;
        }
        return mainWindow;
    }

    public BestSolutionPanel getBestSolutionPanel() {
        return this.bestSolutionPanel;
    }
    public ThreadPanel getThreadPanel() { return this.threadPanel;}
}



