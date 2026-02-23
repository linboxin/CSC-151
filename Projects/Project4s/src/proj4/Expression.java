/** DO NOT ALTER THIS CLASS **/
package proj4;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator for the concept of an Expression, which
 * is a string representation of a fully-parenthesized
 * arithmetic expression.
 */
public class Expression implements Iterable<String>{

    private String exp;  // expression to parse
    private int start;   // index at which next meaningful substring will come from

    /**
     * non-default constructor
     *
     * @param s fully-parenthesized infix expression
     */
    public Expression(String s){
        exp = s;
        start = 0;
    }

    /**
     * creates an Iterator to be used by Expression
     * (requirement of Iterable interface)
     *
     * @return Iterator that must have hasNext() and next()
     */
    @Override
    public Iterator<String> iterator() {
        return new Parser();
    }


    /**
     * Inner class used to create the Iterator for an Expression
     */
    private class Parser implements Iterator<String> {

        /**
         * Says if there's another lexeme (substring) left to parse
         * which can be turned into a Token.  We know there
         * is if start is still a valid index and the substring
         * starting at index start isn't all whitespace
         *
         * @return true if there's a tokenable substring still left to read, else false
         */
        @Override
        public boolean hasNext() {
            return start < exp.length() &&
                    !(exp.substring(start).trim().isEmpty());
        }

        /**
         * Return the next substring (lexeme) that can be turned
         * into a token.  Possible substrings include non-negative operands
         * (single or multi-digit), parentheses, or operators
         *
         * @return "(", ")", "+", "-", "*", "/", "^", or a non-negative operand like "125"
         */
        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            else {
                // get past any whitespace
                char ch = exp.charAt(start);
                while (Character.isWhitespace(ch)) {
                    start++;
                    ch = exp.charAt(start);
                }

                // ch is now a real tokenable substring: either an operator, paren, or start of a number
                String tokenable = "";
                if (Character.isDigit(ch)) {                 // if we've got a digit...
                    do {
                        tokenable = tokenable + String.valueOf(ch);  //...gather any subsequent digits
                        start++;
                        ch = exp.charAt(start);
                    }
                    while (Character.isDigit(ch));
                }
                else {
                    start++;
                    tokenable = String.valueOf(ch);
                }
                return tokenable;
            }
        }
    }
}
