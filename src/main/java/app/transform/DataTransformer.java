package app.transform;

import app.data.Node;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static app.input.InputSyntax.DEFINITION_DELIMITER;
import static app.input.InputSyntax.DEPENDENCY_ARROW;

/**
 * Singleton DataTransformer class
 */
public class DataTransformer implements IDataTransformer {

    private StringToMapTransformation toMapTransformation;

    public DataTransformer(StringToMapTransformation stringToMapTransformation){
        this.toMapTransformation = stringToMapTransformation;
    }

    public Map<String, Node> transformIntoMap(String data) {

        Map<String, Node> dataMap = new ConcurrentHashMap<String, Node>();
        String[] definitions = data.split(DEFINITION_DELIMITER);
        for (String definition : definitions) {
            if (definition.trim().length() == 0) {
                continue;
            } else if (definition.contains(DEPENDENCY_ARROW)) {
                toMapTransformation.parseNodeDependency(definition, dataMap);
            } else {
                toMapTransformation.parseNodeDeclaration(definition, dataMap);
            }
        }

        return dataMap;

    }

    public String transformIntoString(Map<String, Node> data) {
        // TODO implement later
        return null;
    }

}
