/**
 * JUnit test class.  Use these tests as models for your own.
 */
import org.junit.*;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.beans.Transient;

import proj1.Coinbank;

public class CoinbankTest {
	
    @Rule 
    public Timeout timeout = Timeout.millis(100); // fail after 1/10 sec
    
    /**
     * Sets up a bank with the given coins
     * @param pennies number of pennies you want
     * @param nickels number of nickels you want
     * @param dimes number of dimes you want
     * @param quarters number of quarters you want
     * @return the Coinbank filled with the requested coins of each type
     */
    private Coinbank makeBank(int pennies, int nickels,
                              int dimes, int quarters) {
        Coinbank c = new Coinbank();
        int[] money = new int[]{pennies, nickels, dimes, quarters};
        int[] denom = new int[]{1,5,10,25};
        for (int index=0; index<money.length; index++) {
            int numCoins = money[index];
            for (int coin=0; coin<numCoins; coin++) {
                c.insert(denom[index]);
            }
        }
        return c;
    }

    @Test 
    public void testConstruct() {
        Coinbank emptyDefault = new Coinbank();
        assertEquals("Empty coinback has no pennies",
                     0, emptyDefault.get(1));
        assertEquals("Empty coinback has no nickels",
                     0, emptyDefault.get(5));
        assertEquals("Empty coinback has no dimes",
                     0, emptyDefault.get(10));
        assertEquals("Empty coinback has no quarters",
                     0, emptyDefault.get(25));
    }

    @Test
    public void testInsertPennies_return()
    {
        Coinbank c = new Coinbank();
        assertTrue("Inserting a pennies has True result",
        c.insert(1));
        assertEquals("Coinback after inserting a pennies has a pennies",
        1,c.get(1));
    }
    
    @Test
    public void testInsertNickel_return()
    {
        Coinbank c = new Coinbank();
        assertTrue("Inserting a nickel has True result",
        c.insert(5));
        assertEquals("Coinback after inserting a nickel has a nickel",
                1,c.get(5));
    }
    
    @Test
    public void testInsertDimes_return()
    {
        Coinbank c = new Coinbank();
        assertTrue("Inserting a dimes has True result",
                   c.insert(10));
        assertEquals("Coinback after inserting a dimes has a dimes",
                     1,c.get(10));
    }

    @Test 
    public void testInsertInvalidCoin_return()
    {
        Coinbank c = new Coinbank();
        assertFalse("Inserting a 3-cent coin has False result",
                    c.insert(3));
        assertEquals("Coinback after inserting a 3-cent coin has no 3-cent coins",
                     -1,c.get(3));
    }

    @Test
    public void testInsertQuarter_return()
    {
        Coinbank c = new Coinbank();
        assertTrue("Inserting a quarter has True result",
                   c.insert(25));
        assertEquals("Coinback after inserting a quarter has a quarter",
                     1,c.get(25));
    }

    @Test
    public void testGet()
    {
        Coinbank c = makeBank(0,2,15,1);
        assertEquals("Coinback should have no pennies",
                     0,c.get(1));
        assertEquals("Coinback should have 2 nickels",
                     2,c.get(5));
        assertEquals("Coinback should have 15 dimes",
                     15,c.get(10));
        assertEquals("Coinback should have 1 quarter",
                     1,c.get(25));
    }
    
    @Test
    public void testGet_contents()
    {
        Coinbank c = makeBank(0,2,15,1);
        c.get(1);
        c.get(5);
        c.get(10);
        c.get(25);
        String expected = "The bank currently holds $1.85 consisting of \n0 pennies\n2 nickels\n15 dimes\n1 quarters\n";
        assertEquals("Get method doesn't change contents of Coinbank",
                     expected,c.toString());
    }
    
    @Test
    public void testRemove_justEnough()
    {
        Coinbank c = makeBank(4,1,3,5);
        assertEquals("Should remove 5 quarters",
                     5,c.remove(25,5));
        String expected = "The bank currently holds $0.39 consisting of \n4 pennies\n1 nickels\n3 dimes\n0 quarters\n";
        assertEquals("Now there are no quarters",
                     expected,c.toString());
    }
	
    @Test
    public void testRemove_invalidCoin()
    {
        Coinbank c = makeBank(4,1,3,5);
        assertEquals("Removing 3-cent coin doesn't remove anything",
                     0,c.remove(3,1));
    }

    @Test 
    public void testRemove_tooMany()
    {
        Coinbank c = makeBank(4,1,3,5);
        assertEquals("Should only remove 1 nickel",
                1,c.remove(5,3));
        String expected = "The bank currently holds $1.59 consisting of \n4 pennies\n0 nickels\n3 dimes\n5 quarters\n";
        assertEquals("Now there are no nickels",
                     expected,c.toString());
    }

    @Test 
    public void testRemove_noneAvailable()
    {
        Coinbank c = makeBank(4,0,3,5);
        assertEquals("Should remove 0 nickels",
                0,c.remove(5,2));
        String expected = "The bank currently holds $1.59 consisting of \n4 pennies\n0 nickels\n3 dimes\n5 quarters\n";
        assertEquals("Still no nickels",
                     expected,c.toString());
    }

    @Test
    public void testRemove_negative() { 
        Coinbank c = makeBank(4 , 1, 3, 5);
        assertEquals("Removing negative number of dimes removes nothing", 0, c.remove(10, -2)); 
        String expected = "The bank currently holds $1.64 consisting of \n4 pennies\n1 nickels\n3 dimes\n5 quarters\n";
        assertEquals("Coinbank contents unchanged after removing negative dimes", expected, c.toString());
        
    }
    
}
