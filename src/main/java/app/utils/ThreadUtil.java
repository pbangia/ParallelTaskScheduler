package app.utils;


public class ThreadUtil {
    public int incrementIndex(int currentIndex, int size){
        if(currentIndex == size-1){
            return 0;
        } else {
            return currentIndex++;
        }
    }
}
