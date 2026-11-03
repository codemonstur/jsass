package jsass.internal.visitor;

import jsass.internal.tree.Node;

public interface Visitor {

    public void traverse(Node node) throws Exception;
}
