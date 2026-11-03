package jsass.internal.parser;

import java.io.Serializable;

import jsass.internal.Definition;

/**
 * Variable with an immutable name, a modifiable value and an optional flag
 * indicating whether it is guarded.
 * 
 * Guarded variables are not overwritten by further assignments to the same
 * variable (except possibly for assignment of the value null).
 */
public class Variable implements Definition, Serializable {

    private final String name;
    private SassListItem expr = null;
    private final boolean guarded;

    public Variable(String name, SassListItem expr) {
        this(name, expr, false);
    }

    public Variable(String name, SassListItem expr, boolean guarded) {
        this.name = name;
        this.expr = expr;
        this.guarded = guarded;
    }

    public SassListItem getExpr() {
        return expr;
    }

    public void setExpr(SassListItem expr) {
        this.expr = expr;
    }

    public String getName() {
        return name;
    }

    public boolean isGuarded() {
        return guarded;
    }

    public Variable copy() {
        return new Variable(name, expr, guarded);
    }

    /**
     * Replaces each occurrence of ${name} in the parameter. Only evaluates the
     * variable if at least one such occurrence, and then only evaluates it
     * once.
     */
    public String replaceInterpolation(String s) {
        final String interpolation = "#{$" + getName() + "}";
        if (s.contains(interpolation)) {
            return s.replace(interpolation, getExpr().unquotedString());
        } else {
            return s;
        }
    }

}
