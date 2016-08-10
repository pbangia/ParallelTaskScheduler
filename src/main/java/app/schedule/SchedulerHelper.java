package app.schedule;

import app.data.Node;
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

    public List<Node> getAvailableNodes(Node latestNodeAdded, Map<String, Node> dataMap, Set<Node> scheduledNodes) throws NoRootFoundException {

        // TODO remove the first parameter of this method
        List<Node> nextAvailableNodes = new ArrayList<>();
        Iterator<Map.Entry<String, Node>> dataMapIterator = dataMap.entrySet().iterator();

        while (dataMapIterator.hasNext()) {

            Node currentNode = dataMapIterator.next().getValue();
            if (scheduledNodes.contains(currentNode)) {
                continue;
            }

            boolean isAvailable = true;
            Map<Node, Integer> dependentParentMap = currentNode.getParentMap();
            for (Node parentNode : dependentParentMap.keySet()){
                if (!scheduledNodes.contains(parentNode)){
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable){
                nextAvailableNodes.add(currentNode);
            }
        }

        return nextAvailableNodes;
    }
}
