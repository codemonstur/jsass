package jsass.testcases.css;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;
import org.w3c.css.sac.CSSException;

import jsass.AbstractTestBase;

public class EmptyBlock extends AbstractTestBase {
    String css = "/basic/empty_block.css";

    @Test
    public void testParser() throws CSSException, URISyntaxException,
            IOException {
        testParser(css);
    }
}
