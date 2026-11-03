package jsass.internal.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import jsass.internal.ScssContext;
import jsass.internal.parser.Variable;

public class MixinDefNode extends DefNode {
    private static final long serialVersionUID = 5469294053247343948L;

    public MixinDefNode(String name, Collection<Variable> args,
            boolean hasVariableArgs) {
        super(name, args, hasVariableArgs);
    }

    private MixinDefNode(MixinDefNode nodeToCopy) {
        super(nodeToCopy);
    }

    @Override
    public String toString() {
        return "Mixin Definition Node: {name: " + getName() + ", args: "
                + getArglist().size() + "}";
    }

    /**
     * This should only happen on a cloned MixinDefNode, since it changes the
     * Node itself.
     * 
     * @param mixinNode
     * @return
     */
    public MixinDefNode replaceContentDirective(MixinNode mixinNode) {
        return findAndReplaceContentNodeInChildren(this, mixinNode);
    }

    private MixinDefNode findAndReplaceContentNodeInChildren(Node node,
            MixinNode mixinNode) {
        ContentNode contentNode = null;
        for (Node child : new ArrayList<Node>(node.getChildren())) {
            if (child instanceof ContentNode) {
                contentNode = (ContentNode) child;
                replaceContentNode(contentNode, mixinNode);
            } else {
                findAndReplaceContentNodeInChildren(child, mixinNode);
            }
        }
        return this;
    }

    public MixinDefNode replaceContentNode(ContentNode contentNode,
            MixinNode mixinNode) {
        if (contentNode != null) {
            contentNode.getParentNode().replaceNode(contentNode,
                    mixinNode.copyChildren());
        }
        return this;
    }

    @Override
    public MixinDefNode copy() {
        return new MixinDefNode(this);
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        context.defineMixin(this);
        setDefinitionScope(context.getCurrentScope());
        return Collections.emptyList();
    }

}
