package jsass.internal.parser;

public class SCSSParseException extends ParseException {
    private ParseException parseException;
    private String fileName;

    public SCSSParseException(ParseException parseException, String fileName) {
        this.parseException = parseException;
        this.fileName = fileName;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("Error when parsing file ").append(fileName)
                .append("\n").append(parseException.getMessage());
        return builder.toString();
    }
}
