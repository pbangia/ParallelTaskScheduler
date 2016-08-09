package app.schedule;

import app.data.Node;
import app.exceptions.utils.NoRootFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SchedulerHelper {

    private static Logger logger = LoggerFactory.getLogger(SchedulerHelper.class);

    public List<Node> findRoots(Map<String, Node> dataMap) throws NoRootFoundException {

        Set<Node> nodeSet = new HashSet<>();

        /**
         * Add all of the Nodes to the set of nodes.
         */
        Iterator<Map.Entry<String, Node>> entryIterator = dataMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, Node> currentEntry = entryIterator.next();
            nodeSet.add(currentEntry.getValue());
        }

        /**
         * Iterate through all of the Nodes, and for each child
         * in the current Node, remove the child Node from the set of Nodes.
         */
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

        logger.info("Found a total of " + nodeSet.size() + " root nodes in the digraph.");
        return new ArrayList<>(nodeSet);
    }

    public List<Node> getAvailableNodes(Node currentParentNode, Map<String, Node> dataMap, Set<Node> scheduledNodes) {

        List<Node> nextAvailableNodes = new ArrayList<>();
        boolean canBeScheduled;

        if (currentParentNode == null) {
            //call find root nodes method

            return nextAvailableNodes;
        }

        Map<Node, Integer> currentNodeChildrenMap = currentParentNode.getChildrenMap();

        for (Node childNode : currentNodeChildrenMap.keySet()) {
            canBeScheduled = true;
            Map<Node, Integer> dependentParentMap = dataMap.get(childNode.getName()).getParentMap();
            for (Node parentNode : dependentParentMap.keySet()) {
                if (!scheduledNodes.contains(parentNode)) {
                    canBeScheduled = false;
                    break;
                }
            }

            if (canBeScheduled) {
                nextAvailableNodes.add(childNode);
            }
        }


        return nextAvailableNodes;
    }
}
