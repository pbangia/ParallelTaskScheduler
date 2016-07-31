package app.transform;

import app.data.Node;

import java.util.Map;

public interface IDataTransformer {

    Map<String, Node> transformIntoMap(String data);

    String transformIntoString(Map<String, Node> data);

}
