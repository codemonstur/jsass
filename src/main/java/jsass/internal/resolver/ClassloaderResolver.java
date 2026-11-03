package jsass.internal.resolver;

import java.io.InputStream;

import org.w3c.css.sac.InputSource;

public class ClassloaderResolver extends AbstractResolver {

    @Override
    public InputSource resolveNormalized(String identifier) {
        String fileName = identifier;
        if (!fileName.endsWith(".css")) {
            fileName += ".scss";
        }

        // Filename should be a relative path starting with VAADIN/...
        int vaadinIdx = fileName.lastIndexOf("VAADIN/");
        if (vaadinIdx > -1) {
            fileName = fileName.substring(vaadinIdx);
        }

        // Can the classloader find it?
        InputStream is = getClass().getClassLoader().getResourceAsStream(
                fileName);
        if (is != null) {
            InputSource source = new InputSource();
            source.setByteStream(is);
            source.setURI(fileName);
            return source;

        } else {
            return null;
        }

    }

}
