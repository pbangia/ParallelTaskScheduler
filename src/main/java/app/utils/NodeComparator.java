package app.utils;

import app.data.Node;
import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node node1, Node node2){
        if ((node1.getWeight() >= node2.getWeight()) && (!node1.getName().equals(node2.getName()))){
            return -1;
        } else if ((node1.getWeight() < node2.getWeight()) && (!node1.getName().equals(node2.getName()))){
            return 1;
        } else {
            return 0;
        }

    }
}
