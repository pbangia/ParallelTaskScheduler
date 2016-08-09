package app.output;

import app.schedule.solutionEntities.PartialSolution;
import app.schedule.solutionEntities.Processor;
import app.data.Node;


public class OutputGenerator {

    public String generateOutput(PartialSolution solution) {

        StringBuilder sb = new StringBuilder();
        int processorNumber = 0;

        //Iterate through each processor
        for (Processor p : solution.getProcessors()) {
            for (Node n : p.getNodeQueue()) {
                //Update node fields
                n.setProcessorNumber(processorNumber);
                n.setTimestamp(p.getTimeStamp(n));
                sb.append(n.toString());
            }
            processorNumber++;
        }

        // for dependency in dependencies:
        //      sb.append(dependency);

        return sb.toString();
    }
}
