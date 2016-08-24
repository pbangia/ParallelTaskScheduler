package app.schedule.parallel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ThreadManager in charge of determining how many threads are active
 * at any given point in time. Each Thread notifies this manager when it has completed.
 * This ThreadManager notifies the master scheduler once all threads have finished.
 */
public class ThreadManager implements ThreadCompletionListener {

    private ParallelScheduler scheduler;
    private List<BranchThread> branchThreads;
    private Set<Integer> threadIDSet;

    public ThreadManager(List<BranchThread> branchThreads) {
        this.branchThreads = branchThreads;
        this.threadIDSet = new HashSet<>();
        setupBranches();
    }

    public void setListener(ParallelScheduler scheduler) {
        this.scheduler = scheduler;
    }

    private void setupBranches() {

        int idCount = 1;
        for (BranchThread branchThread : branchThreads) {
            branchThread.setName(idCount + "");
            branchThread.setPriority(Thread.MAX_PRIORITY);
            branchThread.setThreadCompletionListener(this);
            threadIDSet.add(idCount);
            idCount += 1;
        }

    }

    @Override
    public synchronized void onThreadCompletion() {
        int intName = Integer.parseInt(Thread.currentThread().getName());
        threadIDSet.remove(intName);

        if (threadIDSet.size() == 0) {
            synchronized (scheduler) {
                scheduler.notify();
            }
        }
    }
}
