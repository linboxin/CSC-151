package proj4;

/**
 *  Runs the ExpressionEvaluator which evaluates a fully parenthesized expression.
 */
public class Client
{
    public static void main(String args[])
    {
        ExpressionEvaluator eval = new ExpressionEvaluator();
        eval.evaluateString("(10*(6-2))");
    }
}
