package app.graphvisualization;

import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel {

    private Graph inputGraph;


    public GraphPanel(Graph inputGraph) {
        this.inputGraph=inputGraph;

        this.inputGraph.setAutoCreate(true);

        Viewer viewer = new Viewer(this.inputGraph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
        viewer.enableAutoLayout();
        View view = viewer.addDefaultView(false);/**/   // false indicates "no JFrame".
        View defaultView = viewer.getDefaultView();

        //defaultView.setSize(400,400);
        //defaultView.setSize(new Dimension(500,400));
        JLabel label = new JLabel("Input Graph");
        //JPanel panel = new JPanel();
        //panel.add(defaultView);
        this.setLayout(new BoxLayout(this,1));
        this.add(label);
        this.add(defaultView);

    }

    public void colorRootNodes(String name, Color c) {
        String color = "#000000";
        org.graphstream.graph.Node node = inputGraph.getNode(name);
        if (c == Color.green){
            color = "#5fec18";
        }

        try {
            Thread.sleep(10);
            node.addAttribute("ui.style", "fill-color: " + color + ";fill-mode: plain; size: 20px; text-size: 16px;");
        } catch (Exception e) {}

    }
}

