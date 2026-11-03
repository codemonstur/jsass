package jsass.internal.parser.function;

import jsass.internal.parser.ArgumentList;
import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;
import jsass.internal.parser.SassListItem;

public class MinMaxFunctionGenerator extends AbstractFunctionGenerator {

    private static String[] argumentNames = { "numbers" };

    public MinMaxFunctionGenerator() {
        super(createArgumentList(argumentNames, true), "min", "max");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {
        ArgumentList params = (ArgumentList) getParam(actualArguments, 0);
        if (params.size() == 0) {
            throw new ParseException("The function "
                    + function.getFunctionName()
                    + " requires at least one parameter", function);
        }
        // keep the unit of the result like sass-lang does
        LexicalUnitImpl result = getParam(function, params.get(0));
        for (int i = 1; i < params.size(); ++i) {
            LexicalUnitImpl value = getParam(function, params.get(i));
            if ("min".equals(function.getFunctionName())) {
                if (value.getFloatValue() < result.getFloatValue()) {
                    result = value;
                }
            } else {
                if (value.getFloatValue() > result.getFloatValue()) {
                    result = value;
                }
            }
        }
        return result;
    }

    private LexicalUnitImpl getParam(LexicalUnitImpl function,
            SassListItem param) {
        if (!(param instanceof LexicalUnitImpl)
                || !((LexicalUnitImpl) param).isNumber()) {
            throw new ParseException("The parameters to the function "
                    + function.getFunctionName() + " must be numerical", param);
        }
        return (LexicalUnitImpl) param;
    }
}
