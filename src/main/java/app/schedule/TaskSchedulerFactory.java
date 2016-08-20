package app.schedule;

import app.data.Node;
import app.utils.ThreadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskSchedulerFactory {
    //TODO NEED TO EDIT ONCE TASKSCHEDULER HAS BEEN MODIFIED
    public static TaskScheduler createTaskScheduler(Map<String, Node> dataMap, int numProcessors, int numThreads) {
        ThreadUtil threadUtil = new ThreadUtil();
        return new TaskScheduler(dataMap.values(), numProcessors, numThreads, threadUtil);
    }
}
