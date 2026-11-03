package jsass.internal.parser.function;

import jsass.internal.parser.LexicalUnitImpl;

public class CeilFunctionGenerator extends
        AbstractSingleParameterFunctionGenerator {

    private static String[] argumentNames = { "value" };

    public CeilFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "ceil");
    }

    @Override
    protected LexicalUnitImpl computeForParam(String functionName,
            LexicalUnitImpl param) {
        return param.copyWithValue((float) Math.ceil(param.getFloatValue()));
    }

}
