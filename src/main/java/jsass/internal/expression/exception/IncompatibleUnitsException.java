package jsass.internal.expression.exception;

public class IncompatibleUnitsException extends ArithmeticException {
    public IncompatibleUnitsException(String errorExpr) {
        super(getErrorMsg(errorExpr));
    }

    private static String getErrorMsg(String errorExpr) {
        StringBuilder builder = new StringBuilder();
        builder.append("Incompatible units found in: ");
        builder.append("'").append(errorExpr).append("'");
        return builder.toString();
    }
}
