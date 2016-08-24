package app.schedule.parallel;


import app.graphvisualization.MainGUI;
import app.schedule.CommonScheduler;
import app.schedule.SchedulerHelper;
import app.schedule.datatypes.Node;
import app.schedule.datatypes.PartialSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * ParallelScheduler is in charge of executing the branch and bound dfs algorithm in parallel.
 */
public class ParallelScheduler extends CommonScheduler implements BranchThreadListener {

    private static Logger logger = LoggerFactory.getLogger(ParallelScheduler.class);

    private List<BranchThread> branchThreadList;
    private ThreadManager threadManager;

    public ParallelScheduler(Collection<Node> nodes, int numberOfProcessors, List<BranchThread> branchThreadList, ThreadManager threadManager, boolean guiRequired) {
        this.nodes = nodes;
        this.numberOfProcessors = numberOfProcessors;
        this.branchThreadList = branchThreadList;
        this.threadManager = threadManager;
        this.guiRequired = guiRequired;
        threadManager.setListener(this);
    }

    /**
     * Runs the parallel DFS algorithm for finding the optimal schedule.
     * @return Optimal Schedule
     */
    public PartialSolution scheduleTasks() {

        Queue<PartialSolution> solutionQueue = new LinkedList<>();
        solutionQueue.add(new PartialSolution(numberOfProcessors, nodes));

        runBFS(solutionQueue);
        distributePartialSolutions(solutionQueue);
        runThreads();
        sleep();

        return bestPartialSolution;
    }

    /**
     * Runs a BFS algorithm to grow the input solution queue to allow
     * for a better distribution of tasks to each Thread.
     * @param solutionQueue
     */
    private void runBFS(Queue<PartialSolution> solutionQueue) {

        while (solutionQueue.size() < branchThreadList.size()) {
            PartialSolution current = solutionQueue.remove();
            Set<Node> scheduledNodes = current.getScheduledNodes();
            List<Node> unscheduledNodes = current.getUnscheduledNodes();
            List<Node> nextAvailableNodes = SchedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes);

            if (nextAvailableNodes.isEmpty()) {
                bestPartialSolution = current;
                logger.debug("New optimal solution found: \n" + bestPartialSolution.toString());
                continue;
            }

            for (Node availableNode : nextAvailableNodes) {
                List<PartialSolution> availablePartialSolutions = SchedulerHelper.getAvailablePartialSolutions(availableNode, current);
                solutionQueue.addAll(availablePartialSolutions);
            }
        }
        logger.debug("Solution queue has size : " + solutionQueue.size() + " before parallel execution.");
    }

    /**
     * Distributes the PartialSolutions inside the input queue to the Threads.
     */
    private void distributePartialSolutions(Queue<PartialSolution> solutionQueue) {
        int threadIndex = 0;
        while (!solutionQueue.isEmpty()) {
            BranchThread currentThread = branchThreadList.get(threadIndex);
            PartialSolution partialSolutionToDistribute = solutionQueue.remove();
            currentThread.addPartialSolution(partialSolutionToDistribute);
            logger.debug("Distributed to thread: " + threadIndex + "\n" + partialSolutionToDistribute.toString());
            threadIndex = incrementIndex(threadIndex);
        }
    }

    /**
     * Starts each Thread
     */
    private void runThreads() {
        for (BranchThread thread : branchThreadList) {
            thread.setBranchThreadListener(this);
            thread.setGUIRequired(guiRequired);
            thread.start();
        }
    }

    /**
     * Waits until notified to wake.
     */
    private void sleep() {
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                logger.error("Unexpected interruption of current thread: " + Thread.currentThread().getId());
            }
        }
    }

    private int incrementIndex(int currentIndex) {
        if (currentIndex == branchThreadList.size() - 1) {
            return 0;
        } else {
            return currentIndex + 1;
        }
    }

    @Override
    /**
     * Updates the best PartialSolution if the input PartialSolution is better.
     */
    public synchronized void onLeafReached(PartialSolution completeSolution) {
        if (completeSolution.isBetterThan(bestPartialSolution)) {
            bestPartialSolution = completeSolution;
            if (guiRequired){
                MainGUI.get().updateCurrentBestLength(bestPartialSolution.length());
            }
            logger.debug("Thread " + Thread.currentThread().getName() + " found new optimal solution: \n" + bestPartialSolution.toString());
        }
    }

    @Override
    /**
     * Returns the current best PartialSolution.
     */
    public synchronized PartialSolution currentBest() {
        return bestPartialSolution;
    }
}
