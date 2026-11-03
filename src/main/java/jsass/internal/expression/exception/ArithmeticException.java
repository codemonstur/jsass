package jsass.internal.expression.exception;

import jsass.internal.parser.SassListItem;

public class ArithmeticException extends RuntimeException {
    public ArithmeticException(String errorMsg) {
        super(errorMsg);
    }

    public ArithmeticException(String error, SassListItem term) {
        super(buildMessage(error, term));
    }

    private static String buildMessage(String message, SassListItem term) {
        if (term == null) {
            return message;
        }

        StringBuilder builder = new StringBuilder(message);

        builder.append(": \"");
        builder.append(term.toString());
        builder.append("\" [");
        builder.append(term.getLineNumber());
        builder.append(",");
        builder.append(term.getColumnNumber());
        builder.append("]");

        return builder.toString();
    }
}
