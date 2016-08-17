package app.schedule;


import app.data.Node;
import app.data.PartialSolution;
import app.exceptions.utils.NoRootFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;

public class TaskScheduler {

    private static Logger logger = LoggerFactory.getLogger(TaskScheduler.class);

    private final Collection<Node> nodes;
    private SchedulerHelper schedulerHelper;
    private int numberOfProcessors;
    private int numberOfThreads;

    private Stack<PartialSolution> solutionStack;
    private ExecutorService executorService;
    private PartialSolution bestPartialSolution = null;
    List<Node> nextAvailableNodes;

    public TaskScheduler(Collection<Node> nodes, SchedulerHelper schedulerHelper, int numberOfProcessors, int numberOfThreads) {
        this.nodes = nodes;
        this.schedulerHelper = schedulerHelper;
        this.numberOfProcessors = numberOfProcessors;
        this.numberOfThreads = numberOfThreads;
    }

    public PartialSolution scheduleTasks() throws NoRootFoundException {

        solutionStack = new Stack<>();
        executorService = Executors.newFixedThreadPool(numberOfThreads);
        solutionStack.push(new PartialSolution(numberOfProcessors, nodes, 0));

        List<BranchThread> branchThreadList = new ArrayList<>(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++){
            branchThreadList.add(i, new BranchThread(this));
        }

        int currentIndex = 0;
        boolean loopCondition = true;
        while (loopCondition) {

            if (!solutionStack.isEmpty() && !branchThreadList.get(currentIndex).isAlive()){
                PartialSolution current = solutionStack.pop();
                branchThreadList.set(currentIndex, new BranchThread(this, current));
                branchThreadList.get(currentIndex).setCurrentPartialSolution(current);
                logger.error(String.valueOf(branchThreadList.get(currentIndex).getState()));
                branchThreadList.get(currentIndex).start();

                if (currentIndex == numberOfThreads - 1){
                    currentIndex = 0;
                } else{
                    currentIndex++;
                }
            }

            loopCondition = !solutionStack.empty() || atLeastOneActive(branchThreadList);

        }

        logger.error("came out");

        return bestPartialSolution;
    }

    private boolean atLeastOneActive(List<? extends Thread> branchThreadList) {
        for (Thread thread : branchThreadList){
            if (thread.isAlive()){
                return true;
            }
        }

        return false;

    }

    public synchronized void setBestPartialSolution(PartialSolution bestPartialSolution) {
        if (bestPartialSolution.isWorseThan(this.bestPartialSolution)){
            return;
        }
        this.bestPartialSolution = bestPartialSolution;
    }

    public PartialSolution getBestPartialSolution() {
        return bestPartialSolution;
    }

    public synchronized void addPartialSolutions(List<PartialSolution> availablePartialSolutions) {
        this.solutionStack.addAll(availablePartialSolutions);
    }
}
