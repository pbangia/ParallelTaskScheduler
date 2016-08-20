package app.schedule;

import app.data.PartialSolution;

public interface BranchThreadListener {

    void onLeafReached(PartialSolution completeSolution);

    PartialSolution getBestSolution();

}

