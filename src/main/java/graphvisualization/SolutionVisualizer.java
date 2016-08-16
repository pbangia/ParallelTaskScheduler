package graphvisualization;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.generator.*;



public class SolutionVisualizer implements IGraphPublisher{

    private Graph solutionTree;

    public void SolutionVisualizer() {
        this.solutionTree = new SingleGraph("SolutionTree");

    }

    public void SolutionVisualizer(Graph graph) {
        this.solutionTree = graph;

    }

    public void addNode(int parent, int child) {
        String parentName = Integer.toString(parent);
        String childName = Integer.toString(child);
        String edgeName = parentName + "," + childName;

        solutionTree.addNode(childName);
        solutionTree.addEdge(edgeName, parentName, childName);

    }

    public void test() {
        Graph graph = new SingleGraph("Random");
        Generator gen = new RandomGenerator(2);
        gen.addSink(graph);


        graph.display();
        gen.begin();
        gen.nextEvents();
        for(int i=0; i<100; i++)
            //gen.nextEvents();
        gen.end();
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
