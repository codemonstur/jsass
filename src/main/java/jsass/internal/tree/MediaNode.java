package jsass.internal.tree;

import java.util.Collection;
import java.util.Collections;

import org.w3c.css.sac.SACMediaList;

import jsass.internal.ScssContext;

public class MediaNode extends Node {
    private static final long serialVersionUID = 2502097081457509523L;

    SACMediaList media;

    public MediaNode(SACMediaList media) {
        super();
        this.media = media;
    }

    private MediaNode(MediaNode nodeToCopy) {
        super(nodeToCopy);
        media = nodeToCopy.media;
    }

    public SACMediaList getMedia() {
        return media;
    }

    public void setMedia(SACMediaList media) {
        this.media = media;
    }

    @Override
    public String printState() {
        return buildString(PRINT_STRATEGY, true);
    }

    @Override
    public String toString() {
        return buildString(TO_STRING_STRATEGY, true);
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        traverseChildren(context);
        return Collections.singleton((Node) this);
    }

    private String buildString(BuildStringStrategy strategy, boolean indent) {
        StringBuilder builder = new StringBuilder("@media ");
        if (media != null) {
            for (int i = 0; i < media.getLength(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(media.item(i));
            }
        }
        builder.append(" {\n");
        for (Node child : getChildren()) {
            builder.append('\t');
            if (child instanceof BlockNode) {
                if (PRINT_STRATEGY.equals(strategy)) {
                    builder.append(((BlockNode) child).buildString(indent));
                } else {
                    builder.append(strategy.build(child));

                }
            } else {
                builder.append(strategy.build(child));
            }
            builder.append('\n');
        }
        builder.append("}");
        return builder.toString();
    }

    @Override
    public MediaNode copy() {
        return new MediaNode(this);
    }
}
