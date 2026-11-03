package jsass.internal.parser.function;

import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;
import jsass.internal.parser.SassListItem;
import jsass.internal.util.ColorUtil;

public class ColorComponentFunctionGenerator extends AbstractFunctionGenerator {

    private static String[] argumentNames = { "color" };

    public ColorComponentFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "red", "green", "blue",
                "hue", "saturation", "lightness");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {
        boolean hslComponent = false;
        int componentNumber = 0; // the index of the wanted component in an RGB
                                 // array
        if ("red".equals(function.getFunctionName())) {
            componentNumber = 0;
        } else if ("green".equals(function.getFunctionName())) {
            componentNumber = 1;
        } else if ("blue".equals(function.getFunctionName())) {
            componentNumber = 2;
        } else if ("hue".equals(function.getFunctionName())) {
            componentNumber = 0;
            hslComponent = true;
        } else if ("saturation".equals(function.getFunctionName())) {
            componentNumber = 1;
            hslComponent = true;
        } else if ("lightness".equals(function.getFunctionName())) {
            componentNumber = 2;
            hslComponent = true;
        }
        checkParameters(function, actualArguments);
        LexicalUnitImpl color = getParam(actualArguments, "color")
                .getContainedValue();
        if (hslComponent) {
            float[] components = ColorUtil.colorToHsl(color);
            if (componentNumber == 0) {
                return LexicalUnitImpl.createDEG(color.getLineNumber(),
                        color.getColumnNumber(), components[componentNumber]);
            } else {
                return LexicalUnitImpl.createPercentage(color.getLineNumber(),
                        color.getColumnNumber(), components[componentNumber]);
            }
        } else {
            int[] components = ColorUtil.colorToRgb(color);
            return LexicalUnitImpl.createInteger(color.getLineNumber(),
                    color.getColumnNumber(), components[componentNumber]);
        }
    }

    private void checkParameters(LexicalUnitImpl function,
            FormalArgumentList args) {
        SassListItem arg = getParam(args, "color");
        if (!(arg instanceof LexicalUnitImpl)) {
            throw new ParseException("Function " + function.getFunctionName()
                    + " must have exactly one single value parameter", function);
        }
        LexicalUnitImpl firstParam = (LexicalUnitImpl) arg;
        if (!ColorUtil.isColor(firstParam) && !ColorUtil.isRgba(firstParam)
                && !ColorUtil.isHsla(firstParam)) {
            throw new ParseException("The parameter of the function "
                    + function.getFunctionName() + " must be a valid color",
                    function);
        }
    }
}