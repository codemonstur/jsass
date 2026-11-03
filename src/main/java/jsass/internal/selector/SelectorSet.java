package jsass.internal.selector;

import java.util.Collection;
import java.util.LinkedHashSet;

public class SelectorSet extends LinkedHashSet<Selector> {

    public SelectorSet() {
        super();
    }

    public SelectorSet(Selector selector) {
        add(selector);
    }

    public SelectorSet(Collection<Selector> selectorCollection) {
        addAll(selectorCollection);
    }

    /**
     * Removes each selector which is subsumed by another selector.
     */
    public SelectorSet eliminateRedundantSelectors() {
        SelectorSet filtered = new SelectorSet();
        for (Selector s1 : this) {
            // Find most general selector matching s1
            Selector s = s1;
            for (Selector s2 : this) {
                if (s2.subsumes(s)) {
                    s = s2;
                }
            }
            filtered.add(s);
        }
        return filtered;
    }
}
