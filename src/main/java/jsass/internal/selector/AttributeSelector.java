package jsass.internal.selector;

import jsass.internal.ScssContext;
import jsass.internal.parser.StringInterpolationSequence;

/**
 * Single CSS3 attribute selector such as "[attribute]" or "[attribute~=value]".
 * 
 * See also {@link SimpleSelectorSequence} and {@link Selector}.
 */
public class AttributeSelector extends SimpleSelector {

    public enum MatchRelation {
        EQUALS("="), INCLUDES("~="), DASHMATCH("|="), PREFIXMATCH("^="), SUFFIXMATCH(
                "$="), SUBSTRINGMATCH("*=");

        private String value;

        private MatchRelation(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private StringInterpolationSequence attribute;
    private MatchRelation matchRelation;
    private StringInterpolationSequence value;

    /**
     * Constructs an attribute selector with an attribute name and optional
     * match relation and value. Match relation and value should both be set or
     * both be null.
     * 
     * @param attribute
     *            attribute name
     * @param matchRelation
     *            CSS3 match relation enum value
     * @param value
     *            string value to compare against
     */
    public AttributeSelector(StringInterpolationSequence attribute,
            MatchRelation matchRelation, StringInterpolationSequence value) {
        this.attribute = attribute;
        this.matchRelation = matchRelation;
        this.value = value;
    }

    public String getAttribute() {
        return attribute.toString();
    }

    public MatchRelation getMatchRelation() {
        return matchRelation;
    }

    private StringInterpolationSequence getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (getValue() == null) {
            return "[" + getAttribute() + "]";
        } else {
            return "[" + getAttribute() + getMatchRelation() + getValue() + "]";
        }
    }

    @Override
    public AttributeSelector replaceVariables(ScssContext context) {
        StringInterpolationSequence newAttribute = attribute
                .replaceVariables(context);
        StringInterpolationSequence newValue = null;
        if (value != null) {
            newValue = value.replaceVariables(context);
        }
        return new AttributeSelector(newAttribute, matchRelation, newValue);
    }
}
