package jsass.internal.tree;

import java.util.Collection;
import java.util.Collections;

import jsass.internal.ScssContext;
import jsass.internal.parser.StringInterpolationSequence;

public class KeyframesNode extends Node implements IVariableNode {
    private String keyframeName;
    private StringInterpolationSequence animationName;

    public KeyframesNode(String keyframeName,
            StringInterpolationSequence animationName) {
        this.keyframeName = keyframeName;
        this.animationName = animationName;
    }

    private KeyframesNode(KeyframesNode nodeToCopy) {
        super(nodeToCopy);
        keyframeName = nodeToCopy.keyframeName;
        animationName = nodeToCopy.animationName;
    }

    @Override
    public String printState() {
        return buildString(PRINT_STRATEGY);
    }

    @Override
    public String toString() {
        return "Key frames node [" + buildString(TO_STRING_STRATEGY) + "]";
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        replaceVariables(context);
        traverseChildren(context);
        return Collections.singleton((Node) this);
    }

    @Override
    public void replaceVariables(ScssContext context) {
        animationName = animationName.replaceVariables(context);
    }

    private String buildString(BuildStringStrategy strategy) {
        StringBuilder string = new StringBuilder();
        string.append(keyframeName).append(" ").append(animationName)
                .append(" {\n");
        for (Node child : getChildren()) {
            string.append("\t\t").append(strategy.build(child)).append("\n");
        }
        string.append("\t}");
        return string.toString();
    }

    @Override
    public KeyframesNode copy() {
        return new KeyframesNode(this);
    }
}