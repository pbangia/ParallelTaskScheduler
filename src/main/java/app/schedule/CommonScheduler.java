package app.schedule;

import app.data.Node;
import app.data.PartialSolution;

import java.util.Collection;
import java.util.List;

/**
 * Created by User on 23/08/2016.
 */
public abstract class CommonScheduler {

    protected final Collection<Node> nodes;
    protected int numberOfProcessors;
    protected PartialSolution bestPartialSolution = null;



}
