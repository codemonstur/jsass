package jsass.internal.tree;

import java.util.Collection;
import java.util.Collections;

import jsass.internal.ScssContext;
import jsass.internal.parser.Variable;
import jsass.internal.util.StringUtil;

/**
 * A simple BlockNode where input text equals output. <b>Note : </b> ignores any
 * possible children so only use it when you are sure no child nodes will be
 * applied.
 * 
 * @author Sebastian Nyholm @ Vaadin Ltd
 * 
 */
public class SimpleNode extends Node implements IVariableNode {

    private String text;

    public SimpleNode(String text) {
        this.text = text;
    }

    private SimpleNode(SimpleNode nodeToCopy) {
        super(nodeToCopy);
        text = nodeToCopy.text;
    }

    @Override
    public String printState() {
        return text;
    }

    @Override
    public String toString() {
        return printState();
    }

    @Override
    public void replaceVariables(ScssContext context) {
        for (final Variable var : context.getVariables()) {
            if (StringUtil.containsVariable(text, var.getName())) {
                text = StringUtil.replaceVariable(text, var.getName(), var
                        .getExpr().printState());
            }
        }
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        replaceVariables(context);
        return Collections.singleton((Node) this);
    }

    @Override
    public SimpleNode copy() {
        return new SimpleNode(this);
    }
}
