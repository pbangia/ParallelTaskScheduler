package app.schedule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThreadManager implements ThreadCompletionListener {

    private List<BranchThread> branchThreads;
    private Set<Integer> threadIDSet;

    public ThreadManager(List<BranchThread> branchThreads) {
        this.branchThreads = branchThreads;
        this.threadIDSet = new HashSet<>();
        setupBranches();
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
    }

    public boolean atLeastOneActiveThread() {
        return threadIDSet.size() != 0;
    }
}
