package graphvisualization;

import app.data.Node;
import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class GraphPanel extends JPanel {

    private Graph inputGraph;
    private int number;

    public GraphPanel(Graph inputGraph, int i) {

        this.number = i;
        this.inputGraph=inputGraph;

        this.inputGraph.setAutoCreate(true);

        Viewer viewer = new Viewer(this.inputGraph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
        viewer.enableAutoLayout();
        View view = viewer.addDefaultView(false);/**/   // false indicates "no JFrame".
        View defaultView = viewer.getDefaultView();

        //defaultView.setSize(400,400);
        //defaultView.setSize(new Dimension(500,400));
        JLabel label = new JLabel("Thread: " + Integer.toString(this.number));
        //JPanel panel = new JPanel();
        //panel.add(defaultView);
        this.setLayout(new BoxLayout(this,1));
        this.add(label);
        this.add(defaultView);

    }

    public void colorNode(String name, Color c) {
        String color = "#000000";
        org.graphstream.graph.Node node = inputGraph.getNode(name);

        if(c == Color.black){
            color="#000000";
            node.removeAttribute("ui.style");
        } else if (c == Color.green){
            color = "#5fec18";
        }


        try {
            Thread.sleep(10);
            node.addAttribute("ui.style", "fill-color: " + color + ";fill-mode: dyn-plain; z-index:1;");
        } catch (Exception e) {}

    }
}

