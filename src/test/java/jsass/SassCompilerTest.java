package jsass;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class SassCompilerTest {

    @Test
    public void mainCompilesScss() throws Exception {
        final File in = new File("src/test/resources/w3ctests/scss/text-indent-intrinsic-002.1.scss");
        final File out = new File("target/test-classes/out.css");
        out.getParentFile().mkdirs();
        SassCompiler.main(new String[]{in.getAbsolutePath(), out.getAbsolutePath()});
        assertTrue(out.exists() && out.length() > 0);
    }
}