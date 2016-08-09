package app.schedule;


import app.data.Node;
import app.schedule.solutionEntities.PartialSolution;

import java.util.*;

public class TaskScheduler {

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

    public PartialSolution scheduleTasks() {

        PartialSolution bestPartialSolution = null;
        Stack<PartialSolution> solutionStack = new Stack<>();
        List<Node> nextAvailableNodes = new ArrayList<>();

        solutionStack.push(new PartialSolution(numberOfProcessors));

        while (!solutionStack.empty()) {
            PartialSolution currentPartialSolution = solutionStack.pop();
            Node latestNodeAdded = currentPartialSolution.getLatestNode();
            scheduledNodes = currentPartialSolution.getScheduledNodes();
            nextAvailableNodes.addAll(schedulerHelper.getAvailableNodes(latestNodeAdded, dataMap, scheduledNodes));

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

    // This function should probably be in scheduler helper, it gets the list of partialSolutions possible based on
    // the current solution and the node being added

    public List<PartialSolution> getAvailablePartialSolutions(Node nodeAdded, PartialSolution currentPartialSolution, int numberOfProcessors) {
        //declare a new list to store the partail solutions
        List<PartialSolution> availablePartialSolutions = new ArrayList<>();
        //generates a partial solution for each processor
        for (int i = 0; i < numberOfProcessors; i++) {
            //constructs new partial solution object
            PartialSolution newPartialSolution = new PartialSolution(numberOfProcessors);
            //clones the current solution
            //adds the node being scheduled into a processor depending on loop counter
            newPartialSolution.addNodeToProcessor(nodeAdded, i, 0);
            //adds the new partial solution to the list of partial solutions possible
            availablePartialSolutions.add(newPartialSolution);
        }
        return availablePartialSolutions;
    }
}
