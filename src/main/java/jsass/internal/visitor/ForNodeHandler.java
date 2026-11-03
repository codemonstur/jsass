package jsass.internal.visitor;

import java.util.ArrayList;
import java.util.Collection;

import jsass.internal.ScssContext;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;
import jsass.internal.parser.SassListItem;
import jsass.internal.parser.Variable;
import jsass.internal.tree.Node;
import jsass.internal.tree.controldirective.ForNode;

public class ForNodeHandler extends LoopNodeHandler {

    public static Collection<Node> traverse(ScssContext context, ForNode forNode) {
        int fromInt = getInt(context, forNode.getFrom());
        int toInt = getInt(context, forNode.getTo());
        if (forNode.isExclusive()) {
            toInt = toInt - 1;
        }
        Collection<Variable> indices = new ArrayList<Variable>();
        for (int idx = fromInt; idx <= toInt; ++idx) {
            LexicalUnitImpl idxUnit = LexicalUnitImpl.createInteger(forNode
                    .getFrom().getLineNumber(), forNode.getFrom()
                    .getColumnNumber(), idx);
            indices.add(new Variable(forNode.getVariableName(), idxUnit));
        }
        return replaceLoopNode(context, forNode, indices);
    }

    private static int getInt(ScssContext context, SassListItem item) {
        item = item.replaceVariables(context);
        SassListItem value = item
                .evaluateFunctionsAndExpressions(context, true);
        if (value instanceof LexicalUnitImpl
                && ((LexicalUnitImpl) value).isNumber()) {
            return ((LexicalUnitImpl) value).getIntegerValue();
        }
        throw new ParseException(
                "The loop indices of @for must evaluate to integer values",
                item);
    }

}
