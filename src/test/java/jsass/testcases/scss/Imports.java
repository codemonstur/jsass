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
import jsass.internal.tree.ImportNode;

public class Imports extends AbstractTestBase {

    String scss = "/scss/imports.scss";
    String css = "/css/imports.css";

    @Test
    public void testParser() throws CSSException, IOException {
        Parser parser = new Parser();
        SCSSDocumentHandler handler = new SCSSDocumentHandlerImpl();
        parser.setDocumentHandler(handler);
        parser.parseStyleSheet(getClass().getResource(scss).getPath());
        ScssStylesheet root = handler.getStyleSheet();
        ImportNode importNode = (ImportNode) root.getChildren().get(0);
        Assert.assertEquals("_partial-for-import", importNode.getUri());
        Assert.assertFalse(importNode.isPureCssImport());
    }

    @Test
    public void testCompiler() throws Exception {
        testCompiler(scss, css);
    }
}
