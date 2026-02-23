package proj4;

/**
 * Describes the methods that must be defined in order for an
 * object to be considered a token.  Every token must be able
 * to be processed (handle) and printable (toString).
 * 
 * @author Chris Fernandes
 * @version 5/18/25
 */
public interface Token
{
	/** Processes the current token.  Since every token will handle
	 *  itself in its own way, handling may involve pushing or
	 *  popping from the given stack.
	 * 
	 *  @param s the Stack the token uses when processing itself.
	 */
    public void handle(Stack<Token> s);
    
    /** Returns the token as a printable String
     * 
     *  @return the String version of the token.  For example, ")"
     *  for a right parenthesis.
     */
    public String toString();

}
