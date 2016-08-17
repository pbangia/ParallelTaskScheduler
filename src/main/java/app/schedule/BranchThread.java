package app.schedule;

import app.data.Node;
import app.data.PartialSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

public class BranchThread extends Thread {

    private static Logger logger = LoggerFactory.getLogger(BranchThread.class);


    private LinkedBlockingDeque<PartialSolution> solutionStack;
    private PartialSolution currentPartialSolution;
    private TaskScheduler taskScheduler;

    public BranchThread(TaskScheduler taskScheduler, PartialSolution currentPartialSolution){
        this.taskScheduler = taskScheduler;
        this.currentPartialSolution = currentPartialSolution;
    }

    public BranchThread(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void setCurrentPartialSolution(PartialSolution currentPartialSolution){
        this.currentPartialSolution = currentPartialSolution;
    }

    @Override
    public void run(){

        Set<Node> scheduledNodes = currentPartialSolution.getScheduledNodes();
        Set<Node> unscheduledNodes = currentPartialSolution.getUnscheduledNodes();

        List<Node> nextAvailableNodes = SchedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes);

        if (currentPartialSolution.isWorseThan(taskScheduler.getBestPartialSolution())) {
            return;
        }

        if (nextAvailableNodes.isEmpty()) {
            taskScheduler.setBestPartialSolution(currentPartialSolution);
            this.notifyAll();
            return;
        }

        for (Node availableNode : nextAvailableNodes) {
            List<PartialSolution> availablePartialSolutions = SchedulerHelper.getAvailablePartialSolutions(availableNode, currentPartialSolution, currentPartialSolution.getNumberOfProcessors());
            taskScheduler.addPartialSolutions(availablePartialSolutions);
            this.notifyAll();

        }

    }
}
