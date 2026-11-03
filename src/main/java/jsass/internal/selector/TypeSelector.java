package jsass.internal.selector;

import jsass.internal.ScssContext;
import jsass.internal.parser.StringInterpolationSequence;

/**
 * Single CSS3 type selector such as "p" or "div".
 * 
 * See also {@link SimpleSelectorSequence} and {@link Selector}.
 */
public class TypeSelector extends SimpleSelector {
    private StringInterpolationSequence localName;

    public TypeSelector(StringInterpolationSequence value) {
        localName = value;
    }

    public StringInterpolationSequence getValue() {
        return localName;
    }

    @Override
    public String toString() {
        return localName.toString();
    }

    @Override
    public TypeSelector replaceVariables(ScssContext context) {
        return new TypeSelector(localName.replaceVariables(context));
    }
}
