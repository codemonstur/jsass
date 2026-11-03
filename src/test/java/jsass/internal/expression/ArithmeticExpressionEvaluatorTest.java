package jsass.internal.expression;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.css.sac.LexicalUnit;

import jsass.internal.ScssContext;
import jsass.internal.expression.exception.ArithmeticException;
import jsass.internal.expression.exception.IncompatibleUnitsException;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.SassListItem;

public class ArithmeticExpressionEvaluatorTest {
    private ArithmeticExpressionEvaluator evaluator = new ArithmeticExpressionEvaluator();

    private LexicalUnitImpl evaluate(SassListItem... terms) {
        // clean context every time - no variables etc.
        ScssContext context = new ScssContext();
        return (LexicalUnitImpl) evaluator.evaluate(context,
                Arrays.asList(terms));
    }

    private final LexicalUnitImpl operand2 = LexicalUnitImpl.createInteger(0,
            0, 2);
    private final LexicalUnitImpl operatorMultiply = LexicalUnitImpl
            .createMultiply(0, 0);
    private final LexicalUnitImpl operand3 = LexicalUnitImpl.createInteger(0,
            0, 3);
    private final LexicalUnitImpl operatorMinus = LexicalUnitImpl.createMinus(
            0, 0);
    private final LexicalUnitImpl operand4 = LexicalUnitImpl.createInteger(0,
            0, 4);
    private final LexicalUnitImpl operand2cm = LexicalUnitImpl.createCM(0, 0,
 2);
    private final LexicalUnitImpl operand3px = LexicalUnitImpl.createPX(0, 0,
 3);
    private final LexicalUnitImpl operand3cm = LexicalUnitImpl.createCM(0, 0,
 3);
    private final LexicalUnitImpl operand4cm = LexicalUnitImpl.createCM(0, 0,
 4);
    private final LexicalUnitImpl operatorDivide = LexicalUnitImpl.createSlash(
            0, 0);
    private final LexicalUnitImpl operatorComma = LexicalUnitImpl.createComma(
            2, 3);

    @Test
    public void testPrecedenceSameAsAppearOrder() {
        // 2 * 3 - 4 = 2
        LexicalUnitImpl result = evaluate(operand2, operatorMultiply, operand3,
                operatorMinus, operand4);
        Assert.assertEquals(2, result.getIntegerValue());
    }

    @Test
    public void testPrecedenceDifferFromAppearOrder() {
        // 2 - 3 * 4 = -10
        LexicalUnitImpl result = evaluate(operand2, operatorMinus, operand3,
                operatorMultiply, operand4);
        Assert.assertEquals(-10, result.getIntegerValue());
    }

    @Test(expected = IncompatibleUnitsException.class)
    public void testIncompatibleUnit() {
        // 2cm - 3px
        evaluate(operand2cm, operatorMinus, operand3px);
    }

    @Test
    public void testMultiplyWithUnitInfirstOperand() {
        // 2cm * 3 = 6cm
        LexicalUnitImpl result = evaluate(operand2cm, operatorMultiply,
                operand3);
        Assert.assertEquals(6, result.getIntegerValue());
        Assert.assertEquals(LexicalUnit.SAC_CENTIMETER,
                result.getLexicalUnitType());
    }

    @Test
    public void testMultiplyWithUnitInSecondOperand() {
        // 2 * 3cm = 6cm
        LexicalUnitImpl result = evaluate(operand2, operatorMultiply,
                operand3cm);
        Assert.assertEquals(6, result.getIntegerValue());
        Assert.assertEquals(LexicalUnit.SAC_CENTIMETER,
                result.getLexicalUnitType());
    }

    @Test
    public void testDivideWithSameUnit() {
        // 4cm / 2cm = 2
        LexicalUnitImpl result = evaluate(operand4cm, operatorDivide,
                operand2cm);
        Assert.assertEquals(2, result.getIntegerValue());
        Assert.assertEquals(LexicalUnit.SAC_REAL, result.getLexicalUnitType());
    }

    @Test
    public void testDivideDenominatorWithoutUnit() {
        // 4cm / 2 = 2cm
        LexicalUnitImpl result = evaluate(operand4cm, operatorDivide, operand2);
        Assert.assertEquals(2, result.getIntegerValue());
        Assert.assertEquals(LexicalUnit.SAC_CENTIMETER,
                result.getLexicalUnitType());
    }

    @Test(expected = ArithmeticException.class)
    public void testNonExistingSignal() {
        LexicalUnitImpl result = evaluate(operand2, operatorComma, operand3);
    }
}
