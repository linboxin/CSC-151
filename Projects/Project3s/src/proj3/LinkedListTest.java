package proj3;

import org.junit.*;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class LinkedListTest {

    @Rule
    public Timeout timeout = Timeout.millis(100);

    @Test
    public void testConstructEmpty() {
        LinkedList list = new LinkedList();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testAddFirstAndGet() {
        LinkedList list = new LinkedList();
        list.addFirst("B");
        list.addFirst("A");
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
    }

    @Test
    public void testAddLastAndGet() {
        LinkedList list = new LinkedList();
        list.addLast("A");
        list.addLast("B");
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
    }

    @Test
    public void testAddAtIndexMiddle() {
        LinkedList list = new LinkedList();
        list.addLast("A");
        list.addLast("C");
        list.add(1, "B");
        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    public void testRemoveFrontMiddleEnd() {
        LinkedList list = new LinkedList();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");

        assertEquals("A", list.remove(0));
        assertEquals(2, list.size());
        assertEquals("B", list.get(0));

        assertEquals("C", list.remove(1));
        assertEquals(1, list.size());
        assertEquals("B", list.get(0));
    }

    @Test
    public void testSetReturnsOldValue() {
        LinkedList list = new LinkedList();
        list.addLast("A");
        assertEquals("A", list.set(0, "Z"));
        assertEquals("Z", list.get(0));
    }

    @Test
    public void testIndexOfHandlesNull() {
        LinkedList list = new LinkedList();
        list.addLast("A");
        list.addLast(null);
        list.addLast("B");
        assertEquals(1, list.indexOf(null));
        assertEquals(2, list.indexOf("B"));
        assertEquals(-1, list.indexOf("NOPE"));
    }

    @Test
    public void testClear() {
        LinkedList list = new LinkedList();
        list.addLast("A");
        list.addLast("B");
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testAddIndex0OnEmpty() {
        LinkedList list = new LinkedList();
        list.add(0, "A");
        assertEquals(1, list.size());
        assertEquals("A", list.get(0));
    }

    @Test
    public void testAddAtEndUsingIndexSize() {
        LinkedList list = new LinkedList();
        list.addLast("A");
        list.addLast("B");
        list.add(list.size(), "C");
        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    public void testAddIndex0OnNonEmpty() {
        LinkedList list = new LinkedList();
        list.addLast("B");
        list.addLast("C");
        list.add(0, "A");   
        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    public void testRemoveOnlyElement() {
        LinkedList list = new LinkedList();
        list.addLast("A");
        assertEquals("A", list.remove(0));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        assertNull(list.get(0));
    }

    @Test
    public void testRemoveLastElementUpdatesEndCorrectly() {
        LinkedList list = new LinkedList();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");

        assertEquals("C", list.remove(2));
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));

        list.addLast("D");
        assertEquals(3, list.size());
        assertEquals("D", list.get(2));
    }

    @Test
    public void testRemoveMiddleKeepsLinks() {
        LinkedList list = new LinkedList();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        list.addLast("D");

        assertEquals("B", list.remove(1));
        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));
        assertEquals("D", list.get(2));
    }

    @Test
    public void testSetInvalidIndexDoesNothing() {
        LinkedList list = new LinkedList();
        list.addLast("A");
        assertNull(list.set(-1, "X"));
        assertNull(list.set(1, "X"));
        assertEquals("A", list.get(0));
    }

    @Test
    public void testRemoveInvalidIndexReturnsNull() {
        LinkedList list = new LinkedList();
        list.addLast("A");
        assertNull(list.remove(-1));
        assertNull(list.remove(1));
        assertEquals(1, list.size());
        assertEquals("A", list.get(0));
    }

    @Test
    public void testGetInvalidIndexReturnsNull() {
        LinkedList list = new LinkedList();
        list.addLast("A");
        assertNull(list.get(-1));
        assertNull(list.get(1));
    }

    @Test
    public void testIndexOfDuplicatesReturnsFirst() {
        LinkedList list = new LinkedList();
        list.addLast("A");
        list.addLast("B");
        list.addLast("B");
        list.addLast("C");
        assertEquals(1, list.indexOf("B"));
    }

}
