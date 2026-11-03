package jsass.internal.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jsass.internal.ScssContext;
import jsass.internal.parser.StringInterpolationSequence;
import jsass.internal.visitor.NestedNodeHandler;

public class NestPropertiesNode extends Node implements IVariableNode {
    private static final long serialVersionUID = 3671253315690598308L;

    private StringInterpolationSequence name;

    public NestPropertiesNode(StringInterpolationSequence name) {
        super();
        this.name = name;
    }

    private NestPropertiesNode(NestPropertiesNode nodeToCopy) {
        super(nodeToCopy);
        name = nodeToCopy.name;
    }

    public StringInterpolationSequence getName() {
        return name;
    }

    public void setName(StringInterpolationSequence name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Nest properties node [ name = " + name + " ]";
    }

    public Collection<Node> unNesting() {
        List<Node> result = new ArrayList<Node>();
        for (Node child : getChildren()) {
            RuleNode createNewRuleNodeFromChild = createNewRuleNodeFromChild((RuleNode) child);
            result.add(createNewRuleNodeFromChild);
        }
        return result;
    }

    public RuleNode createNewRuleNodeFromChild(RuleNode child) {
        StringInterpolationSequence newName = name
                .append(new StringInterpolationSequence("-"));
        newName = newName.append(child.getVariable());
        RuleNode newRuleNode = new RuleNode(newName, child.getValue(),
                child.isImportant(), null);
        return newRuleNode;
    }

    @Override
    public void replaceVariables(ScssContext context) {
        name = name.replaceVariables(context);
        for (Node child : getChildren()) {
            if (child instanceof RuleNode) {
                ((RuleNode) child).replaceVariables(context);
            }
        }
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        traverseChildren(context);
        return NestedNodeHandler.traverse(context, this);
    }

    @Override
    public String printState() {
        return null;
    }

    @Override
    public NestPropertiesNode copy() {
        return new NestPropertiesNode(this);
    }

}
