package app.schedule.serial;

import app.graphvisualization.MainGUI;
import app.schedule.CommonScheduler;
import app.schedule.SchedulerHelper;
import app.schedule.datatypes.Node;
import app.schedule.datatypes.PartialSolution;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * SerialScheduler is in charge of running the branch and bound dfs algorithm in serial (i.e. no extra threads.)
 */
public class SerialScheduler extends CommonScheduler {

    public SerialScheduler(Collection<Node> nodes, int numberOfProcessors, boolean guiRequired) {
        this.nodes = nodes;
        this.numberOfProcessors = numberOfProcessors;
        this.guiRequired = guiRequired;
    }

    @Override
    /**
     * Starts the dfs algorithm to find the optimal schedule.
     * @return PartialSolution the optimal schedule
     */
    public PartialSolution scheduleTasks() {

        PartialSolution bestPartialSolution = null;
        Stack<PartialSolution> solutionStack = new Stack<>();
        List<Node> nextAvailableNodes;

        solutionStack.push(new PartialSolution(numberOfProcessors, nodes));

        while (!solutionStack.empty()) {
            PartialSolution currentPartialSolution = solutionStack.pop();

            if (guiRequired){
                MainGUI.get().updateNumberOfSolutionsExplored();
            }

            Set<Node> scheduledNodes = currentPartialSolution.getScheduledNodes();
            List<Node> unscheduledNodes = currentPartialSolution.getUnscheduledNodes();

            nextAvailableNodes = SchedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes);

            if (currentPartialSolution.isWorseThan(bestPartialSolution)){
                continue;
            }

            if (nextAvailableNodes.isEmpty()) {
                bestPartialSolution = currentPartialSolution;
                if (guiRequired){
                    MainGUI.get().updateCurrentBestLength(bestPartialSolution.length());
                    MainGUI.get().getBestSolutionPanel().updateSolutionTable(bestPartialSolution);
                }
                continue;
            }

            for (Node availableNode : nextAvailableNodes) {
                if (guiRequired){
                    MainGUI.get().getThreadPanel().colorCell("1", availableNode.getName());
                }
                List<PartialSolution> availablePartialSolutions = SchedulerHelper.getAvailablePartialSolutions(availableNode, currentPartialSolution);
                solutionStack.addAll(availablePartialSolutions);
            }
        }

        return bestPartialSolution;
    }

}

