package proj3;

/**
 * A LinkedinList
 */
public class LinkedList {

    private ListNode head;
    private ListNode tail;
    private int size;

    /** Constructs an empty list. */
    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /** @return the String at position index */
    public String get(int index) {
        if (index < 0 || index >= size){
            return null;
        }
        return nodeAt(index).data;
    }

    /**
     * Replaces the element at index with value.
     * @return the old value at index
     */
    public String set(int index, String value) {
        if (index < 0 || index >= size){
            return null;
        }
        ListNode n = nodeAt(index);
        String old = n.data;
        n.data = value;
        return old;
    }

    /** Adds value to the front of the list. */
    public void addFirst(String value) {
        add(0, value);
    }

    /** Adds value to the end of the list. */
    public void addLast(String value) {
        add(size, value);
    }

    /**
     * Inserts value so it becomes the element at position index.
     */
    public void add(int index, String value) {
        if (index < 0 || index > size){
            return;
        }
        if (index == 0) {
            ListNode n = new ListNode(value, head);
            head = n;
            if (size == 0) {
                tail = head;
            }
            size++;
        } else if (index == size) {
            ListNode n = new ListNode(value, null);
            if (size == 0) {
                head = n;
                tail = n;
            } else {
                tail.next = n;
                tail = n;
            }
            size++;
        } else {
            ListNode prev = nodeAt(index - 1);
            prev.next = new ListNode(value, prev.next);
            size++;
        }
    }

    /**
     * Removes and returns the element at position index.
     */
    public String remove(int index) {
        if (index < 0 || index >= size){
            return null;
        }
        String removed;

        if (index == 0) {
            removed = head.data;
            head = head.next;
            size--;
            if (size == 0) {
                tail = null;
            }
            return removed;
        }

        ListNode prev = nodeAt(index - 1);
        ListNode target = prev.next;
        removed = target.data;

        prev.next = target.next;
        size--;

        if (index == size) {
            tail = prev;
        }
        return removed;
    }

    /**
     * @return the first index where value occurs, or -1 if not found.  Works even if value is null.
     */
    public int indexOf(String value) {
        int i = 0;
        for (ListNode n = head; n != null; n = n.next) {
            if (value == null) {
                if (n.data == null) {
                    return i;
                }
            } else {
                if (value.equals(n.data)) {
                    return i;
                }
            }
            i++;
        }
        return -1;
    }

    /** @return size of list */
    public int size() {
        return size;
    }

    /** @return true if list is empty */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Removes all elements from the list. */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /** Returns the node at position index */
    private ListNode nodeAt(int index) {
        ListNode temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

}
