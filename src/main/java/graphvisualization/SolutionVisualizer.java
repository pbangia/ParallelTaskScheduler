package graphvisualization;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.generator.*;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.ViewerPipe;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class SolutionVisualizer implements IGraphPublisher{

    private Graph solutionTree;

    public void addRoot() {
        Node n = solutionTree.addNode("0");
        //Node n = solutionTree.getNode("0");
        n.addAttribute("ui.style", "fill-color: #6CC50D;fill-mode: dyn-plain; z-index:1;");
        n.addAttribute("ui.label","Root");
        //n.setAttribute("xyz", 0, 0, 0);
    }

    public void addNode(int parent, int child) {
        String parentName = Integer.toString(parent);
        String childName = Integer.toString(child);
        String edgeName = parentName + "," + childName;

        Node n = solutionTree.addNode(childName);
        n.addAttribute("ui.style", "fill-color: #808080;fill-mode: dyn-plain;z-index:0;");

        solutionTree.addEdge(edgeName, parentName, childName);
        try{
            //Thread.sleep(2000);
        } catch (Exception e) {}
    }

    public void removeNode(int child){
        String childName = Integer.toString(child);

        solutionTree.removeNode(childName);
    }

    public void init(final Graph solutionTree) {
        this.solutionTree = solutionTree;
       // Viewer v = solutionTree.display();
        //Viewer v = new Viewer(solutionTree, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
       // v.enableAutoLayout();
       // v.addDefaultView(true);
        EventQueue.invokeLater(new Runnable() {
            public void run() {

                // System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

                Graph graph = solutionTree;

             /*   String styleSheet = "node {\n"
                        + "fill-color: black;\n"
                        + "}\n"
                        + "node.marked {\n"
                        + "fill-color: red;"
                        + "size: 25px;\n"
                        + "}";*/

               /* graph.addNode("A");
                graph.addNode("B");
                graph.addNode("C");
                graph.addEdge("AB", "A", "B");
                graph.addEdge("BC", "B", "C");
                graph.addEdge("CA", "C", "A");*/

                Graph g2 = new SingleGraph("2");
                g2.addNode("A");
                g2.addNode("B");
                g2.addNode("C");
                g2.addEdge("AB", "A", "B");
                g2.addEdge("BC", "B", "C");
                g2.addEdge("CA", "C", "A");
                // graph.addAttribute("ui.stylesheet", styleSheet);
                // graph.getNode("A").addAttribute("ui.label", "A Node");
                // graph.getNode("A").addAttribute("ui.class", "marked");


                // graph.getNode("B").addAttribute("ui.clicked", "marked");
                graph.setAutoCreate(true);
                g2.setAutoCreate(true);


                Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
                viewer.enableAutoLayout();
                View view = viewer.addDefaultView(false);/**/   // false indicates "no JFrame".
                View defaultView = viewer.getDefaultView();

                //defaultView.setSize(400,400);
                defaultView.setSize(new Dimension(500,400));


                Viewer viewer2 = new Viewer(g2, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
                viewer2.enableAutoLayout();
                View view2= viewer2.addDefaultView(false);/**/   // false indicates "no JFrame".
                View defaultView2 = viewer2.getDefaultView();
                defaultView2.setSize(new Dimension(100,400));


                //JPanel panel = new JPanel();
                //panel.add(defaultView);
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel,2));
                panel.add(defaultView2);
                panel.add(defaultView);

                JFrame frame = new JFrame("Graph");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 400);
               // frame.add(new JScrollPane(defaultView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
                frame.add(panel);

                frame.setVisible(true);
            }
        });

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
