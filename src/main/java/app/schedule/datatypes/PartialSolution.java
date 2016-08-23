package app.schedule.datatypes;

import app.io.Syntax;
import app.utils.NodeComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.lang.Integer.MAX_VALUE;

public class PartialSolution {

    private static Logger logger = LoggerFactory.getLogger(PartialSolution.class);

    private int numberOfProcessors;
    private int id;
    private Set<Node> scheduledNodes = new HashSet<>();
    //private TreeSet<Node> unscheduledNodes = new TreeSet<>(new NodeComparator());
    List<Node> unscheduledNodes;
    private Processor[] processors;

    public PartialSolution(int numberOfProcessors, Collection<Node> nodes) {
        this.numberOfProcessors = numberOfProcessors;
        this.processors = new Processor[numberOfProcessors];
        for (int i = 0; i < numberOfProcessors; i++) {
            processors[i] = new Processor();
        }
        this.unscheduledNodes = new ArrayList<>(nodes);
        Collections.sort(this.unscheduledNodes, new NodeComparator());
    }

    public PartialSolution(int numberOfProcessors, PartialSolution solutionToClone) {
        this.processors = new Processor[numberOfProcessors];
        this.numberOfProcessors = numberOfProcessors;
        if (solutionToClone != null) {
            clone(solutionToClone);
        }
    }

    public PartialSolution(int numberOfProcessors, Collection<Node> nodes, int id) {
        this.id = id;
        this.numberOfProcessors = numberOfProcessors;
        this.processors = new Processor[numberOfProcessors];
        for (int i = 0; i < numberOfProcessors; i++) {
            processors[i] = new Processor();
        }
        this.unscheduledNodes = new ArrayList<>(nodes);
        Collections.sort(this.unscheduledNodes, new NodeComparator());
    }

    public PartialSolution(int numberOfProcessors, PartialSolution solutionToClone, int id) {
        this.id = id;
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

        int thisValue = this.length() < this.minLength() ? this.minLength() : this.length();

        return thisValue < otherPartialSolution.length();
    }

    public int length() {
        int maxDuration = 0;

        for (int i = 0; i < processors.length; i++) {
            int currentProcessorLength = processors[i].getCurrentTimeStamp();
            if (maxDuration < currentProcessorLength) {
                maxDuration = currentProcessorLength;
            }
        }
        return maxDuration;
    }

    public int minLength() {
        int minDuration = MAX_VALUE;

        for (int i = 0; i < processors.length; i++) {
            int currentProcessorLength = processors[i].getCurrentTimeStamp();
            if (minDuration > currentProcessorLength) {
                minDuration = currentProcessorLength;
            }
        }

        int maxNode = 0;
        if (!unscheduledNodes.isEmpty()) {
            maxNode = unscheduledNodes.get(0).getWeight();
        }

        return minDuration + maxNode;

    }

    public boolean isWorseThan(PartialSolution bestPartialSolution) {
        return !isBetterThan(bestPartialSolution);
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
        //this.unscheduledNodes = new TreeSet<>(new NodeComparator());
        //this.unscheduledNodes.addAll(solutionToClone.unscheduledNodes);
        this.unscheduledNodes = new ArrayList<>(solutionToClone.unscheduledNodes);
        Collections.sort(this.unscheduledNodes, new NodeComparator());
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
                sb.append("]" + Syntax.DEFINITION_DELIMITER + "\n");
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

    public List<Node> getUnscheduledNodes() {
        return unscheduledNodes;
    }

    public int getId() {
        return id;
    }
}
