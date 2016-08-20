package app.schedule;


import app.data.Node;
import app.data.PartialSolution;
import app.exceptions.utils.NoRootFoundException;
import app.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TaskScheduler implements BranchThreadListener{

    private static Logger logger = LoggerFactory.getLogger(TaskScheduler.class);

    private final Collection<Node> nodes;
    private int numberOfProcessors;
    private List<BranchThread> branchThreadList;
    private PartialSolution bestPartialSolution = null;

    public TaskScheduler(Collection<Node> nodes, int numberOfProcessors, List<BranchThread> branchThreadList) {
        this.nodes = nodes;
        this.numberOfProcessors = numberOfProcessors;
        this.branchThreadList = branchThreadList;
    }

    public PartialSolution scheduleTasks() throws NoRootFoundException {

        Queue<PartialSolution> solutionQueue = new LinkedList<>();
        solutionQueue.add(new PartialSolution(numberOfProcessors, nodes, 0));

        while (solutionQueue.size() < branchThreadList.size()){

            PartialSolution currentPartialSolution = solutionQueue.remove();
            Set<Node> scheduledNodes = currentPartialSolution.getScheduledNodes();
            Set<Node> unscheduledNodes = currentPartialSolution.getUnscheduledNodes();
            List<Node> nextAvailableNodes = SchedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes);

            if (nextAvailableNodes.isEmpty()) {
                bestPartialSolution = currentPartialSolution;
                logger.debug("New optimal solution found: \n" + bestPartialSolution.toString());
                continue;
            }

            for (Node availableNode : nextAvailableNodes) {
                List<PartialSolution> availablePartialSolutions = SchedulerHelper.getAvailablePartialSolutions(availableNode, currentPartialSolution);
                solutionQueue.addAll(availablePartialSolutions);
            }
        }

        logger.info(String.valueOf(solutionQueue.size()));

        int threadIndex = 0;
        while (!solutionQueue.isEmpty()){
            BranchThread currentThread = branchThreadList.get(threadIndex);
            PartialSolution partialSolutionToDistribute = solutionQueue.remove();
            currentThread.addPartialSolution(partialSolutionToDistribute);
            threadIndex = incrementIndex(threadIndex);
        }

        for (BranchThread thread : branchThreadList){
            thread.setBranchThreadListener(this);
            thread.start();
        }

        boolean loopCondition = true;
        while (loopCondition){
            loopCondition = ThreadUtils.allThreadsInactive(branchThreadList);
        }

        return bestPartialSolution;
    }

    private int incrementIndex(int currentIndex){
        if (currentIndex == branchThreadList.size() - 1){
            return 0;
        } else{
            return currentIndex++;
        }
    }

    @Override
    public synchronized void onCompletion(PartialSolution completeSolution) {
        if (completeSolution.isBetterThan(bestPartialSolution)){
            bestPartialSolution = completeSolution;
        }
    }
}
