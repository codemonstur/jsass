package jsass.internal.parser.function;

import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.SassList;
import jsass.internal.parser.SassListItem;
import jsass.internal.util.ColorUtil;

public class TypeOfFunctionGenerator extends AbstractFunctionGenerator {

    private static String[] argumentNames = { "value" };

    public TypeOfFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "type-of");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {
        SassListItem param = getParam(actualArguments, 0);
        String type = "string";

        if (param instanceof SassList) {
            type = "list";
        } else if (param instanceof LexicalUnitImpl) {
            LexicalUnitImpl unit = (LexicalUnitImpl) param;
            if (unit.getLexicalUnitType() == LexicalUnitImpl.SCSS_NULL) {
                type = "null";
            } else if (isNumber(unit)) {
                type = "number";
            } else if (isBoolean(unit)) {
                type = "bool";
            } else if (unit.getLexicalUnitType() == LexicalUnitImpl.SAC_RGBCOLOR) {
                type = "color";
            } else if (unit.getLexicalUnitType() == LexicalUnitImpl.SAC_IDENT
                    && ColorUtil.isHexColor(unit.getStringValue())) {
                // TODO support also named colors
                type = "color";
            } else if (unit.getLexicalUnitType() == LexicalUnitImpl.SAC_FUNCTION) {
                if ("rgb".equals(unit.getFunctionName())
                        || "rgba".equals(unit.getFunctionName())
                        || "hsl".equals(unit.getFunctionName())
                        || "hsla".equals(unit.getFunctionName())) {
                    type = "color";
                }
            }
        }

        return createIdent(function, type);
    }

    private boolean isBoolean(LexicalUnitImpl unit) {
        if (unit.getLexicalUnitType() != LexicalUnitImpl.SAC_IDENT) {
            return false;
        }
        return "true".equals(unit.getStringValue())
                || "false".equals(unit.getStringValue());
    }

    private boolean isNumber(LexicalUnitImpl unit) {
        return unit.isNumber();
    }

    private LexicalUnitImpl createIdent(LexicalUnitImpl function,
            String paramType) {
        return LexicalUnitImpl.createRawIdent(function.getLineNumber(),
                function.getColumnNumber(), paramType);
    }

}
