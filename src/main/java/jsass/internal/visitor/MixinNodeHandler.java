package jsass.internal.visitor;

import java.util.Collection;
import java.util.Collections;

import jsass.internal.Scope;
import jsass.internal.ScssContext;
import jsass.internal.handler.SCSSErrorHandler;
import jsass.internal.parser.ParseException;
import jsass.internal.parser.Variable;
import jsass.internal.tree.MixinDefNode;
import jsass.internal.tree.MixinNode;
import jsass.internal.tree.Node;
import jsass.internal.tree.controldirective.TemporaryNode;

public class MixinNodeHandler {

    public static Collection<Node> traverse(ScssContext context, MixinNode node) {
        return replaceMixins(context, node);
    }

    private static Collection<Node> replaceMixins(ScssContext context,
            MixinNode node) {
        MixinDefNode mixinDef = context.getMixinDefinition(node.getName());
        if (mixinDef == null) {
            SCSSErrorHandler.get().traverseError(
                    "Mixin Definition: " + node.getName() + " not found");
            return Collections.emptyList();
        }
        return replaceMixinNode(context, node, mixinDef);
    }

    private static Collection<Node> replaceMixinNode(ScssContext context,
            MixinNode mixinNode, MixinDefNode mixinDef) {
        MixinDefNode defClone = mixinDef.copy();

        defClone.replaceContentDirective(mixinNode);

        if (!mixinDef.getArglist().isEmpty()) {
            defClone.replacePossibleArguments(mixinNode.getArglist());
            defClone.replaceVariables(context);
        }

        // parameters have been evaluated in parent scope, rest should be
        // in the scope where the mixin was defined
        Scope previousScope = context.openVariableScope(defClone
                .getDefinitionScope());
        try {
            // add variables from argList
            for (Variable var : defClone.getArglist().getArguments()) {
                Variable evaluated = new Variable(var.getName(), var.getExpr()
                        .evaluateFunctionsAndExpressions(context, true));
                context.addVariable(evaluated);
            }
            // traverse child nodes in this scope
            // use correct parent with intermediate TemporaryNode
            Node tempParent = new TemporaryNode(mixinNode.getParentNode(),
                    defClone.getChildren());
            return tempParent.traverse(context);
        } finally {
            context.closeVariableScope(previousScope);
        }
    }
}
