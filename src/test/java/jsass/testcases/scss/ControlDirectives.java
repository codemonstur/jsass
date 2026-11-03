package jsass.testcases.scss;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.css.sac.CSSException;

import jsass.AbstractTestBase;
import jsass.internal.ScssStylesheet;
import jsass.internal.handler.SCSSDocumentHandler;
import jsass.internal.handler.SCSSDocumentHandlerImpl;
import jsass.internal.parser.Parser;
import jsass.internal.tree.BlockNode;
import jsass.internal.tree.MixinDefNode;
import jsass.internal.tree.MixinNode;
import jsass.internal.tree.Node;
import jsass.internal.tree.controldirective.EachDefNode;
import jsass.internal.tree.controldirective.ForNode;
import jsass.internal.tree.controldirective.IfElseDefNode;

public class ControlDirectives extends AbstractTestBase {

    String scss = "/scss/control-directives.scss";
    String css = "/css/control-directives.css";

    @Test
    public void testParser() throws CSSException, IOException {
        Parser parser = new Parser();
        SCSSDocumentHandler handler = new SCSSDocumentHandlerImpl();
        parser.setDocumentHandler(handler);
        parser.parseStyleSheet(getClass().getResource(scss).getPath());
        ScssStylesheet root = handler.getStyleSheet();
        Assert.assertNotNull(root);

        List<Node> children = root.getChildren();
        Assert.assertEquals(10, root.getChildren().size());

        Assert.assertTrue(children.get(1) instanceof MixinDefNode);
        Assert.assertTrue(children.get(2) instanceof MixinDefNode);
        Assert.assertTrue(children.get(3) instanceof MixinNode);
        Assert.assertTrue(children.get(4) instanceof BlockNode);
        Assert.assertTrue(children.get(5) instanceof BlockNode);
        Assert.assertTrue(children.get(6) instanceof BlockNode);
        Assert.assertTrue(children.get(8) instanceof ForNode);
        Assert.assertTrue(children.get(9) instanceof ForNode);

        Assert.assertTrue(!(children.get(1).getChildren().get(0) instanceof IfElseDefNode));
        Assert.assertTrue(children.get(2).getChildren().get(0) instanceof EachDefNode);
        Assert.assertTrue(children.get(4).getChildren().get(0) instanceof IfElseDefNode);
        Assert.assertTrue(children.get(5).getChildren().get(0) instanceof IfElseDefNode);

        Assert.assertEquals(1, children.get(1).getChildren().size());

    }

    @Test
    public void testCompiler() throws Exception {
        testCompiler(scss, css);
    }
}
