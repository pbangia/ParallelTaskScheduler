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

public class TaskScheduler implements Runnable {

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

        while (!solutionStack.empty()) {
            for (int i = 0; i < numberOfThreads; i++){
                executorService.execute(this);
            }
        }

        logger.error("came out");

        return bestPartialSolution;
    }

    @Override
    public void run() {

        if (solutionStack.empty()){
            return;
        }

        PartialSolution currentPartialSolution = solutionStack.pop();

        Set<Node> scheduledNodes = currentPartialSolution.getScheduledNodes();
        Set<Node> unscheduledNodes = currentPartialSolution.getUnscheduledNodes();

        nextAvailableNodes = schedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes);

        if (currentPartialSolution.isWorseThan(bestPartialSolution)) {
            return;
        }

        if (nextAvailableNodes.isEmpty()) {
            bestPartialSolution = currentPartialSolution;
            logger.debug("New optimal solution found: \n" + bestPartialSolution.toString());
            return;
        }

        for (Node availableNode : nextAvailableNodes) {
            List<PartialSolution> availablePartialSolutions = schedulerHelper.getAvailablePartialSolutions(availableNode, currentPartialSolution, numberOfProcessors);
            solutionStack.addAll(availablePartialSolutions);
        }


    }
}
