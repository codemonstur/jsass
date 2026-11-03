package jsass.internal.selector;

import jsass.internal.ScssContext;
import jsass.internal.parser.StringInterpolationSequence;

/**
 * Single CSS3 pseudo-element selector such as "::after" or "::first-letter".
 * 
 * See also {@link SimpleSelectorSequence} and {@link Selector}.
 */
public class PseudoElementSelector extends SimpleSelector {

    private StringInterpolationSequence pseudoElement;

    public PseudoElementSelector(StringInterpolationSequence pseudoElement) {
        this.pseudoElement = pseudoElement;
    }

    public StringInterpolationSequence getPseudoElement() {
        return pseudoElement;
    }

    @Override
    public String toString() {
        return "::" + pseudoElement;
    }

    @Override
    public PseudoElementSelector replaceVariables(ScssContext context) {
        return new PseudoElementSelector(
                pseudoElement.replaceVariables(context));
    }
}
