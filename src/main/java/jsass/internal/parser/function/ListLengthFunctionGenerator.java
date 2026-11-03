package jsass.internal.parser.function;

import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.SassList;
import jsass.internal.parser.SassListItem;

public class ListLengthFunctionGenerator extends AbstractFunctionGenerator {

    private static String[] argumentNames = { "list" };

    public ListLengthFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "length");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {
        int length;
        SassListItem list = getParam(actualArguments, "list");
        if (list instanceof SassList) {
            length = ((SassList) list).size();
        } else {
            length = 1;
        }
        return LexicalUnitImpl.createInteger(function.getLineNumber(),
                function.getColumnNumber(), length);
    }

}
