package app.transform;

import app.schedule.datatypes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static app.io.Syntax.DEPENDENCY_ARROW;

public class StringToMapTransformation {

    private static Logger logger = LoggerFactory.getLogger(StringToMapTransformation.class);

    void parseNodeDeclaration(String definition, Map<String, Node> dataMap) {

        String nodeName = definition.substring(0, definition.indexOf("[")).trim();
        int nodeWeight = Integer.parseInt(definition.substring(definition.indexOf("=") + 1, definition.indexOf("]")));
        Node node = dataMap.get(nodeName);

        if (node == null) {
            node = new Node(nodeName, nodeWeight);
            logger.debug("Created " + nodeName + " node with a weight of " + nodeWeight);
            dataMap.put(nodeName, node);
        } else {
            logger.debug("Updated " + nodeName + " node with a weight of " + nodeWeight);
            node.setWeight(nodeWeight);
        }
    }

    void parseNodeDependency(String definition, Map<String, Node> dataMap) {

        String parentNodeName = definition.substring(0, definition.indexOf(DEPENDENCY_ARROW)).trim();
        String childNodeName = definition.substring(definition.indexOf(DEPENDENCY_ARROW) + 2, definition.indexOf("[")).trim();
        int dependencyWeight = Integer.parseInt(definition.substring(definition.indexOf("=") + 1, definition.indexOf("]")));

        Node parentNode = dataMap.get(parentNodeName);
        Node childNode = dataMap.get(childNodeName);

        if (parentNode == null) {
            parentNode = new Node(parentNodeName);
            logger.debug("Created " + parentNodeName + " parent node");
        }

        if (childNode == null) {
            childNode = new Node(childNodeName);
            logger.debug("Created " + parentNodeName + " child node");
        }

        parentNode.addChild(childNode, dependencyWeight);
        logger.debug("Added parent/child dependency between " + parentNodeName +
                " and " + childNodeName + " with a weight of " + dependencyWeight);

        dataMap.put(parentNodeName, parentNode);
        dataMap.put(childNodeName, childNode);
    }

}
