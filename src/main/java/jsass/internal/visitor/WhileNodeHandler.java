package jsass.internal.visitor;

import java.util.ArrayList;
import java.util.Collection;

import jsass.internal.ScssContext;
import jsass.internal.expression.BinaryOperator;
import jsass.internal.handler.SCSSErrorHandler;
import jsass.internal.parser.ParseException;
import jsass.internal.parser.SassListItem;
import jsass.internal.tree.IVariableNode;
import jsass.internal.tree.Node;
import jsass.internal.tree.controldirective.TemporaryNode;
import jsass.internal.tree.controldirective.WhileNode;

public class WhileNodeHandler {

    /**
     * Replace a WhileNode with the expanded set of nodes.
     * 
     * @param context
     *            current compilation context
     * @param whileNode
     *            node to replace
     */
    public static Collection<Node> traverse(ScssContext context,
            WhileNode whileNode) {
        Node parent = whileNode.getParentNode();
        ArrayList<Node> result = new ArrayList<Node>();
        while (evaluateCondition(context, whileNode)) {
            ArrayList<Node> nodes = iteration(context, whileNode);
            if (nodes.size() == 0) {
                SCSSErrorHandler.get().traverseError(new ParseException(
                        "@while loop iteration did nothing, infinite loop",
                        whileNode));
                return result;
            }
            TemporaryNode temp = new TemporaryNode(parent, nodes);
            result.addAll(temp.traverse(context));
        }
        return result;
    }

    private static boolean evaluateCondition(ScssContext context,
            WhileNode whileNode) {
        SassListItem condition = whileNode.getCondition();
        condition = condition.replaceVariables(context);
        condition = condition.evaluateFunctionsAndExpressions(context, true);
        return BinaryOperator.isTrue(condition);
    }

    private static ArrayList<Node> iteration(ScssContext context,
            WhileNode whileNode) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (final Node child : whileNode.getChildren()) {
            Node copy = child.copy();
            replaceVariables(context, copy);
            nodes.add(copy);
        }
        return nodes;
    }

    private static void replaceVariables(ScssContext context, Node copy) {
        if (copy instanceof IVariableNode) {
            IVariableNode n = (IVariableNode) copy;
            n.replaceVariables(context);
        }

        for (Node c : copy.getChildren()) {
            replaceVariables(context, c);
        }
    }

}
