package jsass.internal.selector;

import jsass.internal.ScssContext;
import jsass.internal.parser.StringInterpolationSequence;

/**
 * Simple placeholder selector of the form "%abc". Placeholder selectors are not
 * output as-is but only when used via "@extend".
 */
public class PlaceholderSelector extends SimpleSelector {
    private StringInterpolationSequence value;

    public PlaceholderSelector(StringInterpolationSequence value) {
        this.value = value;
    }

    public StringInterpolationSequence getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "%" + getValue();
    }

    @Override
    public PlaceholderSelector replaceVariables(ScssContext context) {
        return new PlaceholderSelector(value.replaceVariables(context));
    }
}
