package app.schedule;

import app.data.Node;
import app.exceptions.utils.NoRootFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SchedulerHelper {

    private static Logger logger = LoggerFactory.getLogger(SchedulerHelper.class);

    public Node findRoot(Map<String, Node> dataMap) throws NoRootFoundException {

        Set<Node> nodeSet = new HashSet<Node>();

        Iterator<Map.Entry<String, Node>> entryIterator = dataMap.entrySet().iterator();
        while (entryIterator.hasNext()){
            Map.Entry<String, Node> currentEntry = entryIterator.next();
            nodeSet.add(currentEntry.getValue());
        }

        entryIterator = dataMap.entrySet().iterator();
        while (entryIterator.hasNext()){
            Node currentNode = entryIterator.next().getValue();
            if (currentNode.getChildrenMap().size() == 0){
                continue;
            }
            Iterator<Node> children = currentNode.getChildrenMap().keySet().iterator();
            while (children.hasNext()){
                Node child = children.next();
                if (nodeSet.contains(child)){
                    nodeSet.remove(child);
                }
            }
        }

        if (nodeSet.size() != 1){
            throw new NoRootFoundException("Could not find a root for the digraph provided.");
        } else{
            return nodeSet.iterator().next();
        }

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