package graphvisualization;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.generator.*;



public class SolutionVisualizer implements IGraphPublisher{

    private Graph solutionTree;

    public void addRoot() {
        solutionTree.addNode("0");
    }

    public void addNode(int parent, int child) {
        String parentName = Integer.toString(parent);
        String childName = Integer.toString(child);
        String edgeName = parentName + "," + childName;

        solutionTree.addNode(childName);
        solutionTree.addEdge(edgeName, parentName, childName);

    }

    public void init(Graph solutionTree) {
        this.solutionTree = solutionTree;
        solutionTree.display();
    }

    @Override
    public void onNewPartialSolutionAdded() {

    }

    @Override
    public void onPartialSolutionPopped() {

    }

    @Override
    public void onBestPartialSolutionUpdated() {

    }

    @Override
    public void onCurrentPartialSolutionWorseThanBest() {

    }

    @Override
    public void onAlgorithmTerminatedWithBest() {

    }

    public Graph getSolutionTree() {
        return this.solutionTree;
    }

    public void setSolutionTree(Graph solutionTree) {
        this.solutionTree = solutionTree;
    }
}
