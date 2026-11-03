package jsass.testcases.scss;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.css.sac.CSSException;

import jsass.AbstractTestBase;
import jsass.internal.ScssStylesheet;
import jsass.internal.handler.SCSSDocumentHandler;
import jsass.internal.handler.SCSSDocumentHandlerImpl;
import jsass.internal.parser.Parser;
import jsass.internal.tree.BlockNode;
import jsass.internal.tree.NestPropertiesNode;
import jsass.internal.tree.RuleNode;

public class NestedProperties extends AbstractTestBase {
    String scss = "/scss/nested-properties.scss";
    String css = "/css/nested-properties.css";

    @Test
    public void testParser() throws CSSException, IOException {
        Parser parser = new Parser();
        SCSSDocumentHandler handler = new SCSSDocumentHandlerImpl();
        parser.setDocumentHandler(handler);
        parser.parseStyleSheet(getClass().getResource(scss).getPath());
        ScssStylesheet root = handler.getStyleSheet();
        Assert.assertEquals(1, root.getChildren().size());

        BlockNode blockNode = (BlockNode) root.getChildren().get(0);
        Assert.assertEquals(1, blockNode.getChildren().size());

        NestPropertiesNode nestPropertiesNode = (NestPropertiesNode) blockNode
                .getChildren().get(0);
        Assert.assertEquals("font", nestPropertiesNode.getName().toString());
        RuleNode nestedProperty0 = (RuleNode) nestPropertiesNode.getChildren()
                .get(0);
        RuleNode nestedProperty1 = (RuleNode) nestPropertiesNode.getChildren()
                .get(1);
        RuleNode nestedProperty2 = (RuleNode) nestPropertiesNode.getChildren()
                .get(2);
        Assert.assertEquals("family", nestedProperty0.getVariable().toString());
        Assert.assertEquals("weight", nestedProperty1.getVariable().toString());
        Assert.assertEquals("size", nestedProperty2.getVariable().toString());
    }

    @Test
    public void testCompiler() throws Exception {
        testCompiler(scss, css);
    }
}
