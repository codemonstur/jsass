package jsass.internal.tree;

import java.util.Collection;
import java.util.Collections;

import jsass.internal.ScssContext;
import jsass.internal.parser.SassListItem;
import jsass.internal.parser.StringInterpolationSequence;

public class RuleNode extends Node implements IVariableNode, NodeWithUrlContent {
    private static final long serialVersionUID = 6653493127869037022L;

    StringInterpolationSequence variable;
    SassListItem value = null;
    String comment;
    private boolean important;

    public RuleNode(StringInterpolationSequence variable, SassListItem value,
            boolean important, String comment) {
        this.variable = variable;
        setValue(value);
        this.important = important;
        this.comment = comment;
    }

    private RuleNode(RuleNode nodeToCopy) {
        super(nodeToCopy);
        variable = nodeToCopy.variable;
        value = nodeToCopy.value;
        comment = nodeToCopy.comment;
        important = nodeToCopy.important;
    }

    public StringInterpolationSequence getVariable() {
        return variable;
    }

    public void setVariable(StringInterpolationSequence variable) {
        this.variable = variable;
    }

    public SassListItem getValue() {
        return value;
    }

    private void setValue(SassListItem value) {
        this.value = value;
    }

    @Override
    public String printState() {
        return buildString(PRINT_STRATEGY);
    }

    @Override
    public String toString() {
        return "Rule node [" + buildString(TO_STRING_STRATEGY) + "]";
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public void replaceVariables(ScssContext context) {
        variable = variable.replaceVariables(context);
        value = value.replaceVariables(context);
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        /*
         * containsArithmeticalOperator() must be called before
         * replaceVariables. Because for the "/" operator, it needs to see if
         * its predecessor or successor is a Variable or not, to determine it is
         * an arithmetic operator.
         */
        boolean hasOperators = value.containsArithmeticalOperator();
        replaceVariables(context);
        value = value.evaluateFunctionsAndExpressions(context, hasOperators);
        return Collections.singleton((Node) this);
    }

    private String buildString(BuildStringStrategy strategy) {
        String stringValue = strategy.build(value)
                + (important ? " !important" : "");
        StringBuilder builder = new StringBuilder();
        if (!"".equals(stringValue.trim())) {
            builder.append(variable);
            builder.append(": ");
            builder.append(stringValue);
            builder.append(';');
        }

        if (comment != null) {
            builder.append(comment);
        }
        return builder.toString();
    }

    @Override
    public RuleNode copy() {
        return new RuleNode(this);
    }

    @Override
    public RuleNode updateUrl(String prefix) {
        if (getValue() != null) {
            RuleNode newInstance = copy();
            newInstance.value = getValue().updateUrl(prefix);
            return newInstance;
        }
        return this;
    }
}
