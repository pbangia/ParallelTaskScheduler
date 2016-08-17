package app.schedule;


import app.data.PartialSolution;

import java.util.List;

public interface BranchThreadListener {
    // set partial solution method currently . ie compare and set best partial solution
    void onLeafReached(PartialSolution ps);

    // addavailablepartialsolutions
    void onNewPartialSolutionsGenerated(List<PartialSolution> availablePartialSolutions);

    PartialSolution getBestPartialSolution();
}

