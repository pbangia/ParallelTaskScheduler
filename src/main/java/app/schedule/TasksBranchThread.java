package app.schedule;

import app.data.Node;

import java.util.Collection;

/**
 * Created by User on 20/08/2016.
 */
public class TasksBranchThread extends CommonTaskScheduler{
    public TasksBranchThread(Collection<Node> nodes, SchedulerHelper schedulerHelper, int numberOfProcessors) {
        super(nodes, schedulerHelper, numberOfProcessors);
    }
}
