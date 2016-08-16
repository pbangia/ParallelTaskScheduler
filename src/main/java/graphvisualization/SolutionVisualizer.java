package graphvisualization;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.generator.*;
import org.graphstream.ui.swingViewer.Viewer;


public class SolutionVisualizer implements IGraphPublisher{

    private Graph solutionTree;

    public void addRoot() {
        solutionTree.addNode("0");
        Node n = solutionTree.getNode("0");
        n.addAttribute("ui.style", "fill-color: #6CC50D; size: 30px;");
        n.setAttribute("xyz", 0, 0, 0);
    }

    public void addNode(int parent, int child) {
        String parentName = Integer.toString(parent);
        String childName = Integer.toString(child);
        String edgeName = parentName + "," + childName;

        solutionTree.addNode(childName);
        solutionTree.getNode(childName).addAttribute("ui.style", "fill-color: #808080; size: 20px;");

        //solutionTree.getNode(childName).addAttribute("ui.style", "fill-color: ;");

        solutionTree.addEdge(edgeName, parentName, childName);
    }

    public void removeNode(int child){
        String childName = Integer.toString(child);

        solutionTree.removeNode(childName);
    }

    public void init(Graph solutionTree) {
        this.solutionTree = solutionTree;
        Viewer v = solutionTree.display();
       // v.disableAutoLayout();
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
