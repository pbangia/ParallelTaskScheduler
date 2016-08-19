package app.schedule;

import app.data.Node;

import java.util.Collection;

public class TasksBranchThread extends CommonTaskScheduler implements Runnable{
    public TasksBranchThread(Collection<Node> nodes, SchedulerHelper schedulerHelper, int numberOfProcessors) {
        super(nodes, schedulerHelper, numberOfProcessors);
    }

    @Override
    public void run() {

    }
}
