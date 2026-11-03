package jsass.internal.tree.controldirective;

import java.util.Collection;

import jsass.internal.ScssContext;
import jsass.internal.parser.SassList;
import jsass.internal.parser.SassListItem;
import jsass.internal.tree.IVariableNode;
import jsass.internal.tree.Node;
import jsass.internal.tree.NodeWithUrlContent;
import jsass.internal.visitor.EachNodeHandler;

public class EachDefNode extends Node implements IVariableNode,
        NodeWithUrlContent {
    private static final long serialVersionUID = 7943948981204906221L;

    private String var;
    private SassListItem list;

    public EachDefNode(String var, SassListItem list) {
        super();
        this.var = var;
        this.list = list;
    }

    private EachDefNode(EachDefNode nodeToCopy) {
        super(nodeToCopy);
        var = nodeToCopy.var;
        list = nodeToCopy.list;
    }

    public SassList getVariables() {
        if (list instanceof SassList) {
            return (SassList) list;
        } else {
            return new SassList(list);
        }
    }

    public String getVariableName() {
        return var;
    }

    @Override
    public String toString() {
        return "Each Definition Node: {variable : " + var + ", "
                + "children : " + list + "}";
    }

    @Override
    public void replaceVariables(ScssContext context) {
        list = list.replaceVariables(context);
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        replaceVariables(context);
        return EachNodeHandler.traverse(context, this);
    }

    @Override
    public EachDefNode copy() {
        return new EachDefNode(this);
    }

    @Override
    public NodeWithUrlContent updateUrl(String prefix) {
        EachDefNode copy = copy();
        copy.list = list.updateUrl(prefix);
        return copy;
    }
}
