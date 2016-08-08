package app.schedule;


import app.data.Node;

import java.util.*;
import app.schedule.solutionEntities.*;

public class TaskScheduler {

    private Set<Node> scheduledNodes = new HashSet<>();
    private Map<String, Node> dataMap;
    private SchedulerHelper schedulerHelper;
    private int numberOfProcessors;

    private PartialSolution currentPartialSolution;
    private PartialSolution bestPartialSolution;
    private Stack<PartialSolution> solutionStack;

    private Node latestNodeAdded;
    private List<Node> nextAvailableNodes;
    private List<PartialSolution> availablePartialSolutions;

    // Constructor For Class
    public TaskScheduler(Map<String, Node> dataMap, SchedulerHelper schedulerHelper, int numberOfProcessors){
        this.dataMap = dataMap;
        this.schedulerHelper = schedulerHelper;
        this.numberOfProcessors = numberOfProcessors;
    }

    public PartialSolution scheduleTasks(){

        bestPartialSolution = null;


        //TODO: fix construction based on constructor decided
        solutionStack.push(new PartialSolution(numberOfProcessors));

        while (!solutionStack.empty()) {
            currentPartialSolution = solutionStack.pop();
            latestNodeAdded = currentPartialSolution.getLatestNode();
            //Question does the code below mean every iteration a new list will replace an old one? If yes good. If not oops.
            scheduledNodes = currentPartialSolution.getScheduledNodes();
            nextAvailableNodes = schedulerHelper.getAvailableNodes(latestNodeAdded, dataMap, scheduledNodes);

            if (nextAvailableNodes.isEmpty()){
                if (currentPartialSolution.isBetterThan(bestPartialSolution)){
                    bestPartialSolution = currentPartialSolution;
                }
            } else if(bestPartialSolution != null){
                if (!currentPartialSolution.isWorseThan(bestPartialSolution)){
                    for (Node availableNode: nextAvailableNodes ){
                        //Same question as the question above
                        availablePartialSolutions = getAvailablePartialSolutions(availableNode, currentPartialSolution, numberOfProcessors);
                        for (PartialSolution partialSolution: availablePartialSolutions){
                            solutionStack.push(partialSolution);
                        }
                    }
                }
            } else {
                for (Node availableNode: nextAvailableNodes ){
                    //Same question as the question above
                    availablePartialSolutions = getAvailablePartialSolutions(availableNode, currentPartialSolution, numberOfProcessors);
                    for (PartialSolution partialSolution: availablePartialSolutions){
                        solutionStack.push(partialSolution);
                    }
                }
            }
        }
        return bestPartialSolution;
    }

    //Hailun I cant really figure out where to put ur methods that u made below to make the thing work.
    //ATM I'm going to put in a method inside the PartialSolution object which returns a set of scheduled nodes
    public void setScheduledNodes(Node node){
        scheduledNodes.add(node);
    }
    public Set<Node> getScheduledNodes(){
        return scheduledNodes;
    }


    // This function should probably be in scheduler helper, it gets the list of partialSolutions possible based on
    // the current solution and the node being added

    public List<PartialSolution> getAvailablePartialSolutions(Node nodeAdded, PartialSolution currentPartialSolution, int numberOfProcessors){
        //declare a new list to store the partail solutions
        List<PartialSolution> availablePartialSolutions = new ArrayList<>();
        //generates a partial solution for each processor
        for (int i = 0; i < numberOfProcessors; i++){
            //constructs new partial solution object
            PartialSolution newPartialSolution = new PartialSolution(numberOfProcessors);
            //clones the current solution
            newPartialSolution.clone(currentPartialSolution);
            //adds the node being scheduled into a processor depending on loop counter
            newPartialSolution.addNodeToProcessor(nodeAdded,i);
            //adds the new partial solution to the list of partial solutions possible
            availablePartialSolutions.add(newPartialSolution);
        }
        return availablePartialSolutions;
    }
}
