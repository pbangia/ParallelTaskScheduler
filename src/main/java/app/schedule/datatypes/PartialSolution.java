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
    private Set<Node> scheduledNodes = new HashSet<>();
    private List<Node> unscheduledNodes;
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

    /**
     * Compares this PartialSolution to the input PartialSolution
     * @param otherPartialSolution input PartialSolution
     * @return true if the this is better than the input PartialSolution
     */
    public boolean isBetterThan(PartialSolution otherPartialSolution) {
        if (otherPartialSolution == null) {
            return true;
        }

        int thisValue = this.length() < this.minLength() ? this.minLength() : this.length();

        return thisValue < otherPartialSolution.length();
    }

    /**
     * Computes the finish time of this PartialSolution
     * @return finish time of the longest running processor
     */
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

    /**
     * Minimum length that this PartialSolution can finish within.
     * @return minimum finish time
     */
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

    /**
     * Returns the boolean opposite of the isBetterThan method.
     * @param bestPartialSolution PartialSolution to compare to
     * @return true if this PartialSolution is worse than the input Partial Solution
     */
    public boolean isWorseThan(PartialSolution bestPartialSolution) {
        return !isBetterThan(bestPartialSolution);
    }

    /**
     * Adds a Node to the Processor object specified, by computing the timestamp as to when to
     * add that Node.
     * @param nodeToAdd Node that is to be added.
     * @param processorNumber Processor to add the Node to
     */
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

    /**
     * Clones this PartialSolution to match the input PartialSolution
     * @param solutionToClone
     */
    private void clone(PartialSolution solutionToClone) {

        this.scheduledNodes = new HashSet<>(solutionToClone.scheduledNodes);
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

}
