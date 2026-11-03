package jsass.internal.selector;

import jsass.internal.ScssContext;
import jsass.internal.parser.StringInterpolationSequence;
import jsass.internal.parser.Variable;

/**
 * Single CSS3 pseudo-class selector such as ":active" or ":nth-child(2)".
 * 
 * See also {@link SimpleSelectorSequence} and {@link Selector}.
 */
public class PseudoClassSelector extends SimpleSelector {
    private StringInterpolationSequence pseudoClass;
    private String argument = null;

    public PseudoClassSelector(StringInterpolationSequence pseudoClass) {
        this(pseudoClass, null);
    }

    public PseudoClassSelector(StringInterpolationSequence pseudoClass,
            String argument) {
        this.pseudoClass = pseudoClass;
        this.argument = argument;
    }

    public StringInterpolationSequence getClassValue() {
        return pseudoClass;
    }

    @Override
    public String toString() {
        if (argument == null) {
            return ":" + getClassValue();
        } else {
            return ":" + getClassValue() + "(" + argument + ")";
        }
    }

    @Override
    public PseudoClassSelector replaceVariables(ScssContext context) {
        if (argument == null) {
            return new PseudoClassSelector(
                    pseudoClass.replaceVariables(context));
        } else {
            return new PseudoClassSelector(
                    pseudoClass.replaceVariables(context),
                    replaceInterpolation(context, argument));
        }
    }

    private String replaceInterpolation(ScssContext context, String value) {
        String result = value;
        for (Variable var : context.getVariables()) {
            result = var.replaceInterpolation(result);
        }
        return result;
    }
}