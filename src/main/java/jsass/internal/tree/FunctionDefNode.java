package jsass.internal.tree;

import java.util.Collection;
import java.util.Collections;

import jsass.internal.ScssContext;
import jsass.internal.parser.Variable;

public class FunctionDefNode extends DefNode {
    public FunctionDefNode(String name, Collection<Variable> args,
            boolean hasVariableArgs) {
        super(name, args, hasVariableArgs);
    }

    private FunctionDefNode(FunctionDefNode nodeToCopy) {
        super(nodeToCopy);
    }

    @Override
    public FunctionDefNode copy() {
        return new FunctionDefNode(this);
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        context.defineFunction(this);
        setDefinitionScope(context.getCurrentScope());
        return Collections.emptyList();
    }

}
