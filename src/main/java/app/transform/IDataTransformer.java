package app.transform;

import app.schedule.datatypes.Node;
import app.exceptions.transform.EmptyMapException;

import java.util.List;
import java.util.Map;

public interface IDataTransformer {

    Map<String, Node> transformIntoMap(String data) throws EmptyMapException;

    List<String> getDependencies();
}
