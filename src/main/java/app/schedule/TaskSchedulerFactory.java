package app.schedule;

import app.data.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskSchedulerFactory {

    public static ParallelScheduler createTaskScheduler(Map<String, Node> dataMap, int numProcessors, int numThreads) {

        List<BranchThread> branchThreads = new ArrayList<>(numThreads);
        for (int i = 0; i < numThreads; i++){
            branchThreads.add(i, new BranchThread());
        }

        return new ParallelScheduler(dataMap.values(), numProcessors, branchThreads);
    }
}
