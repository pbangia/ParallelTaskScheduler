package app.utils;

import app.data.Node;
import app.exceptions.utils.NoRootFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapUtils {

    private static Logger logger = LoggerFactory.getLogger(MapUtils.class);

    public static Node findRoot(Map<String, Node> dataMap) throws NoRootFoundException {

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
}
