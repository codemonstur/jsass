package jsass.internal.parser.function;

import jsass.internal.parser.LexicalUnitImpl;

public class AbsFunctionGenerator extends
        AbstractSingleParameterFunctionGenerator {

    private static String[] argumentNames = { "value" };

    public AbsFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "abs");
    }

    @Override
    protected LexicalUnitImpl computeForParam(String functionName,
            LexicalUnitImpl param) {
        return param.copyWithValue(Math.abs(param.getFloatValue()));
    }

}
