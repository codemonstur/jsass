package jsass.internal.parser.function;

import java.util.ArrayList;

import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.SassList;
import jsass.internal.parser.SassListItem;

public class ListAppendFunctionGenerator extends ListFunctionGenerator {

    private static String[] argumentNames = { "list", "val", "separator" };
    private static SassListItem[] defaultValues = { null, null,
            LexicalUnitImpl.createIdent("auto") };

    public ListAppendFunctionGenerator() {
        super(createArgumentList(argumentNames, defaultValues, false), "append");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {
        SassListItem listAsItem = getParam(actualArguments, "list");
        SassListItem appendItem = getParam(actualArguments, "val");

        SassList list = asList(listAsItem);
        ArrayList<SassListItem> newList = new ArrayList<SassListItem>();
        for (SassListItem item : list) {
            newList.add(item);
        }
        newList.add(appendItem);

        SassList.Separator sep = getSeparator(getParam(actualArguments,
                "separator"));
        if (sep == null) { // determine the separator in "auto" mode
            sep = getAutoSeparator(list);
        }
        return new SassList(sep, newList);
    }
}