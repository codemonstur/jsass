package jsass.internal.selector;

import jsass.internal.ScssContext;
import jsass.internal.parser.StringInterpolationSequence;

/**
 * Single CSS3 id selector such as "#abc".
 * 
 * See also {@link SimpleSelectorSequence} and {@link Selector}.
 */
public class IdSelector extends SimpleSelector {
    private StringInterpolationSequence value;

    public IdSelector(StringInterpolationSequence id) {
        value = id;
    }

    public StringInterpolationSequence getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "#" + getValue();
    }

    @Override
    public IdSelector replaceVariables(ScssContext context) {
        return new IdSelector(value.replaceVariables(context));
    }

}
