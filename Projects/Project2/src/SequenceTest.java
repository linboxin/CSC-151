/**
* JUnit test class.  Use these tests as models for your own.
*/
import org.junit.*;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import proj2.Sequence;

public class SequenceTest {

   @Rule
   public Timeout timeout = Timeout.millis(100); // fail after 1/10 sec


   /**
    * Builds a Sequence (default capacity 10) containing the given values in order.
    */
   private Sequence makeSeq(String... values) {
       Sequence s = new Sequence();
       for (String v : values) {
           // I added this if v!=null here so that it can adapt to Ayanat's testcases
           if (v != null) s.addAfter(v);
       }
       return s;
   }

   @Test
   public void testConstructor_default() {
       Sequence s = new Sequence();
       assertEquals("Default capacity should be 10", 10, s.getCapacity());
       assertEquals("New sequence should be empty", 0, s.size());
       assertEquals("Empty sequence toString format", "{} (capacity = 10)", s.toString());
   }

   @Test
   public void testConstructWithInitialCapacity(){
       Sequence s = new Sequence(4);
       assertEquals("Capacity should match initialCapacity", 4, s.getCapacity());
       assertEquals("New sequence should be empty", 0, s.size());
       assertEquals("Empty sequence toString format", "{} (capacity = 4)", s.toString());
   }

   @Test
   public void testAddBefore_thereIsCurrent() {
       Sequence s = makeSeq("A", "B", "C");
       assertEquals("Precondition current should be C", "C", s.getCurrent());
       s.addBefore("H");
       assertEquals("Sequence contents and current marker", "{A, B, >H, C} (capacity = 10)", s.toString());
   }
   @Test
   public void testAddBefore_noCurrent_Empty() {
       Sequence s = makeSeq();
       s.addBefore("A");
       assertEquals("Current should be A", "A", s.getCurrent());
       assertEquals("Single element with current", "{>A} (capacity = 10)", s.toString());
   }

   @Test
   public void testAddBefore_noCurrent_atCapacity() {
       Sequence s = makeSeq("A", "B");
       s.start();
       while (s.isCurrent()) s.advance();
       s.addBefore("C");
       assertEquals("Current should be newly added element", "C", s.getCurrent());
       assertEquals("C should be inserted at beginning",
               "{>C, A, B} (capacity = 10)", s.toString());
   }

   @Test
   public void testAddAfter_IsCurrent() {
       Sequence s = makeSeq("A", "B", "C");
       s.addAfter("D");
       assertEquals("Current should become newly added element", "D", s.getCurrent());
       assertEquals("Sequence contents and current marker", "{A, B, C, >D} (capacity = 10)", s.toString());
   }

   @Test
   public void testAddAfter_noCurrent_Empty() {
       Sequence s = makeSeq();
       s.addAfter("A");
       assertEquals("Current should be A", "A", s.getCurrent());
       assertEquals("Single element with current", "{>A} (capacity = 10)", s.toString());
   }

   @Test
   public void testAddAfter_noCurrent_atEND() {
       Sequence s = makeSeq("A", "B");
       s.start();
       while (s.isCurrent()) s.advance();
       s.addAfter("C");
       assertEquals("Current should be newly added element", "C", s.getCurrent());
       assertEquals("C should be appended at end", "{A, B, >C} (capacity = 10)", s.toString());
   }

   @Test
   public void testIsCurrent_IsCurrent() {
       Sequence s = makeSeq("A");
       assertTrue("After adding an element, there should be a current", s.isCurrent());
       assertEquals("Current should be A", "A", s.getCurrent());
   }

   @Test
   public void testIsCurrent_IsNoCurrent() {
       Sequence empty = makeSeq();
       assertFalse("Empty sequence has no current", empty.isCurrent());
       Sequence s = makeSeq("A");
       s.start();
       while (s.isCurrent()) s.advance();
       assertFalse("After reaching end, there should be no current", s.isCurrent());
   }

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

   @Test
   public void testGetCurrent_thereIsCurrent_middle() {
       Sequence s = makeSeq("A", "B", "C");
       s.start();
       s.advance();
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
       while (s.isCurrent()) s.advance();
       assertFalse("No current after reaching end", s.isCurrent());
       assertNull("getCurrent should return null when no current", s.getCurrent());
   }

   @Test
   public void testEnsureCapacity_minEqualsCurrent() {
       Sequence s = makeSeq("A", "B", "C");
       s.start();
       s.advance();

       String currentBefore = s.getCurrent();
       String contentsBefore = s.toString();
       s.ensureCapacity(10);
       assertEquals("Capacity unchanged", 10, s.getCapacity());
       assertEquals("Current unchanged", currentBefore, s.getCurrent());
       assertEquals("Contents unchanged", contentsBefore, s.toString());
   }

   @Test
   public void testEnsureCapacity_minLessThanCurrent() {
       Sequence s = makeSeq("A", "B", "C");
       s.start();
       s.advance();
       String currentBefore = s.getCurrent();
       String contentsBefore = s.toString();
       s.ensureCapacity(5);
       assertEquals("Capacity unchanged", 10, s.getCapacity());
       assertEquals("Current unchanged", currentBefore, s.getCurrent());
       assertEquals("Contents unchanged", contentsBefore, s.toString());
   }

   @Test
   public void testEnsureCapacity_currentLessThanMin() {
       Sequence s = new Sequence(3);
       s.addAfter("A");
       s.addAfter("B");
       s.addAfter("C");
       s.start();
       s.advance();
       String currentBefore = s.getCurrent();
       s.ensureCapacity(12);
       assertEquals("Capacity should increase to minCapacity", 12, s.getCapacity());
       assertEquals("Current should remain the same element", currentBefore, s.getCurrent());
       assertEquals("Order/current marker unchanged, capacity updated", "{A, >B, C} (capacity = 12)", s.toString());
   }

   @Test
   public void testAddAll_sumEqualsCapacity() {
       Sequence a = new Sequence(5);
       a.addAfter("A");
       a.addAfter("B");

       Sequence b = makeSeq("C", "D", "E");
       b.start();
       b.advance();

       String aCurrentBefore = a.getCurrent();
       String bCurrentBefore = b.getCurrent();
       String bBefore = b.toString();
       a.addAll(b);

       assertEquals("a capacity should remain 5", 5, a.getCapacity());
       assertEquals("a current should stay the same element", aCurrentBefore, a.getCurrent());
       assertEquals("b current should stay the same element", bCurrentBefore, b.getCurrent());
       assertEquals("b should be unchanged", bBefore, b.toString());
       assertEquals("a should have b appended, current still on B", "{A, >B, C, D, E} (capacity = 5)", a.toString());
   }

   @Test
   public void testAddAll_sumLessThanCapacity() {
       Sequence a = new Sequence(10);
       a.addAfter("A");
       a.addAfter("B");
       a.start();

       Sequence b = makeSeq("C", "D", "E");
       b.start();
       String aCurrentBefore = a.getCurrent();
       String bCurrentBefore = b.getCurrent();
       String bBefore = b.toString();

       a.addAll(b);
       assertEquals("a capacity should remain 10", 10, a.getCapacity());
       assertEquals("a current should stay the same element", aCurrentBefore, a.getCurrent());
       assertEquals("b current should stay the same element", bCurrentBefore, b.getCurrent());
       assertEquals("b should be unchanged", bBefore, b.toString());
       assertEquals("a should have b appended, current still on A", "{>A, B, C, D, E} (capacity = 10)", a.toString());
   }

   @Test
   public void testAddAll_sumGreaterThanCapacity() {
       Sequence a = new Sequence(4);
       a.addAfter("A");
       a.addAfter("B");
       a.addAfter("C");
       a.start();
       a.advance();

       Sequence b = makeSeq("D", "E", "F");
       b.start();
       b.advance();

       String aCurrentBefore = a.getCurrent();
       String bCurrentBefore = b.getCurrent();
       String bBefore = b.toString();
       a.addAll(b);

       assertEquals("a capacity should expand to just enough", 6, a.getCapacity());
       assertEquals("a current should stay the same element", aCurrentBefore, a.getCurrent());
       assertEquals("b current should stay the same element", bCurrentBefore, b.getCurrent());
       assertEquals("b should be unchanged", bBefore, b.toString());
       assertEquals("a should have b appended, current still on B", "{A, >B, C, D, E, F} (capacity = 6)", a.toString());
   }

   // Ayanat section below ------------------------

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

       // modify original empty seq
       s.addAfter("A");
       assertFalse("Modifying original does not affect the clone",
               s.toString().equals(newS.toString()));
       assertNotEquals("Sizes should differ after modifying original",
               s.size(), newS.size());
   }
   @Test
   public void testCloneNotEmpty() {
       //test sequence that's not empty
       Sequence s = makeSeq("A", "B", "C", "D", null);
       s.start();

       Sequence newS = s.clone();
       assertTrue("Copying a non-empty sequence gives an exact copy",
               s.toString().equals(newS.toString()));
       assertEquals("Original sequence has same size as copied sequence",
               s.size(), newS.size());
       assertEquals("Original and copied have same current",
               s.getCurrent(), newS.getCurrent());

       //modify original seq
       s.advance();
       assertNotEquals("Advancing original changes current vs clone current",
               s.getCurrent(), newS.getCurrent());

       //modify original seq more
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
       s.advance();

       s.removeCurrent();
       assertNull("There should be no current", s.getCurrent());
       assertEquals("Size after removal should be 2", 2, s.size());
   }

   @Test
   public void testRemoveMiddleCurrentInNotFullSeq() {
       Sequence s = makeSeq("A", "B", "C", null, null);
       s.start();
       s.advance();

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
       Sequence s1 = makeSeq("A", "B", "C", null, null);

       Sequence s2 = new Sequence(4);
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



// Daniel

   @Test
   public void start_setsCurrentToFirst_orNoCurrentIfEmpty() {
       Sequence s = makeSeq("A", "B");
       s.start();
       assertEquals("A", s.getCurrent());

       Sequence t = makeSeq();
       t.start();
       assertNull(t.getCurrent());
   }

   @Test
   public void advance_movesForward_andOffEndClearsCurrent() {
       Sequence s = makeSeq("A", "B");
       s.start();
       s.advance();
       assertEquals("B", s.getCurrent());

       s.advance();
       assertNull(s.getCurrent());
   }

   @Test
   public void advance_whenNoCurrent_isNoOp() {
       Sequence s = makeSeq("A");
       s.start();
       while (s.isCurrent()) s.advance();

       s.advance();
       assertEquals("{A} (capacity = 10)", s.toString());
   }

   @Test
   public void ensureCapacity_growsButDoesNotShrink() {
       Sequence s = new Sequence(2);
       s.ensureCapacity(9);
       assertEquals(9, s.getCapacity());

       s.ensureCapacity(5);
       assertEquals("ensureCapacity should not shrink", 9, s.getCapacity());
   }

   @Test
   public void trimToSize_compactsCapacity_andKeepsCurrent() {
       Sequence s = makeSeq("A", "B", "C");
       s.start();
       s.advance();

       s.ensureCapacity(50);
       s.trimToSize();

       assertEquals("Capacity should become size (3)", 3, s.getCapacity());
       assertEquals("Current should remain B", "B", s.getCurrent());
       assertEquals("{A, >B, C} (capacity = 3)", s.toString());
   }

   @Test
   public void size_behavior() {
       Sequence s = makeSeq("A", "B");
       assertEquals(2, s.size());

       s.clear();
       assertEquals(0, s.size());
   }


}
