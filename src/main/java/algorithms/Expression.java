package algorithms;

/**
 * This class can be used to build simple arithmetic expression
 * with binary operator +,-,* and involving one variable 'x'.
 *
 * The expression can be
 * 1) evaluated by replacing the variable x with a specific value
 * 2) derivated to obtain a new expression
 *
 * You must modify this class to make it work
 * You can/should extend this class with inner classes the way you want.
 * You can also modify it but you are not allowed to modify the signature
 * of existing methods
 *
 * As a reminder, the formulas for the derivations as are followed
 *  - (f + g)' = f' + g'
 *  - (f*g)' = f'g + fg'
 *  - (x)' = 1
 *  - (C)' = 0 with C a constant
 */
public abstract class Expression {

    /**
     * Creates the basic variable expression 'x'
     * @return the expression 'x'
     */
    public static Expression x() {

        return new VariableExpression();
    }

    /**
     * Creates the basic constant expression 'v'
     * @return the expression 'v'
     */
    public static Expression value(double v) {

        return new ConstantExpression(v);
    }

    /**
     * Creates the binary expression 'this + r'
     * @param r the right operator
     * @return the binary expression 'this + r'
     */
    public Expression plus(Expression r) {

        return new BinaryExpression(this,r,"+");
    }

    /**
     * Creates the binary expression 'this - r'
     * @param r the right operator
     * @return the binary expression 'this - r'
     */
    public Expression minus(Expression r) {

        return new BinaryExpression(this,r,"-");
    }

    /**
     * Creates the binary expression 'this * r'
     * @param r the right operator
     * @return the binary expression 'this * r'
     */
    public Expression mul(Expression r) {

        return new BinaryExpression(this,r,"*");
    }

    /**
     * Evaluate the expression with fixed value for x
     * @param xValue the value taken by x for the evaluation
     * @return the evaluation of the expression considering x=xValue
     */
    public abstract double evaluate(double xValue);

    /**
     * Derivate the expression wrt to 'x'
     * @return the derivative of the expression with respect to 'x'
     */
    public abstract Expression derivate();

    public static class ConstantExpression extends Expression {

        private final double value;

        public ConstantExpression(double value){
            this.value = value;
        }

        @Override
        public double evaluate(double xValue){
            return value;
        }

        @Override
        public Expression derivate(){
            return Expression.value(0);
        }
    }

    public static class VariableExpression extends Expression {
        @Override
        public double evaluate(double xValue){
            return xValue;
        }

        @Override
        public Expression derivate(){
            return Expression.value(1);
        }
    }

    public class BinaryExpression extends Expression {
        private final Expression left;
        private final Expression right;
        private final String operator;

        public BinaryExpression(Expression left,Expression right,String operator){
            this.left = left;
            this.right = right;
            this.operator = operator;
        }

        @Override
        public double evaluate(double xValue){
            double lvalue = left.evaluate(xValue);
            double rvalue = right.evaluate(xValue);
            switch (operator){
                case "+":
                    return lvalue + rvalue;
                case "-":
                    return lvalue - rvalue;
                case "*":
                    return lvalue * rvalue;
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public Expression derivate(){
            switch (operator){
                case "+":
                    return left.derivate().plus(right.derivate());
                case "-":
                    return left.derivate().minus(right.derivate());
                case "*":
                    return (left.derivate().mul(right)).plus(left.mul(right.derivate()));
                default:
                    throw new IllegalArgumentException();
            }
        }


    }





}
