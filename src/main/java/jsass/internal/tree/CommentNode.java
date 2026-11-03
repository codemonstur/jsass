package jsass.internal.tree;

import java.util.Collection;
import java.util.Collections;

import jsass.internal.ScssContext;

public class CommentNode extends Node {
    private final String comment;

    public CommentNode(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String printState() {
        return comment;
    }

    @Override
    public String toString() {
        return "Comment node [" + comment + "]";
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        return Collections.singleton((Node) this);
    }

    @Override
    public CommentNode copy() {
        return new CommentNode(comment);
    }
}
