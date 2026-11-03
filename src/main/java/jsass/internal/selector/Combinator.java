package jsass.internal.selector;

public enum Combinator implements SelectorSegment {
    DESCENDANT(" "), CHILD(" > "), SIBLING(" + "), GENERAL_SIBLING(" ~ ");

    private String symbol;

    private Combinator(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
