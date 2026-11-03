package jsass.internal.tree;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsass.internal.Scope;
import jsass.internal.ScssContext;
import jsass.internal.parser.ActualArgumentList;
import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;
import jsass.internal.parser.SassListItem;
import jsass.internal.parser.Variable;

/**
 * Transient class representing a function call to a custom (user-defined)
 * function. This class is used to evaluate the function call and is discarded
 * after use. A FunctionCall does not have a parent in the stylesheet node tree.
 */
public class FunctionCall {

    public static SassListItem evaluate(ScssContext context,
            FunctionDefNode def, LexicalUnitImpl invocation) {
        ActualArgumentList invocationArglist = invocation.getParameterList()
                .expandVariableArguments();
        SassListItem value = null;
        // only parameters are evaluated in current scope, body in
        // top-level scope
        try {
            FormalArgumentList arglist = def.getArglist();
            arglist = arglist.replaceFormalArguments(invocationArglist, true);
            // replace variables in default values of parameters
            arglist = arglist.replaceVariables(context);

            // copying is necessary as traversal modifies the parent of the
            // node
            // TODO in the long term, should avoid full copy
            FunctionDefNode defCopy = def.copy();

            // limit variable scope to the scope where the function was defined
            Scope previousScope = context.openVariableScope(def
                    .getDefinitionScope());
            try {
                for (Variable param : arglist) {
                    context.addVariable(param);
                }

                // only contains variable nodes, return nodes and control
                // structures
                while (defCopy.getChildren().size() > 0) {
                    Node firstChild = defCopy.getChildren().get(0);
                    if (firstChild instanceof ReturnNode) {
                        ReturnNode returnNode = ((ReturnNode) firstChild);
                        value = returnNode.evaluate(context);
                        break;
                    }
                    defCopy.replaceNode(firstChild, new ArrayList<Node>(
                            firstChild.traverse(context)));
                }
            } finally {
                context.closeVariableScope(previousScope);
            }
        } catch (Exception e) {
            Logger.getLogger(FunctionCall.class.getName()).log(Level.SEVERE,
                    null, e);
        }
        if (value == null) {
            throw new ParseException("Function " + invocation.getFunctionName()
                    + " did not return a value", invocation);
        }
        return value;
    }

}
