package app.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Node {

    private String name;
    private int weight;
    private Map<Node, Integer> childrenMap = new ConcurrentHashMap<Node, Integer>();

    public Node(String name) {
        this.name = name;
    }

    public Node(String name, int weight) {
        this.name = name;
        this.weight = weight;
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

    public Map<Node, Integer> getChildrenMap() {
        return childrenMap;
    }

    public void addChild(Node child, int proccessingWeight) {
        childrenMap.put(child, proccessingWeight);
    }

}
