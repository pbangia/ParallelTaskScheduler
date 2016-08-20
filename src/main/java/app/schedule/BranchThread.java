package app.schedule;

import app.data.Node;
import app.data.PartialSolution;
import app.exceptions.utils.NoRootFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RunnableFuture;

public class BranchThread extends Thread {

    private static Logger logger = LoggerFactory.getLogger(BranchThread.class);

    private Stack<PartialSolution> solutionStack = new Stack<>();
    private BranchThreadListener branchThreadListener;

    public void addPartialSolution(PartialSolution partialSolution){
        this.solutionStack.push(partialSolution);
    }

    @Override
    public void run(){
        scheduleTasks();
        logger.debug("Thread : "+ Thread.currentThread().getName() + " completed.");
    }

    private void scheduleTasks() {

        while (!solutionStack.empty()) {
            PartialSolution currentPartialSolution = solutionStack.pop();
            Set<Node> scheduledNodes = currentPartialSolution.getScheduledNodes();
            Set<Node> unscheduledNodes = currentPartialSolution.getUnscheduledNodes();

            List<Node> nextAvailableNodes = SchedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes);

            if (nextAvailableNodes.isEmpty()) {
                branchThreadListener.onLeafReached(currentPartialSolution);
                continue;
            }

            PartialSolution best = branchThreadListener.currentBest();
            if (currentPartialSolution.isWorseThan(best)) {
                continue;
            }

            for (Node availableNode : nextAvailableNodes) {
                List<PartialSolution> availablePartialSolutions = SchedulerHelper.getAvailablePartialSolutions(availableNode, currentPartialSolution);
                solutionStack.addAll(availablePartialSolutions);
            }
        }
    }

    public void setBranchThreadListener(BranchThreadListener listener) {
        this.branchThreadListener = listener;
    }
}
