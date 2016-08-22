package app.schedule;


import app.data.Node;
import app.data.PartialSolution;
import app.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ParallelScheduler extends CommonScheduler implements BranchThreadListener{

    private static Logger logger = LoggerFactory.getLogger(ParallelScheduler.class);

    private final Collection<Node> nodes;
    private int numberOfProcessors;
    private List<BranchThread> branchThreadList;
    private PartialSolution bestPartialSolution = null;

    public ParallelScheduler(Collection<Node> nodes, int numberOfProcessors, List<BranchThread> branchThreadList) {
        this.nodes = nodes;
        this.numberOfProcessors = numberOfProcessors;
        this.branchThreadList = branchThreadList;
    }

    public PartialSolution scheduleTasks(){

        Queue<PartialSolution> solutionQueue = new LinkedList<>();
        solutionQueue.add(new PartialSolution(numberOfProcessors, nodes, 0));

        while (solutionQueue.size() < branchThreadList.size()){

            PartialSolution current = solutionQueue.remove();
            Set<Node> scheduledNodes = current.getScheduledNodes();
            Set<Node> unscheduledNodes = current.getUnscheduledNodes();
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

        int threadIndex = 0;
        while (!solutionQueue.isEmpty()){
            BranchThread currentThread = branchThreadList.get(threadIndex);
            PartialSolution partialSolutionToDistribute = solutionQueue.remove();
            currentThread.addPartialSolution(partialSolutionToDistribute);
            logger.debug("Distributed to thread: " + threadIndex + "\n" + partialSolutionToDistribute.toString());
            threadIndex = incrementIndex(threadIndex);
        }

        for (BranchThread thread : branchThreadList){
            thread.setBranchThreadListener(this);
            thread.start();
        }

        boolean loopCondition = true;
        while (loopCondition){
            loopCondition = ThreadUtils.atLeastOneThreadActive(branchThreadList);
        }

        return bestPartialSolution;
    }

    private int incrementIndex(int currentIndex){
        if (currentIndex == branchThreadList.size() - 1){
            return 0;
        } else{
            return currentIndex + 1;
        }
    }

    @Override
    public synchronized void onLeafReached(PartialSolution completeSolution) {
        if (completeSolution.isBetterThan(bestPartialSolution)){
            bestPartialSolution = completeSolution;
            logger.debug("New optimal solution found: \n" + bestPartialSolution.toString());
        }
    }

    @Override
    public synchronized PartialSolution currentBest() {
        return bestPartialSolution;
    }
}
