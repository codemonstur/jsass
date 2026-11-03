package jsass.internal.tree;

import java.util.Collection;
import java.util.Collections;

import jsass.internal.ScssContext;
import jsass.internal.parser.SassListItem;

public class ReturnNode extends Node implements IVariableNode,
        NodeWithUrlContent {

    private SassListItem expr;

    public ReturnNode(SassListItem expression) {
        expr = expression;
    }

    private ReturnNode(ReturnNode nodeToCopy) {
        super(nodeToCopy);
        expr = nodeToCopy.expr;
    }

    @Override
    public void replaceVariables(ScssContext context) {
        expr = expr.replaceVariables(context);
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        // need to replace variables here to make sure all vars are in scope
        expr = evaluate(context);
        return Collections.singleton((Node) this);
    }

    public SassListItem getExpr() {
        return expr;
    }

    /**
     * Evaluate the value of the return node in a context defined by the state
     * of ScssStylesheet (variables currently in scope, defined custom
     * functions).
     * 
     * This method does not modify the ReturnNode itself.
     */
    public SassListItem evaluate(ScssContext context) {
        SassListItem expr = getExpr();
        boolean arith = expr.containsArithmeticalOperator();
        expr = expr.replaceVariables(context);
        expr = expr.evaluateFunctionsAndExpressions(context, arith);
        return expr;
    }

    @Override
    public ReturnNode copy() {
        return new ReturnNode(this);
    }

    @Override
    public ReturnNode updateUrl(String prefix) {
        if (getExpr() != null) {
            ReturnNode newInstance = copy();
            newInstance.expr = getExpr().updateUrl(prefix);
            return newInstance;
        }
        return this;
    }
}
