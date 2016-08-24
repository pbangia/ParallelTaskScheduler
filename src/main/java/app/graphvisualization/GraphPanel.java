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
        View defaultView = viewer.addDefaultView(false);/**/   // false indicates "no JFrame".

        JLabel label = new JLabel("Input Graph");
        this.setLayout(new BoxLayout(this,1));
        this.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.add(label);
        this.add(defaultView);

    }

    public void colorRootNodes(String name) {
        String color = "#0099cc";
        org.graphstream.graph.Node node = inputGraph.getNode(name);

        try {
            Thread.sleep(10);
            node.addAttribute("ui.style", "fill-color: " + color + ";fill-mode: plain; size: 20px; text-size: 16px;");
        } catch (Exception e) {}

    }
}

