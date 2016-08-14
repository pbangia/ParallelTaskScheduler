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
    private Map<Node, Integer> nodeStartTimeMap = new ConcurrentHashMap<>();
    private int currentTimeStamp = 0;

    public void addNodeToQueue(Node node, int minScheduleTime) {
        nodeQueue.add(node);

        if (minScheduleTime > currentTimeStamp) {
            currentTimeStamp = minScheduleTime;
        }

        nodeStartTimeMap.put(node, currentTimeStamp);
        currentTimeStamp += node.getWeight();
    }

    public Processor clone() {
        Processor newProcessor = new Processor();
        Queue<Node> clq = new ConcurrentLinkedQueue<>(this.nodeQueue);
        Map<Node, Integer> newTimeStampMap = new ConcurrentHashMap<>(this.nodeStartTimeMap);
        newProcessor.setNodeQueue(clq);
        newProcessor.setNodeStartTimeMap(newTimeStampMap);
        newProcessor.setCurrentTimeStamp(this.currentTimeStamp);
        return newProcessor;
    }

    public int getTimeStamp(Node n) {
        return nodeStartTimeMap.get(n);
    }

    public Queue<Node> getNodeQueue() {
        return nodeQueue;
    }

    public Map<Node, Integer> getNodeStartTimeMap() {
        return nodeStartTimeMap;
    }

    public int getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setNodeQueue(Queue<Node> nodeQueue) {
        this.nodeQueue = nodeQueue;
    }

    public void setNodeStartTimeMap(Map<Node, Integer> nodeStartTimeMap) {
        this.nodeStartTimeMap = nodeStartTimeMap;
    }

    public void setCurrentTimeStamp(int currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

}
