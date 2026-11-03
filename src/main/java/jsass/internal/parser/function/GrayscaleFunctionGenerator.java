package jsass.internal.parser.function;

import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;
import jsass.internal.util.ColorUtil;

public class GrayscaleFunctionGenerator extends
        AbstractSingleParameterFunctionGenerator {

    private static String[] argumentNames = { "color" };

    public GrayscaleFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "grayscale");
    }

    @Override
    protected LexicalUnitImpl computeForParam(String functionName,
            LexicalUnitImpl firstParam) {
        if (!ColorUtil.isColor(firstParam) && !ColorUtil.isRgba(firstParam)
                && !ColorUtil.isHsla(firstParam)) {
            throw new ParseException(
                    "The argument of grayscale() must be a valid color",
                    firstParam);
        }
        float[] hsl = ColorUtil.colorToHsl(firstParam);
        hsl[1] = 0;
        float alpha = ColorUtil.getAlpha(firstParam);
        return ColorUtil.createHslaOrHslColor(hsl, alpha,
                firstParam.getLineNumber(), firstParam.getColumnNumber());
    }
}