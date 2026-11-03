package jsass.internal.parser.function;

import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;

public class UnitlessFunctionGenerator extends
        AbstractSingleParameterFunctionGenerator {

    private static String[] argumentNames = { "number" };

    public UnitlessFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "unitless");
    }

    @Override
    protected LexicalUnitImpl computeForParam(String functionName,
            LexicalUnitImpl firstParam) {
        if (!firstParam.isNumber()) {
            throw new ParseException("The parameter of " + functionName
                    + "() must be a number", firstParam);
        }
        Boolean value = "".equals(firstParam.getDimensionUnitText());
        LexicalUnitImpl result = LexicalUnitImpl.createIdent(
                firstParam.getLineNumber(), firstParam.getColumnNumber(),
                value.toString());
        return result;
    }
}