package app.schedule;


import app.data.PartialSolution;

public interface BranchThreadListener {
    // set partial solution method currently . ie compare and set best partial solution
    void setLeafReached(PartialSolution ps);

    // addavailablepartialsolutions
    void setNewPartialSolutionsGenerated(PartialSolution ps);
}

