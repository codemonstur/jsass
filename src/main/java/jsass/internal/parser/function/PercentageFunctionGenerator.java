package jsass.internal.parser.function;

import jsass.internal.parser.LexicalUnitImpl;

/**
 * PercentageFunctionGenerator is used for converting values into percentages
 * with rounding.
 * 
 * @author jsass
 */
public class PercentageFunctionGenerator extends
        AbstractSingleParameterFunctionGenerator {

    private static String[] argumentNames = { "value" };
    private static long PERC_PRECISION_FACTOR = 100 * LexicalUnitImpl.PRECISION;

    public PercentageFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "percentage");
    }

    @Override
    protected LexicalUnitImpl computeForParam(String functionName,
            LexicalUnitImpl firstParam) {
        float value = firstParam.getFloatValue();
        value *= PERC_PRECISION_FACTOR;
        int intValue = Math.round(value);
        value = ((float) intValue) / LexicalUnitImpl.PRECISION;

        return LexicalUnitImpl.createPercentage(firstParam.getLineNumber(),
                firstParam.getColumnNumber(), value);
    }
}