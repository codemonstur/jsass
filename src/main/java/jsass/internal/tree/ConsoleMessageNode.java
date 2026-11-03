package jsass.internal.tree;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsass.internal.ScssContext;
import jsass.internal.parser.Variable;
import jsass.internal.util.StringUtil;

public class ConsoleMessageNode extends Node implements IVariableNode {

    private String message;
    private boolean warning;

    public ConsoleMessageNode(String message, boolean warning) {
        this.message = message;
        this.warning = warning;
    }

    @Override
    public void replaceVariables(ScssContext context) {
        // interpolation of variable names in the string
        for (final Variable var : context.getVariables()) {
            if (StringUtil.containsVariable(message, var.getName())) {
                message = StringUtil.replaceVariable(message, var.getName(),
                        var.getExpr().printState());
            }
        }
    }

    @Override
    public String printState() {
        return "";
    }

    @Override
    public String toString() {
        return (warning ? "@warn " : "@debug ") + message;
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        Level level = warning ? Level.SEVERE : Level.INFO;
        Logger.getLogger(ConsoleMessageNode.class.getName())
                .log(level, message);
        return Collections.emptyList();
    }

    @Override
    public ConsoleMessageNode copy() {
        return new ConsoleMessageNode(message, warning);
    }
}
