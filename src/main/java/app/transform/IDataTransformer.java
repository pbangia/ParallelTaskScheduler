package app.transform;

import app.data.Node;
import app.exceptions.transform.EmptyMapException;

import java.util.Map;

public interface IDataTransformer {

    Map<String, Node> transformIntoMap(String data) throws EmptyMapException;

    String transformIntoString(Map<String, Node> data);

}
