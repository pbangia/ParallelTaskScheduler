package app.schedule;


import app.data.Node;
import app.data.PartialSolution;
import app.exceptions.utils.NoRootFoundException;
import graphvisualization.MainGUI;
import graphvisualization.SolutionVisualizer;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TaskScheduler {

    private static Logger logger = LoggerFactory.getLogger(TaskScheduler.class);

    private final Collection<Node> nodes;
    private SchedulerHelper schedulerHelper;
    private int numberOfProcessors;
    private int idCounter = 0;
    private String previousNode = null;

    // Constructor For Class
    public TaskScheduler(Collection<Node> nodes, SchedulerHelper schedulerHelper, int numberOfProcessors) {
        this.nodes = nodes;
        this.schedulerHelper = schedulerHelper;
        this.numberOfProcessors = numberOfProcessors;
    }

    public PartialSolution scheduleTasks() throws NoRootFoundException {



        Graph g = new SingleGraph("SolutionTree");

        PartialSolution bestPartialSolution = null;
        Stack<PartialSolution> solutionStack = new Stack<>();
        List<Node> nextAvailableNodes;

        solutionStack.push(new PartialSolution(numberOfProcessors, nodes, 0));
        idCounter++;

        while (!solutionStack.empty()) {
            PartialSolution currentPartialSolution = solutionStack.pop();
            Set<Node> scheduledNodes = currentPartialSolution.getScheduledNodes();
            Set<Node> unscheduledNodes = currentPartialSolution.getUnscheduledNodes();

            nextAvailableNodes = schedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes);

            if (currentPartialSolution.isWorseThan(bestPartialSolution)) {
                currentPartialSolution = null;
                continue;
            }

            if (nextAvailableNodes.isEmpty()) {
                bestPartialSolution = currentPartialSolution;
                logger.debug("New optimal solution found: \n" + bestPartialSolution.toString());
                continue;
            }

            for (Node availableNode : nextAvailableNodes) {

                MainGUI.getInstance().changeColor(0, availableNode.getName(), Color.green);
                if (previousNode != null){
                    MainGUI.getInstance().changeColor(0, previousNode, Color.black);
                }
                previousNode = availableNode.getName();

                List<PartialSolution> availablePartialSolutions = schedulerHelper.getAvailablePartialSolutions(availableNode, currentPartialSolution, numberOfProcessors, idCounter);
                solutionStack.addAll(availablePartialSolutions);

                for (PartialSolution p: availablePartialSolutions){
                    idCounter++;
                }
            }
        }

        return bestPartialSolution;
    }

}

