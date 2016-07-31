package app.transform;

import app.data.Node;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton DataTransformer class
 */
public class DataTransformer {

    private static final String DEFINITION_DELIMITER = ";";
    private static final String DEPENDENCY_ARROW = "->";
    private static DataTransformer instance;

    public static DataTransformer get() {
        if (instance == null) {
            instance = new DataTransformer();
        }

        return instance;
    }


    public Map<String, Node> transformIntoMap(String data) {

        Map<String, Node> dataMap = new ConcurrentHashMap<String, Node>();
        String[] definitions = data.split(DEFINITION_DELIMITER);
        for (String definition : definitions) {
            if (definition.trim().length() == 0) {
                continue;
            } else if (definition.contains(DEPENDENCY_ARROW)) {
                parseNodeDependency(definition, dataMap);
            } else {
                parseNodeDeclaration(definition, dataMap);
            }
        }

        return dataMap;

    }

    public String transformIntoString(Map<String, Node> data) {
        // TODO implement later
        return null;
    }

    private void parseNodeDeclaration(String definition, Map<String, Node> dataMap) {
        String nodeName = definition.substring(0, definition.indexOf("[")).trim();
        int nodeWeight = Integer.parseInt(definition.substring(definition.indexOf("=") + 1, definition.indexOf("]")));
        Node node = dataMap.get(nodeName);

        if (node == null){
            node = new Node(nodeName, nodeWeight);
            dataMap.put(nodeName, node);
        } else{
            node.setWeight(nodeWeight);
        }
    }

    private void parseNodeDependency(String definition, Map<String, Node> dataMap) {
        String parentNodeName = definition.substring(0, definition.indexOf(DEPENDENCY_ARROW)).trim();
        String childNodeName = definition.substring(definition.indexOf(DEPENDENCY_ARROW) + 2, definition.indexOf("[")).trim();
        int dependencyWeight = Integer.parseInt(definition.substring(definition.indexOf("=") + 1, definition.indexOf("]")));

        Node parentNode = dataMap.get(parentNodeName);
        Node childNode = dataMap.get(childNodeName);

        if (parentNode == null && childNode == null){
            parentNode = new Node(parentNodeName);
            childNode = new Node(childNodeName);
            parentNode.addChild(childNode, dependencyWeight);
        } else if (parentNode == null){
            parentNode = new Node(parentNodeName);
            parentNode.addChild(childNode, dependencyWeight);
        } else if (childNode == null){
            childNode = new Node(childNodeName);
            parentNode.addChild(childNode, dependencyWeight);
        }

        dataMap.put(parentNodeName, parentNode);
        dataMap.put(childNodeName, childNode);
    }
}
