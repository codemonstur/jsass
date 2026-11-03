package jsass.testcases.scss;

import org.junit.Assert;
import org.junit.Test;

import jsass.AbstractTestBase;
import jsass.internal.ScssStylesheet;

public class MixinExtraParameters extends AbstractTestBase {
    String scss = "/scss/mixin-extra-params.scss";

    @Test
    public void testCompiler() {
        ScssStylesheet sheet;
        try {
            sheet = getStyleSheet(scss);
            sheet.compile();
        } catch (RuntimeException e) {
            Assert.assertEquals(
                    "Substitution error: some actual parameters were not used. Formal parameters: FormalArgumentList[$p1: null], actual parameters: Actual argument list [ArgumentList [foo, bar]] at line 4, column 14",
                    e.getMessage());
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
