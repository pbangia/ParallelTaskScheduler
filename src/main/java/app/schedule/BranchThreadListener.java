package app.schedule;

//TODO NEED TO REMOVE
import app.data.PartialSolution;

import java.util.List;

public interface BranchThreadListener {

    void onCompletion(PartialSolution completeSolution);

}

