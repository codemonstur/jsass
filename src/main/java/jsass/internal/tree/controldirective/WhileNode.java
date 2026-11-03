package jsass.internal.tree.controldirective;

import java.util.Collection;

import jsass.internal.ScssContext;
import jsass.internal.parser.SassListItem;
import jsass.internal.tree.Node;
import jsass.internal.visitor.WhileNodeHandler;

public class WhileNode extends Node {

    private SassListItem condition;

    public WhileNode(SassListItem condition) {
        this.condition = condition;
    }

    private WhileNode(WhileNode nodeToCopy) {
        super(nodeToCopy);
        condition = nodeToCopy.condition;
    }

    @Override
    public String toString() {
        return "While Node: { condition: " + condition + "}";
    }

    public SassListItem getCondition() {
        return condition;
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        return WhileNodeHandler.traverse(context, this);
    }

    @Override
    public WhileNode copy() {
        return new WhileNode(this);
    }

}
