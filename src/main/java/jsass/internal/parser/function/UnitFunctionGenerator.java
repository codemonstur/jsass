package jsass.internal.parser.function;

import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;

public class UnitFunctionGenerator extends
        AbstractSingleParameterFunctionGenerator {

    private static String[] argumentNames = { "number" };

    public UnitFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "unit");
    }

    @Override
    protected LexicalUnitImpl computeForParam(String functionName,
            LexicalUnitImpl firstParam) {
        if (!firstParam.isNumber()) {
            throw new ParseException("The parameter of " + functionName
                    + "() must be a number", firstParam);
        }
        String unit = firstParam.getDimensionUnitText();
        return LexicalUnitImpl.createString(firstParam.getLineNumber(),
                firstParam.getColumnNumber(), unit);
    }
}