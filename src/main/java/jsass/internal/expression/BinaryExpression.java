package jsass.internal.expression;

import jsass.internal.parser.SassListItem;

public class BinaryExpression {
    public Object leftOperand = null;
    public BinaryOperator operator = null;
    public Object rightOperand = null;

    /**
     * Create a binary expression.
     * 
     * @param leftOperand
     *            a SassListItem or a BinaryExpression
     * @param operator
     *            operator to combine the operands with
     * @param rightOperand
     *            a SassListItem or a BinaryExpression
     */
    public BinaryExpression(Object leftOperand, BinaryOperator operator,
            Object rightOperand) {
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
    }

    public SassListItem eval() {
        SassListItem leftValue = (leftOperand instanceof BinaryExpression) ? ((BinaryExpression) leftOperand)
                .eval() : (SassListItem) leftOperand;
        SassListItem rightValue = (rightOperand instanceof BinaryExpression) ? ((BinaryExpression) rightOperand)
                .eval() : (SassListItem) rightOperand;
        return operator.eval(leftValue, rightValue);
    }

    @Override
    public String toString() {
        return "(" + leftOperand + " " + operator.type + " " + rightOperand
                + ")";
    }
}
