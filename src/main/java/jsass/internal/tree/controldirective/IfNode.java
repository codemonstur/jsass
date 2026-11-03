package jsass.internal.tree.controldirective;

import java.util.Collection;

import jsass.internal.ScssContext;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.SassListItem;
import jsass.internal.tree.IVariableNode;
import jsass.internal.tree.Node;

public class IfNode extends Node implements IfElseNode, IVariableNode {
    private SassListItem expression;

    public IfNode(SassListItem expression) {
        if (expression == null) {
            expression = LexicalUnitImpl.createIdent("false");
        }
        this.expression = expression;
    }

    private IfNode(IfNode nodeToCopy) {
        super(nodeToCopy);
        expression = nodeToCopy.expression;
    }

    @Override
    public SassListItem getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "@if " + expression.toString();
    }

    @Override
    public void replaceVariables(ScssContext context) {
        expression = expression.replaceVariables(context);
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        return traverseChildren(context);
    }

    @Override
    public IfNode copy() {
        return new IfNode(this);
    }
}