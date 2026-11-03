package jsass.internal.resolver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.w3c.css.sac.InputSource;

import jsass.internal.ScssStylesheet;

public class FilesystemResolver extends AbstractResolver {

    private String[] customPaths = null;

    public FilesystemResolver(String... customPaths) {
        this.customPaths = customPaths;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * jsass.internal.resolver.AbstractResolver#getPotentialPaths(
     * jsass.internal.ScssStylesheet, java.lang.String)
     */
    @Override
    protected List<String> getPotentialParentPaths(
            ScssStylesheet parentStyleSheet, String identifier) {
        List<String> potentialPaths = super.getPotentialParentPaths(
                parentStyleSheet, identifier);
        if (customPaths != null) {
            for (String path : customPaths) {
                potentialPaths.add(extractFullPath(path, identifier));
            }
        }

        return potentialPaths;
    }

    @Override
    public InputSource resolveNormalized(String identifier) {
        String fileName = identifier;
        if (!fileName.endsWith(".css")) {
            fileName += ".scss";
        }

        try {
            InputStream is = new FileInputStream(fileName);
            InputSource source = new InputSource();
            source.setByteStream(is);
            source.setURI(fileName);
            return source;

        } catch (FileNotFoundException e) {
            // not found, try something else
            return null;
        }

    }

}
