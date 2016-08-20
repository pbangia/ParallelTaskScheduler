package app.utils;


import java.util.List;

public class ThreadUtils {

    public static boolean allThreadsInactive(List<? extends Thread> branchThreadList) {
        for (Thread thread : branchThreadList) {
            if (thread.isAlive()) {
                return false;
            }
        }
        return true;

    }

    public static boolean allThreadsActive(List<? extends Thread> branchThreadList) {
        for (Thread thread : branchThreadList) {
            if (!thread.isAlive()) {
                return false;
            }
        }
        return true;

    }

    public static boolean atLeastOneThreadActive(List<? extends Thread> branchThreadList) {
        for (Thread thread : branchThreadList) {
            if (thread.isAlive()) {
                return true;
            }
        }
        return false;

    }
}
