package jsass.internal.tree;

import java.util.Collection;
import java.util.Collections;

import jsass.internal.ScssContext;

public class KeyframeSelectorNode extends Node {
    private String selector;

    public KeyframeSelectorNode(String selector) {
        this.selector = selector;
    }

    private KeyframeSelectorNode(KeyframeSelectorNode nodeToCopy) {
        super(nodeToCopy);
        selector = nodeToCopy.selector;
    }

    @Override
    public String printState() {
        return buildString(PRINT_STRATEGY);
    }

    @Override
    public String toString() {
        return "Key frame selector node [" + buildString(TO_STRING_STRATEGY)
                + "]";
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        traverseChildren(context);
        return Collections.singleton((Node) this);
    }

    public String buildString(BuildStringStrategy strategy) {
        StringBuilder string = new StringBuilder();
        string.append(selector).append(" {\n");
        for (Node child : getChildren()) {
            string.append("\t\t").append(strategy.build(child)).append("\n");
        }
        string.append("\t}");
        return string.toString();
    }

    @Override
    public KeyframeSelectorNode copy() {
        return new KeyframeSelectorNode(this);
    }
}
