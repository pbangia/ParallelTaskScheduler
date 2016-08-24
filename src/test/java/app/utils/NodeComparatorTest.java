package app.utils;

import app.schedule.datatypes.Node;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class NodeComparatorTest {

    @Test
    public void testCompare_withUnorderedArrayList_returnsCorrectArray(){
        List<Node> unorderedArray = new ArrayList<>();
        Node a = new Node("a", 1);
        Node b = new Node("b", 2);
        Node c = new Node("c", 3);
        Node d = new Node("d", 4);
        Node e = new Node("e", 5);
        Node f = new Node("f", 6);
        Node g = new Node("g", 7);

        unorderedArray.add(e);
        unorderedArray.add(c);
        unorderedArray.add(a);
        unorderedArray.add(b);
        unorderedArray.add(f);
        unorderedArray.add(d);
        unorderedArray.add(g);

        List<Node> expectedArray = new ArrayList<>();

        expectedArray.add(g);
        expectedArray.add(f);
        expectedArray.add(e);
        expectedArray.add(d);
        expectedArray.add(c);
        expectedArray.add(b);
        expectedArray.add(a);

        Collections.sort(unorderedArray, new NodeComparator());

        assertEquals(expectedArray, unorderedArray);
    }
}