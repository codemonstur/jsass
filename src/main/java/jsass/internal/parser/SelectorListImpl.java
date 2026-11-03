package jsass.internal.parser;

import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorList;

/**
 * @version $Revision: 1.1 $
 * @author Philippe Le Hegaret
 */
public class SelectorListImpl implements SelectorList {

    Selector[] selectors = new Selector[5];
    int current;

    @Override
    public Selector item(int index) {
        if ((index < 0) || (index >= current)) {
            return null;
        }
        return selectors[index];
    }

    public Selector itemSelector(int index) {
        if ((index < 0) || (index >= current)) {
            return null;
        }
        return selectors[index];
    }

    @Override
    public int getLength() {
        return current;
    }

    public void addSelector(Selector selector) {
        if (current == selectors.length) {
            Selector[] old = selectors;
            selectors = new Selector[old.length + old.length];
            System.arraycopy(old, 0, selectors, 0, old.length);
        }
        selectors[current++] = selector;
    }

    public void replaceSelector(int index, Selector selector) {
        if ((index >= 0) && (index < current)) {
            selectors[index] = selector;
        }
    }
}
