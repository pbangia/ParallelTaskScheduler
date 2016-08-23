package app.schedule;

import app.data.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskSchedulerFactory {

    public static CommonScheduler createTaskScheduler(Map<String, Node> dataMap, int numProcessors, int numThreads) {

        if (numThreads == 0) {
            return new SerialScheduler(dataMap.values(), numProcessors);
        }

        List<BranchThread> branchThreads = new ArrayList<>(numThreads);
        for (int i = 0; i < numThreads; i++) {
            branchThreads.add(i, new BranchThread());
        }

        ThreadManager threadManager = new ThreadManager(branchThreads);

        return new ParallelScheduler(dataMap.values(), numProcessors, branchThreads, threadManager);


    }
}
