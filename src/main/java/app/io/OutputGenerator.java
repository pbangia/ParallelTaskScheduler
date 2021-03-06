package app.io;

import app.schedule.datatypes.Node;
import app.schedule.datatypes.PartialSolution;
import app.schedule.datatypes.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class OutputGenerator {

    private static Logger logger = LoggerFactory.getLogger(OutputGenerator.class);

    private String outputName;

    public OutputGenerator(String outputName) {
        this.outputName = outputName;
    }

    /**
     * Generates a String output representation of the final best partial solution.
     * @param solution Best partial solution.
     * @param dependencies List of dependencies to add to the end of the output.
     * @return String output containing the partial solution and list of dependencies.
     */
    public String generateOutput(PartialSolution solution, List<String> dependencies) {

        StringBuilder sb = new StringBuilder();
        sb.append("digraph \"" + outputName + "\" {\n");
        int processorNumber = 1;

        for (Processor p : solution.getProcessors()) {
            for (Node n : p.getNodeQueue()) {
                n.setProcessorNumber(processorNumber);
                n.setTimestamp(p.getTimeStamp(n));
                sb.append(n.toString());
            }
            processorNumber++;
        }

        for (String dependency : dependencies) {
            sb.append(dependency);
        }

        sb.append("}");

        return sb.toString();
    }
}
