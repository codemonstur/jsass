package jsass.internal.parser.function;

import java.util.ArrayList;
import java.util.List;

import jsass.internal.parser.ActualArgumentList;
import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;
import jsass.internal.parser.SassList;
import jsass.internal.parser.SassListItem;

public class RGBFunctionGenerator extends AbstractFunctionGenerator {

    private static String[] argumentNames = { "red", "green", "blue" };

    public RGBFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "rgb");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {
        List<SassListItem> components = new ArrayList<SassListItem>();
        int line = function.getLineNumber();
        int column = function.getColumnNumber();
        for (int i = 0; i < 3; ++i) {
            SassListItem item = getParam(actualArguments, i);
            if (!LexicalUnitImpl
                    .checkLexicalUnitType(item, LexicalUnitImpl.SAC_INTEGER,
                            LexicalUnitImpl.SAC_PERCENTAGE)) {
                throw new ParseException(
                        "Invalid parameter to the function rgb(): "
                                + item.toString(), line, column);
            }
            components.add(item);
        }
        ActualArgumentList params = new ActualArgumentList(
                SassList.Separator.COMMA, components);
        return LexicalUnitImpl.createRGBColor(line, column, params);
    }
}
