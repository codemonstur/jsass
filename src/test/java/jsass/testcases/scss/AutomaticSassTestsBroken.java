package jsass.testcases.scss;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.w3c.css.sac.CSSException;

import jsass.testcases.scss.SassTestRunner.TestFactory;

@RunWith(SassTestRunner.class)
public class AutomaticSassTestsBroken extends AbstractDirectoryScanningSassTests {

    @Override
    protected URL getResourceURL(String path) {
        return getResourceURLInternal(path);
    }

    private static URL getResourceURLInternal(String path) {
        return AutomaticSassTestsBroken.class.getResource("/automaticbroken"
                + path);
    }

    @TestFactory
    public static Collection<String> getScssResourceNames()
            throws URISyntaxException, IOException {
        URL directoryUrl = getResourceURLInternal("");
        if (directoryUrl != null) {
            return getScssResourceNames(directoryUrl);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void compareScssWithCss(String scssResourceName) throws Exception {
        boolean success = false;
        try {
            super.compareScssWithCss(scssResourceName);
            success = true;
        } catch (CSSException e) {
            // this is an expected outcome
        } catch (AssertionError e) {
            // this is an expected outcome
        }
        if (success) {
            Assert.fail("Test "
                    + scssResourceName
                    + " from automaticbroken that was expected to fail has been fixed. Please move the corresponding CSS and SCSS files to automatic.");
        }
    }

}
