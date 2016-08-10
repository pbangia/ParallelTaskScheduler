package app.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PartialSolution {

    private static Logger logger = LoggerFactory.getLogger(PartialSolution.class);

    private int numberOfProcessors;
    private Node latestNodeAdded;
    private Set<Node> scheduledNodes = new HashSet<>();
    private List<Processor> processors = new ArrayList<>();

    public PartialSolution(int numberOfProcessors) {
        this.numberOfProcessors = numberOfProcessors;
        for (int i = 0; i < numberOfProcessors; i++) {
            processors.add(new Processor());

        }
    }

    public PartialSolution(int numberOfProcessors, PartialSolution solutionToClone) {
        this.numberOfProcessors = numberOfProcessors;
        if (solutionToClone != null) {
            clone(solutionToClone);
        }
    }

    public Node getLatestNode() {
        return latestNodeAdded;
    }

    public Set<Node> getScheduledNodes() {
        return scheduledNodes;
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

    public void addNodeToProcessor(Node nodeToAdd, int processorNumber) {
        this.latestNodeAdded = nodeToAdd;
        scheduledNodes.add(nodeToAdd);

        Processor currentProcessor = processors.get(processorNumber);
        Iterator<Map.Entry<Node, Integer>> parentsIterator = nodeToAdd.getParentMap().entrySet().iterator();
        List<Node> parentList = new ArrayList<>();
        while (parentsIterator.hasNext()) {
            Node parent = parentsIterator.next().getKey();
            if (!currentProcessor.getNodeEndTimeMap().containsKey(parent)) {
                parentList.add(parent);
            }
        }

        int minTimeToStart = 0;
        for (Node parent : parentList) {
            for (int i = 0; i < numberOfProcessors; i++) {
                if (processorNumber == i) {
                    continue;
                }

                if (processors.get(i).getNodeEndTimeMap().containsKey(parent)) {
                    int parentEndTime = processors.get(i).getNodeEndTimeMap().get(parent);
                    int dependencyWeight = nodeToAdd.getParentMap().get(parent);
                    int tempTimeToStart = parentEndTime + dependencyWeight;
                    if (minTimeToStart < tempTimeToStart) {
                        minTimeToStart = tempTimeToStart;
                    }
                }
            }
        }
        processors.get(processorNumber).addNodeToQueue(nodeToAdd, minTimeToStart);
    }


    void clone(PartialSolution solutionToClone) {

        this.scheduledNodes = new HashSet<>(solutionToClone.scheduledNodes);
        this.numberOfProcessors = solutionToClone.numberOfProcessors;
        this.latestNodeAdded = solutionToClone.latestNodeAdded;
        for (Processor processor : solutionToClone.processors) {
            this.processors.add(processor.clone());
        }

    }

    public List<Processor> getProcessors() {
        return processors;
    }

    public int getNumberOfProcessors() {
        return numberOfProcessors;
    }

}
