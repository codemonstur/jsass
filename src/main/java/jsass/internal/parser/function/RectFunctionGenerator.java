package jsass.internal.parser.function;

import static org.w3c.css.sac.LexicalUnit.SAC_CENTIMETER;
import static org.w3c.css.sac.LexicalUnit.SAC_EM;
import static org.w3c.css.sac.LexicalUnit.SAC_EX;
import static org.w3c.css.sac.LexicalUnit.SAC_INCH;
import static org.w3c.css.sac.LexicalUnit.SAC_MILLIMETER;
import static org.w3c.css.sac.LexicalUnit.SAC_PICA;
import static org.w3c.css.sac.LexicalUnit.SAC_PIXEL;
import static org.w3c.css.sac.LexicalUnit.SAC_POINT;

import java.util.ArrayList;
import java.util.List;

import jsass.internal.parser.ActualArgumentList;
import jsass.internal.parser.ArgumentList;
import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;
import jsass.internal.parser.SCSSLexicalUnit;
import jsass.internal.parser.SassList;
import jsass.internal.parser.SassListItem;

public class RectFunctionGenerator extends AbstractFunctionGenerator {

    private static String[] argumentNames = { "value" }; // uses varargs, so
                                                         // this name is not
                                                         // actually used

    public RectFunctionGenerator() {
        super(createArgumentList(argumentNames, true), "rect");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {

        ArgumentList actualParams = (ArgumentList) getParam(actualArguments, 0);
        List<SassListItem> resultParams = new ArrayList<SassListItem>();

        for (int i = 0; i < actualParams.size(); i++) {
            boolean paramOk = true;
            SassListItem item = actualParams.get(i);
            if (!(item instanceof LexicalUnitImpl)) {
                throw new ParseException(
                        "Only simple values are allowed as rect() parameters: "
                                + actualParams);
            }
            LexicalUnitImpl lui = (LexicalUnitImpl) item;
            if (lui.getLexicalUnitType() == SCSSLexicalUnit.SAC_INTEGER) {
                if (lui.getIntegerValue() != 0) {
                    paramOk = false;
                }
            } else if (lui.getLexicalUnitType() == SCSSLexicalUnit.SAC_IDENT) {
                if (!"auto".equals(lui.getStringValue())) {
                    paramOk = false;
                }
            } else if (!LexicalUnitImpl.checkLexicalUnitType(lui, SAC_EM,
                    SAC_EX, SAC_PIXEL, SAC_CENTIMETER, SAC_MILLIMETER,
                    SAC_INCH, SAC_POINT, SAC_PICA)) {
                paramOk = false;
            }
            if (!paramOk) {
                throw new ParseException(
                        "The following value is not accepted as a parameter for rect(): "
                                + item);
            }
            resultParams.add(item);
        }
        ActualArgumentList params = new ActualArgumentList(
                SassList.Separator.COMMA, resultParams);
        if (params.size() == 4) {
            return LexicalUnitImpl.createRect(function.getLineNumber(),
                    function.getColumnNumber(), params);
        } else {
            return LexicalUnitImpl.createFunction(function.getLineNumber(),
                    function.getColumnNumber(), "rect", params);
        }

    }
}
