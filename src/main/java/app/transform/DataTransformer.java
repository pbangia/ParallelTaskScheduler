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

    /**
     * This method reads the contents of the file and creates Node objects and the dependencies
     * between each node by updating the respective child maps and parent maps where appropriate.
     *
     * @param data the contents of the file written into a string
     * @return the data, transformed into a map with the key being the name of the node, as a string and the value
     *         being the actual Node object
     * @throws EmptyMapException if the size of the map is 0 i.e. has no data in it, then this exception is thrown
     */
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
