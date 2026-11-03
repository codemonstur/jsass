package jsass.internal.visitor;

import java.util.Collection;

import jsass.internal.ScssContext;
import jsass.internal.tree.NestPropertiesNode;
import jsass.internal.tree.Node;

/**
 * Handle nested properties nodes (e.g. "font: { family: serif; }" to
 * "font-family: serif;").
 * 
 * Sample SASS code (from www.sass-lang.com):
 * 
 * <pre>
 * li {
 *   font: {
 *     family: serif;
 *     weight: bold;
 *     size: 1.2em;
 *   }
 * }
 * </pre>
 * 
 * Note that this does not apply to nested blocks, which are handled by
 * {@link BlockNodeHandler}.
 */
public class NestedNodeHandler {

    public static Collection<Node> traverse(ScssContext context,
            NestPropertiesNode node) {
        node.replaceVariables(context);
        return node.unNesting();
    }
}