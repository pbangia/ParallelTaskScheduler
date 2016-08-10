package app.schedule;


import app.data.Node;
import app.exceptions.utils.NoRootFoundException;
import app.data.PartialSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TaskScheduler {

    private static Logger logger = LoggerFactory.getLogger(TaskScheduler.class);

    private Map<String, Node> dataMap;
    private SchedulerHelper schedulerHelper;
    private int numberOfProcessors;
    private Set<Node> scheduledNodes = new HashSet<>();

    // Constructor For Class
    public TaskScheduler(Map<String, Node> dataMap, SchedulerHelper schedulerHelper, int numberOfProcessors) {
        this.dataMap = dataMap;
        this.schedulerHelper = schedulerHelper;
        this.numberOfProcessors = numberOfProcessors;
    }

    public PartialSolution scheduleTasks() throws NoRootFoundException {

        PartialSolution bestPartialSolution = null;
        Stack<PartialSolution> solutionStack = new Stack<>();
        List<Node> nextAvailableNodes = new ArrayList<>();

        solutionStack.push(new PartialSolution(numberOfProcessors));

        while (!solutionStack.empty()) {
            PartialSolution currentPartialSolution = solutionStack.pop();
            Node latestNodeAdded = currentPartialSolution.getLatestNode();
            scheduledNodes = currentPartialSolution.getScheduledNodes();

            nextAvailableNodes = schedulerHelper.getAvailableNodes(dataMap, scheduledNodes);

            if (nextAvailableNodes.isEmpty()) {
                if (currentPartialSolution.isBetterThan(bestPartialSolution)) {
                    bestPartialSolution = currentPartialSolution;
                }
                continue;
            }

            if (currentPartialSolution.isBetterThan(bestPartialSolution)) {
                for (Node availableNode : nextAvailableNodes) {
                    List<PartialSolution> availablePartialSolutions = getAvailablePartialSolutions(availableNode, currentPartialSolution, numberOfProcessors);
                    for (PartialSolution partialSolution : availablePartialSolutions) {
                        solutionStack.push(partialSolution);
                    }
                }
            }
        }

        return bestPartialSolution;
    }

    /**
     This function returns a list of partialSolutions possible based on the current solution and the node being added.
     It does this by constructing new partial solution objects (by cloning the current solution) and then adding the
     specified Node to each possible partial solution in every single processor of that partial solution.
     * @param nodeAdded Node to be added to the partial solution provided
     * @param currentPartialSolution the partial solution provided
     * @param numberOfProcessors number of processors in each partial solution
     * @return list of all partial solutions that correspond to the next available partial solutions
     */
    public List<PartialSolution> getAvailablePartialSolutions(Node nodeAdded, PartialSolution currentPartialSolution, int numberOfProcessors) {

        List<PartialSolution> availablePartialSolutions = new ArrayList<>();

        for (int i = 0; i < numberOfProcessors; i++) {
            PartialSolution newPartialSolution = new PartialSolution(numberOfProcessors, currentPartialSolution);
            newPartialSolution.addNodeToProcessor(nodeAdded, i);
            availablePartialSolutions.add(newPartialSolution);
        }

        return availablePartialSolutions;
    }
}
