package jsass.internal.tree.controldirective;

import java.util.Collection;

import jsass.internal.ScssContext;
import jsass.internal.parser.SassListItem;
import jsass.internal.tree.Node;

public class ElseNode extends Node implements IfElseNode {

    public ElseNode() {
    }

    public ElseNode(ElseNode nodeToCopy) {
        super(nodeToCopy);
    }

    @Override
    public SassListItem getExpression() {
        return null;
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        return traverseChildren(context);
    }

    @Override
    public ElseNode copy() {
        return new ElseNode(this);
    }

}
