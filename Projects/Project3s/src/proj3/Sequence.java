package proj3;  // Gradescope needs this.
/**
 * @author James Lin
 * @version Project 3
 * I affirm that I have carried out the attached academic endeavors with full academic honesty, in accordance with the Union College Honor Code and the course syllabus.
 */

public class Sequence
{


    /**
     * Invariants
     *
     * contents is Linkedlist that stores the element of the sequence
     * size is the number of valid elements in the sequence
     * curr is the index of the current element. -1 if there is no curr.
     * capacity is the capcity of the sequence.
     */
//    private String[] contents;
    private LinkedList contents;
    private int size = 0;
    private int curr = -1;
    private int capacity;

    /**
     * Creates a new sequence with initial capacity 10.
     */
    public Sequence() {
        this(10);
    }

    /**
     * Creates a new sequence.
     *
     * @param initialCapacity the initial capacity of the sequence.
     */
    public Sequence(int initialCapacity){
        contents = new LinkedList();
        capacity = initialCapacity;
    }

    // Moved all the private helper methods to one location;
    /**
     * Ensure that the sequence have room for at least one more element.
     *
     * PostCondition: The capacity of the sequence is increased if and only if the sequence was previously full.
     */
    private void growIfFull() {
        if (size() == getCapacity()) {
            ensureCapacity(getCapacity() * 2 + 1);
        }
    }

    /**
     * Insert value at the given index.
     * Precondition: 0 <= index <= size
     * PostCondition: size increases by 1 and curr is now index.
     */
    private void insert(int index, String item) {
        growIfFull();
        contents.add(index, item);
        size += 1;
        curr = index;
    }

    /**
     * Remove the element at the given index.
     * Precondition: 0 <= index < size
     * PostCondition: size decreases by 1.
     * If removed element was last, curr becomes -1 otherwise curr becomes index.
     */
    private void remove(int index) {
        contents.remove(index);
        size -= 1;

        if (index >= size()) {
            curr = -1;
        } else {
            curr = index;
        }
    }

    /**
     * Adds a string to the sequence in the location before the
     * current element. If the sequence has no current element, the
     * string is added to the beginning of the sequence.
     *
     * The added element becomes the current element.
     *
     * If the sequences's capacity has been reached, the sequence will
     * expand to twice its current capacity plus 1.
     *
     * @param value the string to add.
     */
    public void addBefore(String value) {
        if (!isCurrent()) {
            curr = 0;
        }
        insert(curr, value);
    }



    /**
     * Adds a string to the sequence in the location after the current
     * element. If the sequence has no current element, the string is
     * added to the end of the sequence.
     *
     * The added element becomes the current element.
     *
     * If the sequences's capacity has been reached, the sequence will
     * expand to twice its current capacity plus 1.
     *
     * @param value the string to add.
     */
    public void addAfter(String value) {
        if (!isCurrent()) {
            curr = size() - 1;
        }

        insert(curr + 1, value);
    }



    /**
     * @return true if and only if the sequence has a current element.
     */
    public boolean isCurrent()
    {
        return curr != -1;
    }


    /**
     * @return the capacity of the sequence.
     */
    public int getCapacity()
    {
        return capacity;
    }


    /**
     * @return the element at the current location in the sequence, or
     * null if there is no current element.
     */
    public String getCurrent()
    {
        if (!isCurrent()){
            return null;
        }
        return contents.get(curr);
    }


    /**
     * Increase the sequence's capacity to be
     * at least minCapacity.  Does nothing
     * if current capacity is already >= minCapacity.
     *
     * @param minCapacity the minimum capacity that the sequence
     * should now have.
     */
    public void ensureCapacity(int minCapacity)
    {
        if (minCapacity > getCapacity()) {
            capacity = minCapacity;
        }
    }


    /**
     * Places the contents of another sequence at the end of this sequence.
     *
     * If adding all elements of the other sequence would exceed the
     * capacity of this sequence, the capacity is changed to make (just enough) room for
     * all of the elements to be added.
     *
     * Postcondition: NO SIDE EFFECTS!  the other sequence should be left
     * unchanged.  The current element of both sequences should remain
     * where they are. (When this method ends, the current element
     * should refer to the same element that it did at the time this method
     * started.)
     *
     * @param another the sequence whose contents should be added.
     */
    public void addAll(Sequence another)
    {
        if (another ==null || another.size() == 0){
            return;
        }
        int savedCurr = curr;
        int needed = size() + another.size();
        ensureCapacity(needed);

        for (int i = 0; i < another.size(); i++) {
            contents.addLast(another.contents.get(i));
        }

        size = needed;
        curr = savedCurr;
    }


    /**
     * Move forward in the sequence so that the current element is now
     * the next element in the sequence.
     *
     * If the current element was already the end of the sequence,
     * then advancing causes there to be no current element.
     *
     * If there is no current element to begin with, do nothing.
     */
    public void advance()
    {
        if (isCurrent()){
            if (curr < size() -1) {
                curr++;
            } else{
                curr = -1;
            }
        }
    }


    /**
     * Make a copy of this sequence.  Subsequence changes to the copy
     * do not affect the current sequence, and vice versa.
     *
     * Postcondition: NO SIDE EFFECTS!  This sequence's current
     * element should remain unchanged.  The clone's current
     * element will correspond to the same place as in the original.
     *
     * @return the copy of this sequence.
     */
    public Sequence clone()
    {
        Sequence clone = new Sequence(getCapacity());
        for (int i = 0; i < size(); i++) {
            clone.contents.addLast(contents.get(i));
        }

        clone.size = size();
        clone.curr = curr;
        return clone;
    }



    /**
     * Remove the current element from this sequence.  The following
     * element, if there was one, becomes the current element.  If
     * there was no following element (current was at the end of the
     * sequence), the sequence now has no current element.
     *
     * If there is no current element, does nothing.
     */
    public void removeCurrent()
    {
        if (isCurrent()){
            remove(curr);
        }
    }


    /**
     * @return the number of elements stored in the sequence.
     */
    public int size()
    {
        return size;
    }


    /**
     * Sets the current element to the start of the sequence.  If the
     * sequence is empty, the sequence has no current element.
     */
    public void start()
    {
        if (size() == 0 ){
            curr = -1;
        }else{
            curr = 0;
        }
    }


    /**
     * Reduce the current capacity to its actual size, so that it has
     * capacity to store only the elements currently stored.
     */
    public void trimToSize()
    {
        capacity = size();
    }


    /**
     * Produce a string representation of this sequence.  The current
     * location is indicated by a >.  For example, a sequence with "A"
     * followed by "B", where "B" is the current element, and the
     * capacity is 5, would print as:
     *
     *    {A, >B} (capacity = 5)
     *
     * The string you create should be formatted like the above example,
     * with a comma following each element, no comma following the
     * last element, and all on a single line.  An empty sequence
     * should give back "{}" followed by its capacity.
     *
     * @return a string representation of this sequence.
     */
    public String toString()
    {
        String result = "{";
        for (int i = 0; i < size(); i++) {
            if (i>0) {
                result += ", ";
            }
            if (i == curr){
                result += ">";
            }
            result += contents.get(i);
        }
        result += "} (capacity = " + getCapacity() + ")";
        return result;
    }

    /**
     * Checks whether another sequence is equal to this one.  To be
     * considered equal, the other sequence must have the same size
     * as this sequence, have the same elements, in the same
     * order, and with the same element marked
     * current.  The capacity can differ.
     *
     * Postcondition: NO SIDE EFFECTS!  this sequence and the
     * other sequence should remain unchanged, including the
     * current element.
     *
     * @param other the other Sequence with which to compare
     * @return true iff the other sequence is equal to this one.
     */
    public boolean equals(Sequence other)
    {
        if (other == null){
            return false;
        }
        if (this.curr != other.curr){
            return false;
        }
        if (this.size() != other.size()){
            return false;
        }
        for (int i = 0; i < size(); i++) {
            String a = this.contents.get(i);
            String b = other.contents.get(i);
            if (a==null ) {
                if (b!= null){
                    return false;
                }
            }else {
                if (!a.equals(b)) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     *
     * @return true if Sequence empty, else false
     */
    public boolean isEmpty()
    {
        return size() ==0;
    }


    /**
     *  empty the sequence.  There should be no current element.
     */
    public void clear()
    {
        size = 0;
        curr = -1;
    }

}