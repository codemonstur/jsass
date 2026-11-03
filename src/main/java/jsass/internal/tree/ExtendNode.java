package jsass.internal.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsass.internal.ScssContext;
import jsass.internal.handler.SCSSErrorHandler;
import jsass.internal.selector.Selector;
import jsass.internal.visitor.ExtendNodeHandler;

public class ExtendNode extends Node implements IVariableNode {
    private static final long serialVersionUID = 3301805078983796878L;

    private List<Selector> list;
    private boolean optional;

    public ExtendNode(List<Selector> list, boolean optional) {
        super();
        this.list = list;
        this.optional = optional;
    }

    private ExtendNode(ExtendNode nodeToCopy) {
        super(nodeToCopy);
        list = new ArrayList<Selector>(nodeToCopy.list);
        optional = nodeToCopy.optional;
    }

    public List<Selector> getList() {
        return list;
    }

    @Override
    public void replaceVariables(ScssContext context) {

    }

    @Override
    public String toString() {
        return "Extend node [" + getListAsString() + "]";
    }

    public String getListAsString() {
        StringBuilder b = new StringBuilder();
        for (final Selector s : list) {
            b.append(s.toString());
        }

        return b.toString();
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        try {
            // TODO here or later?
            traverseChildren(context);
            return ExtendNodeHandler.traverse(context, this);
        } catch (Exception e) {
            SCSSErrorHandler.get().traverseError(e);
            return Collections.emptyList();
        }
    }

    @Override
    public ExtendNode copy() {
        return new ExtendNode(this);
    }
}
