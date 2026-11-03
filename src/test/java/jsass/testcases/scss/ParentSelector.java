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

public class ParentSelector extends AbstractTestBase {
    String scss = "/scss/parent-selector.scss";
    String css = "/css/parent-selector.css";

    @Test
    public void testParser() throws CSSException, IOException {
        Parser parser = new Parser();
        SCSSDocumentHandler handler = new SCSSDocumentHandlerImpl();
        parser.setDocumentHandler(handler);
        parser.parseStyleSheet(getClass().getResource(scss).getPath());
        ScssStylesheet root = handler.getStyleSheet();
        BlockNode blockNode = (BlockNode) root.getChildren().get(0);
        Assert.assertEquals(5, blockNode.getChildren().size());
        BlockNode nestedBlock1 = (BlockNode) blockNode.getChildren().get(3);
        Assert.assertEquals("&:hover", nestedBlock1.getSelectorList().get(0)
                .toString());
        BlockNode nestedBlock2 = (BlockNode) blockNode.getChildren().get(4);
        Assert.assertEquals("body.firefox &", nestedBlock2.getSelectorList()
                .get(0).toString());
    }

    @Test
    public void testCompiler() throws Exception {
        testCompiler(scss, css);
    }
}
