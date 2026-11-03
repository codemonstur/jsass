package jsass.internal.parser.function;

import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;

public class QuoteUnquoteFunctionGenerator extends
        AbstractSingleParameterFunctionGenerator {
    private static String[] argumentNames = { "string" };

    public QuoteUnquoteFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "quote", "unquote");
    }

    @Override
    protected LexicalUnitImpl computeForParam(String functionName,
            LexicalUnitImpl firstParam) {
        if (!LexicalUnitImpl.checkLexicalUnitType(firstParam,
                LexicalUnitImpl.SAC_IDENT, LexicalUnitImpl.SAC_STRING_VALUE,
                LexicalUnitImpl.SAC_URI)) {
            throw new ParseException("The parameter of " + functionName
                    + "() must be a string", firstParam);
        }
        String result = firstParam.printState();
        if ("quote".equals(functionName)) {
            if (isQuoted(result)) {
                return firstParam;
            } else {
                return LexicalUnitImpl.createString(firstParam.getLineNumber(),
                        firstParam.getColumnNumber(), result);
            }
        } else {
            result = unquote(result);
            return LexicalUnitImpl.createIdent(firstParam.getLineNumber(),
                    firstParam.getColumnNumber(), result);
        }
    }

    private String unquote(String string) {
        if (!isQuoted(string)) {
            return string;
        } else {
            return string.substring(1, string.length() - 1);
        }
    }

    private boolean isQuoted(String string) {
        return (string.length() > 1 && ((string.charAt(0) == '"' && string
                .charAt(string.length() - 1) == '"') || (string.charAt(0) == '\'' && string
                .charAt(string.length() - 1) == '\'')));
    }
}