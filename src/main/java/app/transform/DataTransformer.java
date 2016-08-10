package app.transform;

import app.data.Node;
import app.exceptions.transform.EmptyMapException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static app.io.InputSyntax.DEFINITION_DELIMITER;
import static app.io.InputSyntax.DEPENDENCY_ARROW;

/**
 * Singleton DataTransformer class
 */
public class DataTransformer implements IDataTransformer {

    private static Logger logger = LoggerFactory.getLogger(DataTransformer.class);

    private StringToMapTransformation toMapTransformation;

    public DataTransformer(StringToMapTransformation stringToMapTransformation) {
        this.toMapTransformation = stringToMapTransformation;
    }

    public Map<String, Node> transformIntoMap(String data) throws EmptyMapException {
        logger.info("Starting transformation of io to map representation.");
        Map<String, Node> dataMap = new ConcurrentHashMap<String, Node>();
        String[] definitions = data.split(DEFINITION_DELIMITER);
        for (String definition : definitions) {
            if (definition.trim().length() == 0) {
                continue;
            } else if (definition.contains(DEPENDENCY_ARROW)) {
                logger.debug("Parsing node dependency: " + definition.trim());
                toMapTransformation.parseNodeDependency(definition, dataMap);
            } else {
                logger.debug("Parsing node declaration: " + definition.trim());
                toMapTransformation.parseNodeDeclaration(definition, dataMap);
            }
        }

        if (dataMap.size() == 0) {
            logger.error("ERROR: The provided digraph has no nodes or dependencies.");
            throw new EmptyMapException("The provided digraph has no nodes or dependencies.");
        }

        return dataMap;

    }

    public String transformIntoString(Map<String, Node> data) {
        // TODO implement later
        return null;
    }

}
