package app.schedule;

import app.data.Node;
import app.data.PartialSolution;
import app.exceptions.utils.NoRootFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RunnableFuture;

//TODO NEED TO REMOVE
public class BranchThread extends CommonTaskScheduler implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(BranchThread.class);

    private BranchThreadListener branchThreadListener;

    public BranchThread(Collection<Node> nodes, SchedulerHelper schedulerHelper, int numberOfProcessors, BranchThreadListener listener) {
        super(nodes, schedulerHelper, numberOfProcessors);
        this.branchThreadListener = listener;
    }

    @Override
    public void run(){

        try {
            PartialSolution bestPartialSolution = scheduleTasks();
            branchThreadListener.onCompletion(bestPartialSolution);
        } catch (NoRootFoundException e) {
            e.printStackTrace();
        }

    }
}
