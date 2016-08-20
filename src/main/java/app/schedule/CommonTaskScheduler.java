package app.schedule;


import app.data.Node;
import app.data.PartialSolution;
import app.exceptions.utils.NoRootFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public abstract class CommonTaskScheduler {

    private static Logger logger = LoggerFactory.getLogger(TaskScheduler.class);

    private final Collection<Node> nodes;
    private SchedulerHelper schedulerHelper;
    private int numberOfProcessors;

    // Constructor For Class
    public CommonTaskScheduler(Collection<Node> nodes, SchedulerHelper schedulerHelper, int numberOfProcessors) {
        this.nodes = nodes;
        this.schedulerHelper = schedulerHelper;
        this.numberOfProcessors = numberOfProcessors;
    }

//    public PartialSolution scheduleTasks() {
//
//        PartialSolution bestPartialSolution = null;
//        Stack<PartialSolution> solutionStack = new Stack<>();
//        List<Node> nextAvailableNodes;
//
//        solutionStack.push(new PartialSolution(numberOfProcessors, nodes));
//
//        while (!solutionStack.empty()) {
//            PartialSolution currentPartialSolution = solutionStack.pop();
//            Set<Node> scheduledNodes = currentPartialSolution.getScheduledNodes();
//            Set<Node> unscheduledNodes = currentPartialSolution.getUnscheduledNodes();
//
//            nextAvailableNodes = schedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes);
//
//            if (currentPartialSolution.isWorseThan(bestPartialSolution)) {
//                continue;
//            }
//
//            if (nextAvailableNodes.isEmpty()) {
//                bestPartialSolution = currentPartialSolution;
//                logger.debug("New optimal solution found: \n" + bestPartialSolution.toString());
//                continue;
//            }
//
//            for (Node availableNode : nextAvailableNodes) {
//                List<PartialSolution> availablePartialSolutions = schedulerHelper.getAvailablePartialSolutions(availableNode, currentPartialSolution, numberOfProcessors);
//                solutionStack.addAll(availablePartialSolutions);
//            }
//        }
//
//        return bestPartialSolution;
//    }

}