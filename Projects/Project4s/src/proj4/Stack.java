package proj4;

/**
 * A default Last In First Out stack.
 * 
 * @author James, Shun, Asiya
 * @version 2/22/2026
 */
public class Stack<T>
{
    private T[] contents;
    private int size;

    // Create the stack with a default capacity of 10
    public Stack() {
        contents = (T[]) new Object[10];
        size = 0;
    }


    public boolean isEmpty() {
        return size() == 0;
    }


    public void push(T value) {
        ensureCapacity(size + 1);
        contents[size++] = value;
    }


    public T pop() {
        if (isEmpty()) {
            return null;
        }

        // James: IDK if required, but if it is, you guys need to basically free the popped element by setting it to null or something like that.
        return contents[--size];
    }


    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return contents[size-1];
    }


    public int size() {
        return size;
    }

    /**
     * return stack as a printable string
     * @return string in the form {>A, B, C} where > denotes the stack top
     */
    public String toString() {
        String res = new String("{>");
        for (int i = size - 1; i >= 0; i--) {
            res += contents[i];
            if (i != 0) {
                res += ", ";
            }
        }
        res = res + "}";
        return res;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > contents.length) {
            T[] res = (T[]) new Object[minCapacity];
            for (int i = 0; i < size; i++) {
                res[i] = contents[i];
            }
            contents = res;
        }
    }
    
} 
   

