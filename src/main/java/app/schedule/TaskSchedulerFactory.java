package app.schedule;

import app.data.Node;

import java.util.Map;

public class TaskSchedulerFactory {

    public static TaskScheduler createTaskScheduler(Map<String, Node> dataMap, int numProcessors, int numThreads) {
        return new TaskScheduler(dataMap.values(), numProcessors, numThreads);
    }
}
