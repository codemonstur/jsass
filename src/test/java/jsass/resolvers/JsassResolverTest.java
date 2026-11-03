package jsass.resolvers;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import jsass.internal.resolver.AbstractResolver;
import jsass.internal.resolver.ClassloaderResolver;
import jsass.internal.resolver.FilesystemResolver;

public class JsassResolverTest {

    @Test
    public void testFilesystemResolverPathNormalization() throws Exception {
        testPathNormalization(new FilesystemResolver());
    }

    @Test
    public void testClassloaderResolverPathNormalization() throws Exception {
        testPathNormalization(new ClassloaderResolver());
    }

    public void testPathNormalization(AbstractResolver resolver)
            throws Exception {

        Method normalizeMethod = AbstractResolver.class.getDeclaredMethod(
                "normalize", String.class);
        normalizeMethod.setAccessible(true);

        String identifier, result;

        identifier = "a/b/../../../a b/b.scss";
        result = (String) normalizeMethod.invoke(resolver, identifier);
        Assert.assertEquals("../a b/b.scss", result);

        identifier = "./a/b/../c/d/.././e.scss";
        result = (String) normalizeMethod.invoke(resolver, identifier);
        Assert.assertEquals("a/c/e.scss", result);

        identifier = "/äåäåäääå/:;:;:;/???????/- -/e.scss";
        result = (String) normalizeMethod.invoke(resolver, identifier);
        Assert.assertEquals("/äåäåäääå/:;:;:;/???????/- -/e.scss", result);

        identifier = ".";
        result = (String) normalizeMethod.invoke(resolver, identifier);
        Assert.assertEquals("", result);

        identifier = "../..";
        result = (String) normalizeMethod.invoke(resolver, identifier);
        Assert.assertEquals("../..", result);

        identifier = "./../a.scss";
        result = (String) normalizeMethod.invoke(resolver, identifier);
        Assert.assertEquals("../a.scss", result);
    }

}
