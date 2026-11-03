package jsass.internal.parser.function;

import jsass.internal.parser.LexicalUnitImpl;

public class FloorFunctionGenerator extends
        AbstractSingleParameterFunctionGenerator {

    private static String[] argumentNames = { "value" };

    public FloorFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "floor");
    }

    @Override
    protected LexicalUnitImpl computeForParam(String functionName,
            LexicalUnitImpl param) {
        return param.copyWithValue((float) Math.floor(param.getFloatValue()));
    }

}
