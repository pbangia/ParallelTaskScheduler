package app.data;

import app.io.InputSyntax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PartialSolution {

    private static Logger logger = LoggerFactory.getLogger(PartialSolution.class);

    private int numberOfProcessors;
    private Set<Node> scheduledNodes = new HashSet<>();
    private Set<Node> unscheduledNodes = new HashSet<>();
    private Processor[] processors;
    //private List<Processor> processors = new ArrayList<>();

    public PartialSolution(int numberOfProcessors) {
        this.numberOfProcessors = numberOfProcessors;
        this.processors = new Processor[numberOfProcessors];
        for (int i = 0; i < numberOfProcessors; i++) {
            processors[i] = new Processor();
        }
    }

    public PartialSolution(int numberOfProcessors, PartialSolution solutionToClone) {
        this.processors = new Processor[numberOfProcessors];
        this.numberOfProcessors = numberOfProcessors;
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

    private int length() {
        int maxDuration = 0;

        for (int i = 0; i < processors.length; i++) {
            if (maxDuration < processors[i].getCurrentTimeStamp()) {
                maxDuration = processors[i].getCurrentTimeStamp();
            }
        }

        return maxDuration;
    }

    public void addNodeToProcessor(Node nodeToAdd, int processorNumber) {
        scheduledNodes.add(nodeToAdd);
        unscheduledNodes.remove(nodeToAdd);

        Processor currentProcessor = processors[processorNumber];
        Iterator<Map.Entry<Node, Integer>> parentsIterator = nodeToAdd.getParentMap().entrySet().iterator();

        int minTimeToStart = 0;
        while (parentsIterator.hasNext()) {
            Node parent = parentsIterator.next().getKey();
            if (currentProcessor.getNodeSet().contains(parent)) continue;

            for (int i = 0; i < numberOfProcessors; i++) {
                if (processorNumber == i || !processors[i].getNodeSet().contains(parent)) {
                    continue;
                }

                int parentEndTime = processors[i].getTimeStamp(parent) + parent.getWeight();
                int dependencyWeight = nodeToAdd.getParentMap().get(parent);
                int tempTimeToStart = parentEndTime + dependencyWeight;
                if (minTimeToStart < tempTimeToStart) minTimeToStart = tempTimeToStart;
            }
        }

        processors[processorNumber].addNodeToQueue(nodeToAdd, minTimeToStart);
    }

    private void clone(PartialSolution solutionToClone) {

        this.scheduledNodes = new HashSet<>(solutionToClone.scheduledNodes);
        this.unscheduledNodes = new HashSet<>(solutionToClone.unscheduledNodes);
        this.numberOfProcessors = solutionToClone.numberOfProcessors;

        for (int i = 0; i < solutionToClone.processors.length; i++) {
            this.processors[i] = solutionToClone.processors[i].clone();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int processorNumber = 1;

        for (Processor p : processors) {
            for (Node n : p.getNodeQueue()) {
                sb.append(n.getName() + "\t");
                sb.append("[Weight=" + n.getWeight());
                sb.append(",Start=" + p.getTimeStamp(n));
                sb.append(",Processor=" + processorNumber);
                sb.append("]" + InputSyntax.DEFINITION_DELIMITER + "\n");
            }
            processorNumber++;
        }
        return sb.toString();
    }

    public Processor[] getProcessors() {
        return processors;
    }

    public int getNumberOfProcessors() {
        return numberOfProcessors;
    }

    public Set<Node> getScheduledNodes() {
        return scheduledNodes;
    }

    public Set<Node> getUnscheduledNodes() { return unscheduledNodes; }

}
