package proj2;  // Gradescope needs this.
/**
 *  I'd fill this in if I were you.
 */
public class Sequence
{

    private String[] contents;
    private int size = 0;
    private int curr = -1;


    /**
     * Creates a new sequence with initial capacity 10.
     */
    public Sequence() {
        contents = new String[10];
    }
    

    /**
     * Creates a new sequence.
     * 
     * @param initialCapacity the initial capacity of the sequence.
     */
    public Sequence(int initialCapacity){
    	contents = new String[initialCapacity];
    }


    /**
     * Insert value at the given index.
     * Make space in the array for the inserted value.
     * Precondition: Index has to be 0<= index <= size
     * PostCondition: size increases by 1 and curr is now the new index.
     */
    private void insert(int index, String item){
        if (size == contents.length) {
            ensureCapacity(contents.length * 2 + 1);
        }

        for (int i =size; i>index; i--) {
            contents[i] = contents[i-1];
        }
        contents[index] = item;
        size += 1;
        curr = index;

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
    public void addBefore(String value)
    {
        if (!isCurrent()) {
            insert(0, value);
        }else{
            insert(curr, value);
        }
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
    public void addAfter(String value)
    {
        int afterCurr = curr + 1;
        if (!isCurrent()) {
            insert(size, value);
        }else{
            insert(afterCurr, value);
        }
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
        return contents.length;
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
        return contents[curr];
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
        if (minCapacity > contents.length){
            String[] newCapacity = new String[minCapacity];
            for (int i = 0; i < size; i++){
                newCapacity[i] = contents[i];
            }
            contents = newCapacity;
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
//        if (isCurrent()){
//            if (current < size() -1) {
//                current++;
//            } else{
//                current = -1;
//            }
//        }
//        if (isCurrent()) {
//            current++;
//        }

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
        return new Sequence(size);
    }


    /**
     * Remove the element at the given index and shift everything to the left.
     * Precondition: 0 <= index < size;
     * PostCondition: size decreases by 1;
     * If the remove element was the last one then the curr becomes -1. or else the curr becomes the index
     */
    private void remove(int index) {
        for (int i = index; i < size -1; i++) {
           contents[i] = contents[i+1];
       }
       contents[size - 1] = null;
       size -=1;

       if (index>= size){
           curr = -1;
       }else{
           curr = index;
       }
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
        if (size == 0 ){
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

        String [] newContents = new String[size];
        for (int i = 0; i < size; i++){
            newContents[i] = contents[i];
        }
        contents = newContents;
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
        return ("Temp");
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
        return true;
    }
    
    
    /**
     * 
     * @return true if Sequence empty, else false
     */
    public boolean isEmpty()
    {
        return size ==0;
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