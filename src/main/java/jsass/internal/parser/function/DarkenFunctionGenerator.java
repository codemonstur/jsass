package jsass.internal.parser.function;

import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.SassListItem;
import jsass.internal.util.ColorUtil;

/**
 * 
 * @author jsass Ltd
 */
public class DarkenFunctionGenerator extends AbstractFunctionGenerator {

    private static String[] argumentNames = { "color", "amount" };

    public DarkenFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "darken");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {
        LexicalUnitImpl color = getParam(actualArguments, "color")
                .getContainedValue();
        float amount = getParam(actualArguments, "amount").getContainedValue()
                .getFloatValue();
        return ColorUtil.darken(color, amount);
    }

}
