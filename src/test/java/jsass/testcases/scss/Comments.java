package jsass.testcases.scss;

import java.io.IOException;
import java.net.URISyntaxException;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.css.sac.CSSException;

import jsass.AbstractTestBase;
import jsass.internal.ScssStylesheet;
import jsass.internal.handler.SCSSDocumentHandler;
import jsass.internal.handler.SCSSDocumentHandlerImpl;
import jsass.internal.parser.Parser;
import jsass.internal.tree.CommentNode;

public class Comments extends AbstractTestBase {
    String scss = "/scss/comments.scss";
    String css = "/css/comments.css";

    @Test
    public void testParser() throws CSSException, URISyntaxException,
            IOException {
        Parser parser = new Parser();
        SCSSDocumentHandler handler = new SCSSDocumentHandlerImpl();
        parser.setDocumentHandler(handler);
        parser.parseStyleSheet(getClass().getResource(scss).getPath());
        ScssStylesheet root = handler.getStyleSheet();
        Assert.assertNotNull(root);
        Assert.assertEquals(6, root.getChildren().size());
        Assert.assertTrue(root.getChildren().get(1) instanceof CommentNode);
        Assert.assertTrue(root.getChildren().get(2) instanceof CommentNode);
    }

    @Test
    public void testCompiler() throws Exception {
        testCompiler(scss, css);
    }
}
