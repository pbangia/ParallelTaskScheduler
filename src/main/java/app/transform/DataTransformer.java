package app.transform;

import app.exceptions.transform.EmptyMapException;
import app.schedule.datatypes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static app.io.Syntax.DEFINITION_DELIMITER;
import static app.io.Syntax.DEPENDENCY_ARROW;

public class DataTransformer {

    private static Logger logger = LoggerFactory.getLogger(DataTransformer.class);

    private StringToMapTransformation toMapTransformation;
    private List<String> dependencies = new ArrayList<>();

    public DataTransformer(StringToMapTransformation stringToMapTransformation) {
        this.toMapTransformation = stringToMapTransformation;
    }

    public Map<String, Node> transformIntoMap(String data) throws EmptyMapException {
        logger.info("Starting transformation of io to map representation.");
        Map<String, Node> dataMap = new ConcurrentHashMap<String, Node>();
        String[] definitions = data.split(DEFINITION_DELIMITER);
        for (String definition : definitions) {
            if (definition.trim().length() == 0 || !definition.toLowerCase().contains("weight")) {
                continue;
            } else if (definition.contains(DEPENDENCY_ARROW)) {
                logger.debug("Parsing node dependency: " + definition.trim());
                toMapTransformation.parseNodeDependency(definition, dataMap);
                dependencies.add(definition.trim() + DEFINITION_DELIMITER + "\n");
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

    public List<String> getDependencies() {
        return dependencies;
    }

}
