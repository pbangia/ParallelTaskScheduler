package app.utils;

import app.data.Node;
import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node node1, Node node2){
        if (node1.getWeight() > node2.getWeight()){
            return -1;
        } else {
            return 1;
        }
    }
}
