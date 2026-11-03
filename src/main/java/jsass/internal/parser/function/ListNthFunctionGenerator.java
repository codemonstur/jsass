package jsass.internal.parser.function;

import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;
import jsass.internal.parser.SassList;
import jsass.internal.parser.SassListItem;

public class ListNthFunctionGenerator extends AbstractFunctionGenerator {

    private static String[] argumentNames = { "list", "n" };

    public ListNthFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "nth");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {
        SassListItem listAsItem = getParam(actualArguments, "list");
        if (!(listAsItem instanceof SassList)) {
            listAsItem = new SassList(listAsItem);
        }
        SassListItem nAsItem = getParam(actualArguments, "n");
        if (!(nAsItem instanceof LexicalUnitImpl)
                || ((LexicalUnitImpl) nAsItem).getLexicalUnitType() != LexicalUnitImpl.SAC_INTEGER) {
            throw new ParseException(
                    "The second parameter of nth() must be an integer. Actual value: "
                            + nAsItem);
        }
        SassList list = (SassList) listAsItem;
        int n = ((LexicalUnitImpl) nAsItem).getIntegerValue();
        if (n < 1 || n > list.size()) {
            throw new ParseException(
                    "Index out of range for the function nth(). List: " + list
                            + ", index: " + n);
        }
        return list.get(n - 1);
    }
}
