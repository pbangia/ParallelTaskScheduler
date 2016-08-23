package app.schedule.serial;

import app.schedule.CommonScheduler;
import app.schedule.SchedulerHelper;
import app.schedule.datatypes.Node;
import app.schedule.datatypes.PartialSolution;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Created by User on 23/08/2016.
 */
public class SerialScheduler extends CommonScheduler {


    // Constructor For Class
    public SerialScheduler(Collection<Node> nodes, int numberOfProcessors) {
        this.nodes = nodes;
        this.numberOfProcessors = numberOfProcessors;
    }

    @Override
    public PartialSolution scheduleTasks() {

        PartialSolution bestPartialSolution = null;
        Stack<PartialSolution> solutionStack = new Stack<>();
        List<Node> nextAvailableNodes;

        solutionStack.push(new PartialSolution(numberOfProcessors, nodes));

        while (!solutionStack.empty()) {
            PartialSolution currentPartialSolution = solutionStack.pop();
            Set<Node> scheduledNodes = currentPartialSolution.getScheduledNodes();
            List<Node> unscheduledNodes = currentPartialSolution.getUnscheduledNodes();


            nextAvailableNodes = SchedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes);

            // Hit if clause when leaf is reached
            if (nextAvailableNodes.isEmpty()) {
                if (currentPartialSolution.isBetterThan(bestPartialSolution)) {
                    bestPartialSolution = currentPartialSolution;
                }
                continue;
            }

            if (currentPartialSolution.isBetterThan(bestPartialSolution)) {
                for (Node availableNode : nextAvailableNodes) {
                    List<PartialSolution> availablePartialSolutions = SchedulerHelper.getAvailablePartialSolutions(availableNode, currentPartialSolution);
                    solutionStack.addAll(availablePartialSolutions);
                }
            }
        }

        return bestPartialSolution;
    }

}

