package jsass.internal.visitor;

import java.util.ArrayList;
import java.util.Collection;

import jsass.internal.ScssContext;
import jsass.internal.parser.SassListItem;
import jsass.internal.parser.Variable;
import jsass.internal.tree.Node;
import jsass.internal.tree.controldirective.EachDefNode;

public class EachNodeHandler extends LoopNodeHandler {

    public static Collection<Node> traverse(ScssContext context,
            EachDefNode eachNode) {
        Collection<Variable> loopVariables = new ArrayList<Variable>();
        for (final SassListItem var : eachNode.getVariables()) {
            loopVariables.add(new Variable(eachNode.getVariableName()
                    .substring(1), var));
        }
        return replaceLoopNode(context, eachNode, loopVariables);
    }

}
