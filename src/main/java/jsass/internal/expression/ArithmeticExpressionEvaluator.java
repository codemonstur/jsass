package jsass.internal.expression;

import java.util.List;
import java.util.Stack;

import jsass.internal.ScssContext;
import jsass.internal.expression.exception.ArithmeticException;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.SCSSLexicalUnit;
import jsass.internal.parser.SassExpression;
import jsass.internal.parser.SassListItem;

public class ArithmeticExpressionEvaluator {
    private static ArithmeticExpressionEvaluator instance;

    public static ArithmeticExpressionEvaluator get() {
        if (instance == null) {
            instance = new ArithmeticExpressionEvaluator();
        }
        return instance;
    }

    private void createNewOperand(BinaryOperator operator,
            Stack<Object> operands) {
        Object rightOperand = operands.pop();
        operands.push(new BinaryExpression(operands.pop(), operator,
                rightOperand));
    }

    private Object createExpression(ScssContext context,
            List<SassListItem> terms) {
        SassListItem current = null;
        boolean afterOperand = false;
        Stack<Object> operands = new Stack<Object>();
        Stack<Object> operators = new Stack<Object>();
        inputTermLoop: for (int i = 0; i < terms.size(); ++i) {
            current = terms.get(i).evaluateFunctionsAndExpressions(context,
                    true);
            if (SassExpression.isWhitespace(current)) {
                continue;
            }
            if (afterOperand) {
                if (LexicalUnitImpl.checkLexicalUnitType(current,
                        SCSSLexicalUnit.SCSS_OPERATOR_RIGHT_PAREN)) {
                    Object operator = null;
                    while (!operators.isEmpty()
                            && ((operator = operators.pop()) != Parentheses.LEFT)) {
                        createNewOperand((BinaryOperator) operator, operands);
                    }
                    continue;
                }
                afterOperand = false;
                for (BinaryOperator operator : BinaryOperator.values()) {
                    if (LexicalUnitImpl.checkLexicalUnitType(current,
                            operator.type)) {
                        while (!operators.isEmpty()
                                && (operators.peek() != Parentheses.LEFT)
                                && (((BinaryOperator) operators.peek()).precedence >= operator.precedence)) {
                            createNewOperand((BinaryOperator) operators.pop(),
                                    operands);
                        }
                        operators.push(operator);

                        continue inputTermLoop;
                    }
                }
                throw new ArithmeticException("Illegal arithmetic expression",
                        current);
            }
            if (LexicalUnitImpl.checkLexicalUnitType(current,
                    SCSSLexicalUnit.SCSS_OPERATOR_LEFT_PAREN)) {
                operators.push(Parentheses.LEFT);
                continue;
            }
            afterOperand = true;

            operands.push(current);
        }

        while (!operators.isEmpty()) {
            Object operator = operators.pop();
            if (operator == Parentheses.LEFT) {
                throw new ArithmeticException("Unexpected \"(\" found", current);
            }
            createNewOperand((BinaryOperator) operator, operands);
        }
        Object expression = operands.pop();
        if (!operands.isEmpty()) {
            LexicalUnitImpl operand = (LexicalUnitImpl) operands.peek();
            throw new ArithmeticException("Unexpected operand "
                    + operand.toString() + " found", current);
        }
        return expression;
    }

    public SassListItem evaluate(ScssContext context, List<SassListItem> terms) {
        Object result = ArithmeticExpressionEvaluator.get().createExpression(
                context, terms);
        if (result instanceof BinaryExpression) {
            return ((BinaryExpression) result).eval();
        }
        // createExpression returns either a BinaryExpression or a
        // SassListItem
        return (SassListItem) result;
    }
}