package jsass.internal.parser.function;

import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;
import jsass.internal.parser.SassList;
import jsass.internal.parser.SassList.Separator;
import jsass.internal.parser.SassListItem;

public abstract class ListFunctionGenerator extends AbstractFunctionGenerator {

    public ListFunctionGenerator(FormalArgumentList formalArguments,
            String... functionNames) {
        super(formalArguments, functionNames);
    }

    protected SassList.Separator getAutoSeparator(SassList firstList,
            SassList secondList) {
        if (firstList.size() > 1) {
            return firstList.getSeparator();
        } else if (secondList.size() > 1) {
            return secondList.getSeparator();
        } else {
            return SassList.Separator.SPACE;
        }
    }

    protected SassList.Separator getAutoSeparator(SassList firstList) {
        if (firstList.size() > 1) {
            return firstList.getSeparator();
        } else {
            return SassList.Separator.SPACE;
        }
    }

    protected Separator getSeparator(SassListItem separatorItem) {
        if (!(separatorItem instanceof LexicalUnitImpl)) {
            throw new ParseException(
                    "The separator of "
                            + getFunctionNames()[0]
                            + "() must be one of 'auto', 'comma', 'space'. Actual value: "
                            + separatorItem);
        }
        String sepString = ((LexicalUnitImpl) separatorItem).getStringValue();
        if ("comma".equals(sepString)) {
            return SassList.Separator.COMMA;
        } else if ("space".equals(sepString)) {
            return SassList.Separator.SPACE;
        } else if (!("auto".equals(sepString))) {
            throw new ParseException(
                    "The separator of "
                            + getFunctionNames()[0]
                            + "() must be one of 'auto', 'comma', 'space'. Actual value: "
                            + sepString);
        }
        return null;
    }

    protected SassList asList(SassListItem item) {
        return (item instanceof SassList) ? (SassList) item
                : new SassList(item);
    }

}
