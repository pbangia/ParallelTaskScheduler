package app.transform;

import java.util.Map;

import static app.io.InputSyntax.DEPENDENCY_ARROW;

public class StringToMatrixTransformation {


    void parseNodeDeclaration(String definition, Map<String, Integer> dataMap, Integer nodeCol) {

        String nodeName = definition.substring(0, definition.indexOf("[")).trim();
        if (!dataMap.containsKey(nodeName)) {
            dataMap.put(nodeName, nodeCol);
        }
    }

    void parseNodeDependency(String definition, Map<String, Integer> dataMap, int[][] matrix) {

        String parentNodeName = definition.substring(0, definition.indexOf(DEPENDENCY_ARROW)).trim();
        String childNodeName = definition.substring(definition.indexOf(DEPENDENCY_ARROW) + 2, definition.indexOf("[")).trim();
        int dependencyWeight = Integer.parseInt(definition.substring(definition.indexOf("=") + 1, definition.indexOf("]")));

        int parent = dataMap.get(parentNodeName);
        int child = dataMap.get(childNodeName);

        matrix[parent][child] = dependencyWeight;

    }

    void parseNodeWeight(String definition, Map<String, Integer> dataMap, int[][] matrix) {

        String nodeName = definition.substring(0, definition.indexOf("[")).trim();
        int nodeWeight = Integer.parseInt(definition.substring(definition.indexOf("=") + 1, definition.indexOf("]")));
        int nodeIndex = dataMap.get(nodeName);

        matrix[nodeIndex][nodeIndex] = nodeWeight;

    }

}
