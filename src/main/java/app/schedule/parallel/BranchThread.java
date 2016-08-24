package app.schedule.parallel;

import app.schedule.SchedulerHelper;
import app.schedule.datatypes.Node;
import app.schedule.datatypes.PartialSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Represents the DFS task that is to be completed by each instance of this class.
 */
public class BranchThread extends Thread {

    private static Logger logger = LoggerFactory.getLogger(BranchThread.class);

    private Stack<PartialSolution> solutionStack = new Stack<>();
    private BranchThreadListener branchThreadListener;
    private PartialSolution bestPartialSolution = null;
    private ThreadManager threadCompletionListener;

    public void addPartialSolution(PartialSolution partialSolution) {
        this.solutionStack.push(partialSolution);
    }

    @Override
    public void run() {
        scheduleTasks();
        threadCompletionListener.onThreadCompletion();
        logger.debug("Thread : " + Thread.currentThread().getName() + " completed.");
    }

    /**
     * Runs the Branch and Bound DFS algorithm.
     */
    private void scheduleTasks() {

        while (!solutionStack.empty()) {
            PartialSolution currentPartialSolution = solutionStack.pop();
            Set<Node> scheduledNodes = currentPartialSolution.getScheduledNodes();
            List<Node> unscheduledNodes = currentPartialSolution.getUnscheduledNodes();

            List<Node> nextAvailableNodes = SchedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes);

            if (currentPartialSolution.isWorseThan(bestPartialSolution)) {
                continue;
            }

            bestPartialSolution = branchThreadListener.currentBest();
            if (currentPartialSolution.isWorseThan(bestPartialSolution)) {
                continue;
            }

            if (nextAvailableNodes.isEmpty()) {
                bestPartialSolution = currentPartialSolution;
                branchThreadListener.onLeafReached(bestPartialSolution);
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

    public void setThreadCompletionListener(ThreadManager threadCompletionListener) {
        this.threadCompletionListener = threadCompletionListener;
    }
}
