package app.schedule.datatypes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Node {

    private String name;
    private int weight;
    private int timestamp;
    private int processorNumber;
    private Map<Node, Integer> parentMap = new ConcurrentHashMap<>();
    private Map<Node, Integer> childrenMap = new ConcurrentHashMap<>();

    public Node(String name) {
        this.name = name;
    }

    public Node(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.name + "\t");
        sb.append("[Weight=" + weight);
        sb.append(",Start=" + timestamp);
        sb.append(",Processor=" + processorNumber);
        sb.append("];\n");

        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setProcessorNumber(int processorNumber) {
        this.processorNumber = processorNumber;
    }

    public Map<Node, Integer> getChildrenMap() {
        return childrenMap;
    }

    public Map<Node, Integer> getParentMap() {
        return parentMap;
    }

    public void addChild(Node child, int dependencyWeight) {
        childrenMap.put(child, dependencyWeight);
        if (!child.getParentMap().containsKey(this)) {
            child.addParent(this, dependencyWeight);
        }
    }

    public void addParent(Node parent, int dependencyWeight) {
        parentMap.put(parent, dependencyWeight);
        if (!parent.getChildrenMap().containsKey(this)) {
            parent.addChild(this, dependencyWeight);
        }
    }

}
