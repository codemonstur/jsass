package jsass.internal.selector;

import java.io.Serializable;

import jsass.internal.ScssContext;

/**
 * Simple CSS3 selector such as an id selector or an attribute selector.
 * 
 * {@link SimpleSelector} instances are immutable and
 * {@link #replaceVariables()} returns a modified copy of the selector if
 * necessary.
 * 
 * Multiple concatenated simple selectors are grouped in
 * {@link SimpleSelectorSequence} and multiple sequences of selectors joined
 * with combinators are then joined into {@link Selector}.
 */
public abstract class SimpleSelector implements Serializable {

    public SimpleSelector replaceVariables(ScssContext context) {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass().equals(obj.getClass())
                && toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
