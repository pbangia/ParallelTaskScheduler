package app.utils;

import app.data.Node;
import app.exceptions.utils.NoRootFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MapUtils {

    private static Logger logger = LoggerFactory.getLogger(MapUtils.class);
    private List<Node> nextAvailableNodes = new ArrayList<>();
    private Map<Node, Integer> currentNodeChildrenMap;
    private Map<Node, Integer> dependentParentMap;
    private boolean canBeScheduled = true;

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

    public List<Node> getAvailableNodes(Node parentNode, Map<String, Node> dataMap, Set<Node> scheduledNodes) {
        nextAvailableNodes.clear();

        if (parentNode == null) {
            //call find root nodes method

            return nextAvailableNodes;
        }

        currentNodeChildrenMap = parentNode.getChildrenMap();

        if (currentNodeChildrenMap.isEmpty()) {
            return nextAvailableNodes;
        }



        for (Node childrenMapKey : currentNodeChildrenMap.keySet()) {
            dependentParentMap = dataMap.get(childrenMapKey).getParentMap();
            for (Node parentMapKey : dependentParentMap.keySet()) {
                if (!scheduledNodes.contains(parentMapKey)) {
                    canBeScheduled = false;
                    break;
                }
            }

            if (canBeScheduled){
                nextAvailableNodes.add(childrenMapKey);
            }
        }


        return nextAvailableNodes;
    }
}
