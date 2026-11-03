package jsass.internal.tree.controldirective;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsass.internal.ScssContext;
import jsass.internal.handler.SCSSErrorHandler;
import jsass.internal.tree.Node;
import jsass.internal.visitor.IfElseNodeHandler;

public class IfElseDefNode extends Node {

    public IfElseDefNode() {
    }

    private IfElseDefNode(IfElseDefNode nodeToCopy) {
        super(nodeToCopy);
    }

    @Override
    public String printState() {
        return buildString(PRINT_STRATEGY);
    }

    @Override
    public String toString() {
        return "IfElseDef node [" + buildString(TO_STRING_STRATEGY) + "]";
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        try {
            return IfElseNodeHandler.traverse(context, this);
        } catch (Exception e) {
            SCSSErrorHandler.get().traverseError(e);
            return Collections.emptyList();
        }
    }

    private String buildString(BuildStringStrategy strategy) {
        StringBuilder b = new StringBuilder();
        for (final Node child : getChildren()) {
            b.append(strategy.build(child));
            b.append("\n");
        }
        return b.toString();
    }

    @Override
    public IfElseDefNode copy() {
        return new IfElseDefNode(this);
    }

}
