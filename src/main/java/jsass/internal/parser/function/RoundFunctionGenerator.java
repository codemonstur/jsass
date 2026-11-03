package jsass.internal.parser.function;

import jsass.internal.parser.LexicalUnitImpl;

public class RoundFunctionGenerator extends
        AbstractSingleParameterFunctionGenerator {

    private static String[] argumentNames = { "value" };

    public RoundFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "round");
    }

    @Override
    protected LexicalUnitImpl computeForParam(String functionName,
            LexicalUnitImpl param) {
        // duplicate the behavior of sass-lang implementation, as Math.round()
        // behaves differently for negative halves
        float value = param.getFloatValue();
        return param.copyWithValue(Math.signum(value)
                * Math.round(Math.abs(value)));
    }

}
