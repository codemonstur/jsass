package jsass.internal.selector;

import jsass.internal.ScssContext;
import jsass.internal.parser.StringInterpolationSequence;

/**
 * Single CSS3 universal selector "*".
 * 
 * See also {@link SimpleSelectorSequence} and {@link Selector}.
 */
public class UniversalSelector extends TypeSelector {

    public static final UniversalSelector it = new UniversalSelector();

    private UniversalSelector() {
        super(new StringInterpolationSequence("*"));
    }

    @Override
    public UniversalSelector replaceVariables(ScssContext context) {
        return this;
    }

}
