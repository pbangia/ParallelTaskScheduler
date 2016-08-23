package app.schedule;

import app.schedule.datatypes.Node;
import app.schedule.datatypes.PartialSolution;

import java.util.Collection;

/**
 * Created by User on 23/08/2016.
 */
public abstract class CommonScheduler {

    protected Collection<Node> nodes;
    protected int numberOfProcessors;
    protected PartialSolution bestPartialSolution = null;

    public abstract PartialSolution scheduleTasks();

}
