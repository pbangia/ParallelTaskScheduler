package app.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StopwatchTest {

    private Stopwatch stopwatch;

    @Before
    public void setup(){
        stopwatch = new Stopwatch();
    }

    @Test
    public void testGetTimeMs() throws InterruptedException {
        stopwatch.start();
        Thread.sleep(1000);
        stopwatch.stop();
        assertTrue(980 <= stopwatch.getTimeMs() && stopwatch.getTimeMs() <= 1020);
    }

    @Test
    public void testGetTimeString() throws InterruptedException {
        stopwatch.start();
        Thread.sleep(1050);
        stopwatch.stop();
        assertEquals("0m:1s", stopwatch.getTimeString());
    }



}