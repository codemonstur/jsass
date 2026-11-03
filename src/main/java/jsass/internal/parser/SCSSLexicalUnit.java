package jsass.internal.parser;

import org.w3c.css.sac.LexicalUnit;

public interface SCSSLexicalUnit extends LexicalUnit {
    static final short SCSS_VARIABLE = 100;
    static final short SCSS_OPERATOR_LEFT_PAREN = 101;
    static final short SCSS_OPERATOR_RIGHT_PAREN = 102;
    static final short SCSS_OPERATOR_EQUALS = 103;
    static final short SCSS_OPERATOR_NOT_EQUAL = 104;
    static final short SCSS_OPERATOR_AND = 105;
    static final short SCSS_OPERATOR_OR = 106;
    static final short SCSS_OPERATOR_NOT = 107;
    static final short SCSS_NULL = 110;

    static final short SAC_LEM = 200;
    static final short SAC_REM = 201;

}
