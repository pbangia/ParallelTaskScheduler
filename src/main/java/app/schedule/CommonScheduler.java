package app.schedule;

import app.schedule.datatypes.Node;
import app.schedule.datatypes.PartialSolution;

import java.util.Collection;

/**
 * Abstract class that the Parallel and Serial schedulers extend from.
 */
public abstract class CommonScheduler {

    protected Collection<Node> nodes;
    protected int numberOfProcessors;
    protected PartialSolution bestPartialSolution = null;

    public abstract PartialSolution scheduleTasks();

}
