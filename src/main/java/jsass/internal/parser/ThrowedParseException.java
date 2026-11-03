package jsass.internal.parser;

/**
 * @version $Revision: 1.1 $
 * @author Philippe Le Hegaret
 */
class ThrowedParseException extends RuntimeException {
    private static final long serialVersionUID = -7926371344505913546L;

    ParseException e;

    /**
     * Creates a new ThrowedParseException
     */
    ThrowedParseException(ParseException e) {
        this.e = e;
    }
}
