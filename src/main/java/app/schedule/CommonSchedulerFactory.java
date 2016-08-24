package app.schedule;

import app.schedule.datatypes.Node;
import app.schedule.parallel.BranchThread;
import app.schedule.parallel.ParallelScheduler;
import app.schedule.parallel.ThreadManager;
import app.schedule.serial.SerialScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonSchedulerFactory {

    /**
     * Creates the scheduler to be run based on the number of threads specified.
     * @param dataMap Input data map
     * @param numProcessors number of processors for the scheduling
     * @param numThreads number of threads to be used
     * @return Either the parallel or serial scheduler based on the number of threads specified.
     */
    public static CommonScheduler createTaskScheduler(Map<String, Node> dataMap, int numProcessors, int numThreads) {

        if (numThreads < 2) {
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
