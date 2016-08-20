package app.schedule;

import app.data.PartialSolution;

public interface BranchThreadListener {

    void onCompletion(PartialSolution completeSolution);

}

