package app.schedule;


import app.data.Node;
import app.utils.MapUtils;

import java.util.*;

public class TaskScheduler {

    private Set<Node> scheduledNodes = new HashSet<>();
    private Map<String, Node> dataMap;
    private MapUtils mapUtils;
    private List<Node> nextAvailableNodes;

    public TaskScheduler(Map<String, Node> dataMap, MapUtils mapUtils){
        this.dataMap = dataMap;
        this.mapUtils = mapUtils;
    }

    //void for now as not sure what it will be returning.
    public void scheduleTasks(){
        nextAvailableNodes = mapUtils.getAvailableNodes(null, dataMap, scheduledNodes);
    }

    public void setScheduledNodes(Node node){
        scheduledNodes.add(node);
    }

    public Set<Node> getScheduledNodes(){

        return scheduledNodes;
    }




}