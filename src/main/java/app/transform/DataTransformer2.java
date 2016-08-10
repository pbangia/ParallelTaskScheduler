package app.transform;

import app.exceptions.transform.EmptyMapException;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static app.io.InputSyntax.DEFINITION_DELIMITER;
import static app.io.InputSyntax.DEPENDENCY_ARROW;

/**
 * Singleton DataTransformer class
 */
public class DataTransformer2 implements IDataTransformer2 {

    private StringToMatrixTransformation toMapTransformation;

    public DataTransformer2(StringToMatrixTransformation stringToMapTransformation) {
        this.toMapTransformation = stringToMapTransformation;
    }

    public Map<String, Integer> transformIntoMap(String data) throws EmptyMapException {

        Map<String, Integer> dataMap = new ConcurrentHashMap<String, Integer>();
        String[] definitions = data.split(DEFINITION_DELIMITER);
        int nodeCol = 0;
        for (String definition : definitions) {

            if (definition.trim().length() == 0) {
                continue;
            } else if (!definition.contains(DEPENDENCY_ARROW)) {
                toMapTransformation.parseNodeDeclaration(definition, dataMap, nodeCol);
                nodeCol++;
            }
        }

        if (dataMap.size() == 0) {
            throw new EmptyMapException("The provided digraph has no nodes or dependencies.");
        }

        return dataMap;

    }

    public int[][] transformIntoMatrix(Map<String, Integer> dataMap, String data) {

        int[][] matrix = new int[dataMap.size()][dataMap.size()];
        //-1 to represent no dependency. Cols/rows represent node from map.
        for (int[] row : matrix) {
            Arrays.fill(row, -1);
        }

        String[] definitions = data.split(DEFINITION_DELIMITER);
        int nodeCol = 0;
        for (String definition : definitions) {

            if (definition.trim().length() == 0 || !definition.toLowerCase().contains("weight")) {
                continue;
            } else if (definition.contains(DEPENDENCY_ARROW)) {
                //dependency to be stored in matrix[parent][child]
                toMapTransformation.parseNodeDependency(definition, dataMap, matrix);
            } else {
                //self weight to be stored in matrix[i][i]
                toMapTransformation.parseNodeWeight(definition, dataMap, matrix);
            }
        }

        return matrix;

    }

    public String transformIntoString(Map<String, Integer> data) {
        // TODO implement later
        return null;
    }

}
