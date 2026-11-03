package jsass.internal.tree;

import java.util.Collection;

import jsass.internal.Definition;
import jsass.internal.Scope;
import jsass.internal.ScssContext;
import jsass.internal.parser.ActualArgumentList;
import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.Variable;

/**
 * DefNode defines the shared functionality of mixin and function definition
 * nodes. This includes the handling of parameter lists.
 * 
 * @author jsass
 * 
 */
public abstract class DefNode extends Node implements Definition,
        IVariableNode, NodeWithUrlContent {
    private String name;
    private FormalArgumentList arglist;
    private Scope definitionScope;

    public DefNode(String name, Collection<Variable> args,
            boolean hasVariableArgs) {
        super();
        this.name = name;
        arglist = new FormalArgumentList(args, hasVariableArgs);
    }

    protected DefNode(DefNode nodeToCopy) {
        super(nodeToCopy);
        name = nodeToCopy.name;
        arglist = nodeToCopy.arglist;
        definitionScope = nodeToCopy.definitionScope;
    }

    public String getName() {
        return name;
    }

    public FormalArgumentList getArglist() {
        return arglist;
    }

    public boolean hasVariableArguments() {
        return arglist.hasVariableArguments();
    }

    @Override
    public void replaceVariables(ScssContext context) {
        arglist = arglist.replaceVariables(context);
    }

    public void replacePossibleArguments(ActualArgumentList actualArgumentList) {
        // TODO instead of modifying def, return a VariableArgumentList?
        arglist = arglist.replaceFormalArguments(actualArgumentList, true);
    }

    public Scope getDefinitionScope() {
        return definitionScope;
    }

    protected void setDefinitionScope(Scope scope) {
        definitionScope = scope;
    }

    public DefNode updateUrl(String prefix) {
        DefNode newInstance = (DefNode) copy();
        newInstance.arglist = arglist.updateUrl(prefix);
        return newInstance;
    }
}
