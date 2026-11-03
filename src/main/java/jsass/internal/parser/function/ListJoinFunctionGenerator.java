package jsass.internal.parser.function;

import java.util.ArrayList;

import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.SassList;
import jsass.internal.parser.SassListItem;

public class ListJoinFunctionGenerator extends ListFunctionGenerator {

    private static String[] argumentNames = { "list1", "list2", "separator" };
    private static SassListItem[] defaultValues = { null, null,
            LexicalUnitImpl.createIdent("auto") };

    public ListJoinFunctionGenerator() {
        super(createArgumentList(argumentNames, defaultValues, false), "join");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {
        SassListItem firstListAsItem = getParam(actualArguments, "list1");
        SassListItem secondListAsItem = getParam(actualArguments, "list2");

        SassList firstList = asList(firstListAsItem);
        SassList secondList = asList(secondListAsItem);
        ArrayList<SassListItem> newList = new ArrayList<SassListItem>();
        for (SassListItem firstItem : firstList) {
            newList.add(firstItem);
        }
        for (SassListItem secondItem : secondList) {
            newList.add(secondItem);
        }

        SassList.Separator sep = getSeparator(getParam(actualArguments,
                "separator"));
        if (sep == null) { // determine the separator in "auto" mode
            sep = getAutoSeparator(firstList, secondList);
        }
        return new SassList(sep, newList);
    }

}
