package proj4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Evaluates a fully-parenthesized expression.
 *
 * evaluateString() demonstrates using the Expression iterator. You need to
 * alter the method body for this project.
 *
 * evaluateFile() works completely and should not be altered. It lets you
 * evaluate multiple expressions in a text file by calling evaluateString()
 * on each line of the file.  Try it by calling it from the main() method
 * with the provided sample file.
 */
public class ExpressionEvaluator
{
    /**
     * Given a fully-parenthesized arithmetic expression, evaluates it
     * and returns the answer
     *
     * @param expression infix expression as a string like "(1-(2+3))"
     * @return answer string, like "-4" for the above example
     */
    public String evaluateString(String expression)
    {
        Expression exp = new Expression(expression);

        // example of using a for-each loop to get each meaningful substring
        for (String substring: exp){
            System.out.println(substring);
        }
        return "";
    }


    /**
     * Given a filename with one arithmetic expression per line, evaluate each
     * expression and print it.
     *
     * @param filename name of file as a string.  Include the extension
     *                 (like "input.txt") and if you are not supplying a path, then
     *                 this file is assumed to be inside your src folder in IntelliJ,
     *                 but not inside the proj4 package.
     */
    public void evaluateFile(String filename){
        try (Scanner myscan = new Scanner(new File(filename))) {
            while (myscan.hasNextLine()) {
                String line = myscan.nextLine();
                System.out.print(line + " = ");
                System.out.println(evaluateString(line));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found. Make sure it's in src folder but not proj4 folder");
        }
    }



}
