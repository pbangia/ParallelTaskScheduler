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

            if (nextAvailableNodes.contains(latestNodeAdded)){
                nextAvailableNodes.remove(latestNodeAdded);
            }
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
    //declare a new list to store the partail solutions
    //constructs new partial solution object
    //clones the current solution
    //adds the node being scheduled into a processor depending on loop counter
    //generates a partial solution for each processor
    //adds the new partial solution to the list of partial solutions possible

    public List<PartialSolution> getAvailablePartialSolutions(Node nodeAdded, PartialSolution currentPartialSolution, int numberOfProcessors) {

        List<PartialSolution> availablePartialSolutions = new ArrayList<>();

        for (int i = 0; i < numberOfProcessors; i++) {

            PartialSolution newPartialSolution = new PartialSolution(numberOfProcessors);

            newPartialSolution.addNodeToProcessor(nodeAdded, i, 0);

            availablePartialSolutions.add(newPartialSolution);
        }
        return availablePartialSolutions;
    }
}
