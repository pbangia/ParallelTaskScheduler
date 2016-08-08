package app.schedule;


import app.data.Node;
import app.exceptions.utils.NoRootFoundException;

import java.util.*;

public class TaskScheduler {

    private Set<Node> scheduledNodes = new HashSet<>();
    private Map<String, Node> dataMap;
    private SchedulerHelper schedulerHelper;
    private List<Node> nextAvailableNodes;

    public TaskScheduler(Map<String, Node> dataMap, SchedulerHelper schedulerHelper){
        this.dataMap = dataMap;
        this.schedulerHelper = schedulerHelper;
    }

    //void for now as not sure what it will be returning.
    public void scheduleTasks() throws NoRootFoundException {
        nextAvailableNodes = schedulerHelper.getAvailableNodes(null, dataMap, scheduledNodes);
    }

    public void setScheduledNodes(Node node){
        scheduledNodes.add(node);
    }

    public Set<Node> getScheduledNodes(){

        return scheduledNodes;
    }




}