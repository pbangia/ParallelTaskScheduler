package app.schedule.parallel;

import app.schedule.datatypes.PartialSolution;

public interface BranchThreadListener {

    void onLeafReached(PartialSolution completeSolution);

    PartialSolution currentBest();
}

