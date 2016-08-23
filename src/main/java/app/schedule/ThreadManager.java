package app.schedule;

import java.util.List;

public class ThreadManager {

    private List<BranchThread> branchThreads;

    public ThreadManager(List<BranchThread> branchThreads) {
        this.branchThreads = branchThreads;
        setupBranches(branchThreads);
    }

    private void setupBranches(List<BranchThread> branchThreads) {

        int idCount = 1;
        for (BranchThread branchThread : branchThreads) {
            branchThread.setName(idCount + "");
            branchThread.setPriority(Thread.MAX_PRIORITY);
            idCount += 1;
        }

    }


}
