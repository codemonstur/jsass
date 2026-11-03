package jsass.testcases.scss;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.css.sac.CSSException;

import jsass.AbstractTestBase;
import jsass.internal.ScssStylesheet;
import jsass.internal.handler.SCSSDocumentHandler;
import jsass.internal.handler.SCSSDocumentHandlerImpl;
import jsass.internal.parser.Parser;
import jsass.internal.tree.BlockNode;

public class Functions extends AbstractTestBase {

    String scss = "/scss/functions.scss";
    String css = "/css/functions.css";

    @Test
    public void testParser() throws CSSException, IOException {
        Parser parser = new Parser();
        SCSSDocumentHandler handler = new SCSSDocumentHandlerImpl();
        parser.setDocumentHandler(handler);
        parser.parseStyleSheet(getClass().getResource(scss).getPath());
        ScssStylesheet root = handler.getStyleSheet();
        Assert.assertEquals(6, root.getChildren().size());
        BlockNode blockNode = (BlockNode) root.getChildren().get(5);
        Assert.assertEquals(18, blockNode.getChildren().size());
    }

    @Test
    public void testCompiler() throws Exception {
        testCompiler(scss, css);
    }
}
