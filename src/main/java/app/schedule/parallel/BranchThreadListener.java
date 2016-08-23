package app.schedule.parallel;

import app.data.PartialSolution;

public interface BranchThreadListener {

    void onLeafReached(PartialSolution completeSolution);

    PartialSolution currentBest();
}

