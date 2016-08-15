package app.utils;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.TimeUnit;

public class Stopwatch {

    private StopWatch stopWatch;

    public Stopwatch(){
        stopWatch = new StopWatch();
    }

    public void start(){
        stopWatch.start();
    }

    public void stop(){
        stopWatch.stop();
    }

    public String getTimeString(){
        long timeTakenMs = getTimeMs();
        String timeTakenFormatted = String.format("%dm:%ds",
                TimeUnit.MILLISECONDS.toMinutes(timeTakenMs),
                TimeUnit.MILLISECONDS.toSeconds(timeTakenMs) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTakenMs))
        );
        return timeTakenFormatted;
    }

    public long getTimeMs(){
        return stopWatch.getTime();
    }

}
