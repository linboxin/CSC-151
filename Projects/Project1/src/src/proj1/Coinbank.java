package proj1;  // Don't change the package name.  Gradescope expects this.

/**
 * FILL THIS IN FOR EVERY PROJECT.  Include a class description, name, and date (for version) 
 * @author James Lin
 * @version Project 1 – We're in the Money, 1/19/2026
 * I affirm that I have carried out the attached academic endeavors with full academic honesty, in accordance with the Union College Honor Code and the course syllabus.
 */
public class Coinbank {
	
    // Denominations
    public static final int PENNY_VALUE = 1;
    public static final int NICKEL_VALUE = 5;
    public static final int DIME_VALUE = 10;
    public static final int QUARTER_VALUE = 25;
	
    // give meaningful names to holder array indices
    private final int PENNY = 0;
    private final int NICKEL = 1;
    private final int DIME = 2;
    private final int QUARTER = 3;
	
    // how many types of coins does the bank hold?
    private final int COINTYPES = 4;
	
    private int[] holder;

    /**
     * Constructs an empty coinbank.
     */
    public Coinbank() {
        holder = new int[COINTYPES];
    }


    /**
     * Array Indices of Coins.
     *
     * @param coinType denomination of coin to get. Valid
     * denominations are 1,5,10,25
     * @return the corresponding array indices for the coinType. Indices are 0,1,2,3. Returns -1 if the coin is not valid.
     */
    private int indexOfCoin(int coinType) {
        if (coinType == PENNY_VALUE){
            return PENNY;
        }else if (coinType == NICKEL_VALUE){
            return NICKEL;
        }else if (coinType == DIME_VALUE){
            return DIME;
        }else if (coinType == QUARTER_VALUE){
            return QUARTER;
        }else {
            return -1;
        }
    }


    /**
     * getter
     *
     * @param coinType denomination of coin to get. Valid
     *   denominations are 1,5,10,25
     * @return number of coins that bank is holding of that type, or
     *   -1 if denomination not valid
     */
    public int get(int coinType){
        int i = indexOfCoin(coinType);
        if (i == -1){
            return -1;
        }else{
            return holder[i];
        }
    }
	
    /**
     * setter
     *
     * @param coinType denomination of coin to set
     * @param numCoins number of coins
     */
    private void set(int coinType, int numCoins) {
        int i = indexOfCoin(coinType);
        if (i != -1){
            holder[i] = numCoins;
        }
    }
	
    /**
     * Determine if this back can hold the given coin.
     *
     * @param coin penny, nickel, dime, or quarter is bankable.  All others are not.
     * @return true iff bank can hold this coin.
     */
    private boolean isBankable(int coin){
        switch (coin) {
            case PENNY_VALUE: case NICKEL_VALUE: 
            case DIME_VALUE: case QUARTER_VALUE:
                return true;
            default: 
                return false;
        }
    }
	
    /** 
     * Insert a single coin into bank.  Returns true iff deposit
     * successful (i.e. coin was penny, nickel, dime, or quarter).
     * 
     * @param coinType either 1, 5, 10, or 25 to be valid
     * @return true iff deposit successful.
     */
    public boolean insert(int coinType){
        if (!isBankable(coinType)) {
            return false;
        }
        else {
            set(coinType, get(coinType)+1);
            return true;
        }
    }
	
    /**
     * Removes the requested number of the given coin type from the
     * bank, if possible.  Does nothing if the coin type is invalid.
     * If bank holds fewer coins than is requested, then all of the
     * coins of that type will be returned.
     *
     * @param coinType either 1, 5, 10, or 25 to be valid
     * @param requestedCoins number of coins to be removed
     * @return number of coins that are actually removed
     */
    public int remove(int coinType, int requestedCoins) {
        if (!isBankable(coinType) || requestedCoins <= 0) {
            return 0;
        }

        int before = get(coinType);
        int after = numLeft(requestedCoins, before);
        set(coinType, after);

        return before - after;
    }


    /**
     * Determine number of coins remaining after removing the
     * requested amount.  Returns zero if requested amount more than
     * what we have.
     
     * @param numWant number of coins to be removed
     * @param numHave number of coins you have
     * @return number of coins left after removal
     */
    private int numLeft(int numWant, int numHave){
        return Math.max(0, numHave-numWant);
    }
	
    /**
     * Produces a printable string version of this coinback, with the
     * following format:
     *
     * The bank currently holds $1.85 consisting of
     * 0 pennies
     * 2 nickels
     * 15 dimes
     * 1 quarters
     *
     * @return printable String version of coinbank.
     */
    public String toString() {
        double total = (get(PENNY_VALUE) * PENNY_VALUE +
                        get(NICKEL_VALUE) * NICKEL_VALUE + 
                        get(DIME_VALUE) * DIME_VALUE +
                        get(QUARTER_VALUE) * QUARTER_VALUE) / 100.0;
        
        String toReturn = "The bank currently holds $" + total + " consisting of \n";
        toReturn+=get(PENNY_VALUE) + " pennies\n";
        toReturn+=get(NICKEL_VALUE) + " nickels\n";
        toReturn+=get(DIME_VALUE) + " dimes\n";
        toReturn+=get(QUARTER_VALUE) + " quarters\n";
        return toReturn;
    }
}
