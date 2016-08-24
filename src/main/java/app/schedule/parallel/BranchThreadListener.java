package app.schedule.parallel;

import app.schedule.datatypes.PartialSolution;

/**
 * Callback interface to allow BranchThreads to communicate with the master scheduler.
 */
public interface BranchThreadListener {

    void onLeafReached(PartialSolution completeSolution);

    PartialSolution currentBest();
}

