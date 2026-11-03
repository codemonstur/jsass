package jsass.testcases.scss;

import org.junit.Assert;
import org.junit.Test;

import jsass.AbstractTestBase;
import jsass.internal.ScssContext;
import jsass.internal.ScssStylesheet;

public class UrlModes extends AbstractTestBase {
    private String scssFileName = "/urlmode/url-in-imported-stylesheet.scss";

    @Test
    public void testAbsoluteUrlMode() throws Exception {
        String cssFileName = "/urlmode/css/absolute.css";
        testCompiler(scssFileName, cssFileName, ScssContext.UrlMode.ABSOLUTE);
    }

    @Test
    public void testMixedUrlMode() throws Exception {
        String cssFileName = "/urlmode/css/mixed.css";
        testCompiler(scssFileName, cssFileName, ScssContext.UrlMode.MIXED);
    }

    @Test
    public void testRelativeUrlMode() throws Exception {
        String cssFileName = "/urlmode/css/relative.css";
        testCompiler(scssFileName, cssFileName, ScssContext.UrlMode.RELATIVE);
    }

    public ScssStylesheet testCompiler(String scss, String css,
            ScssContext.UrlMode urlMode) throws Exception {
        comparisonCss = getFileContent(css);
        comparisonCss = comparisonCss.replaceAll(CR, "");
        ScssStylesheet sheet = getStyleSheet(scss);
        sheet.compile(urlMode);
        parsedScss = sheet.printState();
        parsedScss = parsedScss.replaceAll(CR, "");
        Assert.assertEquals("Original CSS and parsed CSS do not match",
                comparisonCss, parsedScss);
        return sheet;
    }
}
