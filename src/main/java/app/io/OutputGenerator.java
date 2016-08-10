package app.io;

import app.data.PartialSolution;
import app.data.Processor;
import app.data.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class OutputGenerator {

    private static Logger logger = LoggerFactory.getLogger(OutputGenerator.class);

    public String generateOutput(PartialSolution solution, List<String> dependencies) {

        StringBuilder sb = new StringBuilder();
        int processorNumber = 0;

        for (Processor p : solution.getProcessors()) {
            for (Node n : p.getNodeQueue()) {
                n.setProcessorNumber(processorNumber);
                n.setTimestamp(p.getTimeStamp(n));
                sb.append(n.toString());
            }
            processorNumber++;
        }

        for (String dependency : dependencies){
            sb.append(dependency);
        }

        return sb.toString();
    }
}