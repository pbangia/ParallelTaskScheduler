package app.schedule.parallel;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ThreadManagerTest {

    private ThreadManager threadManager;

    @Test
    public void testSetupBranches(){
        List<BranchThread> branchThreadList = createBranchThreads(5);
        threadManager = new ThreadManager(branchThreadList);

        for (int i = 0, size = branchThreadList.size(); i < size; i++){
            assertEquals( i + 1 + "", branchThreadList.get(i).getName());
            assertEquals(Thread.MAX_PRIORITY, branchThreadList.get(i).getPriority());
        }
    }

    private List<BranchThread> createBranchThreads(int numberToMake) {
        List<BranchThread> branchThreads = new ArrayList<>();
        for (int i = 0; i < numberToMake; i++){
            branchThreads.add(i, new BranchThread());
        }

        return null;
    }

}