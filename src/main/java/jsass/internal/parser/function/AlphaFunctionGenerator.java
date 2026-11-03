package jsass.internal.parser.function;

import jsass.internal.parser.ActualArgumentList;
import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;
import jsass.internal.parser.SassListItem;
import jsass.internal.util.ColorUtil;

public class AlphaFunctionGenerator extends AbstractFunctionGenerator {

    private static String[] argumentNames = { "color" };

    public AlphaFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "alpha", "opacity");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {
        checkParameters(function, actualArguments);
        LexicalUnitImpl color = (LexicalUnitImpl) getParam(actualArguments,
                "color");
        float opacity = 1.0f;
        if (ColorUtil.isRgba(color) || ColorUtil.isHsla(color)) {
            ActualArgumentList parameterList = color.getParameterList();
            SassListItem last = parameterList.get(parameterList.size() - 1);
            opacity = ((LexicalUnitImpl) last).getFloatValue();
        }
        return LexicalUnitImpl.createNumber(function.getLineNumber(),
                function.getColumnNumber(), opacity);
    }

    private void checkParameters(LexicalUnitImpl function,
            FormalArgumentList args) {
        LexicalUnitImpl color = (LexicalUnitImpl) getParam(args, "color");
        if (!(color instanceof LexicalUnitImpl)
                || (!ColorUtil.isColor(color) && !ColorUtil.isRgba(color) && !ColorUtil
                        .isHsla(color))) {
            throw new ParseException("The function "
                    + function.getFunctionName()
                    + " requires a color as its first parameter", function);
        }
    }
}
