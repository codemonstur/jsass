package jsass.testcases.scss;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.css.sac.CSSException;

import jsass.AbstractTestBase;
import jsass.internal.ScssStylesheet;
import jsass.internal.handler.SCSSDocumentHandler;
import jsass.internal.handler.SCSSDocumentHandlerImpl;
import jsass.internal.parser.Parser;
import jsass.internal.resolver.FilesystemResolver;
import jsass.internal.tree.ImportNode;

public class CompassImports extends AbstractTestBase {

    String scssOtherDirectory = "/scss/compass-test/compass-import.scss";
    String scssSameDirectory = "/scss/compass-test2/compass-import2.scss";
    String css = "/css/compass-import.css";

    String compassPath = "/scss/compass-test2";

    @Test
    public void testParser() throws CSSException, IOException {
        Parser parser = new Parser();
        SCSSDocumentHandler handler = new SCSSDocumentHandlerImpl();
        parser.setDocumentHandler(handler);
        parser.parseStyleSheet(getClass().getResource(scssOtherDirectory)
                .getPath());
        ScssStylesheet root = handler.getStyleSheet();
        ImportNode importVariableNode = (ImportNode) root.getChildren().get(0);
        Assert.assertEquals("compass", importVariableNode.getUri());
        Assert.assertFalse(importVariableNode.isPureCssImport());
    }

    @Test
    public void testCompiler() throws Exception {
        testCompiler(scssSameDirectory, css, null);
    }

    @Test
    public void testCompilerWithCustomPath() throws Exception {
        File rootPath = new File(getClass().getResource(compassPath).toURI());

        testCompiler(scssOtherDirectory, css, rootPath.getPath());
    }

    public void testCompiler(String scss, String css, String additionalPath)
            throws Exception {
        comparisonCss = getFileContent(css);
        comparisonCss = comparisonCss.replaceAll(CR, "");
        ScssStylesheet sheet = getStyleSheet(scss);
        Assert.assertNotNull(sheet);
        sheet.addResolver(new FilesystemResolver(additionalPath));

        sheet.compile();
        parsedScss = sheet.printState();
        parsedScss = parsedScss.replaceAll(CR, "");
        Assert.assertEquals("Original CSS and parsed CSS do not match",
                comparisonCss, parsedScss);
    }
}
