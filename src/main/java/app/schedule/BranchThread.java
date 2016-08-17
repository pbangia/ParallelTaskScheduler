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
    private BranchThreadListener branchThreadListener;

    //TODO Branchthread should not take in a TaskScheduler instance. Change to interface reference.
    public BranchThread(BranchThreadListener branchThreadListener, PartialSolution currentPartialSolution){
        this.branchThreadListener = branchThreadListener;
        this.currentPartialSolution = currentPartialSolution;
    }

    //This can now be removed I think, or change to take in interface reference
    public BranchThread(BranchThreadListener branchThreadListener) {
        this.branchThreadListener = branchThreadListener;
    }

    public void setCurrentPartialSolution(PartialSolution currentPartialSolution){
        this.currentPartialSolution = currentPartialSolution;
    }

    //Each thread has these tasks to executes
    @Override
    public void run(){

        Set<Node> scheduledNodes = currentPartialSolution.getScheduledNodes();
        Set<Node> unscheduledNodes = currentPartialSolution.getUnscheduledNodes();

        List<Node> nextAvailableNodes = SchedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes);

        if (currentPartialSolution.isWorseThan(branchThreadListener.getBestPartialSolution())) {
            return;
        }

        //Reached end of solution tree, instead of comparing if this is the best partial solution, it gives the
        //current solution it just computed to taskScheduler which will then conpare
        if (nextAvailableNodes.isEmpty()) {
            branchThreadListener.onLeafReached(currentPartialSolution);
            this.notifyAll();
            return;
        }

        //After creation of all new partial solutions where node can be places next, gives this to taskScheduler to add onto the stack
        for (Node availableNode : nextAvailableNodes) {
            List<PartialSolution> availablePartialSolutions = SchedulerHelper.getAvailablePartialSolutions(availableNode, currentPartialSolution, currentPartialSolution.getNumberOfProcessors());
            branchThreadListener.onNewPartialSolutionsGenerated(availablePartialSolutions);
            this.notifyAll();

        }

    }
}
