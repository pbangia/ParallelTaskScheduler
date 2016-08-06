package app.schedule;


import app.data.Node;

import java.util.*;

public class TaskScheduler {

    private Set<Node> scheduledNodes = new HashSet<>();
    private List<Node> nextAvailableNodes = new ArrayList<>();

    public List<Node> getAvailableNodes(Node parentNode) {
        if (parentNode == null) {
            //call find root nodes method
        } else {
            Map childrenMap = parentNode.getChildrenMap();

            for(Object childrenMapKeys : childrenMap.keySet()){
                
            }
        }


        return nextAvailableNodes;
    }


}