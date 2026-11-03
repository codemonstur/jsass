package jsass.internal.selector;

import jsass.internal.ScssContext;
import jsass.internal.parser.StringInterpolationSequence;

/**
 * Single SCSS parent selector "&".
 * 
 * The parent selector is not passed to CSS as-is but is replaced with the
 * selectors from the parent block in the case of nested blocks.
 * 
 * See also {@link SimpleSelectorSequence} and {@link Selector}.
 */
public class ParentSelector extends TypeSelector {

    public static final ParentSelector it = new ParentSelector();

    private ParentSelector() {
        super(new StringInterpolationSequence("&"));
    }

    @Override
    public ParentSelector replaceVariables(ScssContext context) {
        return this;
    }
}
