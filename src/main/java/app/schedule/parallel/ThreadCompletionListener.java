package app.schedule.parallel;

/**
 * Callback interface to notify the listener that a Thread has completed its task.
 */
public interface ThreadCompletionListener {

    void onThreadCompletion();

}
