package app.schedule;


import app.data.Node;
import app.data.PartialSolution;
import app.exceptions.utils.NoRootFoundException;
import graphvisualization.SolutionVisualizer;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TaskScheduler {

    private static Logger logger = LoggerFactory.getLogger(TaskScheduler.class);

    private final Collection<Node> nodes;
    private SchedulerHelper schedulerHelper;
    private int numberOfProcessors;
    private SolutionVisualizer sv;

    // Constructor For Class
    public TaskScheduler(Collection<Node> nodes, SchedulerHelper schedulerHelper, int numberOfProcessors) {
        this.nodes = nodes;
        this.schedulerHelper = schedulerHelper;
        this.numberOfProcessors = numberOfProcessors;
        this.sv = new SolutionVisualizer();

    }

    public PartialSolution scheduleTasks() throws NoRootFoundException {
        Graph g = new SingleGraph("SolutionTree");
        sv.init(g);

        PartialSolution bestPartialSolution = null;
        Stack<PartialSolution> solutionStack = new Stack<>();
        List<Node> nextAvailableNodes;

        solutionStack.push(new PartialSolution(numberOfProcessors, nodes, 0));
        sv.addRoot();

        while (!solutionStack.empty()) {
            PartialSolution currentPartialSolution = solutionStack.pop();
            Set<Node> scheduledNodes = currentPartialSolution.getScheduledNodes();
            Set<Node> unscheduledNodes = currentPartialSolution.getUnscheduledNodes();

            nextAvailableNodes = schedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes);

            if (currentPartialSolution.isWorseThan(bestPartialSolution)) {
                continue;
            }

            if (nextAvailableNodes.isEmpty()) {
                bestPartialSolution = currentPartialSolution;
                logger.debug("New optimal solution found: \n" + bestPartialSolution.toString());
                continue;
            }

            for (Node availableNode : nextAvailableNodes) {
                List<PartialSolution> availablePartialSolutions = schedulerHelper.getAvailablePartialSolutions(availableNode, currentPartialSolution, numberOfProcessors);
                solutionStack.addAll(availablePartialSolutions);

                for (PartialSolution p: availablePartialSolutions){
                    //sv.addNode(currentPartialSolution.getId(), p.getId());
                }
            }
        }

        return bestPartialSolution;
    }

}
