package jsass.internal.parser.function;

import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;
import jsass.internal.parser.SassListItem;

/**
 * AbstractSingleParameterFunctionGenerator is used as a base class for
 * implementing Sass functions that take exactly one single value parameter. A
 * single value parameter is of type LexicalUnitImpl.
 * 
 * @author jsass
 * 
 */
public abstract class AbstractSingleParameterFunctionGenerator extends
        AbstractFunctionGenerator {

    public AbstractSingleParameterFunctionGenerator(FormalArgumentList args,
            String... functionNames) {
        super(args, functionNames);
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList arglist) {
        SassListItem param = getParam(arglist, 0);
        if (!(param instanceof LexicalUnitImpl)) {
            throw new ParseException("Function " + function.getFunctionName()
                    + " must have exactly one single value parameter", function);
        }
        LexicalUnitImpl firstParam = (LexicalUnitImpl) param;
        return computeForParam(function.getFunctionName(), firstParam);
    }

    /**
     * Compute the value of the function.
     * 
     * This method must not modify firstParam. If necessary, the implementation
     * should copy the parameter before making modifications.
     * 
     * @param functionName
     *            The name of the function whose value is to be computed.
     * @param firstParam
     *            The only parameter of the function.
     * @return
     */
    protected abstract LexicalUnitImpl computeForParam(String functionName,
            LexicalUnitImpl firstParam);

}
