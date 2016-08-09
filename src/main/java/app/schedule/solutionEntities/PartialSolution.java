package app.schedule.solutionEntities;
import app.data.*;


import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PartialSolution {
    private Node latestNodeAdded;
    private int numberOfProcessors;
    private Set<Node> scheduledNodes = new HashSet<>();

    //Processor names start from 0
    private List<Processor> processors = new ArrayList<>();

    public PartialSolution (int numberOfProcessors){
        this(numberOfProcessors, null);
    }

    public PartialSolution(int numberOfProcessors, PartialSolution solutionToClone){
        this.numberOfProcessors = numberOfProcessors;
        for (int i = 0; i < numberOfProcessors; i++){
            processors.add(new Processor());

        }
        clone(solutionToClone);
    }
    // Returns last node added to PartialSolution
    public Node getLatestNode(){
        return latestNodeAdded;
    }

    public Set<Node> getScheduledNodes(){
        return scheduledNodes;
    }

    // Returns boolean, true if this PartialSolution is better than another partialSolution
    public boolean isBetterThan(PartialSolution otherPartialSolution){
        //TODO: fill me in :3
        //returns true for now
        return true;
    }

    public void addNodeToProcessor(Node addedNode, int processorNumber, int waitTime){
        this.latestNodeAdded=addedNode;
        scheduledNodes.add(addedNode);
        processors.get(processorNumber).addNodeToQueue(addedNode,waitTime);
    }


    // This method clones a PartialSolution
    void clone(PartialSolution clonedSolution){

        this.scheduledNodes=new HashSet<>(clonedSolution.scheduledNodes);
        this.numberOfProcessors=clonedSolution.numberOfProcessors;
        this.latestNodeAdded = clonedSolution.latestNodeAdded;
        for (Processor processor : clonedSolution.processors){
            this.processors.add(processor.clone());
        }

    }

}
