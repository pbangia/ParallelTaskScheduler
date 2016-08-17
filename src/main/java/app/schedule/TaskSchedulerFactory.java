package app.schedule;

import app.data.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskSchedulerFactory {
    //TODO Need to give task scheduler list of of threads it may access. Initially empty. No need to give it number of threads
    public static TaskScheduler createTaskScheduler(Map<String, Node> dataMap, int numProcessors, int numThreads) {
        List<BranchThread> branchThreadList = new ArrayList<>(numThreads);
        return new TaskScheduler(dataMap.values(), numProcessors, branchThreadList);
    }
}
