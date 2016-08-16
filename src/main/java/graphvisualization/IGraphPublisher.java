package graphvisualization;

import app.data.PartialSolution;

public interface IGraphPublisher {

    void onNewPartialSolutionAdded();

    void onPartialSolutionPopped();

    void onBestPartialSolutionUpdated();

    void onCurrentPartialSolutionWorseThanBest();

    void onAlgorithmTerminatedWithBest();
}
