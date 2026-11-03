package jsass.internal.resolver;

import org.w3c.css.sac.InputSource;

import jsass.internal.ScssStylesheet;

public interface ScssStylesheetResolver {
    /**
     * Called with the "identifier" of a stylesheet that the resolver should try
     * to find. The identifier is basically a filename, like "runo.scss" or
     * "addon/styles.scss", but might exclude ".scss". The resolver must
     * {@link InputSource#setURI(String)} to the final location where the
     * stylesheet was found, e.g "runo.scss" might result in a URI like
     * "VAADIN/themes/runo/runo.scss".
     * 
     * @param parentStylesheet
     *            The parent style sheet
     * @param identifier
     *            used fo find stylesheet
     * @return InputSource for stylesheet (with URI set) or null if not found
     */
    public InputSource resolve(ScssStylesheet parentStylesheet,
            String identifier);
}
