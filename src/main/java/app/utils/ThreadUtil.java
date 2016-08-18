package app.utils;


import java.util.List;

public class ThreadUtil {
    public int incrementIndex(int currentIndex, int size){
        if(currentIndex == size-1){
            return 0;
        } else {
            return currentIndex++;
        }
    }

    public boolean allInactive(List<? extends Thread> branchThreadList) {
        for (Thread thread : branchThreadList){
            if (thread.isAlive()){
                return false;
            }
        }

        return true;

    }
}
