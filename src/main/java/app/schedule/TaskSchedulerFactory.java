package app.schedule;

import app.data.Node;
import app.utils.ThreadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskSchedulerFactory {
    //TODO NEED TO EDIT ONCE TASKSCHEDULER HAS BEEN MODIFIED
    public static TaskScheduler createTaskScheduler(Map<String, Node> dataMap, int numProcessors, int numThreads) {
        List<BranchThread> branchThreadList = new ArrayList<>(numThreads);
        for(int i = 0; i < numThreads; i++){
            branchThreadList.add(null);
        }
        System.out.println("Number of threads: " + numThreads);
        System.out.println("Size of arrayList of threads: " + branchThreadList.size());
        ThreadUtil threadUtil = new ThreadUtil();
        return new TaskScheduler(dataMap.values(), numProcessors, branchThreadList, threadUtil);
    }
}
