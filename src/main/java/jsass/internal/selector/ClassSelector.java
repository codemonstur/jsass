package jsass.internal.selector;

import jsass.internal.ScssContext;
import jsass.internal.parser.StringInterpolationSequence;

/**
 * Single CSS3 class selector such as ".abc".
 * 
 * See also {@link SimpleSelectorSequence} and {@link Selector}.
 */
public class ClassSelector extends SimpleSelector {

    private StringInterpolationSequence classValue;

    public ClassSelector(StringInterpolationSequence classValue) {
        this.classValue = classValue;
    }

    public StringInterpolationSequence getClassValue() {
        return classValue;
    }

    @Override
    public String toString() {
        return "." + getClassValue();
    }

    @Override
    public ClassSelector replaceVariables(ScssContext context) {
        return new ClassSelector(classValue.replaceVariables(context));
    }
}
