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
import jsass.internal.tree.ImportNode;

public class ParentImports extends AbstractTestBase {

    String scss = "/scss/folder-test/parent-import.scss";
    String css = "/css/parent-import.css";

    @Test
    public void testParser() throws CSSException, IOException {
        Parser parser = new Parser();
        SCSSDocumentHandler handler = new SCSSDocumentHandlerImpl();
        parser.setDocumentHandler(handler);
        parser.parseStyleSheet(getClass().getResource(scss).getPath());
        ScssStylesheet root = handler.getStyleSheet();
        ImportNode importVariableNode = (ImportNode) root.getChildren().get(0);
        Assert.assertEquals("../folder-test2/variables.scss",
                importVariableNode.getUri());
        Assert.assertFalse(importVariableNode.isPureCssImport());

        ImportNode importURLNode = (ImportNode) root.getChildren().get(1);
        Assert.assertEquals("../folder-test2/url", importURLNode.getUri());
        Assert.assertFalse(importURLNode.isPureCssImport());

        ImportNode importImportNode = (ImportNode) root.getChildren().get(2);
        Assert.assertEquals("../folder-test2/base-imported.scss",
                importImportNode.getUri());
        Assert.assertFalse(importImportNode.isPureCssImport());
    }

    @Test
    public void testCompiler() throws Exception {
        ScssStylesheet sheet = testCompiler(scss, css);
        List<String> importedSheets = sheet.getSourceUris();
        Assert.assertEquals(5, importedSheets.size());
        Assert.assertTrue(importedSheets.get(0).replace('\\', '/')
                .endsWith("/scss/folder-test/parent-import.scss"));
        Assert.assertTrue(importedSheets.get(1).replace('\\', '/')
                .endsWith("/scss/folder-test2/variables.scss"));
        Assert.assertTrue(importedSheets.get(2).replace('\\', '/')
                .endsWith("/scss/folder-test2/url.scss"));
        Assert.assertTrue(importedSheets.get(3).replace('\\', '/')
                .endsWith("/scss/folder-test2/base-imported.scss"));
        Assert.assertTrue(importedSheets.get(4).replace('\\', '/')
                .endsWith("/scss/folder-test2/base.scss"));
    }
}
