package app.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Processor {

    private static Logger logger = LoggerFactory.getLogger(Processor.class);

    private Queue<Node> nodeQueue = new ConcurrentLinkedQueue<>();
    private Map<Integer, Integer> nodeStartTimeMap = new ConcurrentHashMap<>();
    private Map<Node, Integer> nodeEndTimeMap = new ConcurrentHashMap<>();
    private int queueSize = 0;
    private int currentTimeStamp = 0;


    public void addNodeToQueue(Node node, int minScheduleTime) {
        nodeQueue.add(node);
        queueSize++;

        if (minScheduleTime > currentTimeStamp) {
            currentTimeStamp = minScheduleTime;
        }

        nodeStartTimeMap.put(queueSize, currentTimeStamp);
        currentTimeStamp += node.getWeight();
        nodeEndTimeMap.put(node, currentTimeStamp);
    }

    public Processor clone() {
        Processor newProcessor = new Processor();
        Queue<Node> clq = new ConcurrentLinkedQueue<Node>(this.nodeQueue);
        Map<Integer, Integer> newTimeStampMap = new ConcurrentHashMap<>(this.nodeStartTimeMap);
        Map<Node, Integer> newNodeEndTimeMap = new ConcurrentHashMap<>(this.nodeEndTimeMap);
        newProcessor.setNodeQueue(clq);
        newProcessor.setNodeStartTimeMap(newTimeStampMap);
        newProcessor.setNodeEndTimeMap(newNodeEndTimeMap);
        newProcessor.setCurrentTimeStamp(this.currentTimeStamp);
        newProcessor.setQueueSize(this.queueSize);
        return newProcessor;
    }

    public Queue<Node> getNodeQueue() {
        return nodeQueue;
    }

    public Map<Integer, Integer> getNodeStartTimeMap() {
        return nodeStartTimeMap;
    }

    public Map<Node, Integer> getNodeEndTimeMap() {
        return nodeEndTimeMap;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public int getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public int getTimeStamp(Node n) {
        int endTime = nodeEndTimeMap.get(n);

        return endTime - n.getWeight();
    }

    public void setNodeQueue(Queue<Node> nodeQueue) {
        this.nodeQueue = nodeQueue;
    }

    public void setNodeStartTimeMap(Map<Integer, Integer> nodeStartTimeMap) {
        this.nodeStartTimeMap = nodeStartTimeMap;
    }

    public void setNodeEndTimeMap(Map<Node, Integer> nodeEndTimeMap) {
        this.nodeEndTimeMap = nodeEndTimeMap;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public void setCurrentTimeStamp(int currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

}
