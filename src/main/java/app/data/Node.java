package app.data;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Node {

    private String name;
    private int weight;
    private Map<Node, Integer> parentMap = new ConcurrentHashMap<>();
    private Map<Node, Integer> childrenMap = new ConcurrentHashMap<>();

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

    public void addChild(Node child, int dependencyWeight) {
        childrenMap.put(child, dependencyWeight);
    }

    public void addParent(Node parent, int dependencyWeight){
        parentMap.put(parent, dependencyWeight);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------------\n");
        sb.append("Name: " + name + "\t" + "Weight: " + weight + "\n");
        sb.append("Children:\n");

        Iterator<Node> childrenNodeIterator = childrenMap.keySet().iterator();
        while (childrenNodeIterator.hasNext()) {
            Node childNode = childrenNodeIterator.next();
            String childName = childNode.getName();
            Integer dependencyWeight = childrenMap.get(childNode);
            sb.append("\t" + "Child name: " + childName + ", " + "Dependency weight: " + dependencyWeight + "\n");
        }
        sb.append("------------------------------------\n");
        return sb.toString();
    }

}
