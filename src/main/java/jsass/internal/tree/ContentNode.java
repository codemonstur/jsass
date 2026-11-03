/**
 * ContentNode represents a {@literal @}content in a SCSS tree. 
 */
package jsass.internal.tree;

import java.util.Collection;
import java.util.Collections;

import jsass.internal.ScssContext;

public class ContentNode extends Node {

    public ContentNode() {
    }

    private ContentNode(Node nodeToCopy) {
        super(nodeToCopy);
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        /*
         * ContentNode is basically just a placeholder for some content which
         * will be included. So for traverse of this node, it does nothing. it
         * will be replaced when traversing MixinDefNode which contains it.
         */
        return Collections.singleton((Node) this);
    }

    @Override
    public ContentNode copy() {
        return new ContentNode(this);
    }

}
