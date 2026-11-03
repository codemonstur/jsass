package jsass.internal.parser.function;

import jsass.internal.ScssContext;
import jsass.internal.expression.BinaryOperator;
import jsass.internal.parser.ActualArgumentList;
import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;
import jsass.internal.parser.SassListItem;

public class IfFunctionGenerator implements SCSSFunctionGenerator {

    private static String[] argumentNames = { "condition", "if-true",
            "if-false" };

    private String[] functionNames;
    private FormalArgumentList arguments;

    public IfFunctionGenerator() {
        arguments = AbstractFunctionGenerator.createArgumentList(argumentNames,
                false);
        functionNames = new String[] { "if" };
    }

    @Override
    public String[] getFunctionNames() {
        return functionNames;
    }

    @Override
    public SassListItem compute(ScssContext context, LexicalUnitImpl function) {
        ActualArgumentList args = function.getParameterList();
        FormalArgumentList functionArguments;
        try {
            functionArguments = arguments.replaceFormalArguments(args, true);
        } catch (ParseException e) {
            throw new ParseException("Error in parameters of function "
                    + function.getFunctionName() + "(), line "
                    + function.getLineNumber() + ", column "
                    + function.getColumnNumber() + ": [" + e.getMessage() + "]");
        }
        return computeForArgumentList(context, function, functionArguments);
    }

    protected SassListItem computeForArgumentList(ScssContext context,
            LexicalUnitImpl function, FormalArgumentList actualArguments) {
        SassListItem firstParam = AbstractFunctionGenerator.getParam(
                actualArguments, "condition").evaluateFunctionsAndExpressions(
                context, true);
        if (BinaryOperator.isTrue(firstParam)) {
            return AbstractFunctionGenerator.getParam(actualArguments,
                    "if-true").evaluateFunctionsAndExpressions(context, true);
        } else {
            return AbstractFunctionGenerator.getParam(actualArguments,
                    "if-false").evaluateFunctionsAndExpressions(context, true);
        }
    }
}