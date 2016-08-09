package app.schedule.solutionEntities;

import app.data.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PartialSolution {
    private Node latestNodeAdded;
    private int numberOfProcessors;
    private Set<Node> scheduledNodes = new HashSet<>();

    //Processor names start from 0
    private List<Processor> processors = new ArrayList<>();

    public PartialSolution(int numberOfProcessors) {
        this(numberOfProcessors, null);
    }

    public PartialSolution(int numberOfProcessors, PartialSolution solutionToClone) {
        this.numberOfProcessors = numberOfProcessors;
        for (int i = 0; i < numberOfProcessors; i++) {
            processors.add(new Processor());

        }

        if (solutionToClone != null) {
            clone(solutionToClone);
        }
    }

    public boolean isBetterThan(PartialSolution otherPartialSolution) {

        if (otherPartialSolution == null) {
            return true;
        }
        return this.length() < otherPartialSolution.length();

    }

    int length() {
        int maxDuration = 0;
        for (Processor processor : processors) {
            if (maxDuration < processor.getCurrentTimeStamp()) {
                maxDuration = processor.getCurrentTimeStamp();
            }
        }
        return maxDuration;
    }

    public void addNodeToProcessor(Node addedNode, int processorNumber, int waitTime) {
        this.latestNodeAdded = addedNode;
        scheduledNodes.add(addedNode);
        processors.get(processorNumber).addNodeToQueue(addedNode, waitTime);
    }


    // This method clones a PartialSolution
    void clone(PartialSolution clonedSolution) {

        this.scheduledNodes = new HashSet<>(clonedSolution.scheduledNodes);
        this.numberOfProcessors = clonedSolution.numberOfProcessors;
        this.latestNodeAdded = clonedSolution.latestNodeAdded;
        for (Processor processor : clonedSolution.processors) {
            this.processors.add(processor.clone());
        }

    }

    //Getters
    public Node getLatestNode() {
        return latestNodeAdded;
    }
    public Set<Node> getScheduledNodes() {
        return scheduledNodes;
    }
    public List<Processor> getProcessors() { return processors; }



}
