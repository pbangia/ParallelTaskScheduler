package app.schedule;


import app.data.Node;
import app.data.PartialSolution;
import app.exceptions.utils.NoRootFoundException;
import app.utils.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;

public class TaskScheduler implements BranchThreadListener{

    private static Logger logger = LoggerFactory.getLogger(TaskScheduler.class);

    private final Collection<Node> nodes;
    private int numberOfProcessors;
    private List<BranchThread> branchThreadList;
    private ThreadUtil threadUtil;
    private Stack<PartialSolution> solutionStack;
    private PartialSolution bestPartialSolution = null;

    public TaskScheduler(Collection<Node> nodes, int numberOfProcessors, List<BranchThread> branchThreadList, ThreadUtil threadUtil) {
        this.nodes = nodes;
        this.numberOfProcessors = numberOfProcessors;
        this.threadUtil = threadUtil;
    }

    public PartialSolution scheduleTasks() throws NoRootFoundException {

        solutionStack = new Stack<>();
        solutionStack.push(new PartialSolution(numberOfProcessors, nodes, 0));


        int currentIndex = 0;
        boolean loopCondition = true;
        while (loopCondition) {
            
            // Need to clean this as an empty ArrayList will return NULL
            if (!solutionStack.isEmpty() && !branchThreadList.get(currentIndex).isAlive()){
                PartialSolution current = solutionStack.pop();
                branchThreadList.set(currentIndex, new BranchThread(this, current));
                branchThreadList.get(currentIndex).setCurrentPartialSolution(current); // Get rid of this line - redundant
                branchThreadList.get(currentIndex).start();

                currentIndex = threadUtil.incrementIndex(currentIndex, branchThreadList.size());

            }
            
            // Wait until empty stack or ALL threads have died (one active thread might add to solutionStack)
            //If true, algorithm not finished, do not exit while
            loopCondition = !solutionStack.empty() || !threadUtil.allInactive(branchThreadList);

        }

        return bestPartialSolution;
    }

    
    // Might have to rename this to clarify what it's actually doing
    // Not specifically setting the bestPartialSolution, but comparing Partial Solutions
    @Override
    public synchronized void onLeafReached(PartialSolution bestPartialSolution) {
        if (bestPartialSolution.isWorseThan(this.bestPartialSolution)){
            return;
        }
        logger.info("New optimal solution found: " + bestPartialSolution);
        this.bestPartialSolution = bestPartialSolution;
    }

    @Override
    public PartialSolution getBestPartialSolution() {
        return bestPartialSolution;
    }

    @Override
    public synchronized void onNewPartialSolutionsGenerated(List<PartialSolution> availablePartialSolutions) {
        this.solutionStack.addAll(availablePartialSolutions);
    }
}
