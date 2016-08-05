package app.transform;

import app.data.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static app.input.InputSyntax.DEPENDENCY_ARROW;

public class StringToMapTransformation {

    private static Logger logger = LoggerFactory.getLogger(StringToMapTransformation.class);

    void parseNodeDeclaration(String definition, Map<String, Node> dataMap) {

        String nodeName = definition.substring(0, definition.indexOf("[")).trim();
        int nodeWeight = Integer.parseInt(definition.substring(definition.indexOf("=") + 1, definition.indexOf("]")));
        Node node = dataMap.get(nodeName);

        if (node == null) {
            node = new Node(nodeName, nodeWeight);
            dataMap.put(nodeName, node);
        } else {
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
        }

        if (childNode == null) {
            childNode = new Node(childNodeName);
        }

        parentNode.addChild(childNode, dependencyWeight);
        childNode.addParent(parentNode, dependencyWeight);

        dataMap.put(parentNodeName, parentNode);
        dataMap.put(childNodeName, childNode);
    }

}
