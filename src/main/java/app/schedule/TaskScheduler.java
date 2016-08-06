package app.schedule;


import app.data.Node;

import java.util.*;

public class TaskScheduler {

    private Set<Node> scheduledNodes = new HashSet<>();
    private Map<String, Node> dataMap;

    public TaskScheduler(Map<String, Node> dataMap){
        this.dataMap = dataMap;
    }




}