package app.schedule;

import app.data.Node;
import app.data.PartialSolution;

import java.util.*;

/**
 * Created by User on 23/08/2016.
 */
public class SerialScheduler extends CommonScheduler {

    private Collection<Node> nodes;
    private SchedulerHelper schedulerHelper;

    // Constructor For Class
    public SerialScheduler(Collection<Node> nodes, SchedulerHelper schedulerHelper, int numberOfProcessors) {
        this.nodes = nodes;
        this.schedulerHelper = schedulerHelper;
        this.numberOfProcessors = numberOfProcessors;
    }

    @Override
    public PartialSolution scheduleTasks(){

        PartialSolution bestPartialSolution = null;
        Stack<PartialSolution> solutionStack = new Stack<>();
        List<Node> nextAvailableNodes;

        solutionStack.push(new PartialSolution(numberOfProcessors, nodes));

        while (!solutionStack.empty()) {
            PartialSolution currentPartialSolution = solutionStack.pop();
            Set<Node> scheduledNodes = currentPartialSolution.getScheduledNodes();
            Set<Node> unscheduledNodes = currentPartialSolution.getUnscheduledNodes();

            nextAvailableNodes = schedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes);

            // Hit if clause when leaf is reached
            if (nextAvailableNodes.isEmpty()) {
                if (currentPartialSolution.isBetterThan(bestPartialSolution)) {
                    bestPartialSolution = currentPartialSolution;
                }
                continue;
            }

            if (currentPartialSolution.isBetterThan(bestPartialSolution)) {
                for (Node availableNode : nextAvailableNodes) {
                    List<PartialSolution> availablePartialSolutions = getAvailablePartialSolutions(availableNode, currentPartialSolution, numberOfProcessors);
                    solutionStack.addAll(availablePartialSolutions);
                }
            }
        }

        return bestPartialSolution;
    }

    /**
     * This function returns a list of partialSolutions possible based on the current solution and the node being added.
     * It does this by constructing new partial solution objects (by cloning the current solution) and then adding the
     * specified Node to each possible partial solution in every single processor of that partial solution.
     *
     * @param nodeToAdd              Node to be added to the partial solution provided
     * @param currentPartialSolution the partial solution provided
     * @param numberOfProcessors     number of processors in each partial solution
     * @return list of all partial solutions that correspond to the next available partial solutions
     */

    // TODO move to helper class
    public List<PartialSolution> getAvailablePartialSolutions(Node nodeToAdd, PartialSolution currentPartialSolution, int numberOfProcessors) {

        List<PartialSolution> availablePartialSolutions = new ArrayList<>();

        for (int i = 0; i < numberOfProcessors; i++) {
            PartialSolution newPartialSolution = new PartialSolution(numberOfProcessors, currentPartialSolution);
            newPartialSolution.addNodeToProcessor(nodeToAdd, i);
            availablePartialSolutions.add(newPartialSolution);
        }

        return availablePartialSolutions;
    }
}

