package app.schedule.solutionEntities;
import app.data.*;


import java.util.*;

public class PartialSolution {
    private Node latestNodeAdded;
    private int numberOfProcessors;

    //Processor names start from 0
    private List<Processor> processors;

    //Constructor
    public PartialSolution (int numberOfProcessors){
        this.numberOfProcessors = numberOfProcessors;
        for (int i = 0; i < numberOfProcessors; i++){
            //TODO: change the constuctor below depending on how Processor is done
            processors.add(new Processor());
        }
    }

    // Returns last node added to PartialSolution
    public Node getLatestNode(){
        //TODO: fill me in :
        return latestNodeAdded;
    }

    public Set<Node> getScheduledNodes(){
        //TODO: get the scheduled nodes
        return null;
    }


    // Returns boolean, true if this PartialSolution is worse than another partialSolution
    public boolean isWorseThan(PartialSolution otherPartialSolution){
        //TODO: fill me in :3
        //returns true for now
        return true;
    }

    // Returns boolean, true if this PartialSolution is better than another partialSolution
    public boolean isBetterThan(PartialSolution otherPartialSolution){
        //TODO: fill me in :3
        //returns true for now
        return true;
    }

    // Adds a node to a specified processor
    public void addNodeToProcessor(Node addedNode, int processorNumber){
        //TODO: fill me in :3
        //add a node to a processor
        //set the latest node added
    }


    // This method clones a PartialSolution
    public void clone(PartialSolution clonedSolution){
        this.latestNodeAdded = clonedSolution.getLatestNode();
        //TODO: finish off the method
        //needs to clone the processors also, make sure its not copying the object address but actually copying the fields

    }

}
