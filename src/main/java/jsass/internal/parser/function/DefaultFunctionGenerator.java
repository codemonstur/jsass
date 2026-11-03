package jsass.internal.parser.function;

import jsass.internal.ScssContext;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.SassListItem;

public class DefaultFunctionGenerator implements SCSSFunctionGenerator {

    @Override
    public String[] getFunctionNames() {
        return null;
    }

    @Override
    public SassListItem compute(ScssContext context, LexicalUnitImpl function) {
        return function;

    }

}
