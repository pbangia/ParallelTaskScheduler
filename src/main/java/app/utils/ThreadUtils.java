package app.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ThreadUtils {

    private static Logger logger = LoggerFactory.getLogger(ThreadUtils.class);

    public static boolean atLeastOneThreadActive(List<? extends Thread> branchThreadList) {
        for (Thread thread : branchThreadList) {
            if (thread.getState() != Thread.State.TERMINATED) {
                return true;
            }
        }
        return false;

    }
}
