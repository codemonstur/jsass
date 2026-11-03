package jsass.internal.tree;

import java.util.Collection;
import java.util.Collections;

import jsass.internal.ScssContext;

public class FontFaceNode extends Node {

    public FontFaceNode() {
    }

    private FontFaceNode(FontFaceNode nodeToCopy) {
        super(nodeToCopy);
    }

    @Override
    public String printState() {
        return buildString(PRINT_STRATEGY);
    }

    @Override
    public String toString() {
        return "FontFace node [" + buildString(TO_STRING_STRATEGY) + "]";
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        traverseChildren(context);
        return Collections.singleton((Node) this);
    }

    private String buildString(BuildStringStrategy strategy) {
        StringBuilder builder = new StringBuilder();
        builder.append("@font-face {\n");

        for (final Node child : getChildren()) {
            builder.append("\t" + strategy.build(child) + "\n");
        }

        builder.append("}");
        return builder.toString();
    }

    @Override
    public FontFaceNode copy() {
        return new FontFaceNode(this);
    }

}
