package jsass.internal.visitor;

import jsass.internal.ScssContext;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.SCSSLexicalUnit;
import jsass.internal.parser.SassListItem;
import jsass.internal.parser.Variable;
import jsass.internal.tree.VariableNode;

public class VariableNodeHandler {

    public static void traverse(ScssContext context, VariableNode node) {
        Variable variable = context.getVariable(node.getName());
        if (!node.isGuarded() || variable == null || variable.getExpr() == null) {
            context.setVariable(node.getVariable());
        } else { // Handle the case where a variable has the value SCSS_NULL
            SassListItem value = variable.getExpr();
            if (value instanceof LexicalUnitImpl) {
                LexicalUnitImpl unit = (LexicalUnitImpl) value;
                if (unit.getLexicalUnitType() == SCSSLexicalUnit.SCSS_NULL) {
                    context.setVariable(node.getVariable());
                }
            }
        }
    }
}
