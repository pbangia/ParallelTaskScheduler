package app.schedule;

import app.data.Node;
import app.data.PartialSolution;
import app.exceptions.utils.NoRootFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SchedulerHelper {

    private static Logger logger = LoggerFactory.getLogger(SchedulerHelper.class);

    /**
     * Finds the roots of a provided digraph represented in a data map.
     * Algorithm:
     * 1. All all of the Nodes to a set of Nodes.
     * 2. Iterate through each Node in that set
     * 3. For each child that the current Node has, remove the child Node from the set of Nodes.
     * 4. Any nodes leftover in the set are your Nodes.
     *
     * @param dataMap representing the digraph
     * @return List of roots in the digraph
     * @throws NoRootFoundException if no roots are found in the digraph (for e.g. cyclic graphs)
     */
    public List<Node> findRoots(Map<String, Node> dataMap) throws NoRootFoundException {

        Set<Node> nodeSet = new HashSet<>();

        Iterator<Map.Entry<String, Node>> entryIterator = dataMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, Node> currentEntry = entryIterator.next();
            nodeSet.add(currentEntry.getValue());
        }

        entryIterator = dataMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Node currentNode = entryIterator.next().getValue();
            if (currentNode.getChildrenMap().size() == 0) {
                continue;
            }
            Iterator<Node> children = currentNode.getChildrenMap().keySet().iterator();
            while (children.hasNext()) {
                Node child = children.next();
                if (nodeSet.contains(child)) {
                    nodeSet.remove(child);
                }
            }
        }

        if (nodeSet.size() == 0) {
            logger.error("Could not find a root for the digraph provided.");
            throw new NoRootFoundException("Could not find a root for the digraph provided.");
        }

        logger.info("Found a total of " + nodeSet.size() + " root(s) nodes in the digraph.");
        return new ArrayList<>(nodeSet);
    }


    /**
     * Finds the set of available nodes to schedule next based on the nodes currently scheduled.
     * Algorithm:
     * 1. Iterate through each node in the set of unscheduled nodes.
     * 2. For each of these nodes, check their parents are in the set of scheduled nodes.
     * 3. Unscheduled nodes who's parents have all been scheduled will be added to the list of nodes available to be scheduled
     *
     * @param scheduledNodes set of nodes which have been scheduled
     * @param unscheduledNodes set of nodes which haven't been scheduled.
     * @return List nodes available to be scheduled.
     */
    public static List<Node> getAvailableNodes(Set<Node> scheduledNodes, Set<Node> unscheduledNodes) {

        List<Node> nextAvailableNodes = new ArrayList<>();
        Iterator<Node> unscheduledNodesIterator = unscheduledNodes.iterator();

        while (unscheduledNodesIterator.hasNext()) {
            Node currentNode = unscheduledNodesIterator.next();

            boolean isAvailable = true;
            Map<Node, Integer> dependentParentMap = currentNode.getParentMap();
            for (Node parentNode : dependentParentMap.keySet()) {
                if (!scheduledNodes.contains(parentNode)) {
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                nextAvailableNodes.add(currentNode);
            }
        }

        return nextAvailableNodes;
    }

    /**
     * This function returns a list of partialSolutions possible based on the current solution and the node being added.
     * It does this by constructing new partial solution objects (by cloning the current solution) and then adding the
     * specified Node to each possible partial solution in every single processor of that partial solution.
     *
     * @param nodeToAdd              Node to be added to the partial solution provided
     * @param currentPartialSolution the partial solution provided
     * @return list of all partial solutions that correspond to the next available partial solutions
     */

    public static List<PartialSolution> getAvailablePartialSolutions(Node nodeToAdd, PartialSolution currentPartialSolution) {

        List<PartialSolution> availablePartialSolutions = new ArrayList<>();
        int numberOfProcessors = currentPartialSolution.getNumberOfProcessors();
        int loopCounter = numberOfProcessors;
        if (currentPartialSolution.getId() == 0){
            loopCounter = 1;
        }

        for (int i = 0; i < loopCounter; i++) {
            int newId = currentPartialSolution.getId() + i + 1;
            PartialSolution newPartialSolution = new PartialSolution(numberOfProcessors, currentPartialSolution, newId);
            newPartialSolution.addNodeToProcessor(nodeToAdd, i);
            availablePartialSolutions.add(newPartialSolution);
        }

        return availablePartialSolutions;
    }
}
