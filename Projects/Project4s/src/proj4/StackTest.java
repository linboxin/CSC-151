package proj4;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * 
 * Write a description of class StackTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 *
 */
public class StackTest {

	@Rule
    public Timeout timeout = Timeout.millis(100);
	
    private Stack<String> stack;
    
    @Before
    public void setUp() throws Exception {
        stack = new Stack<String>();
    }

    @After
    public void tearDown() throws Exception {
        stack = null;
    }

    @Test
    public void testStackConstructor_toString () {
        assertEquals ("An empty stack. (> indicates the top of the stack)", "{>}", stack.toString());
    }
    
    @Test
    public void testStackPushOneOntoEmptyStack () {
        stack.push("A");
        assertEquals ("Pushing A onto an empty stack.", "{>A}", stack.toString().replaceAll("[ ]+", ""));
    }
    
    @Test
    public void testStackPushTwoOntoEmptyStack () {
        stack.push("A");
        stack.push("B");
        assertEquals ("Pushing first A and then B onto an empty stack.", "{>B,A}", stack.toString().replaceAll("[ ]+", ""));
    }
    
    @Test
    public void testStackPushThreeOntoEmptyStack () {
        stack.push("A");
        stack.push("B");
        stack.push("C");
        assertEquals ("Pushing first A, then B, then C onto an empty stack.", "{>C,B,A}", stack.toString().replaceAll("[ ]+", ""));
    }
}