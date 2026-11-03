package jsass.parser;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.css.sac.InputSource;

import jsass.internal.handler.SCSSDocumentHandler;
import jsass.internal.handler.SCSSDocumentHandlerImpl;
import jsass.internal.parser.Parser;

public class ParserTest {

    @Test
    public void testCanIngoreSingleLineComment() {
        Parser parser = new Parser();
        SCSSDocumentHandler handler = new SCSSDocumentHandlerImpl();
        parser.setDocumentHandler(handler);
        try {
            parser.parseStyleSheet(new InputSource(new StringReader(
                    "//kjaljsföajsfalkj\n@12abcg;")));
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
