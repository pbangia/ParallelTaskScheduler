package app.schedule.solutionEntities;

import app.data.Node;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Processor {
    //needs data strucutre to hold the nodes and the start and end times

    private Queue<Node> nodeQueue = new ConcurrentLinkedQueue<>(); // DATA STRUCTURE can be a queue, linkedlist or arraylist... should probably implement the List interface
    private Map<Integer, Integer> timeStampMap = new ConcurrentHashMap<>();
    private int queueSize = 0;
    private int currentTimeStamp = 0;


    public void addNodeToQueue(Node node, int waitTime){
        nodeQueue.add(node);
        //adding wait time to the when the last node ended. This calculates new timestamp for currrent node to be scheduled
        currentTimeStamp += waitTime;
        timeStampMap.put(queueSize, currentTimeStamp);
        queueSize++;
        //Timestamp for when next node to be scheduled can start ie end timestamp of latest node
        currentTimeStamp += node.getWeight();
    }

    public Queue<Node> getNodeQueue(){
        return nodeQueue;
    }

    public Map<Integer,Integer> getTimeStampMap(){
        return timeStampMap;
    }

    public int getQueueSize(){
        return queueSize;
    }

    public int getCurrentTimeStamp() {
        return currentTimeStamp;
    }

}
