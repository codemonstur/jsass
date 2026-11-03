package jsass.internal.visitor;

import java.util.Collection;
import java.util.Collections;

import org.w3c.flute.parser.ParseException;

import jsass.internal.ScssContext;
import jsass.internal.expression.BinaryOperator;
import jsass.internal.parser.SassListItem;
import jsass.internal.tree.Node;
import jsass.internal.tree.controldirective.ElseNode;
import jsass.internal.tree.controldirective.IfElseDefNode;
import jsass.internal.tree.controldirective.IfNode;
import jsass.internal.tree.controldirective.TemporaryNode;

public class IfElseNodeHandler {

    public static Collection<Node> traverse(ScssContext context,
            IfElseDefNode node) throws Exception {
        for (final Node child : node.getChildren()) {
            if (child instanceof IfNode) {
                SassListItem expression = ((IfNode) child).getExpression();
                expression = expression.replaceVariables(context);
                expression = expression.evaluateFunctionsAndExpressions(
                        context, true);

                if (BinaryOperator.isTrue(expression)) {
                    return traverseChild(context, node.getParentNode(), child);
                }
            } else {
                if (!(child instanceof ElseNode)
                        && node.getChildren().indexOf(child) == node
                                .getChildren().size() - 1) {
                    throw new ParseException(
                            "Invalid @if/@else in scss file for " + node);
                } else {
                    return traverseChild(context, node.getParentNode(), child);
                }
            }
        }
        // no matching branch
        return Collections.emptyList();
    }

    private static Collection<Node> traverseChild(ScssContext context,
            Node parent, Node child) {
        TemporaryNode tempParent = new TemporaryNode(parent,
                child.getChildren());
        return tempParent.traverse(context);
    }

}
