package jsass.internal.parser.function;

import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.SassList;
import jsass.internal.parser.SassListItem;

public class ListIndexFunctionGenerator extends AbstractFunctionGenerator {

    private static String[] argumentNames = { "list", "value" };

    public ListIndexFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "index");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {
        SassListItem listItem = getParam(actualArguments, "list");
        SassListItem item = getParam(actualArguments, "value");
        int index = -1;
        if (!(listItem instanceof SassList)) {
            index = listItem.equals(item) ? 1 : -1;
        } else {
            SassList list = (SassList) listItem;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(item)) {
                    index = i + 1;
                    break;
                }
            }
        }
        if (index == -1) {
            return LexicalUnitImpl.createIdent(function.getLineNumber(),
                    function.getColumnNumber(), "false");
        } else {
            return LexicalUnitImpl.createInteger(function.getLineNumber(),
                    function.getColumnNumber(), index);
        }
    }
}