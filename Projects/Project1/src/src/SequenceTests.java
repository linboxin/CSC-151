import org.junit.*;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import proj2.Sequence;

public class SequenceTests {

    @Rule
    public Timeout timeout = Timeout.millis(100);

    /**
     * Basic setup helper:
     * Builds a Sequence (default capacity 10) containing the given values in order.
     * If a value is null, it is skipped (so you can call makeSeq(null, null, ...)).
     *
     * Note: after adding, if there is at least one element, the current element
     * will be the LAST element added (because addAfter makes the new element current).
     */
    private Sequence makeSeq(String... values) {
        Sequence s = new Sequence();
        for (String v : values) {
            if (v != null) s.addAfter(v);
        }
        return s;
    }

    // ----------------- testConstructor -----------------

    @Test
    public void testConstructor_default() {
        Sequence s = new Sequence();
        assertEquals("Default capacity should be 10", 10, s.getCapacity());
        assertEquals("New sequence should be empty", 0, s.size());
        assertFalse("Empty sequence should have no current", s.isCurrent());
        assertNull("getCurrent should be null when there is no current", s.getCurrent());
        assertEquals("Empty sequence toString format", "{} (capacity = 10)", s.toString());
    }

    @Test
    public void testConstructor_custom() {
        Sequence s = new Sequence(4);
        assertEquals("Capacity should match initialCapacity", 4, s.getCapacity());
        assertEquals("New sequence should be empty", 0, s.size());
        assertFalse("Empty sequence should have no current", s.isCurrent());
        assertNull("getCurrent should be null when there is no current", s.getCurrent());
        assertEquals("Empty sequence toString format", "{} (capacity = 4)", s.toString());
    }

    // ----------------- testAddBefore -----------------

    @Test
    public void testAddBefore_thereIsCurrent() {
        // makeSeq("A","B","C") => current is "C"
        Sequence s = makeSeq("A", "B", "C");
        s.start();     // current = "A"
        s.advance();   // current = "B"
        assertEquals("Precondition current should be B", "B", s.getCurrent());

        s.addBefore("X"); // insert before B, current becomes X

        assertEquals("Current should become newly added element", "X", s.getCurrent());
        assertEquals("Sequence contents and current marker",
                "{A, >X, B, C} (capacity = 10)", s.toString());
    }

    @Test
    public void testAddBefore_noCurrent_sequenceEmpty() {
        Sequence s = makeSeq(); // empty, no current
        assertFalse("Precondition: empty has no current", s.isCurrent());

        s.addBefore("A");

        assertTrue("Now there should be a current", s.isCurrent());
        assertEquals("Current should be A", "A", s.getCurrent());
        assertEquals("Single element with current",
                "{>A} (capacity = 10)", s.toString());
    }

    @Test
    public void testAddBefore_noCurrent_reachedEnd() {
        Sequence s = makeSeq("A", "B");
        s.start();
        while (s.isCurrent()) s.advance(); // now no current (past end)
        assertFalse("Precondition: no current after reaching end", s.isCurrent());

        s.addBefore("X"); // spec: if no current, add to beginning

        assertTrue("Should have current after addBefore", s.isCurrent());
        assertEquals("Current should be newly added element", "X", s.getCurrent());
        assertEquals("X should be inserted at beginning",
                "{>X, A, B} (capacity = 10)", s.toString());
    }

    // ----------------- testAddAfter -----------------

    @Test
    public void testAddAfter_thereIsCurrent() {
        Sequence s = makeSeq("A", "B", "C");
        s.start();     // A
        s.advance();   // B
        assertEquals("Precondition current should be B", "B", s.getCurrent());

        s.addAfter("X"); // insert after B, current becomes X

        assertEquals("Current should become newly added element", "X", s.getCurrent());
        assertEquals("Sequence contents and current marker",
                "{A, B, >X, C} (capacity = 10)", s.toString());
    }

    @Test
    public void testAddAfter_noCurrent_sequenceEmpty() {
        Sequence s = makeSeq();
        assertFalse("Precondition: empty has no current", s.isCurrent());

        s.addAfter("A");

        assertTrue("Now there should be a current", s.isCurrent());
        assertEquals("Current should be A", "A", s.getCurrent());
        assertEquals("Single element with current",
                "{>A} (capacity = 10)", s.toString());
    }

    @Test
    public void testAddAfter_noCurrent_reachedEnd() {
        Sequence s = makeSeq("A", "B");
        s.start();
        while (s.isCurrent()) s.advance(); // now no current (past end)
        assertFalse("Precondition: no current after reaching end", s.isCurrent());

        s.addAfter("X"); // spec: if no current, add to end

        assertTrue("Should have current after addAfter", s.isCurrent());
        assertEquals("Current should be newly added element", "X", s.getCurrent());
        assertEquals("X should be appended at end",
                "{A, B, >X} (capacity = 10)", s.toString());
    }

    // ----------------- testIsCurrent -----------------

    @Test
    public void testIsCurrent_whenThereIsCurrent() {
        Sequence s = makeSeq("A");
        assertTrue("After adding an element, there should be a current", s.isCurrent());
        assertEquals("Current should be A", "A", s.getCurrent());
    }

    @Test
    public void testIsCurrent_whenThereIsNoCurrent() {
        Sequence empty = makeSeq();
        assertFalse("Empty sequence has no current", empty.isCurrent());

        Sequence s = makeSeq("A");
        s.start();
        while (s.isCurrent()) s.advance(); // past end
        assertFalse("After reaching end, there should be no current", s.isCurrent());
    }

    // ----------------- testGetCapacity -----------------

    @Test
    public void testGetCapacity_sequenceEmpty() {
        Sequence s = makeSeq();
        assertEquals("Empty default sequence capacity should be 10", 10, s.getCapacity());
    }

    @Test
    public void testGetCapacity_sequenceNotEmpty() {
        Sequence s = makeSeq("A", "B");
        assertEquals("Capacity should still be 10 if no expansion happened", 10, s.getCapacity());
        assertEquals("Size should be 2", 2, s.size());
    }

    // ----------------- testGetCurrent -----------------

    @Test
    public void testGetCurrent_thereIsCurrent_middle() {
        Sequence s = makeSeq("A", "B", "C");
        s.start();     // A
        s.advance();   // B
        assertTrue("Should have current", s.isCurrent());
        assertEquals("Current should be B", "B", s.getCurrent());
    }

    @Test
    public void testGetCurrent_noCurrent_empty() {
        Sequence s = makeSeq();
        assertFalse("Empty has no current", s.isCurrent());
        assertNull("getCurrent should return null when empty", s.getCurrent());
    }

    @Test
    public void testGetCurrent_noCurrent_reachedEnd() {
        Sequence s = makeSeq("A", "B");
        s.start();
        while (s.isCurrent()) s.advance(); // past end
        assertFalse("No current after reaching end", s.isCurrent());
        assertNull("getCurrent should return null when no current", s.getCurrent());
    }

    // ----------------- testEnsureCapacity -----------------

    @Test
    public void testEnsureCapacity_minEqualsCurrent_doesNothing_keepsCurrent() {
        Sequence s = makeSeq("A", "B", "C"); // capacity 10
        s.start();
        s.advance(); // current = B

        String currentBefore = s.getCurrent();
        String contentsBefore = s.toString();

        s.ensureCapacity(10);

        assertEquals("Capacity unchanged", 10, s.getCapacity());
        assertEquals("Current unchanged", currentBefore, s.getCurrent());
        assertEquals("Contents unchanged", contentsBefore, s.toString());
    }

    @Test
    public void testEnsureCapacity_minLessThanCurrent_doesNothing_keepsCurrent() {
        Sequence s = makeSeq("A", "B", "C"); // capacity 10
        s.start();
        s.advance(); // current = B

        String currentBefore = s.getCurrent();
        String contentsBefore = s.toString();

        s.ensureCapacity(5);

        assertEquals("Capacity unchanged", 10, s.getCapacity());
        assertEquals("Current unchanged", currentBefore, s.getCurrent());
        assertEquals("Contents unchanged", contentsBefore, s.toString());
    }

    @Test
    public void testEnsureCapacity_currentLessThanMin_increases_keepsCurrent() {
        Sequence s = new Sequence(3);
        s.addAfter("A");
        s.addAfter("B");
        s.addAfter("C"); // current = C
        s.start();
        s.advance(); // current = B

        String currentBefore = s.getCurrent();

        s.ensureCapacity(12);

        assertEquals("Capacity should increase to minCapacity", 12, s.getCapacity());
        assertEquals("Current should remain the same element", currentBefore, s.getCurrent());
        assertEquals("Order/current marker unchanged, capacity updated",
                "{A, >B, C} (capacity = 12)", s.toString());
    }

    // ----------------- testAddAll -----------------

    @Test
    public void testAddAll_sumEqualsCapacity_addsAll_keepsCurrents_noSideEffects() {
        Sequence a = new Sequence(5);
        a.addAfter("A");
        a.addAfter("B"); // current = B
        a.start();
        a.advance();     // ensure current = B

        Sequence b = makeSeq("C", "D", "E"); // current = E
        b.start();
        b.advance();     // current = D

        String aCurrentBefore = a.getCurrent();
        String bCurrentBefore = b.getCurrent();
        String bBefore = b.toString();

        // a.size=2, b.size=3, a.capacity=5 => exactly fits
        a.addAll(b);

        assertEquals("a capacity should remain 5", 5, a.getCapacity());
        assertEquals("a current should stay the same element", aCurrentBefore, a.getCurrent());
        assertEquals("b current should stay the same element", bCurrentBefore, b.getCurrent());
        assertEquals("b should be unchanged (NO SIDE EFFECTS)", bBefore, b.toString());

        assertEquals("a should have b appended, current still on B",
                "{A, >B, C, D, E} (capacity = 5)", a.toString());
    }

    @Test
    public void testAddAll_sumLessThanCapacity_addsAll_keepsCurrents_noSideEffects() {
        Sequence a = new Sequence(10);
        a.addAfter("A");
        a.addAfter("B"); // current = B
        a.start();       // current = A

        Sequence b = makeSeq("C", "D", "E");
        b.start();       // current = C

        String aCurrentBefore = a.getCurrent(); // "A"
        String bCurrentBefore = b.getCurrent(); // "C"
        String bBefore = b.toString();

        a.addAll(b);

        assertEquals("a capacity should remain 10", 10, a.getCapacity());
        assertEquals("a current should stay the same element", aCurrentBefore, a.getCurrent());
        assertEquals("b current should stay the same element", bCurrentBefore, b.getCurrent());
        assertEquals("b should be unchanged", bBefore, b.toString());

        assertEquals("a should have b appended, current still on A",
                "{>A, B, C, D, E} (capacity = 10)", a.toString());
    }

    @Test
    public void testAddAll_sumGreaterThanCapacity_expandsJustEnough_addsAll_keepsCurrents_noSideEffects() {
        Sequence a = new Sequence(4);
        a.addAfter("A");
        a.addAfter("B");
        a.addAfter("C"); // size=3, cap=4
        a.start();
        a.advance();     // current = B

        Sequence b = makeSeq("D", "E", "F"); // size=3
        b.start();
        b.advance();     // current = E

        String aCurrentBefore = a.getCurrent(); // "B"
        String bCurrentBefore = b.getCurrent(); // "E"
        String bBefore = b.toString();

        // total size = 6 > 4 => must expand; spec says "just enough" => capacity 6
        a.addAll(b);

        assertEquals("a capacity should expand to just enough (6)", 6, a.getCapacity());
        assertEquals("a current should stay the same element", aCurrentBefore, a.getCurrent());
        assertEquals("b current should stay the same element", bCurrentBefore, b.getCurrent());
        assertEquals("b should be unchanged", bBefore, b.toString());

        assertEquals("a should have b appended, current still on B",
                "{A, >B, C, D, E, F} (capacity = 6)", a.toString());
    }

    // =====================================================================
    // Friend's tests (clone, removeCurrent, clear, isEmpty, equals) merged in
    // =====================================================================

    @Test
    public void testCloneEmpty() {
        Sequence s = makeSeq(null, null, null, null, null);
        s.start();

        Sequence newS = s.clone();
        assertTrue("Copying an empty sequence gives an exact empty copy",
                s.toString().equals(newS.toString()));
        assertEquals("Empty sequence has same size as copied sequence",
                s.size(), newS.size());
        assertEquals("Empty sequence and copied sequence have no current",
                s.getCurrent(), newS.getCurrent());

        // modify original; copy should not change
        s.addAfter("A");
        assertFalse("Modifying original does not affect the clone",
                s.toString().equals(newS.toString()));
        assertNotEquals("Sizes should differ after modifying original",
                s.size(), newS.size());
    }

    @Test
    public void testCloneNotEmpty() {
        Sequence s = makeSeq("A", "B", "C", "D", null);
        s.start();

        Sequence newS = s.clone();
        assertTrue("Copying a non-empty sequence gives an exact copy",
                s.toString().equals(newS.toString()));
        assertEquals("Original sequence has same size as copied sequence",
                s.size(), newS.size());
        assertEquals("Original and copied have same current",
                s.getCurrent(), newS.getCurrent());

        // modify original current; clone current should stay where it was
        s.advance();
        assertNotEquals("Advancing original changes current vs clone current",
                s.getCurrent(), newS.getCurrent());

        // modify original structure; clone should not change
        newS.advance();
        s.addAfter("A");
        assertFalse("Modifying original does not affect the clone",
                s.toString().equals(newS.toString()));
        assertNotEquals("Sizes should differ after modifying original",
                s.size(), newS.size());
    }

    @Test
    public void testRemoveLastCurrentInFullSeq() {
        Sequence s = makeSeq("A", "B", "C", "D", "E");
        s.start();
        // make current last
        for (int i = 1; i < 5; i++) {
            s.advance();
        }

        s.removeCurrent();
        assertNull("Should be no current", s.getCurrent());
        assertEquals("Size after removal of last element should be 4", 4, s.size());
    }

    @Test
    public void testRemoveFirstCurrentInFullSeq() {
        Sequence s = makeSeq("A", "B", "C", "D", "E");
        s.start();

        s.removeCurrent();
        assertEquals("Current should become next element", "B", s.getCurrent());
        assertEquals("Size after removal should be 4", 4, s.size());
    }

    @Test
    public void testRemoveLastCurrentInNotFullSeq() {
        Sequence s = makeSeq("A", "B", "C", null, null);
        s.start();
        s.advance();
        s.advance(); // current = C (last)

        s.removeCurrent();
        assertNull("There should be no current", s.getCurrent());
        assertEquals("Size after removal should be 2", 2, s.size());
    }

    @Test
    public void testRemoveMiddleCurrentInNotFullSeq() {
        Sequence s = makeSeq("A", "B", "C", null, null);
        s.start();
        s.advance(); // current = B

        s.removeCurrent();
        assertEquals("Current should become next element", "C", s.getCurrent());
        assertEquals("Size after removal should be 2", 2, s.size());
    }

    @Test
    public void testClear() {
        Sequence s = makeSeq("A", "B", "C", null, null);
        s.start();

        s.clear();
        assertEquals("Cleared sequence should have size 0", 0, s.size());
        assertNull("Cleared sequence should have no current", s.getCurrent());
        assertTrue("Cleared sequence should be empty", s.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        Sequence emptyS = makeSeq(null, null, null, null, null);
        assertTrue("Empty sequence should return true", emptyS.isEmpty());

        Sequence notEmptyS = makeSeq("A", null, null, null, null);
        assertFalse("Non-empty sequence should return false", notEmptyS.isEmpty());
    }

    @Test
    public void testEqualsEmptySeqs() {
        Sequence emptyS1 = makeSeq(null, null, null, null, null);
        Sequence emptyS2 = makeSeq(null, null, null, null, null);
        emptyS1.start();
        emptyS2.start();

        assertTrue("Sequences with same elements and same current are equal",
                emptyS1.equals(emptyS2));
        assertEquals("Equal sequences should have same size",
                emptyS1.size(), emptyS2.size());
    }

    @Test
    public void testEqualsFullSeqs() {
        Sequence s1 = makeSeq("A", "B", "C", "D", "E");
        Sequence s2 = makeSeq("A", "B", "C", "D", "E");
        s1.start();
        s2.start();

        assertTrue("Sequences with same elements and same current are equal",
                s1.equals(s2));
        assertEquals("Equal sequences should have same size",
                s1.size(), s2.size());

        s1.advance();
        assertFalse("Different current makes sequences unequal",
                s1.equals(s2));
    }

    @Test
    public void testEqualsNotFullSeqs() {
        Sequence s1 = makeSeq("A", "B", "C", null, null);
        Sequence s2 = makeSeq("A", "B", "C", null, null);
        s1.start();
        s2.start();

        assertTrue("Sequences with same elements and same current are equal",
                s1.equals(s2));
        assertEquals("Equal sequences should have same size",
                s1.size(), s2.size());

        s1.advance();
        assertFalse("Different current makes sequences unequal",
                s1.equals(s2));
    }

    @Test
    public void testEqualsNotFullSeqsDiffCap() {
        Sequence s1 = makeSeq("A", "B", "C", null, null); // capacity 10

        Sequence s2 = new Sequence(4); // different capacity
        s2.addAfter("A");
        s2.addAfter("B");
        s2.addAfter("C");

        s1.start();
        s2.start();

        assertTrue("Capacity can differ and still be equal (per spec)",
                s1.equals(s2));
        assertEquals("Equal sequences should have same size",
                s1.size(), s2.size());

        s1.advance();
        assertFalse("Different current makes sequences unequal",
                s1.equals(s2));
    }
}
