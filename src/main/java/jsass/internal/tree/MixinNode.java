package jsass.internal.tree;

import java.util.ArrayList;
import java.util.Collection;

import jsass.internal.ScssContext;
import jsass.internal.parser.ActualArgumentList;
import jsass.internal.parser.SassList;
import jsass.internal.parser.Variable;
import jsass.internal.visitor.MixinNodeHandler;

/**
 * Node for including a Mixin.
 * 
 * MixinNode handles argument lists with support for variable arguments. When
 * variable arguments are used, a MixinNode expands a list into separate
 * arguments, whereas a DefNode packs several arguments into a list. The
 * corresponding definition node is {@link MixinDefNode}.
 * 
 * @author jsass
 */
public class MixinNode extends Node implements IVariableNode,
        NodeWithUrlContent {
    private static final long serialVersionUID = 4725008226813110658L;

    // these are the actual parameter values, not whether the definition node
    // uses varargs
    private ActualArgumentList arglist;
    private String name;

    public MixinNode(String name) {
        this(name, new ArrayList<Variable>(), false);
    }

    public MixinNode(String name, Collection<Variable> args,
            boolean hasVariableArgs) {
        this.name = name;
        arglist = new ActualArgumentList(SassList.Separator.COMMA, args,
                hasVariableArgs);
    }

    private MixinNode(MixinNode nodeToCopy) {
        super(nodeToCopy);
        arglist = nodeToCopy.arglist;
        name = nodeToCopy.name;
    }

    public ActualArgumentList getArglist() {
        return arglist;
    }

    protected void expandVariableArguments() {
        arglist = arglist.expandVariableArguments();
    }

    public String getName() {
        return name;
    }

    /**
     * Replace variable references with their values in the argument list and
     * name.
     */
    @Override
    public void replaceVariables(ScssContext context) {
        arglist = arglist.replaceVariables(context);
        arglist = arglist.evaluateFunctionsAndExpressions(context, true);
    }

    @Override
    public String printState() {
        return "name: " + getName() + " args: " + getArglist();
    }

    @Override
    public String toString() {
        return "Mixin node [" + printState() + "]";
    }

    protected void replaceVariablesForChildren(ScssContext context) {
        for (Node child : getChildren()) {
            if (child instanceof IVariableNode) {
                ((IVariableNode) child).replaceVariables(context);
            }
        }
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        try {
            replaceVariables(context);
            expandVariableArguments();
            // for the content block, use the scope where it is defined
            // (consistent with sass-lang)
            replaceVariablesForChildren(context);
            // inner scope is managed by MixinNodeHandler
            return MixinNodeHandler.traverse(context, this);
        } catch (Exception e) {
            throw e instanceof RuntimeException re ? re : new IllegalStateException(e);
        }
    }

    @Override
    public MixinNode copy() {
        return new MixinNode(this);
    }

    @Override
    public NodeWithUrlContent updateUrl(String prefix) {
        MixinNode newInstance = copy();
        newInstance.arglist = arglist.updateUrl(prefix);
        return newInstance;
    }

}
