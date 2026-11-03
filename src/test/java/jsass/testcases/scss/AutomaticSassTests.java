package jsass.testcases.scss;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import org.junit.runner.RunWith;

import jsass.testcases.scss.SassTestRunner.TestFactory;

@RunWith(SassTestRunner.class)
public class AutomaticSassTests extends AbstractDirectoryScanningSassTests {

    @Override
    protected URL getResourceURL(String path) {
        return getResourceURLInternal(path);
    }

    private static URL getResourceURLInternal(String path) {
        return AutomaticSassTests.class.getResource("/automatic" + path);
    }

    @TestFactory
    public static Collection<String> getScssResourceNames()
            throws URISyntaxException, IOException {
        return getScssResourceNames(getResourceURLInternal(""));
    }

}
